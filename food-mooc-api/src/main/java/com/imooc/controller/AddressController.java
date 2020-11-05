package com.imooc.controller;


import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.AddressService;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "收货地址", tags = {"收货地址相关的接口"})
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "新增收货地址", notes = "用于新增收货地址的接口", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestBody UserAddress userAddress) {
        if (checkAddress(userAddress)!=null){
            return IMOOCJSONResult.errorMsg(checkAddress(userAddress));
        }
        addressService.add(userAddress);
        System.out.println("这是我提交的代码");
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询收货地址列表", notes = "用于查询收货地址列表的接口", httpMethod = "GET")
    @PostMapping("/list")
    public IMOOCJSONResult list(
        @RequestParam @ApiParam(value = "用户id",example = "linghu",required = true) String userId) {
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id为空");
        }
        List<UserAddress> addressList = addressService.list(userId);
        return IMOOCJSONResult.ok(addressList);
    }

    @ApiOperation(value = "更新收货地址", notes = "用于更新收货地址的接口", httpMethod = "PUT")
    @PostMapping("/update")
    public IMOOCJSONResult update(
        @RequestBody UserAddress userAddress) {
       if (checkAddress(userAddress)!=null){
           return IMOOCJSONResult.errorMsg(checkAddress(userAddress));
       }
       addressService.update(userAddress);
       return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "删除收货地址", notes = "用于删除收货地址的接口", httpMethod = "Delete")
    @PostMapping("/detele")
    public IMOOCJSONResult delete(
        @RequestParam String addressId,
        @RequestParam String userId) {
        if (StringUtils.isBlank(addressId)||StringUtils.isNotBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id或收货地址id为空");
        }
        addressService.delete(addressId,userId);
        return IMOOCJSONResult.ok();
    }
    private String checkAddress(UserAddress userAddress) {
        if (StringUtils.isBlank(userAddress.getUserId())){
            return "关联用id为空" ;
        }
        if (StringUtils.isBlank(userAddress.getReceiver())){
            return "收件人姓名为空" ;
        }
        if (StringUtils.isBlank(userAddress.getMobile())){
            return "收件人手机号为空" ;
        }
        if (StringUtils.isBlank(userAddress.getProvince())){
            return "省份为空" ;
        }
        if (StringUtils.isBlank(userAddress.getCity())){
            return "城市为空" ;
        }
        if (StringUtils.isBlank(userAddress.getDistrict())){
            return "区县为空" ;
        }
        if (StringUtils.isBlank(userAddress.getDistrict())){
            return "详细地址为空" ;
        }
        return null;
    }

}

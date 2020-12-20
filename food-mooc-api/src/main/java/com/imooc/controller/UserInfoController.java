package com.imooc.controller;

import com.imooc.common.MyConstant;
import com.imooc.common.MyConstant.pathSConstant;
import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.DateUtil;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
import com.imooc.config.FileProperties;
import com.imooc.config.UrlProperties;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserInfoBo;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UserInfoCotroller TODO
 *
 * @author linHu daXia
 * @date 2020/11/22 17:53
 */
@RestController
@RequestMapping("/userInfo")
@Api(value = "用户中心接口",tags = "用户中心接口")
public class UserInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private UrlProperties urlProperties;

    @PostMapping("/uploadFace")
    @ApiOperation(value = "上传用户头像的接口",notes = "上传用户头像接口",httpMethod = "POST")
    public IMOOCJSONResult updateUserFace(
        @RequestParam @ApiParam(value = "用户id",required = true,example = "200924GA7FBNTSCH") String userId,
        MultipartFile file,
        HttpServletRequest request,
        HttpServletResponse response){

        if (file!=null|| StringUtils.isNotBlank(file.getOriginalFilename())){
            //定义用户文件夹路径
            String dirPath= fileProperties.getImgUserFaceLocation()+ File.separator+userId + File.separator;
            //定义文件名称
            String[] split = file.getOriginalFilename().split("\\.");
            String suffix=split[split.length-1];//拿到后缀名
            if (!"jpeg".equalsIgnoreCase(suffix) &&!"jpg".equalsIgnoreCase(suffix)&&!"png".equalsIgnoreCase(suffix)){
                return IMOOCJSONResult.errorMsg("图片格式不正确");
            }
            String fileName="face"+userId+"."+suffix;
            //定义绝对路径
            String filePath=dirPath+fileName;
            //定义用户文件夹
            File newFile = new File(filePath);
            //创建用户文件夹
            newFile.getParentFile().mkdirs();
            //创建文件
            FileOutputStream fileOutputStream=null;
            try{
                InputStream inputStream = file.getInputStream();
                fileOutputStream = new FileOutputStream(newFile);
                IOUtils.copy(inputStream, fileOutputStream);
                // 获取访问改图片映射路径,更新用户表的头像字段,携带时间戳为路径参数,防止浏览器缓存
                String userFaceUrl = urlProperties.getFileUrlPathHeader()+"/"+userId+"/"+fileName + "?time=" +DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
                Users users = userService.updateUserFaceUrl(userFaceUrl, userId);
                // 更新cookie
                setNull(users);
                CookieUtils.setCookie(request,response,"user",JsonUtils.objectToJson(users),true);


            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (fileOutputStream!=null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else{
            return IMOOCJSONResult.errorMsg("无文件或文件名为空");
        }
        return IMOOCJSONResult.ok();
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息接口",notes = "更新用户信息接口",httpMethod = "POST")
    public IMOOCJSONResult updateUserInfo(
        @RequestParam @ApiParam(value = "用户id",required = true,example = "200924GA7FBNTSCH") String userId,
        @Valid @RequestBody UserInfoBo userInfoBo,
        BindingResult result,
        HttpServletRequest request,
        HttpServletResponse response){
        //验证参数
        Map<String, String> errorMap = getErrorMap(result);
        if (errorMap.size()>=1){
            return IMOOCJSONResult.errorMap(errorMap);
        }
        UserInfoVo userInfoVo=userCenterService.updateUserInfo(userInfoBo,userId,request,response);
        return IMOOCJSONResult.ok(userInfoVo);
    }
    private Map<String, String> getErrorMap(BindingResult result){
        HashMap<String, String> errorMessageResult = new HashMap<>();
        for (FieldError error:result.getFieldErrors()){
            errorMessageResult.put(error.getField(),error.getDefaultMessage());
        }
        return errorMessageResult;
    }

    private void setNull(Users userResult){
        userResult.setPassword(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setBirthday(null);
    }
}

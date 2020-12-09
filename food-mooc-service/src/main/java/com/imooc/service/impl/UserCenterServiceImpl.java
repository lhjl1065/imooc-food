package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.JsonUtils;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserInfoBo;
import com.imooc.pojo.vo.OrderVo;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserCenterServiceImpl 用户中心serviceImpl
 *
 * @author linHu daXia
 * @date 2020/11/15 22:17
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Override
    public UserInfoVo getUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        //为了用户信息不泄露,只提供前端需要的那部分数据
        BeanUtils.copyProperties(user, userInfoVo);
        return userInfoVo;
    }

    @Override
    public UserInfoVo updateUserInfo(UserInfoBo userInfoBo,String userId, HttpServletRequest request,
        HttpServletResponse response) {
        //更新数据库
        Users updateUsers = new Users();
        updateUsers.setId(userId);
        BeanUtils.copyProperties(userInfoBo,updateUsers);
        usersMapper.updateByPrimaryKeySelective(updateUsers);
        Users users = usersMapper.selectByPrimaryKey(userId);
        //刷新cookie
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(users,userInfoVo);
        return userInfoVo;
    }

    @Override
    public PagedGridResult queryUserOrder(Integer page, Integer pageSize, String userId, Integer status) {
        //分页查询
        PageHelper.startPage(page,pageSize);
        List<OrderVo> orders = ordersMapperCustom.getMyOrders(userId, status);
        return setterPagedGridResult(orders,page);

    }
    PagedGridResult setterPagedGridResult(List<?> list,Integer page){
        PageInfo pageList = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult;

    }


}

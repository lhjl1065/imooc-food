package com.imooc.service;

import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.UserInfoBo;
import com.imooc.pojo.vo.OrderVo;
import com.imooc.pojo.vo.UserInfoVo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户中心Service
 */
public interface UserCenterService {
    UserInfoVo getUserInfo(String userId);

    UserInfoVo updateUserInfo(UserInfoBo userInfoBo,String userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询用户的订单信息
     * @param userId
     * @param status
     * @return
     */
    PagedGridResult queryUserOrder(Integer page,Integer pageSize,String userId,Integer status);
}

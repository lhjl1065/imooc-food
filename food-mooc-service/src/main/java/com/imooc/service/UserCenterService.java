package com.imooc.service;

import com.imooc.pojo.bo.UserInfoBo;
import com.imooc.pojo.vo.UserInfoVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户中心Service
 */
public interface UserCenterService {
    UserInfoVo getUserInfo(String userId);

    UserInfoVo updateUserInfo(UserInfoBo userInfoBo,String userId, HttpServletRequest request, HttpServletResponse response);
}

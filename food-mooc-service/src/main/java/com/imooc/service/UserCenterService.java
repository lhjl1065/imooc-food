package com.imooc.service;

import com.imooc.pojo.vo.UserInfoVo;

/**
 * 用户中心Service
 */
public interface UserCenterService {
    UserInfoVo getUserInfo(String userId);
}

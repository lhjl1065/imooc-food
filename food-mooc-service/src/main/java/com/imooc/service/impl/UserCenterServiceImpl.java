package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
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

    @Override
    public UserInfoVo getUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        //为了用户信息不泄露,只提供前端需要的那部分数据
        BeanUtils.copyProperties(user, userInfoVo);
        return userInfoVo;
    }
}

package com.imooc.service.impl;

import com.imooc.common.enums.SexEnum;
import com.imooc.common.utils.DateUtil;
import com.imooc.common.utils.MD5Utils;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import java.util.Date;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    private final static String PICTURE_URL="http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String name){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);
        Users users = usersMapper.selectOneByExample(example);
        return users ==null?false:true;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(UserBo userBo){
        Users users = new Users();
        users.setId(sid.nextShort());
        users.setUsername(userBo.getUsername());
        users.setNickname(userBo.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setFace(PICTURE_URL);
        users.setSex(SexEnum.secret.getType_());
        users.setBirthday(DateUtil.stringToDate("1990-01-01"));
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
    }

    /**
     * 验证用户用户登录
     * @param userBo
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users login(UserBo userBo)  {
        String username=userBo.getUsername();
        String password = null;
        try {
            password = MD5Utils.getMD5Str(userBo.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        Users userResult = usersMapper.selectOneByExample(example);
        return userResult;
    }

    /**
     * 更新用户头像url
     * @param userFaceUrl
     * @param userId
     * @return
     */
    @Override
    public Users updateUserFaceUrl(String userFaceUrl, String userId) {
        Users users = new Users();
        users.setId(userId);
        users.setFace(userFaceUrl);
        usersMapper.updateByPrimaryKeySelective(users);
        return getUserInfo(userId);
    }

    @Override
    public Users getUserInfo(String userId) {
        Users users = new Users();
        users.setId(userId);
        return usersMapper.selectByPrimaryKey(userId);
    }


}

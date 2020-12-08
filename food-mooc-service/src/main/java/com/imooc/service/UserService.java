package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;

public interface UserService {
    boolean queryUsernameIsExist(String name);
    void createUser(UserBo userBo);
    Users login(UserBo userBo);
    Users updateUserFaceUrl(String userFaceUrl,String userId);
    Users getUserInfo(String userId);

}

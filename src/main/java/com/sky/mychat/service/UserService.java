package com.sky.mychat.service;

import com.sky.mychat.pojo.bean.User;
import com.sky.mychat.pojo.dto.LoginResult;
import com.sky.mychat.pojo.dto.RegisterAndLoginParam;

/**
 * @author tiankong
 */
public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username username
     * @return User
     */
    User getByUsername(String username);

    /**
     * 注册
     *
     * @param param param
     * @return LoginResult
     */
    LoginResult register(RegisterAndLoginParam param);

    /**
     * 登录
     *
     * @param param param
     * @return LoginResult
     */
    LoginResult login(RegisterAndLoginParam param);
}

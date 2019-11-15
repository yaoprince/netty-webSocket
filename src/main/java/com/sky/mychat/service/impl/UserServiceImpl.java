package com.sky.mychat.service.impl;

import com.sky.mychat.mapper.UserMapper;
import com.sky.mychat.pojo.bean.User;
import com.sky.mychat.pojo.dto.LoginResult;
import com.sky.mychat.pojo.dto.RegisterAndLoginParam;
import com.sky.mychat.service.UserService;
import com.sky.mychat.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public User getByUsername(String username) {
        return userMapper.selectOneByUsername(username);
    }

    @Override
    public LoginResult register(RegisterAndLoginParam param) {
        User data = getByUsername(param.getUsername());
        if (data != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setIcon("/test.png");
        user.setUsername(param.getUsername());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        userMapper.insert(user);
        param.setCheckPassword(false);
        return login(param);
    }

    @Override
    public LoginResult login(RegisterAndLoginParam param) {
        User user = getByUsername(param.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (param.isCheckPassword() && !passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误!");
        }
        LoginResult loginResult = new LoginResult();
        loginResult.setId(user.getId());
        loginResult.setUsername(param.getUsername());
        loginResult.setIcon(user.getIcon());
        loginResult.setToken(tokenHead + " " + jwtTokenUtil.generateToken(param.getUsername()));
        return loginResult;
    }
}

package me.tanyp.service.local.impl;

import me.tanyp.entity.User;
import me.tanyp.param.user.LoginParam;
import me.tanyp.param.user.RegisterParam;
import me.tanyp.service.local.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanyp on 2018/8/16
 */
@Service("me.tanyp.service.local.UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public User login(LoginParam login) {

        return null;
    }

    @Override
    public User register(RegisterParam register) {

        return null;
    }
}

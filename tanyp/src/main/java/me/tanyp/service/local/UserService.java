package me.tanyp.service.local;

import me.tanyp.entity.User;
import me.tanyp.param.user.LoginParam;
import me.tanyp.param.user.RegisterParam;

/**
 * Created by tanyp on 2018/8/16
 */
public interface UserService {

    User login(LoginParam login);

    User register(RegisterParam register);
}

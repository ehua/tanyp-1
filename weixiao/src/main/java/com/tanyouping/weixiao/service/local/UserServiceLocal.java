package com.tanyouping.weixiao.service.local;

import com.tanyouping.weixiao.domain.User;
import com.tanyouping.weixiao.param.LoginParam;
import com.tanyouping.weixiao.param.RegisterParam;

/**
 * Created by Tan Youping on 2018/1/9
 */
public interface UserServiceLocal {

    User login(LoginParam loginParam);

    void register(RegisterParam registerParam);

    void update(User user);

    void delete(Integer id);

    void logout(LoginParam loginParam);

    User getLoginRecord(Integer userId);
}

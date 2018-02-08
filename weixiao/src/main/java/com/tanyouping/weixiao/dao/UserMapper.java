package com.tanyouping.weixiao.dao;

import com.tanyouping.weixiao.domain.LoginRecord;
import com.tanyouping.weixiao.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Repository
public interface UserMapper {

    void create(User user);

    User get(String mobile);

    void delete(Integer userId);

    List<User> list(User user);

    void update(User user);

    User getMobile(String mobile);

    void saveLoginRecord(LoginRecord loginRecord);

    User getLoginRecord(Integer userId);
}

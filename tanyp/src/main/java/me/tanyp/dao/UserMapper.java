package me.tanyp.dao;

import me.tanyp.entity.LoginRecord;
import me.tanyp.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by tanyp on 2018/8/16
 */
@Repository
public interface UserMapper {

    void saveLoginRecord(LoginRecord loginRecord);

    User getEmail(String email);

    void create(User user);
}

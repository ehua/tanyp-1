package me.tanyp.service.local.impl;

import me.tanyp.controller.common.SystemException;
import me.tanyp.dao.UserMapper;
import me.tanyp.entity.LoginRecord;
import me.tanyp.entity.User;
import me.tanyp.param.user.LoginParam;
import me.tanyp.param.user.RegisterParam;
import me.tanyp.service.local.UserService;
import me.tanyp.util.RedisManager;
import me.tanyp.util.basic.DateUtils;
import me.tanyp.util.client.ClientInfoHolder;
import me.tanyp.util.md5.MD5Util;
import me.tanyp.util.basic.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tanyp on 2018/8/16
 */
@Service("me.tanyp.service.local.UserService")
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public User login(LoginParam login) {
        if (StringUtils.isEmpty(login.getEmail()) || StringUtils.isEmpty(login.getPwd())) {
            throw new SystemException("请输入完整登录信息");
        } else {
            User user = userMapper.getEmail(login.getEmail());
            if (user == null) {
                throw new SystemException("用户名或密码错误");
            } else {
                if (StringUtils.isEmpty(user.getPwd()) || StringUtils.isEmpty(user.getSalt())) {
                    throw new SystemException("用户信息异常");
                } else {
                    String[] encryptPasswordWithSalt = MD5Util.encryptPasswordWithSalt(login.getPwd(), user.getSalt());
                    if (!encryptPasswordWithSalt[0].equals(user.getPwd())) {
                        throw new SystemException("用户名或密码错误");
                    }
                    LoginRecord loginRecord = new LoginRecord();
                    loginRecord.init();
                    loginRecord.setUserId(user.getId());
                    loginRecord.setLoginIP(ClientInfoHolder.get().getIp());
                    loginRecord.setBrowser(ClientInfoHolder.get().getBrowser());
                    userMapper.saveLoginRecord(loginRecord);
                    logger.info("login success" + user.getId()+"，date"+ DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    return user;
                }
            }
        }
    }

    @Override
    public User register(RegisterParam register) {
        if (StringUtils.isNotEmpty(register.getCode()) &&
                StringUtils.isNotEmpty(register.getPwd()) && StringUtils.isNotEmpty(register.getEmail())) {
            String code = redisManager.get(String.class, register.getEmail());
            if (StringUtils.isNotEmpty(code)) {
                if (code.equals(register.getCode())) {
                    redisManager.delete(register.getEmail());
                    User user = userMapper.getEmail(register.getEmail());
                    if (user != null) {
                        throw new SystemException("邮箱已被注册，请直接登录！");
                    } else {
                        user = new User();
                        user.init();
                        String[] encryptPwd = MD5Util.encryptPasswordWithSalt(register.getPwd(), user.getId());
                        user.setPwd(encryptPwd[0]);
                        user.setSalt(encryptPwd[1]);
                        user.setEmail(register.getEmail());
                        user.setHeadPortrait("img/head.png");
                        userMapper.create(user);
                        return user;
                    }
                } else {
                    throw new SystemException("校验码校验失败，请确认您的邮箱！");
                }
            } else {
                throw new SystemException("无效校验码，请重新获取！");
            }
        } else {
            throw new SystemException("请输入完整的注册信息！");
        }
    }
}

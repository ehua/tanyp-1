package com.tanyouping.weixiao.service.local.impl;

import com.tanyouping.weixiao.dao.UserMapper;
import com.tanyouping.weixiao.domain.LoginRecord;
import com.tanyouping.weixiao.domain.User;
import com.tanyouping.weixiao.exception.BusinessException;
import com.tanyouping.weixiao.exception.SystemException;
import com.tanyouping.weixiao.param.LoginParam;
import com.tanyouping.weixiao.param.RegisterParam;
import com.tanyouping.weixiao.redis.RedisManager;
import com.tanyouping.weixiao.service.local.UserServiceLocal;
import com.tanyouping.weixiao.util.DateUtils;
import com.tanyouping.weixiao.util.MD5Util;
import com.tanyouping.weixiao.util.StringUtils;
import com.tanyouping.weixiao.web.client.ClientInfoHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Service("com.tanyouping.weixiao.service.local.UserServiceLocal")
@Transactional
public class UserServiceLocalImpl implements UserServiceLocal{

    private Logger logger = Logger.getLogger(UserServiceLocal.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public User login(LoginParam loginParam) {
        User user = userMapper.get(loginParam.getName());
        if (user == null)
            throw new SystemException("用户名or密码错误");
        String[] encryptPasswordWithSalt = MD5Util.encryptPasswordWithSalt(loginParam.getPwd(), user.getSalt());
        if (!encryptPasswordWithSalt[0].equals(user.getPwd()))
            throw new SystemException("用户名or密码错误");

        if (user.getStatus() != 1)
            throw new SystemException("账号已停用！");

        logger.info(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，登录成功！");
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.init();
        loginRecord.setUserId(user.getId());
        loginRecord.setLoginIP(ClientInfoHolder.get().getIp());
        userMapper.saveLoginRecord(loginRecord);
        logger.info(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，记录登录信息成功！");
        return user;
    }


    @Override
    public void register(RegisterParam register) {

        if (StringUtils.isEmpty(register.getMobile())
                || StringUtils.isEmpty(register.getName()) || StringUtils.isEmpty(register.getPwd()))
            throw new BusinessException("基本信息不允许为空！");

        User oldUser = userMapper.getMobile(register.getMobile());
        if (oldUser != null)
            throw new BusinessException("手机号已注册，可直接登录！");
        User user = new User();
        user.init();
        String[] encryptPwd = MD5Util.encryptPasswordWithSalt(register.getPwd() ,null);
        user.setPwd(encryptPwd[0]);
        user.setSalt(encryptPwd[1]);
        user.setName(register.getName());
        user.setMobile(register.getMobile());
        userMapper.create(user);
    }

    @Override
    public void update(User user) {if (user.getId() != null) userMapper.update(user);throw new BusinessException("参数不可忽略");
    }

    @Override
    public void delete(Integer id) { if (id != null) userMapper.delete(id); throw new BusinessException("参数不可忽略");}

    @Override
    public void logout(LoginParam loginParam) {

    }

    @Override
    public User getLoginRecord(Integer userId) {
        if (userId == null)
            return null;
        return userMapper.getLoginRecord(userId);
    }


}

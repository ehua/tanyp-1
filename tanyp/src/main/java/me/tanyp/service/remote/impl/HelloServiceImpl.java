package me.tanyp.service.remote.impl;

import me.tanyp.dao.HelloMapper;
import me.tanyp.param.HelloParam;
import me.tanyp.service.remote.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanyp on 2018/8/12
 */
@Service("me.tanyp.service.remote.HelloService")
@Transactional
public class HelloServiceImpl implements HelloService {

    @Autowired
    private HelloMapper helloMapper;

    @Override
    public void hello(HelloParam hello) {
        helloMapper.create(hello);
    }
}

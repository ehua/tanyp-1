package me.tanyp.service.local.impl;

import me.tanyp.param.HelloParam;
import me.tanyp.service.local.WorldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanyp on 2018/8/13
 */
@Service("me.tanyp.service.local.WorldService")
@Transactional
public class WorldServiceImpl implements WorldService {
    @Override
    public void hello(HelloParam hello) {

    }
}

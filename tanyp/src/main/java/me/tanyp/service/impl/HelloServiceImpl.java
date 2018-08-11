package me.tanyp.service.impl;

import me.tanyp.service.HelloService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanyp on 2018/8/12
 */
@Service("me.tanyp.service.impl.HelloService")
@Transactional
public class HelloServiceImpl implements HelloService {
}

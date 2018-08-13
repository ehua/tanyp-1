package me.tanyp.controller;

import me.tanyp.param.HelloParam;
import me.tanyp.service.local.WorldService;
import me.tanyp.util.RedisManager;
import me.tanyp.util.SpringServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tanyp on 2018/8/12
 */
@RestController
public class HelloController {

    @Autowired
    private WorldService worldService;

    @PostMapping("/hello")
    public void hello(@RequestBody HelloParam hello){
        RedisManager redisManager = SpringServiceFactory.get().getService(RedisManager.class);
//        redisManager.put("tanyp","i.tanyp@qq.com");
        System.out.println(redisManager.get(String.class,"tanyp"));
        redisManager.delete("tanyp");
        System.out.println(redisManager);
    }
    @GetMapping("/world")
    public void world(){
        System.out.println("world");
    }
}

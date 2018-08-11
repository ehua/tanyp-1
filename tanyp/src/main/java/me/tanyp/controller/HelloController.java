package me.tanyp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tanyp on 2018/8/12
 */
@RestController
public class HelloController {

    @PostMapping("/hello")
    public void hello(){
        System.out.println("hello");
    }
    @GetMapping("/world")
    public void world(){
        System.out.println("world");
    }
}

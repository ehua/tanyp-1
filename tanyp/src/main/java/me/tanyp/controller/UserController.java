package me.tanyp.controller;

import me.tanyp.entity.User;
import me.tanyp.json.JSONResultModel;
import me.tanyp.param.user.LoginParam;
import me.tanyp.param.user.RegisterParam;
import me.tanyp.service.local.UserService;
import me.tanyp.util.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tanyp on 2018/8/16
 */
@Controller
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisManager redisManager;

    @RequestMapping("/login")
    @PostMapping
    public JSONResultModel<Object> login(@RequestBody LoginParam login){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userService.login(login);
//        redisManager.put();
        return resultModel;
    }

    @RequestMapping("/register")
    @PostMapping
    public JSONResultModel<Object> register(@RequestBody RegisterParam register){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userService.register(register);
        return resultModel;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JSONResultModel<Object> logout(){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        userManager.logout();
        return resultModel;
    }


}

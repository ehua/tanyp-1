package me.tanyp.controller;

import me.tanyp.entity.User;
import me.tanyp.json.JSONResultModel;
import me.tanyp.param.user.LoginParam;
import me.tanyp.param.user.RegisterParam;
import me.tanyp.service.local.UserService;
import me.tanyp.util.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tanyp on 2018/8/16
 */
@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @PostMapping("/login")
    public JSONResultModel<Object> login(@RequestBody LoginParam login) throws Exception {
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userService.login(login);
        userManager.saveUser(user,true);
        resultModel.setData(user);
        return resultModel;
    }

    @PostMapping("/register")
    public JSONResultModel<Object> register(@RequestBody RegisterParam register){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userService.register(register);
        return resultModel;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JSONResultModel<Object> logout(){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        userManager.logout();
        return resultModel;
    }


}

package com.tanyouping.weixiao.controller;

import com.tanyouping.weixiao.domain.User;
import com.tanyouping.weixiao.json.JSONResultModel;
import com.tanyouping.weixiao.param.LoginParam;
import com.tanyouping.weixiao.param.RegisterParam;
import com.tanyouping.weixiao.security.UserManager;
import com.tanyouping.weixiao.service.local.UserServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Controller
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserServiceLocal userServiceLocal;

    @Autowired
    private UserManager userManager;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResultModel<Object> login(@RequestBody LoginParam loginParam) throws Exception {
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userServiceLocal.login(loginParam);
        userManager.saveUser(user,loginParam.isAgainLogin());
        return resultModel;
    }

    @RequestMapping("/loginRecord/get")
    @ResponseBody
    public JSONResultModel<Object> loginRecordGet(){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        User user = userServiceLocal.getLoginRecord(userManager.getCurrentUser().getId());
        resultModel.setData(user);
        return resultModel;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JSONResultModel<Object> logout(){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        userManager.logout();
        return resultModel;
    }

    @RequestMapping("/register")
    @ResponseBody
    public JSONResultModel<Object> register(@RequestBody RegisterParam registerParam){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        userServiceLocal.register(registerParam);
        return resultModel;
    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONResultModel<Object> update(User user){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        userServiceLocal.update(user);
        return resultModel;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public JSONResultModel<Object> delete(Integer id){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        userServiceLocal.delete(id);
        return resultModel;
    }




}

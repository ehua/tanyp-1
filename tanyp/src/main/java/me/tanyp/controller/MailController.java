package me.tanyp.controller;

import me.tanyp.json.JSONResultModel;
import me.tanyp.util.RedisManager;
import me.tanyp.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tanyp on 2018/8/22
 */
@RestController
public class MailController {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private TaskExecutor taskExecutor;

    @PostMapping("/send/mail")
    private JSONResultModel<Object> sendMail(String email) {
        SendMail sendMail = new SendMail();
        sendMail.sendMail(email);
        return new JSONResultModel<>();
    }

    @PostMapping("/mail/check")
    private JSONResultModel<Object> check(String email) {
        String code = redisManager.get(String.class, email);
        System.out.println(code);
        return new JSONResultModel<>();
    }
}

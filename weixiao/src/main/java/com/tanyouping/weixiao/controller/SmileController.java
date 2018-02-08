package com.tanyouping.weixiao.controller;

import com.tanyouping.weixiao.redis.RedisManager;
import com.tanyouping.weixiao.service.local.SmileServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Controller
public class SmileController {


    @Autowired
    private SmileServiceLocal smileServiceLocal;

    @Autowired
    private RedisManager redisManager;

//    @RequestMapping(value = "/smile/{id}",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONResultModel<Object> get(@PathVariable("id") Integer id){
//        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        return resultModel;
//    }
//
//    @RequestMapping(value = "/smile",method = RequestMethod.POST)
//    @ResponseBody
//    public JSONResultModel<Object> save(@RequestBody Blog blog){
//        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        smileServiceLocal.save(blog);
//        return resultModel;
//    }
//
//    @RequestMapping(value = "/smile",method = RequestMethod.PUT)
//    @ResponseBody
//    public JSONResultModel<Object> update(@RequestBody Blog blog){
//        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        smileServiceLocal.update(blog);
//        return resultModel;
//    }
//
//    @RequestMapping(value = "/smile",method = RequestMethod.PATCH)
//    @ResponseBody
//    public JSONResultModel<Object> modify(@RequestBody Blog blog){
//        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        smileServiceLocal.modify(blog);
//        return resultModel;
//    }
//
//    @RequestMapping(value = "/smile",method = RequestMethod.DELETE)
//    @ResponseBody
//    public JSONResultModel<Object> delete(@RequestBody Integer id){
//        JSONResultModel<Object> resultModel = new JSONResultModel<>();
//        smileServiceLocal.delete(id);
//        return resultModel;
//    }

}

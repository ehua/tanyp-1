package com.tanyouping.weixiao.controller;

import com.tanyouping.weixiao.dto.MoodDto;
import com.tanyouping.weixiao.json.JSONResultModel;
import com.tanyouping.weixiao.param.CommentParam;
import com.tanyouping.weixiao.param.QueryParam;
import com.tanyouping.weixiao.param.ReleaseMoodParam;
import com.tanyouping.weixiao.service.local.MoodServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Controller
public class MoodController {


    @Autowired
    private MoodServiceLocal moodServiceLocal;

    @RequestMapping("/release")
    @ResponseBody
    public JSONResultModel<Object> Release(@RequestBody ReleaseMoodParam release){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        moodServiceLocal.release(release);
        return resultModel;
    }


    @RequestMapping("/comment")
    @ResponseBody
    public JSONResultModel<Object> Comment(@RequestBody CommentParam comment){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        moodServiceLocal.comment(comment);
        return resultModel;
    }


    @RequestMapping("/mood/list")
    @ResponseBody
    public JSONResultModel<Object> ListMood(@RequestBody QueryParam queryParam){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        List<MoodDto> moods = moodServiceLocal.listMood(queryParam);
        resultModel.setData(moods);
        return resultModel;

    }


}

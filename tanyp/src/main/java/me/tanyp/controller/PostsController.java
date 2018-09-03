package me.tanyp.controller;

import me.tanyp.json.JSONResultModel;
import me.tanyp.param.posts.DeletePosts;
import me.tanyp.param.posts.ReleasePosts;
import me.tanyp.param.posts.ReportPosts;
import me.tanyp.service.local.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tanyp on 2018/9/2
 */
@RestController
public class PostsController {

    @Autowired
    private PostsService postsService;

    @PostMapping("/release")
    public JSONResultModel<Object> release(@RequestBody ReleasePosts release){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        postsService.release(release);
        return resultModel;
    }

    @PostMapping("/delete")
    public JSONResultModel<Object> delete(@RequestBody DeletePosts delete){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        postsService.delete(delete);
        return resultModel;
    }

    @PostMapping("/report")
    public JSONResultModel<Object> report(@RequestBody ReportPosts report){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        postsService.report(report);
        return resultModel;
    }
}

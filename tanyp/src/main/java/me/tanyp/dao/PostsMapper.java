package me.tanyp.dao;

import me.tanyp.entity.Posts;
import me.tanyp.param.posts.DeletePosts;
import me.tanyp.param.posts.ReportPosts;

/**
 * Created by tanyp on 2018/9/2
 */
public interface PostsMapper {

    void release(Posts posts);

    void delete(DeletePosts delete);

    void report(ReportPosts report);
}

package me.tanyp.service.local;

import me.tanyp.param.posts.DeletePosts;
import me.tanyp.param.posts.ReleasePosts;
import me.tanyp.param.posts.ReportPosts;

/**
 * Created by tanyp on 2018/9/2
 */
public interface PostsService {

    void release(ReleasePosts release);

    void delete(DeletePosts delete);

    void report(ReportPosts report);
}

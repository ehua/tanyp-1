package me.tanyp.service.local.impl;

import me.tanyp.dao.PostsMapper;
import me.tanyp.entity.Posts;
import me.tanyp.param.posts.DeletePosts;
import me.tanyp.param.posts.ReleasePosts;
import me.tanyp.param.posts.ReportPosts;
import me.tanyp.service.local.PostsService;
import me.tanyp.util.basic.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanyp on 2018/9/2
 */
@Service("me.tanyp.service.local.PostsService")
@Transactional
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsMapper postsMapper;

    @Override
    public void release(ReleasePosts release) {
        Posts posts = new Posts();
        posts.init();
        posts.setContent(release.getContent());
        posts.setMedias(release.getMedias());
        postsMapper.release(posts);
    }

    @Override
    public void delete(DeletePosts delete) {
        if (StringUtils.isNotEmpty(delete.getPostsId())){
            postsMapper.delete(delete);
        }
    }

    @Override
    public void report(ReportPosts report) {
        if (StringUtils.isNotEmpty(report.getPostsId()) && StringUtils.isNotEmpty(report.getRemark())){
            postsMapper.report(report);
        }
    }
}

package com.tanyouping.weixiao.service.local.impl;

import com.tanyouping.weixiao.dao.MoodMapper;
import com.tanyouping.weixiao.domain.Comment;
import com.tanyouping.weixiao.domain.Mood;
import com.tanyouping.weixiao.dto.MoodDto;
import com.tanyouping.weixiao.param.CommentParam;
import com.tanyouping.weixiao.param.QueryParam;
import com.tanyouping.weixiao.param.ReleaseMoodParam;
import com.tanyouping.weixiao.service.local.MoodServiceLocal;
import com.tanyouping.weixiao.util.DateUtils;
import com.tanyouping.weixiao.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Service("com.tanyouping.weixiao.service.local.MoodServiceLocal")
@Transactional
public class MoodServiceLocalImpl implements MoodServiceLocal{

    private Logger logger = Logger.getLogger(MoodServiceLocal.class);

    @Autowired
    private MoodMapper moodMapper;

    @Override
    public void release(ReleaseMoodParam release) {
        Mood mood = new Mood();
        mood.init();
        if (release.getClassify() == null)
            return;
        if (release.getClassify() == 2){
            if (StringUtils.isEmpty(release.getTitle()))
                return;
            mood.setClassify(2);
            mood.setTitle(release.getTitle());
        }
        try {
            mood.setText(release.getText().getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            logger.error(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，不支持的编码转换");
        }
        moodMapper.create(mood);
        logger.info(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，发布心情成功");
    }

    @Override
    public void comment(CommentParam param) {
        Comment comment = new Comment();
        comment.init();
        try {
            comment.setText(param.getText().getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            logger.error(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，不支持编码异常");
        }
        moodMapper.saveComment(comment);
        logger.info(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，评论心情维护成功");
    }

    @Override
    public List<MoodDto> listMood(QueryParam queryParam) {
        if (queryParam.getStart() == null) queryParam.setStart(0);
        if (queryParam.getLimit() == null) queryParam.setLimit(10);
        if (queryParam.getLimit() > 10) queryParam.setLimit(10);
        List<Mood> moods = moodMapper.listMood(queryParam);
        List<MoodDto> moodDtos = new ArrayList<>();
        try {
            for (int i = 0; i < moods.size(); i++) {
                MoodDto mood = new MoodDto();
                mood.setClassify(moods.get(i).getClassify());
                mood.setHates(moods.get(i).getHates());
                mood.setLikes(moods.get(i).getLikes());
                mood.setReads(moods.get(i).getReads());
                mood.setText(new String(moods.get(i).getText(),"UTF-8"));
                mood.setTitle(moods.get(i).getTitle());
                mood.setTags(moods.get(i).getTags());
                moodDtos.add(mood);
            }
        }catch (UnsupportedEncodingException e){
            logger.error(DateUtils.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss")+"，不支持的编码转换");
        }
        return moodDtos;
    }



}

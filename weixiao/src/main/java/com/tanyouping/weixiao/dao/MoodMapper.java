package com.tanyouping.weixiao.dao;

import com.tanyouping.weixiao.domain.Comment;
import com.tanyouping.weixiao.domain.Mood;
import com.tanyouping.weixiao.domain.MoodRS;
import com.tanyouping.weixiao.param.QueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tan Youping on 2018/1/10
 */
@Repository
public interface MoodMapper {

    Integer create(Mood mood);

    List<Mood> listMood(QueryParam queryParam);

    void delete(Integer id);

    Mood get(Integer id);

    void createMoodRS(MoodRS moodRS);

    void saveComment(Comment comment);
}

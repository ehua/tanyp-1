package com.tanyouping.weixiao.service.local;

import com.tanyouping.weixiao.dto.MoodDto;
import com.tanyouping.weixiao.param.CommentParam;
import com.tanyouping.weixiao.param.QueryParam;
import com.tanyouping.weixiao.param.ReleaseMoodParam;

import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 */
public interface MoodServiceLocal {

    void release(ReleaseMoodParam release);

    void comment(CommentParam comment);

    List<MoodDto> listMood(QueryParam queryParam);
}

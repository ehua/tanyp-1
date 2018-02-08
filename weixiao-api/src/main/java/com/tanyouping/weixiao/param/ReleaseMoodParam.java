package com.tanyouping.weixiao.param;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class ReleaseMoodParam extends UpdateParam{

    //1.文本无标题，日志有标题
    private String title;
    private String text;
    //1. 文本 2.日志
    private Integer classify;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }
}

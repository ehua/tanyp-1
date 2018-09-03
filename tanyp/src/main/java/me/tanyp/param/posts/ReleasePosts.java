package me.tanyp.param.posts;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tanyp on 2018/9/2
 */
public class ReleasePosts implements Serializable {

    private String content;
    private List<String> medias;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }
}

package com.tanyouping.weixiao.domain.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class BaseEntity implements Serializable{

    private Integer id;
    private Date created;
    private Long createdMillisecond;

    public void init(){
        created = new Date();
        createdMillisecond = new Date().getTime();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getCreatedMillisecond() {
        return createdMillisecond;
    }

    public void setCreatedMillisecond(Long createdMillisecond) {
        this.createdMillisecond = createdMillisecond;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

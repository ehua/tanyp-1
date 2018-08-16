package me.tanyp.entity.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tanyp on 2018/8/16
 */
public class BaseEntity implements Serializable {

    private String id;
    private Date created;
    private Long createdMillisecond;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

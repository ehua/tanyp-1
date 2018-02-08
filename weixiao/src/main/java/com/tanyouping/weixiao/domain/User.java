package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 * 用户
 */
public class User extends BaseEntity {

    //用户名
    private String name;
    //密码
    private String pwd;
    private String salt;
    //联系方式
    private String mobile;
    //邮箱
    private String email;
    //性别 0：女 1：男
    private Integer sex;
    //生日
    private LocalDate birthday;
    //账号状态 1：正常 2：休眠 3：封号
    private Integer status;
    //违规次数
    private Integer illegal;
    //创建时间
    private String remark;

    private List<LoginRecord> loginRecords = new ArrayList<>();

    public void init(){
        status = 1;
        illegal = 0;
        super.init();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIllegal() {
        return illegal;
    }

    public void setIllegal(Integer illegal) {
        this.illegal = illegal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<LoginRecord> getLoginRecords() {
        return loginRecords;
    }

    public void setLoginRecords(List<LoginRecord> loginRecords) {
        this.loginRecords = loginRecords;
    }
}

package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String username;
    private String workerId;
    private String password;
    private int role;
    private Date createTime;
    private int status;
    private String wxOpenId;
}

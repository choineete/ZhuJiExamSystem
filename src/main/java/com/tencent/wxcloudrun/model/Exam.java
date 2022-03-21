package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class Exam {
    private int id;
    private String title;
    private int duration;
    private Date startTime;
    private Date endTime;
    private String createUser;
    private int questionCount;
}

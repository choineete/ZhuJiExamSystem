package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.Date;
@Data
public class Question {
    private int id;
    private int questionType;
    private int score;
    private int correct;
    private String stem;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Date createTime;
    private int status;
}

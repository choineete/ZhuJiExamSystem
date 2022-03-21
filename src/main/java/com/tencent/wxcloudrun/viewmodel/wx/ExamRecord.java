package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import java.util.Date;

@Data
public class ExamRecord {
    private int id;
    private int examId;
    private int examPaperId;
    private int userId;
    private int questionId;
    private int isRight;
    private Date createTime;
    private int answer;
}

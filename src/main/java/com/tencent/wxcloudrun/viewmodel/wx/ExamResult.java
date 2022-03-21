package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import java.util.Date;

@Data
public class ExamResult {
    private int id;
    private int examId;
    private int examPaperId;
    private int userId;
    private int correctCount;
    private int totalCount;
    private int score;
    private Date createTime;
}

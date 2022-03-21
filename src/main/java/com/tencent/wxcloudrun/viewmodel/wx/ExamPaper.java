package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import java.util.Date;

@Data
public class ExamPaper {
    private int id;
    private int examId;
    private int userId;
    private String content;
    private Date createTime;

}

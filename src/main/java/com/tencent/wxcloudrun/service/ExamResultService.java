package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;

import java.util.List;

public interface ExamResultService {

    //查询用户全部考试机里
    List<ExamResult> getAllExamResultByUserId(int userId);

    //查询用户某场考试的记录
    ExamResult getExamResult(int examId, int userId);

    //查询某场考试全部用户的成绩
    List<ExamResult> getAllExamResultInOneExam(int examId);

    //填充result
    int setExamResult(ExamResult examResult);

}

package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;

import java.util.List;

public interface ExamPaperService {
    //生成试卷
    List<Integer> generateExamPaper(Exam exam);

    //插入生成的试卷
    int setExamPaper(ExamPaper examPaper);

    //获取试卷
    ExamPaper getExamPaperById(int examPaperId);

    //通过考试id和用户id获取试卷
    ExamPaper getExamPaperByExamIdAndUserId(int examId,int userId);
}

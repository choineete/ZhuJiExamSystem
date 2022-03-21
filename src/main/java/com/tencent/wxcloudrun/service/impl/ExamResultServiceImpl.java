package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ExamResultMapper;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamResultServiceImpl implements ExamResultService {
    @Autowired
    ExamResultMapper examResultMapper;


    @Override
    public List<ExamResult> getAllExamResultByUserId(int userId) {

        List<ExamResult> examResultList = examResultMapper.selectExamResultByUserId(userId);

        return examResultList;

    }

    @Override
    public ExamResult getExamResult(int examId, int userId) {
        return examResultMapper.selectExamResultByExamIdAndUserId(examId,userId);
    }

    @Override
    public List<ExamResult> getAllExamResultInOneExam(int examId) {
        return examResultMapper.selectAllExamResultInOneExam(examId);
    }

    @Override
    public int setExamResult(ExamResult examResult) {
        return examResultMapper.insertExamResult(examResult);
    }
}

package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ExamRecordMapper;
import com.tencent.wxcloudrun.service.ExamRecordService;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamRecordServiceImpl implements ExamRecordService {
    @Autowired
    ExamRecordMapper examRecordMapper;

    @Override
    public List<ExamRecord> findExamRecordByUserIdAndExamId(int userId, int examId) {
        return examRecordMapper.selectExamRecordByUserIdAndExamId(userId,examId);
    }

    @Override
    public int setExamRecord(ExamRecord examRecord) {
        return examRecordMapper.insertExamRecord(examRecord);
    }

    @Override
    public ExamRecord findExamRecord(int examPaperId, int questionId, int userId) {
        return examRecordMapper.selectExamRecord(examPaperId,questionId,userId);
    }

    @Override
    public int updateExamRecord(ExamRecord examRecord) {
        return examRecordMapper.updateExamRecord(examRecord);
    }
}

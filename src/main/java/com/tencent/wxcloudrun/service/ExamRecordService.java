package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;

import java.util.List;

public interface ExamRecordService {
    //获取用户某场考试的答题记录
    List<ExamRecord> findExamRecordByUserIdAndExamId(int userId, int examId);

    //插入答题记录
    int setExamRecord(ExamRecord examRecord);

    //查找某条考试记录
    ExamRecord findExamRecord(int examPaperId,int questionId,int userId);

    //更新某条考试记录
    int updateExamRecord(ExamRecord examRecord);


}

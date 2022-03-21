package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;

import java.util.List;

public interface RecordAndResultService {

    //做答题记录
    int recordAndMakeResult(List<Integer> answerList, ExamPaper examPaper);
}

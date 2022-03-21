package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ExamRecordMapper;
import com.tencent.wxcloudrun.dao.ExamResultMapper;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.service.QuestionService;
import com.tencent.wxcloudrun.service.RecordAndResultService;
import com.tencent.wxcloudrun.util.JsonUtil;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;
import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordAndResultImpl implements RecordAndResultService {

    @Autowired
    ExamRecordMapper examRecordMapper;
    @Autowired
    ExamResultMapper examResultMapper;
    @Autowired
    QuestionService questionService;

    @Override
    public int recordAndMakeResult(List<Integer> answerList, ExamPaper examPaper) {

        int correctCount = 0;
        int totalScore = 0;

        List<Integer> questionList = JsonUtil.toJsonListObject(examPaper.getContent(),List.class);

        for (int i = 0; i < answerList.size(); i++){

            Question question = questionService.findQuestionById(questionList.get(i));

            ExamRecord record = new ExamRecord();

            record.setExamId(examPaper.getExamId());
            record.setExamPaperId(examPaper.getId());
            record.setUserId(examPaper.getUserId());
            record.setQuestionId(question.getId());
            record.setAnswer(answerList.get(i));

            //判题
            if (answerList.get(i) == question.getCorrect()){
                //设置正确
                record.setIsRight(1);
                //正确数量+1
                correctCount++;
                //总分加上该题分数
                totalScore += question.getScore();
            }
            else{
                //设置错误
                record.setIsRight(2);
            }
            //插入答题记录
            examRecordMapper.insertExamRecord(record);
        }


        //所有记录插入完毕之后，makeResult

        ExamResult result = new ExamResult();

        result.setExamId(examPaper.getExamId());
        result.setExamPaperId(examPaper.getId());
        result.setUserId(examPaper.getUserId());
        result.setCreateTime(new Date());
        result.setTotalCount(questionList.size());
        //设置正确数量和总分
        result.setCorrectCount(correctCount);
        result.setScore(totalScore);

        //插入到考试记录表中
        examResultMapper.insertExamResult(result);

        return totalScore;

    }
}

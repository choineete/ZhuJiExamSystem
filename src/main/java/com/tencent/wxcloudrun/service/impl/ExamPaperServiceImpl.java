package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ExamPaperMapper;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.service.ExamPaperService;
import com.tencent.wxcloudrun.service.QuestionService;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamPaperServiceImpl implements ExamPaperService, ConstantUtil {

    @Autowired
    ExamPaperMapper examPaperMapper;
    @Autowired
    QuestionService questionService;

    public int addExamPaper(ExamPaper examPaper) {

        return examPaperMapper.insertExamPaper(examPaper);

    }

    @Override
    public List<Integer> generateExamPaper(Exam exam) {

        //题目总数
        int count = exam.getQuestionCount();
        //选择题数目
        int multipleChoiceCount = (int) (count * QUESTION_MULTIPLE_CHOICE_RATE);
        //判断题数目
        int TorFCount = (int) (count * QUESTION_TorF_RATE);

        //选择题，题目集
        Set<Integer> multipleChoiceQuestionSet = questionService.findQuestionIdByType(QUESTION_TYPE_MULTIPLE_CHOICE);
        List<Integer> ids = new ArrayList<>(multipleChoiceQuestionSet);
        Collections.shuffle(ids);

        //判断题题，题目集
        Set<Integer> TorFQuestionSet = questionService.findQuestionIdByType(QUESTION_TYPE_TorF);
        List<Integer> ids1 = new ArrayList<>(TorFQuestionSet);
        Collections.shuffle(ids1);

        if (ids.size() < multipleChoiceCount || ids1.size() < TorFCount){
            throw new RuntimeException("题目数量不够");
        }

        //先将选择题加入题目集
        List<Integer> questionSet = ids.subList(0,multipleChoiceCount);

        //再将判断题题加入题目集
        List<Integer> questionSet1 = ids1.subList(0,TorFCount);

        questionSet.addAll(questionSet1);


        return questionSet;

    }

    @Override
    public int setExamPaper(ExamPaper examPaper) {
        return examPaperMapper.insertExamPaper(examPaper);
    }

    @Override
    public ExamPaper getExamPaperById(int examPaperId) {
        return examPaperMapper.selectExamPaperById(examPaperId);
    }

    @Override
    public ExamPaper getExamPaperByExamIdAndUserId(int examId, int userId) {
        return examPaperMapper.selectExamPaperByExamIdAndUserId(examId,userId);
    }
}

package com.tencent.wxcloudrun.controller.wx.another;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.*;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.util.JsonUtil;
import com.tencent.wxcloudrun.viewmodel.wx.AnswerInfo;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;
import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@ResponseBody
@RequestMapping("/api/wx")
public class SwitchAndSubmitController extends BaseApiWXController implements ConstantUtil {

    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamRecordService examRecordService;
    @Autowired
    RecordAndResultService recordAndResultService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ExamService examService;
    @Autowired
    ExamResultService examResultService;

    //切换题目，上一题下一题功能集成
    @PostMapping("/switchQuestion")
    public RestResponse switchQuestion(int examPaperId,int currentQuestionId,int answer,int switchType){

        ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);


        //做记录
        ExamRecord currentExamRecord = examRecordService.findExamRecord(examPaper.getId(),
                currentQuestionId,getCurrentUser().getId());
        //获取当前问题的正确答案
        int correct = questionService.findQuestionById(currentQuestionId).getCorrect();


        //如果已有答题记录，则采用更新策略
        if (currentExamRecord != null) {
            //如果之前已经作答,且当前未作答，则无需改变作答记录
            //如果之前未作答，或者当前已作答，需要进入更新流程
            if (currentExamRecord.getAnswer() == 0 || answer != 0){
                //答题记录改变
                if (answer != currentExamRecord.getAnswer()){
                    currentExamRecord.setAnswer(answer);
                    currentExamRecord.setIsRight(answer == correct ? 1:2);
                    currentExamRecord.setCreateTime(new Date());

                    examRecordService.updateExamRecord(currentExamRecord);
                }
            }
        }

        //如果没有答题记录，需要插入
        else {
            ExamRecord record = new ExamRecord();
            //用户是否作答，未作答即answer为0
            record.setAnswer(answer);
            record.setQuestionId(currentQuestionId);

            //回答正确设置为1，错误设置为2
            if (correct == answer) {
                record.setIsRight(1);
            } else {
                record.setIsRight(2);
            }
            record.setCreateTime(new Date());
            record.setExamId(examPaper.getExamId());
            record.setExamPaperId(examPaper.getId());
            record.setUserId(getCurrentUser().getId());

            examRecordService.setExamRecord(record);
        }

        //找出下一题or上一题

        String contentString = examPaper.getContent();

        List<Integer> content = JsonUtil.toJsonObject(contentString,List.class);

        //下一题or上一题的id
        int questionId;
        int questionIndex;

        if (switchType == PREVIOUS_QUESTION){
            questionIndex = content.indexOf(currentQuestionId) - 1;
        }
        //(switchType == NEXT_QUESTION)
        else{
            questionIndex = content.indexOf(currentQuestionId) + 1;
        }
        questionId = content.get(questionIndex);

        Question question = questionService.findQuestionById(questionId);

        ExamRecord examRecord = examRecordService.findExamRecord(examPaper.getId(),question.getId(),getCurrentUser().getId());
        //将需要返回的题目和其对应的答题记录封装并返回
        Map<String,Object> questionAndRecord = new HashMap<>();
        questionAndRecord.put("question",question);
        questionAndRecord.put("record",examRecord);

        //返回下一题or上一题
        return RestResponse.ok(questionAndRecord);
    }

    //另一套逻辑的提交功能，提交之后，生成Result
    @PostMapping("/submit")
    public RestResponse submit(int examPaperId){

        ExamPaper paper = examPaperService.getExamPaperById(examPaperId);
        Exam exam =  examService.getExamById(paper.getExamId());

        User user = getCurrentUser();

        ExamResult existrResult = examResultService.getExamResult(exam.getId(), user.getId());

        if( existrResult != null ){
            return RestResponse.fail(2,"请勿重复提交");
        }

        //获取某用户在某场考试中全部的考试记录
        List<ExamRecord> recordList = examRecordService.findExamRecordByUserIdAndExamId(user.getId(), paper.getExamId());
        //遍历，计分，生成result
        ExamResult result = new ExamResult();
        int totalScore = 0;
        int correctCount = 0;
        for (ExamRecord record : recordList){

            Question question = questionService.findQuestionById(record.getQuestionId());

            if (record.getIsRight() == 1){
                totalScore += question.getScore();
                correctCount++;
            }

        }
        //设置好result，装入数据库
        result.setScore(totalScore);
        result.setTotalCount(exam.getQuestionCount());
        result.setExamId(exam.getId());
        result.setCorrectCount(correctCount);
        result.setExamPaperId(paper.getId());
        result.setUserId(user.getId());
        result.setCreateTime(new Date());

        examResultService.setExamResult(result);

        return RestResponse.ok(totalScore);
    }

    @PostMapping("/submitQuestion")
    public RestResponse submitQuestion(int examPaperId,int currentQuestionId,int answer){
        ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);

        //做记录
        ExamRecord currentExamRecord = examRecordService.findExamRecord(examPaper.getId(),
                currentQuestionId,getCurrentUser().getId());
        //获取当前问题的正确答案
        int correct = questionService.findQuestionById(currentQuestionId).getCorrect();


        //如果已有答题记录，则采用更新策略
        if (currentExamRecord != null) {
            //如果之前已经作答,且当前未作答，则无需改变作答记录
            //如果之前未作答，或者当前已作答，需要进入更新流程
            if (currentExamRecord.getAnswer() == 0 || answer != 0){
                //答题记录改变
                if (answer != currentExamRecord.getAnswer()){
                    currentExamRecord.setAnswer(answer);
                    currentExamRecord.setIsRight(answer == correct ? 1:2);
                    currentExamRecord.setCreateTime(new Date());

                    examRecordService.updateExamRecord(currentExamRecord);
                }
            }
        }

        //如果没有答题记录，需要插入
        else {
            ExamRecord record = new ExamRecord();
            //用户是否作答，未作答即answer为0
            record.setAnswer(answer);
            record.setQuestionId(currentQuestionId);

            //回答正确设置为1，错误设置为2
            if (correct == answer) {
                record.setIsRight(1);
            } else {
                record.setIsRight(2);
            }
            record.setCreateTime(new Date());
            record.setExamId(examPaper.getExamId());
            record.setExamPaperId(examPaper.getId());
            record.setUserId(getCurrentUser().getId());

            examRecordService.setExamRecord(record);
        }
        return RestResponse.ok();
    }


}
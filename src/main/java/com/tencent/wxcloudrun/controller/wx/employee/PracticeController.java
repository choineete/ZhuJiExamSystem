package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.Wrong;
import com.tencent.wxcloudrun.service.QuestionService;
import com.tencent.wxcloudrun.service.WrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/wx")
@ResponseBody
public class PracticeController extends BaseApiWXController{

    @Autowired
    QuestionService questionService;
    @Autowired
    WrongService wrongService;


    //开始顺序练习
    @RequestMapping("/startPractice")
    public RestResponse startPractice(){

        List<Integer> questionList = new ArrayList<>();
        questionList = questionService.getAllQuestions();

        int questionCount = questionList.size();

        Map<String,Object> allQuestions = new HashMap<>();

        allQuestions.put("questionList",questionList);
        allQuestions.put("questionCount",questionCount);

        allQuestions.put("firstQuestion",questionService.findQuestionById(questionList.get(0)));

        return RestResponse.ok(allQuestions);

    }

    //添加错题
    @RequestMapping("/addWrong")
    public RestResponse addWrong(int questionId){
        User user = getCurrentUser();

        if (wrongService.getWrongByUserIdAndQuestionId(user.getId(),questionId) != null){
            return RestResponse.fail(2,"该题已在错题本中");
        }

        Wrong wrong = new Wrong();
        wrong.setUserId(user.getId());
        wrong.setQuestionId(questionId);

        wrongService.addWrong(wrong);
        return RestResponse.ok();
    }

    //删除错题
    @RequestMapping("/removeWrong")
    public RestResponse removeWrong(int questionId){

        int row = wrongService.deleteWrongById(getCurrentUser().getId(),questionId);
        if(row == 1) {
            return RestResponse.ok();
        }
        else{
            return RestResponse.fail(2,"请勿重复操作");
        }

    }

    //查询某用户的所有错题
    @RequestMapping("/getAllWrongs")
    public RestResponse getAllWrongs(){
        User user = getCurrentUser();

        List<Wrong> wrongList = wrongService.getWrongByUserId(user.getId());

        List<Map<String,Object>> wrongQuestions = new ArrayList<>();

        for (Wrong wrong : wrongList){

            Map<String,Object> wrongQuestion = new HashMap<>();
            wrongQuestion.put("wrong", wrong);

            //将错题信息载入
            wrongQuestion.put("question",questionService.findQuestionById(wrong.getQuestionId()));

            wrongQuestions.add(wrongQuestion);
        }

        return RestResponse.ok(wrongQuestions);

    }
}

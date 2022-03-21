package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/wx")
public class QuestionController extends BaseApiWXController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/getQuestion")
    public RestResponse getQuestion(int questionId){

        Question question = questionService.findQuestionById(questionId);

        if (question == null){
            return RestResponse.fail(2,"无此题目");
        }
        return RestResponse.ok(question);
    }

}

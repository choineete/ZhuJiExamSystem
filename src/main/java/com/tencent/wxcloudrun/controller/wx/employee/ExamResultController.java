package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.service.ExamService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/api/wx")
public class ExamResultController extends BaseApiWXController {

    @Autowired
    ExamResultService examResultService;
    @Autowired
    ExamService examService;
    @Autowired
    UserService userService;



    //获取个人成绩列表
    @PostMapping("/getGradeList")
    public RestResponse getGradeList(){
        User user = getCurrentUser();
        //查出用户全部的考试记录
        List<ExamResult> examResultList = examResultService.getAllExamResultByUserId(user.getId());
        if (examResultList == null){
            return RestResponse.fail(2,"你尚未进行过考试");
        }

        //返回数据，
        //1.title,考试名称
        //2.createTime,考试提交事件
        //3.total，总分
        //4.score,得分
        //装入resultList中
        List<Map<String,Object>> resultList = new ArrayList<>();


        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



        for(ExamResult examResult : examResultList){
            Map<String,Object> result = new HashMap<>();

            Exam exam = examService.getExamById(examResult.getExamId());

            result.put("title",exam.getTitle());
            result.put("createTime",ft.format(examResult.getCreateTime()));
            result.put("score",String.valueOf(examResult.getScore()));
            //默认每题1分
            result.put("totalScore",String.valueOf(examResult.getTotalCount()));

            resultList.add(result);
        }

        return RestResponse.ok(resultList);
    }
    //获取某次考试全部用户的考试成绩
    @PostMapping("/getResultsByExam")
    public RestResponse getResultsByExam(int examId){
        List<ExamResult> examResultList = examResultService.getAllExamResultInOneExam(examId);

        List<Map<String,Object>> resultList = new ArrayList<>();

        for (ExamResult examResult : examResultList){
            Map<String,Object> result = new HashMap<>();

            User user = userService.getUserByUserId(examResult.getUserId());

            result.put("user",user);

            result.put("examResult",examResult);

            resultList.add(result);
        }

        return RestResponse.ok(resultList);
    }

}

package com.tencent.wxcloudrun.controller.wx.hr;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamRecordService;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
//@RequestMapping("/api/wx")
public class ExamResultManageController {

    @Autowired
    UserService userService;
    @Autowired
    ExamRecordService examRecordService;
    @Autowired
    ExamResultService examResultService;

    //获取某次考试中所有用户的成绩
    @PostMapping("/getAllResult")
    public RestResponse getAllResult(int examId){

        List<ExamResult> examResultList = examResultService.getAllExamResultInOneExam(examId);

        if (examResultList == null){
            return RestResponse.fail(2,"尚无考试记录");
        }

        //将考试记录及其对应的用户信息封装，传递到前台
        List<Map<String,Object>> resultAndUserList = new ArrayList<>();

        for (ExamResult result : examResultList){
            Map<String,Object> resultAndUser = new HashMap<>();

            User user = userService.getUserByUserId(result.getUserId());

            resultAndUser.put("result",result);
            resultAndUser.put("user",user);

            resultAndUserList.add(resultAndUser);
        }
        //返回相应的考试结果及其对应的用户信息
        return RestResponse.ok(resultAndUserList);
    }



}

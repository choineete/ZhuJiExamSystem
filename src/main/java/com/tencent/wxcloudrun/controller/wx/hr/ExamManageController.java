package com.tencent.wxcloudrun.controller.wx.hr;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamService;
import com.tencent.wxcloudrun.viewmodel.wx.AddExamVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.tencent.wxcloudrun.util.StringUtil.isBlank;

@Controller
@ResponseBody
@RequestMapping("/api/wx")
public class ExamManageController extends BaseApiWXController {

    @Autowired
    ExamService examService;

    @PostMapping("/addExam")
    public RestResponse addExam(AddExamVM addExamVM){



        if (isBlank(addExamVM.getTitle())){
            return RestResponse.fail(2,"标题不能为空");
        }
        if (addExamVM.getDuration() == 0){
            return RestResponse.fail(2,"时长不能为0");
        }
        if (addExamVM.getQuestionCount() == 0){
            return RestResponse.fail(2,"题目数量不能为0");
        }

        if(examService.getExamByTitle(addExamVM.getTitle()) != null){
            return RestResponse.fail(3,"考试已存在");
        }


        //映射到Exam对象
        Exam exam = modelMapper.map(addExamVM, Exam.class);

        User user = getCurrentUser();

        exam.setCreateUser(user.getWorkerId());

        //将分钟转化为秒
        exam.setDuration(exam.getDuration() * 60);

        examService.addExam(exam);

        return RestResponse.ok(exam);
    }
}

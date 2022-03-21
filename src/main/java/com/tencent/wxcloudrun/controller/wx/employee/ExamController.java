package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamPaperService;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.service.ExamService;
import com.tencent.wxcloudrun.util.JsonUtil;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
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
public class ExamController extends BaseApiWXController {

  @Autowired
  ExamService examService;
  @Autowired
  ExamPaperService examPaperService;
  @Autowired
  ExamResultService examResultService;


    @PostMapping("/examList")
    public RestResponse examList(){

      List<Exam> allExam = examService.examList();

      if (allExam == null){
        return RestResponse.fail(2,"无考试记录");
      }

      //获取当前用户，将考试记录载入
      User user = getCurrentUser();

      //将考试列表及其分数相关信息进行封装
      List<Map<String,Object>> allExamAndResult = new ArrayList<>();

      for (Exam exam : allExam){
        Map<String,Object> examAndResult = new HashMap<>();
        examAndResult.put("exam",exam);

       ExamResult examResult = examResultService.getExamResult(exam.getId(),user.getId());
       if(examResult != null){
         examAndResult.put("taken",true);
         examAndResult.put("score",examResult.getScore());
         examAndResult.put("date",examResult.getCreateTime());
       }

       allExamAndResult.add(examAndResult);

      }

      return RestResponse.ok(allExamAndResult);

    }

    @PostMapping("/startExam")
    public RestResponse generateAndKeepAndReturnExamPaper(int examId){

      Exam exam = examService.getExamById(examId);

      User user = getCurrentUser();


      //生成试卷
      List<Integer> paperContent = examPaperService.generateExamPaper(exam);

      String content = JsonUtil.toJsonStr(paperContent);

      ExamPaper paper = new ExamPaper();

      paper.setContent(content);
      paper.setExamId(exam.getId());
      paper.setCreateTime(new Date());
      paper.setUserId(user.getId());
      //保存试卷
      examPaperService.setExamPaper(paper);

       //封装好试卷全部信息，返回
      Map<String,Object> examInfo = new HashMap<>();

      examInfo.put("exam",exam);
      examInfo.put("examPaper",paper);

      return RestResponse.ok(examInfo);
    }






}

package com.tencent.wxcloudrun.controller.wx.another;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamPaperService;
import com.tencent.wxcloudrun.service.ExamRecordService;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.service.ExamService;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.util.JsonUtil;
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
public class AnotherExamController extends BaseApiWXController implements ConstantUtil {

  @Autowired
  ExamService examService;
  @Autowired
  ExamPaperService examPaperService;
  @Autowired
  ExamResultService examResultService;
  @Autowired
  ExamRecordService examRecordService;


    @PostMapping("/another_examList")
    public RestResponse examList(){

      List<Exam> allExam = examService.examList();

      if (allExam == null){
        return RestResponse.fail(2,"当前无考试");
      }

      //获取当前用户，将考试记录载入
      User user = getCurrentUser();

      //将考试列表及其分数相关信息进行封装
      List<Map<String,Object>> allExamAndResult = new ArrayList<>();

      for (Exam exam : allExam){
        Map<String,Object> examAndResult = new HashMap<>();
        examAndResult.put("exam",exam);

       ExamResult examResult = examResultService.getExamResult(exam.getId(),user.getId());
       //结果表中存在结果，说明已经提交，不可再考直接显示分数
       if(examResult != null){
         examAndResult.put("examStatus",SUBMITTED);
         examAndResult.put("score",examResult.getScore());
         examAndResult.put("date",examResult.getCreateTime());
       }
       //如果没有结果，则有三种情况 1.考试未开始 2.考试进行中未提交 3.考试结束未提交
       else{

         ExamPaper paper = examPaperService.getExamPaperByExamIdAndUserId(exam.getId(),user.getId());
         //考试还未开始
         if (paper == null){
           examAndResult.put("examStatus",EXAM_NOT_START);
         }
         //未提交
         else{
           //考试开始时间
           Date startTime = paper.getCreateTime();

           //当前时间
           Date currentTime = new Date();

           //考试结束时间
           Date endTime = null;

           Calendar calendar = Calendar.getInstance();

           calendar.setTime(startTime);
           calendar.add(Calendar.MINUTE,exam.getDuration());
           endTime = calendar.getTime();
           //考试还未结束
           if (currentTime.before(endTime)){
             examAndResult.put("examStatus",NOT_SUBMIT_EXAM_NOT_END);
           }
           //考试已经结束
           else{
             examAndResult.put("examStatus",NOT_SUBMIT_EXAM_END);
           }

         }

       }

       allExamAndResult.add(examAndResult);

      }

      return RestResponse.ok(allExamAndResult);

    }

    @PostMapping("/another_startExam")
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
      examInfo.put("remainingSeconds",exam.getDuration());

      return RestResponse.ok(examInfo);
    }

    @PostMapping("/continueExam")
  public RestResponse continueExam(int examId){

      Exam exam = examService.getExamById(examId);

      User user = getCurrentUser();

      ExamPaper paper = examPaperService.getExamPaperByExamIdAndUserId(exam.getId(),user.getId());


      //考试开始时间
      Date startTime = paper.getCreateTime();

      //当前时间
      Date currentTime = new Date();

      //考试结束时间
      Date endTime = null;

      Calendar calendar = Calendar.getInstance();

      calendar.setTime(startTime);
      calendar.add(Calendar.MINUTE,exam.getDuration());
      endTime = calendar.getTime();

      if (currentTime.after(endTime)){
        return RestResponse.fail(2,"考试已经结束");
      }

      long remainingSeconds = (endTime.getTime() - currentTime.getTime()) / 1000;



      //获取答题记录
      List<ExamRecord> recordList = examRecordService.findExamRecordByUserIdAndExamId(user.getId(), exam.getId());

      Map<String,Object> examInfo = new HashMap<>();

      examInfo.put("exam",exam);
      examInfo.put("examPaper",paper);
      examInfo.put("recordList",recordList);
      examInfo.put("remainingSeconds",remainingSeconds);

      return RestResponse.ok(examInfo);

    }

}

package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.dao.ExamRecordMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ExamPaperService;
import com.tencent.wxcloudrun.service.ExamRecordService;
import com.tencent.wxcloudrun.service.ExamResultService;
import com.tencent.wxcloudrun.service.RecordAndResultService;
import com.tencent.wxcloudrun.viewmodel.wx.AnswerInfo;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;
import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@ResponseBody
//@RequestMapping("/api/wx")
public class ExamRecordController extends BaseApiWXController {

    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamRecordService examRecordService;
    @Autowired
    RecordAndResultService recordAndResultService;


    @PostMapping("/submitAnswer")
    public RestResponse submitAnswer(AnswerInfo answerInfo){

        ExamPaper examPaper = examPaperService.getExamPaperById(answerInfo.getExamPaperId());

        List<Integer> answerList = new ArrayList<>();
        for (Integer answer : answerInfo.getAnswerList()){
            answerList.add(answer);
        }
        //将答题记录插入数据库，并获得最后的分数
        int score = recordAndResultService.recordAndMakeResult(answerList,examPaper);

        return RestResponse.ok(score);
    }

    //返回用户某考试全部的答题记录
    @PostMapping("/getExamRecord")
    public RestResponse getExamRecord(int examId){
        User user = getCurrentUser();

       List<ExamRecord> examRecordList = examRecordService.findExamRecordByUserIdAndExamId(user.getId(),examId);

        if (examRecordList.isEmpty()){
            return RestResponse.fail(2,"无考试记录，？？？，哪里出了问题");
        }
        //成功返回该条考试记录
        return RestResponse.ok(examRecordList);
    }

}
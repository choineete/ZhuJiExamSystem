package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.dao.ExamMapper;
import com.tencent.wxcloudrun.model.Exam;
import com.tencent.wxcloudrun.service.ExamService;
import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamMapper examMapper;

    @Override
    public List<Exam> examList() {

        List<Exam> allExam = examMapper.selectAllExam();

        if (allExam.isEmpty()){
            return null;
        }
        return allExam;
    }

    @Override
    public Exam getExamById(int id) {
        return examMapper.selectExamById(id);
    }

    @Override
    public int addExam(Exam exam) {
        return examMapper.insertExamSelective(exam);
    }

    @Override
    public Exam getExamByTitle(String title) {
        return examMapper.selectExamByTitle(title);
    }
}

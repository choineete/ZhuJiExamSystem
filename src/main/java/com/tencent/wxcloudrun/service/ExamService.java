package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Exam;

import java.util.List;

public interface ExamService {

    //返回考试列表
    List<Exam> examList();

    //根据id查找考试
    Exam getExamById(int id);

    //添加考试
    int addExam(Exam exam);

    //根据考试title查询考试
    Exam getExamByTitle(String title);



}

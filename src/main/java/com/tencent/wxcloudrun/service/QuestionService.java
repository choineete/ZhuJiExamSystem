package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {

    /**
     * 添加题目
     * @param question
     * @return
     */
    int addQuestion(Question question);

    /**
     * 根据id查找题目
     * @param id
     * @return
     */
    Question findQuestionById(int id);

    /**
     * 根据题目类型来找出对应类型全部题目的id
     * @param type
     * @return
     */
    Set<Integer> findQuestionIdByType(int type);

    List<Integer> getAllQuestions();



}

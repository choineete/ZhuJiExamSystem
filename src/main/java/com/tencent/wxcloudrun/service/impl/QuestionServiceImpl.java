package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.QuestionMapper;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public int addQuestion(Question question) {
        return questionMapper.insertQuestion(question);
    }

    @Override
    public Question findQuestionById(int id) {
        return questionMapper.selectQuestionById(id);
    }

    @Override
    public Set<Integer> findQuestionIdByType(int type) {
        return questionMapper.selectQuestionByType(type);
    }

    @Override
    public List<Integer> getAllQuestions() {
        return questionMapper.selectAllQuestions();
    }


}

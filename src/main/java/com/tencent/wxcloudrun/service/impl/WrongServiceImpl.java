package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.WrongMapper;
import com.tencent.wxcloudrun.model.Wrong;
import com.tencent.wxcloudrun.service.WrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WrongServiceImpl implements WrongService {


    @Autowired
    WrongMapper wrongMapper;

    @Override
    public int addWrong(Wrong wrong) {
        return wrongMapper.insertWrong(wrong);
    }

    @Override
    public int deleteWrongById(int userId,int questionId) {
        return wrongMapper.deleteWrongById(userId,questionId);
    }

    @Override
    public List<Wrong> getWrongByUserId(int userId) {
        return wrongMapper.selectWrongByUserId(userId);
    }

    @Override
    public Wrong getWrongByUserIdAndQuestionId(int userId, int questionId) {
        return wrongMapper.selectWrongByUserIdAndQuestionId(userId,questionId);
    }
}

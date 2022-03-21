package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Wrong;

import java.util.List;

public interface WrongService {
    /**
     * 添加错题
     * @param wrong
     * @return
     */
    int addWrong(Wrong wrong);

    /**
     * 删除错题
     * @param
     * @return
     */
    int deleteWrongById(int userId,int questionId);

    /**
     * 获取用户所有错题
     * @param userId
     * @return
     */
    List<Wrong> getWrongByUserId(int userId);

    /**
     * 获取用户某条错题记录
     * @param userId
     * @param questionId
     * @return
     */
    Wrong getWrongByUserIdAndQuestionId(int userId,int questionId);
}

package com.tencent.wxcloudrun.util;

public interface ConstantUtil {
    /**
     * 用户状态：正常 1
     *         未激活 2
     *         删除 3
     */
    int USER_STATUS_ABLE = 1;
    int USER_STATUS_DISABLE = 2;

    /**
     * 用户角色类型
     * 普通员工 1
     * 领导 2
     * 人资 3
     */
    int ROLE_EMM = 1;
    int ROLE_LEADER = 2;
    int ROLE_HR = 3;


    /**
     * 题目类型
     * 选择题 1
     * 判断题 2
     */
    int QUESTION_TYPE_MULTIPLE_CHOICE = 1;
    int QUESTION_TYPE_TorF = 2;


    /**
     * 题目类型的比率
     * 选择题 0.6
     * 判断题 0.4
     */

    double QUESTION_MULTIPLE_CHOICE_RATE = 0.6;
    double QUESTION_TorF_RATE = 0.4;

    /**
     * 题目的切换类型
     * 上一题 1
     * 下一题 2
     */
    int PREVIOUS_QUESTION  = 1;
    int NEXT_QUESTION  = 2;

    /**
     * 考试状态
     * 已提交 1
     * 未提交 考试进行中 2
     * 未提交 考试已结束 3
     * 考试未开始 4
     */
    int SUBMITTED = 1;
    int NOT_SUBMIT_EXAM_NOT_END = 2;
    int NOT_SUBMIT_EXAM_END = 3;
    int EXAM_NOT_START = 4;


}

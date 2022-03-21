package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.viewmodel.wx.ExamResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamResultMapper {


    @Insert({
            "insert into exam_result(exam_id,exam_paper_id,user_id,correct_count,total_count,score,create_time)" +
                    "values(#{examId},#{examPaperId},#{userId},#{correctCount},#{totalCount},#{score},#{createTime})"
    })
    int insertExamResult(ExamResult examResult);

    @Select({
            "select * from exam_result where exam_id = #{examId} and user_id = #{userId}"
    })
    ExamResult selectExamResultByExamIdAndUserId(int examId,int userId);

    @Select({
            "select * from exam_result where user_id = #{userId}"
    })
    List<ExamResult> selectExamResultByUserId(int userId);

    @Select({
            "select * from exam_result where exam_id = #{examId} order by score desc"
    })
    List<ExamResult> selectAllExamResultInOneExam(int examId);

}

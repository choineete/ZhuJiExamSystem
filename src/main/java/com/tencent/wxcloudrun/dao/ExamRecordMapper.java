package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import com.tencent.wxcloudrun.viewmodel.wx.ExamRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamRecordMapper {



    @Insert({
            "insert into exam_record" +
                    "(exam_id,exam_paper_id,user_id,question_id,is_right,create_time,answer)" +
                    "values(#{examId},#{examPaperId},#{userId},#{questionId},#{isRight},#{createTime},#{answer})"
    })
    int insertExamRecord(ExamRecord examRecord);

    @Select({
            "select * from exam_record where user_id = #{userId} and exam_id = #{examId}"
    })
    List<ExamRecord> selectExamRecordByUserIdAndExamId(int userId, int examId);

    @Select({
            "select * from exam_record where exam_paper_id = #{examPaperId} " +
                    "and question_id = #{questionId} and user_id = #{userId}"
    })
    ExamRecord selectExamRecord(int examPaperId,int questionId,int userId);

    @Update({
            "update exam_record set answer = #{answer},is_right = #{isRight},create_time = #{createTime} where id = #{id}"
    })
    int updateExamRecord(ExamRecord record);


}

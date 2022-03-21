package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.viewmodel.wx.ExamPaper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamPaperMapper {



    @Insert({
            "insert into exam_paper(exam_id,user_id,content,create_time)" ,
                    "values(#{examId},#{userId},#{content},#{createTime})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertExamPaper(ExamPaper examPaper);

    @Select({
            "select * from exam_paper where exam_id = #{examId}"
    })
    List<ExamPaper> selectExamPaperByExamId(int examId);

    @Select({
            "select * from exam_paper where id = #{examPaperId}"
    })
    ExamPaper selectExamPaperById(int examPaperId);


    @Select({
            "select * from exam_paper where user_id = #{userId}"
    })
    List<ExamPaper> selectExamPaperByUserId(int userId);

    @Select({
            "select * from exam_paper where exam_id = #{examId} and user_id = #{userId}"
    })
    ExamPaper selectExamPaperByExamIdAndUserId(int examId,int userId);
}

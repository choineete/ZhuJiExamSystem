package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Exam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamMapper {

    @Select({
            "select * from exam"
    })
    List<Exam> selectAllExam();

    @Select({"select * from exam where id = #{id}"})
    Exam selectExamById(int id);


    @Delete({
            "delete from exam where id = #{id}"
    })
    int deleteExamById(int id);


    int insertExamSelective(Exam exam);

    int updateExamByIdSelective(int id);

    @Select({"select * from exam where title = #{title}"})
    Exam selectExamByTitle(String title);




}

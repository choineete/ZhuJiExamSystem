package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Wrong;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WrongMapper {


    @Insert({
            "insert into wrong(user_id,question_id) values(#{userId},#{questionId})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertWrong(Wrong wrong);

    @Delete({
            "delete from wrong where user_id = #{userId} and question_id = #{questionId}"
    })
    int deleteWrongById(int userId,int questionId);

    @Select({
            "select * from wrong where user_id = #{userId}"
    })
    List<Wrong> selectWrongByUserId(int userId);

    @Select({
            "select * from wrong where user_id = #{userId} and question_id = #{questionId}"
    })
    Wrong selectWrongByUserIdAndQuestionId(int userId,int questionId);


}

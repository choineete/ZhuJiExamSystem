package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.model.UploadData;
import com.tencent.wxcloudrun.viewmodel.wx.QuestionUploadVM;
import org.apache.ibatis.annotations.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface QuestionMapper {




    @Insert({
            "insert into " ,
                    "question(question_type,score,correct,stem,option_a,option_b,option_c,option_d,create_time,status)" ,
                    "values(#{questionType},#{score},#{correct},#{stem},#{optionA},#{optionB},#{optionC},#{optionD},#{createTime},#{status})"
    })
    int insertQuestion(Question question);

    @Select({
            "select * from question where id = #{id} and status =1"
    })
    Question selectQuestionById(int id);

    @Select({
            "select * from question where question_type = #{question_type}"
    })
    Set<Integer> selectQuestionByType(int questionType);


    @Select({
            "select id from question where status =1"
    })
    List<Integer> selectAllQuestions();


    @Delete({
            "delete from question where id = #{id}"
    })
    int deleteQuestionById(int id);


    /**
     * 将题目批量插入数据库
     * @param QuestionList
     */
    @InsertProvider(type = QuestionMapper.Provider.class, method = "batchInsert")

    void insertQuestionsFromExcel(List<Question> QuestionList);
    
    class Provider {
        /* 批量插入 */
        public String batchInsert(Map map) {
            List<Question> QuestionList = (List<Question>) map.get("list");

            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO question (question_type,score,correct," +
                    "stem,option_a,option_b,option_c,option_d," +
                    "create_time,status) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].questionType}, #'{'list[{0}].score}, #'{'list[{0}].correct}, " +
                            "#'{'list[{0}].stem}, #'{'list[{0}].optionA}, #'{'list[{0}].optionB}, #'{'list[{0}].optionC}" +
                            ", #'{'list[{0}].optionD}, #'{'list[{0}].createTime}, #'{'list[{0}].status})"
            );

            for (int i = 0; i < QuestionList.size(); i++) {
                sb.append(mf.format(new Object[]{i}));
                if (i < QuestionList.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

    }
}

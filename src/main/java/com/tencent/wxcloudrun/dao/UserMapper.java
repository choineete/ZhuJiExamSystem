package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    @Select({
            "select *",
            "from user where status = 1 and worker_id=#{workerId}"
    })
    User getUserByWorkerId(String workerId);


    @Select({
            "select *",
            "from user where status = 1 and wx_open_id=#{openId} limit 1"
    })
    User getUserByWxOpenId(String openId);

    @Select({
            "select *",
            "from user where " ,
                    "status = 1 " ,
                    "and wx_open_id=#{openId} " ,
                    "and worker_id = #{workerId}"
    })
    User getUserByWxOpenIdAndWorkerId(String openId, String workerId);

    @Select({
            "select * from user where id = #{userId}"
    })
    User selectUserByUserId(int userId);

}

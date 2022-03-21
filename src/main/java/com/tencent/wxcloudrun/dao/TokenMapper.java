package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Token;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper extends BaseMapper<Token>{
    @Select({
            "select *",
            "from token where token=#{token}"
    })
    Token getToken(String token);

    @Select({
            "select *",
            "from token where wx_open_id=#{openId} limit 1"
    })
    Token getTokenByOpenId(String openId);

    @Delete({
           "delete from token where wx_open_id = #{openId}"
    })
    int deleteTokenByWxOpenId(String openId);

}

package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;
import com.github.pagehelper.PageInfo;
import java.util.List;


public interface UserService extends BaseService<User>{

    User getUserByWorkerId(String workerId);

    User getUserByWxOpenId(String openId);

    User getUserByWxOpenIdAndWorkerId(String wxOpenId,String workerId);

    User getUserByUserId(int userId);

}

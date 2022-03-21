package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BaseMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(BaseMapper<User> baseMapper,UserMapper userMapper) {
        super(baseMapper);
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByWorkerId(String WorkerId) {
        return userMapper.getUserByWorkerId(WorkerId);
    }

    @Override
    public User getUserByWxOpenId(String openId){
        return userMapper.getUserByWxOpenId(openId);
    }

    @Override
    public User getUserByWxOpenIdAndWorkerId(String wxOpenId, String workerId) {
        return userMapper.getUserByWxOpenIdAndWorkerId(wxOpenId,workerId);
    }

    @Override
    public User getUserByUserId(int userId) {
        return userMapper.selectUserByUserId(userId);
    }
}

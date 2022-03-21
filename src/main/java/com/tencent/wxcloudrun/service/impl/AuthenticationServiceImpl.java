package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.config.property.SystemConfig;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.AuthenticationService;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final SystemConfig systemConfig;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, SystemConfig systemConfig) {
        this.userService = userService;
        this.systemConfig = systemConfig;
    }


    /**
     * @param workerId username
     * @param password password
     * @return boolean
     */
    @Override
    public boolean authUser(String workerId, String password) {
        User user = userService.getUserByWorkerId(workerId);
        return authUser(user, workerId, password);
    }


    @Override
    public boolean authUser(User user, String workerId, String password) {
        if (user == null) {
            return false;
        }
        String truePwd = user.getPassword();

        if (null == truePwd || truePwd.length() == 0) {
            return false;
        }

        return truePwd.equals(password);
    }

}

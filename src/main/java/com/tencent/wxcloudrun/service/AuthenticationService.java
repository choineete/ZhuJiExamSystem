package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

public interface AuthenticationService {
    /**
     * authUser
     *
     * @param workerId workerId
     * @param password password
     * @return boolean
     */
    boolean authUser(String workerId, String password);



    /**
     * authUser
     *
     * @param user     user
     * @param workerId workerId
     * @param password password
     * @return boolean
     */
    boolean authUser(User user, String workerId, String password);


}

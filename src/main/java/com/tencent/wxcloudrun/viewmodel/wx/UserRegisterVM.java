package com.tencent.wxcloudrun.viewmodel.wx;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRegisterVM {

    private String workerId;


    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String name;



    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

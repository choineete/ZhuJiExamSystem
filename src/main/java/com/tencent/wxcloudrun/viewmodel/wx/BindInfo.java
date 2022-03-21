package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class BindInfo {
    @NotBlank
    private String workerId;

    @NotBlank
    private String password;

    @NotBlank
    private String code;

}

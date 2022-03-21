package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddExamVM {


    private String title;


    private int duration;//此处的考试时长单位为分钟

    // @NotBlank
    // private Date startTime;

    // @NotBlank
    // private Date endTime;


    private int questionCount;


}

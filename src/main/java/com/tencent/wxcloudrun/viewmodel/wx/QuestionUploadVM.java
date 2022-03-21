package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class QuestionUploadVM {

    private int questionType;
    private int score;
    private int correct;
    private String stem;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

}

package com.tencent.wxcloudrun.viewmodel.wx;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerInfo {

    @NotBlank
    private int[] answerList;//答题记录

    @NotBlank
    private int examPaperId;//试卷id



}

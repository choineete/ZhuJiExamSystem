package com.tencent.wxcloudrun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AlphaController {


    @GetMapping(value = "/alpha")
    @ResponseBody
    String WxGet(){
        return "alpha";
    }
}

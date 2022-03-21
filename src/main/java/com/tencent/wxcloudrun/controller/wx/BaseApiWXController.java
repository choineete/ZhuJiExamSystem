package com.tencent.wxcloudrun.controller.wx;

import com.tencent.wxcloudrun.context.WxContext;
import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.util.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseApiWXController {
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    @Autowired
    private WxContext wxContext;

    protected User getCurrentUser() {
        return wxContext.getCurrentUser();
    }

    protected Token getUserToken() {
        return wxContext.getCurrentToken();
    }
}

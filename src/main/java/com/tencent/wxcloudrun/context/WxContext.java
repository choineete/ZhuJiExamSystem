package com.tencent.wxcloudrun.context;

import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class WxContext {
    private static final String USER_ATTRIBUTES = "USER_ATTRIBUTES";
    private static final String TOKEN_ATTRIBUTES = "TOKEN_ATTRIBUTES";

    public void  setContext(User user, Token token){
        RequestContextHolder.currentRequestAttributes().setAttribute(USER_ATTRIBUTES,user, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.currentRequestAttributes().setAttribute(TOKEN_ATTRIBUTES,token, RequestAttributes.SCOPE_REQUEST);
    }

    public User getCurrentUser(){
        return (User)RequestContextHolder.currentRequestAttributes().getAttribute(USER_ATTRIBUTES,RequestAttributes.SCOPE_REQUEST);
    }
    public Token getCurrentToken(){
        Token token = (Token)RequestContextHolder.currentRequestAttributes().getAttribute(TOKEN_ATTRIBUTES,RequestAttributes.SCOPE_REQUEST);
        return  token;
    }
}

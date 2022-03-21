package com.tencent.wxcloudrun.config.spring.wx;

import com.tencent.wxcloudrun.base.SystemCode;
import com.tencent.wxcloudrun.context.WxContext;
import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.TokenService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.RestUtil;
import com.tencent.wxcloudrun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final UserService userService;
    private final WxContext wxContext;

    @Autowired
    public TokenHandlerInterceptor(TokenService tokenService, UserService userService, WxContext wxContext) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.wxContext = wxContext;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        if (StringUtil.isBlank(token)) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        if (token.length() != 36) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        Token userToken = tokenService.getToken(token);
        if (null == userToken) {
            RestUtil.response(response, SystemCode.UNAUTHORIZED);
            return false;
        }

        Date now = new Date();

        //此处在openid和工号唯一对饮的时候启用
        //User user = userService.getUserByWxOpenId(userToken.getWxOpenId());

        User user = userService.getUserByUserId(userToken.getUserId());

        if (now.before(userToken.getEndTime())) {

            wxContext.setContext(user,userToken);

            return true;
        } else {

            RestUtil.response(response, SystemCode.AccessTokenError.getCode(),
                    SystemCode.AccessTokenError.getMessage());
            return false;
        }
    }


}

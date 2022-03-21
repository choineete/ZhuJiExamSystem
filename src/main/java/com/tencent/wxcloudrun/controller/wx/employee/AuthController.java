package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.config.property.SystemConfig;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.AuthenticationService;
import com.tencent.wxcloudrun.service.TokenService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.util.WxUtil;
import com.tencent.wxcloudrun.viewmodel.wx.BindInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Controller("WXStudentAuthController")
@RequestMapping(value = "/api/wx")
@ResponseBody
public class AuthController extends BaseApiWXController implements ConstantUtil {
    private final SystemConfig systemConfig;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(SystemConfig systemConfig,
                          AuthenticationService authenticationService,
                          UserService userService,
                          TokenService tokenService) {

        this.systemConfig = systemConfig;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/bind")
    public RestResponse bind(@Valid BindInfo model){


        User user = userService.getUserByWorkerId(model.getWorkerId());


        if (user == null) {
            return RestResponse.fail(2, "工号或密码错误");
        }

        boolean result = authenticationService.authUser(user, model.getWorkerId(), model.getPassword());
        if (!result) {
            return RestResponse.fail(2, "工号或密码错误");
        }


        //UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (user.getStatus() == USER_STATUS_DISABLE) {
            return RestResponse.fail(3, "用户被禁用");
        }


//        String code = model.getCode();
//        String openId = WxUtil.getOpenId(systemConfig.getWxConfig().getAppid(), systemConfig.getWxConfig().getSecret(), code);
//
//        if (null == openId) {
//            return RestResponse.fail(4, "获取微信OpenId失败");
//        }


        //user.setWxOpenId(openId);

        Token token = tokenService.bind(user);

        Map<String,Object> map = new HashMap<>();

        map.put("token",token.getToken());
        map.put("role",user.getRole());
        map.put("user",user);

        return RestResponse.ok(map);
    }
       /*检查登录状态*/
    @PostMapping(value = "/checkBind")
    public RestResponse checkBind(@Valid @NotBlank String code) {

        String openId = WxUtil.getOpenId(systemConfig.getWxConfig().getAppid(), systemConfig.getWxConfig().getSecret(), code);
        if (null == openId) {
            return RestResponse.fail(3, "获取微信OpenId失败");
        }
        /*
        仅仅判断用户是否注册过
         */
        User user = userService.getUserByWxOpenId(openId);
        if (user == null){
            return RestResponse.fail(2, "用户未注册");
        }

        Token token = tokenService.checkBind(openId);
        if (null == token) {
            return RestResponse.fail(4, "登录状态已过期");
        }

        Map<String,Object> map = new HashMap<>();

        map.put("token",token.getToken());
        map.put("role",user.getRole());
        map.put("user",user);

        return RestResponse.ok(map);

    }


    @PostMapping("/unBind")
    public RestResponse unBind() {

        Token token = getUserToken();

        tokenService.unBind(token);
        return RestResponse.ok();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

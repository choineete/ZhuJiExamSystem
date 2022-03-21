package com.tencent.wxcloudrun.controller.wx.employee;

import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.config.property.SystemConfig;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.util.StringUtil;
import com.tencent.wxcloudrun.util.WxUtil;
import com.tencent.wxcloudrun.viewmodel.wx.UserRegisterVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/api/wx")
@ResponseBody
public class UserController extends BaseApiWXController implements ConstantUtil {


    @Autowired
    UserService userService;
    @Autowired
    SystemConfig systemConfig;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestResponse register(UserRegisterVM model,String code) {



        if(StringUtil.isBlank(model.getWorkerId())){
            return RestResponse.fail(2,"工号不能为空");
        }
        if(StringUtil.isBlank(model.getName())){
            return RestResponse.fail(2,"用户名不能为空");
        }
        if(StringUtil.isBlank(model.getPassword())){
            return RestResponse.fail(2,"密码不能为空");
        }




        /*

        使得工号与openid一一对应
        String openId = WxUtil.getOpenId(systemConfig.getWxConfig().getAppid(), systemConfig.getWxConfig().getSecret(), code);
        if (null == openId) {
            return RestResponse.fail(3, "获取微信OpenId失败");
        }

        User existUser = userService.getUserByWxOpenIdAndWorkerId(openId,model.getWorkerId());
        */
        /*
        工号唯一，一个openid可以对应对多个workerid
         */
        User existUser = userService.getUserByWorkerId(model.getWorkerId());

        if (null != existUser) {
            return new RestResponse<>(3, "用户已存在");
        }





        //做关系映射
        User user = modelMapper.map(model, User.class);


        user.setRole(ROLE_EMM);

        user.setCreateTime(new Date());
        user.setStatus(USER_STATUS_ABLE);

        String openId = WxUtil.getOpenId(systemConfig.getWxConfig().getAppid(), systemConfig.getWxConfig().getSecret(),code);

        if (openId == null){
            throw new RuntimeException("openId为空");
        }

        user.setWxOpenId(openId);

        userService.insertByFilter(user);

        return RestResponse.ok();


    }


}

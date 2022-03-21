package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.config.property.SystemConfig;
import com.tencent.wxcloudrun.dao.TokenMapper;
import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.TokenService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl extends BaseServiceImpl<Token> implements TokenService {

    private final TokenMapper tokenMapper;
    private final UserService userService;
    private final SystemConfig systemConfig;

    @Autowired
    public TokenServiceImpl(TokenMapper tokenMapper, UserService userService, SystemConfig systemConfig) {
        super(tokenMapper);
        this.tokenMapper = tokenMapper;
        this.userService = userService;
        this.systemConfig = systemConfig;
    }


    @Override
    @Transactional
    public Token bind(User user) {
        //userService.updateByIdFilter(user);
        return insertToken(user);
    }


    @Override
    public Token checkBind(String openId) {

        Token token =  tokenMapper.getTokenByOpenId(openId);
        /*如果token未过期*/
        if(token != null){
            if(token.getEndTime().before(new Date())){
                tokenMapper.deleteTokenByWxOpenId(openId);
                return null;
            }
            return token;
        }
        return null;
    }

    @Override
    public Token getToken(String token) {
        return tokenMapper.getToken(token);
    }

    @Override
    public Token insertToken(User user) {

        Date startTime = new Date();

        Date endTime = DateTimeUtil.addDuration(startTime, systemConfig.getWxConfig().getTokenToLive());

        Token token = new Token();

        token.setToken(UUID.randomUUID().toString());
        token.setUserId(user.getId());
        token.setWxOpenId(user.getWxOpenId());
        //token.setCreateTime(startTime);
        token.setEndTime(endTime);

        //token.setUserName(user.getUserName());
        userService.updateByIdFilter(user);
        tokenMapper.insertSelective(token);

        return token;
    }

    @Override
    /*退出登录*/
    public void unBind(Token token) {
        //User user = userService.selectById(token.getUserId());
        //user.setModifyTime(new Date());
        //user.setWxOpenId(null);
        //userService.updateById(user);

        tokenMapper.deleteTokenByWxOpenId(token.getWxOpenId());
    }

}

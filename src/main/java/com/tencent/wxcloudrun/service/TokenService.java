package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Token;
import com.tencent.wxcloudrun.model.User;

public interface TokenService extends BaseService<Token>{
    /**
     * 微信token绑定
     *
     * @param user user
     * @return Token
     */
    Token bind(User user);

    /**
     * 检查微信openId是否绑定过
     *
     * @param openId openId
     * @return Token
     */
    Token checkBind(String openId);

    /**
     * 根据token获取Token，带缓存的
     *
     * @param token token
     * @return Token
     */
    Token getToken(String token);

    /**
     * 插入用户Token
     *
     * @param user user
     * @return Token
     */
    Token insertToken(User user);

    /**
     * 微信小程序退出，清除缓存
     *
     * @param userToken userToken
     */
    void unBind(Token userToken);
}

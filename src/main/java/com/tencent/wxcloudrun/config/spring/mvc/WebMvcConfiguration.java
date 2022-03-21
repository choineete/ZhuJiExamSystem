package com.tencent.wxcloudrun.config.spring.mvc;

import com.tencent.wxcloudrun.config.property.SystemConfig;
import com.tencent.wxcloudrun.config.spring.wx.TokenHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private final TokenHandlerInterceptor tokenHandlerInterceptor;
    private final SystemConfig systemConfig;

    @Autowired
    public WebMvcConfiguration(TokenHandlerInterceptor tokenHandlerInterceptor, SystemConfig systemConfig) {
        this.tokenHandlerInterceptor = tokenHandlerInterceptor;
        this.systemConfig = systemConfig;
    }



    public void addInterceptors(InterceptorRegistry registry) {
        List<String> securityIgnoreUrls = systemConfig.getWxConfig().getSecurityIgnoreUrls();
        securityIgnoreUrls.add("/index.html");
        String[] ignores = new String[securityIgnoreUrls.size()];
        //System.out.println(ignores[0]);
        registry.addInterceptor(tokenHandlerInterceptor)
                .addPathPatterns("/api/wx/**")
                .excludePathPatterns(securityIgnoreUrls.toArray(ignores));
        super.addInterceptors(registry);
    }
}

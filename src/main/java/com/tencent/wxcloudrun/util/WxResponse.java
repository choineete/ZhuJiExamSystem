package com.tencent.wxcloudrun.util;



import lombok.Data;

import java.io.Serializable;
@Data
public class WxResponse implements Serializable {
    private String session_key;
    private String openid;
}

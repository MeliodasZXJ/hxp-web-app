package com.hxp.common.rongCloud.result;

import java.io.Serializable;

/**
 * Created by slyi on 2016/7/13.
 */
public class TokenResult implements Serializable{
    private static final long serialVersionUID = 7672533489013885289L;
    private String code;
    private String userId;
    private String token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

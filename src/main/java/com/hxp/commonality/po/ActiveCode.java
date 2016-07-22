package com.hxp.commonality.po;

import java.io.Serializable;

import com.hxp.base.BaseBean;

public class ActiveCode extends BaseBean implements Serializable{
    private String code;

    private String usr;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr == null ? null : usr.trim();
    }
}
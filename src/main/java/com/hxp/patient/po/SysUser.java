package com.hxp.patient.po;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/9.
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = -8900624346014975601L;

    private Integer id;

    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

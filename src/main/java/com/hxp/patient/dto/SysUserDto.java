package com.hxp.patient.dto;


import com.hxp.patient.po.SysDict;
import com.hxp.patient.po.SysUser;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/9.
 */
public class SysUserDto implements Serializable {

    private SysDict sysDict;

    private SysUser sysUser;

    public SysDict getSysDict() {
        return sysDict;
    }

    public void setSysDict(SysDict sysDict) {
        this.sysDict = sysDict;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}

package com.hxp.sys.service;

import com.hxp.sys.po.SysVersion;

public interface ISysVersionService {

    /***
     * 查询我的版本号
     * @param collection
     * @return
     */
	SysVersion selectVersion(SysVersion sysVersion);

}

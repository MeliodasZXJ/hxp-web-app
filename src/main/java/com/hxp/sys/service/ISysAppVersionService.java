package com.hxp.sys.service;

import java.util.List;

import com.hxp.sys.po.SysVersion;

/**
 * Created by anpushang on 2016/7/14.
 */
public interface ISysAppVersionService {

	SysVersion selectVersion(Integer clientType, Integer pfType);

	List<SysVersion> selectThisLast(Integer type, Integer pfType, String last);

	SysVersion selectVersion(Integer clientType);

	

}

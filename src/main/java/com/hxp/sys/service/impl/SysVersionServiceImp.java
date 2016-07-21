package com.hxp.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysVersionDao;
import com.hxp.sys.po.SysVersion;
import com.hxp.sys.service.ISysVersionService;


@Service
public class SysVersionServiceImp implements ISysVersionService{
	
	 @Autowired
	private  SysVersionDao sysVersionDao;

	@Override
	public SysVersion selectVersion(SysVersion sysVersion) {
		// TODO Auto-generated method stub
		return sysVersionDao.get("selectSysVersion",sysVersion);
	}

}

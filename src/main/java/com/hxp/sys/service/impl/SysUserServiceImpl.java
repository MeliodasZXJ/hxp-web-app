package com.hxp.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysUserDao;
import com.hxp.sys.po.SysUser;
import com.hxp.sys.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public SysUser getLoginSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		return sysUserDao.get("getLoginInfo",sysUser);
	}

}

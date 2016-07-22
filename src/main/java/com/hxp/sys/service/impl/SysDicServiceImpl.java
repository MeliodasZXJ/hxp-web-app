package com.hxp.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysUserDicDao;
import com.hxp.sys.po.SysDic;
import com.hxp.sys.service.ISysDicService;

@Service
public class SysDicServiceImpl implements ISysDicService {

	@Autowired
	private SysUserDicDao sysUserDicDao;
		
	@Override
	public List<SysDic> findAllDictType() {
		// TODO Auto-generated method stub
		return sysUserDicDao.find("findAllDictType");
	}

	@Override
	public List<SysDic> findDictByCode(String code) {
		// TODO Auto-generated method stub
		return sysUserDicDao.find("findDictByCode",code);
	}

}

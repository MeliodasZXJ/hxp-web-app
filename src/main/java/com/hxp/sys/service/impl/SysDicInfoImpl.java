package com.hxp.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysDicInfoDao;
import com.hxp.sys.po.SysDicInfo;
import com.hxp.sys.service.ISysDicInfoService;

@Service
public class SysDicInfoImpl implements ISysDicInfoService {
	
	
	@Autowired
	private SysDicInfoDao sysDicInfoDao;

	@Override
	public int insert(SysDicInfo record) {
		return  sysDicInfoDao.insert(record);
	}

	@Override
	public int update(SysDicInfo record) {
		return sysDicInfoDao.update("updateByPrimaryKeySelective",record);
	}

	@Override
	public List<SysDicInfo> selectSysDicInfoByTypeCode(String typeCode) {
		return sysDicInfoDao.find("selectDicInfoByTypeCode",typeCode);
	}
	

}

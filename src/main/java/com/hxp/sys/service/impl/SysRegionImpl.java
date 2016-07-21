package com.hxp.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysRegionDao;
import com.hxp.sys.po.SysRegion;
import com.hxp.sys.service.ISysRegionService;

@Service
public class SysRegionImpl implements ISysRegionService {

	@Autowired
	private SysRegionDao sysRegionDao;

	/**
	 * 基础数据省
	 */
	@Override
	public List<SysRegion> getAllProvince() {
		return sysRegionDao.getAllProvince();
	}

	/**
	 * 基础数据市sysRegionDao
	 */
	@Override
	public List<SysRegion> getRegionByPid(Long pid) {
		return sysRegionDao.getRegionByPid(pid);
	}

}

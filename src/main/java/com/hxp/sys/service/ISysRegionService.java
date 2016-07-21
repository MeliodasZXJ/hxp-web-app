package com.hxp.sys.service;

import java.util.List;

import com.hxp.sys.po.SysRegion;

/**
 * 省、市、县、区
 *
 */
public interface ISysRegionService {
	
    
    /**
	 * 基础数据省
	 */
	List<SysRegion>  getAllProvince();
	
	/**
	 * 基础数据市sysRegionDao
	 */
	List<SysRegion> getRegionByPid(Long pid);
	

}

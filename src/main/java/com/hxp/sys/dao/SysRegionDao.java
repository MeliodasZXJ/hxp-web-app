package com.hxp.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hxp.common.framework.dao.impl.SimpleDaoImpl;
import com.hxp.sys.po.SysRegion;

@Repository
public class SysRegionDao extends SimpleDaoImpl<SysRegion> {

	public List<SysRegion> getAllProvince(){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("status", "-1");
		map.put("pid", 0);
		List<SysRegion> sysRegionList = this.find("findRegion",map);
		return sysRegionList;
	}
	
	public List<SysRegion> getRegionByPid(Long pid){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("status", "-1");
		map.put("pid", pid);
		List<SysRegion> sysRegionList = this.find("findRegion",map);
		return sysRegionList;
	}

}

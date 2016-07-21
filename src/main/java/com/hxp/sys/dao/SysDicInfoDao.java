package com.hxp.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hxp.common.framework.dao.impl.SimpleDaoImpl;
import com.hxp.sys.po.SysDicInfo;

@Repository
public class SysDicInfoDao extends SimpleDaoImpl<SysDicInfo> {

	public List<SysDicInfo> getAllDicInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "-1");
		map.put("typeCode", 0);
		List<SysDicInfo> sysDicInfoList = this.find("findDicInfo", map);
		return sysDicInfoList;
	}

	public int updateByPrimaryKeySelective(SysDicInfo record) {
		return 0;
	}

	public SysDicInfo selectByPrimaryKey(Integer id) {
		return null;
	}

	public List<SysDicInfo> selectDicInfoByTypeCode(String typeCode) {
		return null;
	}

}

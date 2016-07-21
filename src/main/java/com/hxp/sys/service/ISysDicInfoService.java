package com.hxp.sys.service;

import java.util.List;

import com.hxp.sys.po.SysDicInfo;


/**
 * 字典Service接口
 *
 */
public interface ISysDicInfoService {
	

	int insert(SysDicInfo record);

	int update(SysDicInfo record);
	
	/**
	 * 根据字典类型Typecode查找字典值
	 * 
	 * @param typeCode
	 *
	 * @return
	 */
	List<SysDicInfo> selectSysDicInfoByTypeCode(String typeCode);



}

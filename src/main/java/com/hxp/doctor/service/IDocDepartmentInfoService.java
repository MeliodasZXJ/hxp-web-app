package com.hxp.doctor.service;

import java.util.List;

import com.hxp.doctor.po.DocDepartmentInfo;

/**
 * Created by qinjingyu on 2016/7/15.
 */
public interface IDocDepartmentInfoService {

	/***
	 *  获取科室信息
	 * @param pageNum
	 * @param pageSize
	 * @param docDepartmentInfo
	 * @return
	 */
	List<DocDepartmentInfo> findDocDepartmentInfoList();
}

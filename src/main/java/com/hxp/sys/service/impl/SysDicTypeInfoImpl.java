package com.hxp.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.SysDicTypeInfoDao;
import com.hxp.sys.po.SysDicTypeInfo;
import com.hxp.sys.service.ISysDicTypeInfoService;

@Service
public class SysDicTypeInfoImpl implements ISysDicTypeInfoService {

	@Autowired
	private SysDicTypeInfoDao sysDicTypeInfoDao;

	@Override
	public String selectSysDicType() {
		String treeData = "[";
		sysDicTypeInfoDao.find("findDicTypeInfo");
		List<SysDicTypeInfo> pList = sysDicTypeInfoDao.find("findDicTypeInfo");
		for (SysDicTypeInfo dicTypeInfo : pList) {
			treeData = treeData
					+ (treeData.equals("[") ? "" : ",")
					+ ("{\"text\":\"" + dicTypeInfo.getName()
							+ "\",\"code\":\"" + dicTypeInfo.getDicCode() + "\"}");
		}
		treeData += "]";
		return treeData;
	}
}

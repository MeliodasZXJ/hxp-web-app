package com.hxp.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.doctor.dao.DocDepartmentInfoDao;
import com.hxp.doctor.po.DocDepartmentInfo;
import com.hxp.doctor.service.IDocDepartmentInfoService;

/**
 * Created by qinjingyu on 2016/7/15.
 */
@Service
public class DocDepartmentInfoServiceImpl implements IDocDepartmentInfoService {

    @Autowired
    private DocDepartmentInfoDao docDepartmentInfoDao;

	@Override
	public List<DocDepartmentInfo> findDocDepartmentInfoList() {
		List<DocDepartmentInfo> docDepartmentInfoList = docDepartmentInfoDao.find();
        return docDepartmentInfoList;
	}
	
}

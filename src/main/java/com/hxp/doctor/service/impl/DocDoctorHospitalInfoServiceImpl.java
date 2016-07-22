package com.hxp.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.doctor.dao.DocDoctorHospitalInfoDao;
import com.hxp.doctor.po.DocDoctorHospitalInfo;
import com.hxp.doctor.service.IDocDoctorHospitalInfoService;

/**
 * Created by qinjingyu on 2016/7/13.
 */
@Service
public class DocDoctorHospitalInfoServiceImpl implements IDocDoctorHospitalInfoService {

    @Autowired
    private DocDoctorHospitalInfoDao docDoctorHospitalInfoDao;

	@Override
	public List<DocDoctorHospitalInfo> findDocDoctorHospitalInfoList(DocDoctorHospitalInfo docDoctorHospitalInfo) {
		List<DocDoctorHospitalInfo> docDoctorHospitalInfoList = docDoctorHospitalInfoDao.find(docDoctorHospitalInfo);
        return docDoctorHospitalInfoList;
	}

	@Override
	public void update(DocDoctorHospitalInfo docDoctorHospitalInfo) {
		// TODO Auto-generated method stub
		docDoctorHospitalInfoDao.update("updateByPrimaryKeySelective", docDoctorHospitalInfo);
	}

	@Override
	public void insert(DocDoctorHospitalInfo docDoctorHospitalInfo) {
		// TODO Auto-generated method stub
		docDoctorHospitalInfoDao.insert(docDoctorHospitalInfo);
		
	}
}

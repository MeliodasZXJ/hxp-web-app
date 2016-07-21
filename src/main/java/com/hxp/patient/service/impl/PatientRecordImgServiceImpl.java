package com.hxp.patient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.patient.dao.PatientRecordImgDao;
import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientRecordImgService;

@Service
public class PatientRecordImgServiceImpl implements IPatientRecordImgService {
	@Autowired
	private PatientRecordImgDao patientRecordImgDao;

	@Override
	public int insert(PatientRecordImg patientRecordImg) {
		return patientRecordImgDao.insert("insertPatientRecordImg",patientRecordImg);
	}

	@Override
	public void insertPatientRecordImgList(List<PatientRecordImg> patientRecordImgList) {
		for(PatientRecordImg patientRecordImg:patientRecordImgList) {
		   patientRecordImgDao.insert("insertPatientRecordImg",patientRecordImg);
		}
	}

	@Override
	public List<PatientRecordImgDto> findPatientRecordImgList(PatientRecordImg patientRecordImg) {
		return patientRecordImgDao.find("findListBySessionId", patientRecordImg);
	}

}

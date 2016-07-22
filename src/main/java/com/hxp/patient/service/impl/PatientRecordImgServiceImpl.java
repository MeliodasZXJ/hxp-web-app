package com.hxp.patient.service.impl;

import com.hxp.patient.dao.PatientRecordImgDao;
import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientRecordImgService;
import com.hxp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientRecordImgServiceImpl implements IPatientRecordImgService {
	@Autowired
	private PatientRecordImgDao patientRecordImgDao;

	@Override
	public int insert(PatientRecordImg patientRecordImg) {
		return patientRecordImgDao.insert("insertPatientRecordImg",patientRecordImg);
	}

	@Override
	public void insertPatientRecordImgList(String sessionId,String imageIds) {
		String[] imgIds=imageIds.split(",");
		if(imgIds!=null&&imgIds.length>0) {
			for (int i = 0; i < imgIds.length; i++) {
				PatientRecordImg patientRecordImg = new PatientRecordImg();
				patientRecordImg.setImgId(Long.parseLong(imgIds[i]));
				patientRecordImg.setSessionId(sessionId);
				patientRecordImg.setStatus(1);
				patientRecordImg.setCreateTime(DateUtil.getCurrentTime());
				patientRecordImgDao.insert("insertPatientRecordImg", patientRecordImg);
			}
		}
	}

	@Override
	public List<PatientRecordImgDto> findPatientRecordImgList(PatientRecordImg patientRecordImg) {
		return patientRecordImgDao.find("findListBySessionId", patientRecordImg);
	}

}

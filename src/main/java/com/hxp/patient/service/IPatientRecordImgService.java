package com.hxp.patient.service;

import java.util.List;

import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.patient.po.PatientRecordImg;

public interface IPatientRecordImgService {
	 /**
     * 写入患者聊天图片
     *
     * @param patientRecordImg
     * @return
     */
    int insert(PatientRecordImg patientRecordImg);
    
    /**
     * 批量写入患者聊天图片
     *
     * @param patientRecordImgList
     * @return
     */
    void insertPatientRecordImgList(List<PatientRecordImg> patientRecordImgList);
    
    
    /**
     * 根据会话id获取会诊记录图片
     * @param patientRecordImg
     * @return
     */
    List<PatientRecordImgDto> findPatientRecordImgList(PatientRecordImg patientRecordImg);
}

package com.hxp.patient.service;

import java.util.List;

import com.hxp.patient.po.PatientDoctorRel;

/**
 * Created by slyi on 2016/7/13.
 */
public interface IPatientDoctorRelService {

    /**
     * 患者医生会话关系
     *
     * @param patientDoctorRel
     * @return
     */
    int insert(PatientDoctorRel patientDoctorRel);


    /**
     * 获取患者医生会话关系
     * @param pageNum
     * @param pageSize
     * @param patientDoctorRel
     * @return
     */
    List<PatientDoctorRel> findPatientDoctorRelList(int pageNum, int pageSize, PatientDoctorRel patientDoctorRel);


    /**
     * 患者评分
     * @param patientDoctorRelList
     */
    void updatePatientDoctorRelList(List<PatientDoctorRel> patientDoctorRelList);

    /**
     * 查询PatientDoctorRel
     * @param patientDoctorRel
     * @return
     */
    PatientDoctorRel getByPatientDoctorRel(PatientDoctorRel patientDoctorRel);
    
    
}

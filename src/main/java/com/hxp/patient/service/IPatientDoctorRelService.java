package com.hxp.patient.service;

import com.hxp.patient.dto.PatientDcotorRelDto;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.vo.PatientDoctorRefVo;

import java.util.List;

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
     * @param patientDoctorRel
     * @return
     */
    List<PatientDoctorRel> findPatientDoctorRelList(PatientDoctorRel patientDoctorRel);


    /**
     * 获取患者医生会话关系
     * @param patientDoctorRel
     * @return
     */
    PatientDoctorRel findPatientDoctorRel(PatientDoctorRel patientDoctorRel);

    /**
     * 患者评分
     * @param patientDoctorRefVo
     */
    int updatePatientDoctorRelList(PatientDoctorRefVo patientDoctorRefVo);

    /**
     * 查询PatientDoctorRel
     * @param patientDoctorRel
     * @return
     */
    PatientDoctorRel getByPatientDoctorRel(PatientDoctorRel patientDoctorRel);

    /**
     * 获取患者医生会话关系  能获取医生头像，名称
     * @param patientDoctorRel
     * @return
     */
    List<PatientDcotorRelDto> findPatientDoctorRelDtoList(PatientDoctorRel patientDoctorRel);


}

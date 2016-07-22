package com.hxp.patient.service;

import java.util.List;

import com.hxp.patient.dto.PatientRecordDto;
import com.hxp.patient.po.PatientRecord;

/**
 * Created by slyi on 2016/7/12.
 */
public interface IPatientRecordService {
    /**
     * 根据ID删除PatientRecord
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

   /**
    * 写入患者会话记录
    * @param patientRecord
    * @param imageIds
    * @return
    */
    int insert(PatientRecord patientRecord,String imageIds);


    /**
     * 更新PatientRecord
     *
     * @param patientRecord
     * @return
     */
    int updateByPrimaryKey(PatientRecord patientRecord);
    
    /**
     * 更新PatientRecord 状态
     * @param patientRecord
     * @return
     */
    int updateStatusByPrimaryKey(PatientRecord patientRecord);

    /**
     * 查询PatientRecord
     *
     * @param patientRecord
     * @return
     */
    PatientRecord getByPatientRecord(PatientRecord patientRecord);

    PatientRecordDto getPatientRecord(PatientRecord patientRecord);
    
    
    /**
     * 医生查询会诊中心
     *
     * @param patientRecord
     * @return
     */
    List<PatientRecordDto> findNewPatientRecord(PatientRecord patientRecord);

    /**
     * 医生查询我的会诊
     *
     * @param
     * @return
     */
    List<PatientRecordDto> findMyPatientRecord(long doctorId);
    

    /**
     * 查询提问表
     * @param patientRecord
     * @return
     */
    List<PatientRecord> findPatientRecordList(PatientRecord patientRecord) ;
    
    /**
     * 查询提问
     * @param doctorId    医生id
     * @param type		   1:查询最新提问,2查询我的会诊	
     * @return
     */
    List<PatientRecordDto> findPatientRecordList(Long doctorId,int type);
    
    
    /**
     * 抢答问题
     * @param doctorId     医生id
     * @param patientId	        患者id
     * @param sessionId    房间id
     * @return
     */
    boolean patientRecordResponder(Long doctorId,Long patientId,String sessionId);





    /**
     * 获取问题详情
     * @param sessionId
     * @return
     */
   PatientRecordDto getPatientRecordById(String sessionId);
}

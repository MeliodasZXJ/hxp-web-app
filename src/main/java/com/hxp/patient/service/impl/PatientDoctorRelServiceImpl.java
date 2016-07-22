package com.hxp.patient.service.impl;

import com.hxp.patient.dao.PatientDoctorRelDao;
import com.hxp.patient.dto.PatientDcotorRelDto;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.service.IPatientDoctorRelService;
import com.hxp.patient.service.IPatientRecordService;
import com.hxp.patient.vo.PatientDoctorRefVo;
import com.hxp.util.ConstantsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slyi on 2016/7/13.
 */
@Service
public class PatientDoctorRelServiceImpl implements IPatientDoctorRelService {
    @Autowired
    private PatientDoctorRelDao patientDoctorRelDao;

    @Autowired
    private IPatientRecordService patientRecordService;

    @Override
    public int insert(PatientDoctorRel patientDoctorRel) {
        return patientDoctorRelDao.insert("insertSelective",patientDoctorRel);
    }

    @Override
    public List<PatientDoctorRel> findPatientDoctorRelList(PatientDoctorRel patientDoctorRel) {
        List<PatientDoctorRel> patientDoctorRelList = patientDoctorRelDao.find(patientDoctorRel);
        return patientDoctorRelList;
    }

    @Override
    public List<PatientDcotorRelDto> findPatientDoctorRelDtoList(PatientDoctorRel patientDoctorRel) {
        List<PatientDcotorRelDto> patientDoctorRelDtoList = patientDoctorRelDao.find("findPatientDoctorRelList",patientDoctorRel);
        return patientDoctorRelDtoList;
    }

    /**
     * 获取患者医生会话关系
     * @param patientRel
     * @return  patientDoctorRel
     */
    @Override
    public PatientDoctorRel findPatientDoctorRel(PatientDoctorRel patientRel){
        List<PatientDoctorRel> patientDoctorRelList = patientDoctorRelDao.find(patientRel);
        PatientDoctorRel patientDoctorRel = null;
        if(patientDoctorRelList != null && patientDoctorRelList.size()>0){
            patientDoctorRel = patientDoctorRelList.get(0);
        }
        return patientDoctorRel;
    }

    /**
     * 批量更新
     * @param patientDoctorRefVo
     */
    @Override
    public int updatePatientDoctorRelList(PatientDoctorRefVo patientDoctorRefVo) {
        int count=1;

        String sessionId=patientDoctorRefVo.getSessionId();
        long patientId=patientDoctorRefVo.getPatientId();
        String doctorIds=patientDoctorRefVo.getDoctorIds();
        String scores=patientDoctorRefVo.getScores();
        //医生id分割
        String[] doctorStrs= doctorIds.split(",");
        //评分分割
        String[] scoreStrs= scores.split(",");

        //医生ID和评分数量判断
        if(doctorStrs.length!=scoreStrs.length)
        {
            count=-1;
            return count;
        }

        for(int i=0;i< doctorStrs.length;i++)
        {
            String doctorId=doctorStrs[i];
            String score=scoreStrs[i];

            PatientDoctorRel patientDoctorRel=new PatientDoctorRel();
            patientDoctorRel.setSessionId(sessionId);
            patientDoctorRel.setPatientId(patientId);
            patientDoctorRel.setDocId(Long.parseLong(doctorId));
            patientDoctorRel.setAccess(score);//评价
            patientDoctorRelDao.update("updatePatientDoctorRel",patientDoctorRel);
        }

        PatientRecord patientReocrd = new PatientRecord();
        patientReocrd.setSessionId(sessionId);
        patientReocrd.setStatus(3);
        patientRecordService.updateStatusByPrimaryKey(patientReocrd);
        return count;
    }


    /**
     * 查询单个
     * @param patientDoctorRel
     * @return
     */
    @Override
    public PatientDoctorRel getByPatientDoctorRel(PatientDoctorRel patientDoctorRel) {
        return patientDoctorRelDao.get(patientDoctorRel);
    }

}

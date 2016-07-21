package com.hxp.patient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hxp.patient.dao.PatientDoctorRelDao;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.service.IPatientDoctorRelService;

/**
 * Created by slyi on 2016/7/13.
 */
@Service
public class PatientDoctorRelServiceImpl implements IPatientDoctorRelService {
    @Autowired
    private PatientDoctorRelDao patientDoctorRelDao;

    @Override
    public int insert(PatientDoctorRel patientDoctorRel) {
        return patientDoctorRelDao.insert("insertPatientDoctorRel",patientDoctorRel);
    }

    @Override
    public List<PatientDoctorRel> findPatientDoctorRelList(int pageNum, int pageSize, PatientDoctorRel patientDoctorRel) {
        PageHelper.startPage(pageNum, pageSize);
        List<PatientDoctorRel> patientDoctorRelList = patientDoctorRelDao.find(patientDoctorRel);
        return patientDoctorRelList;
    }

    /**
     * 批量更新
     * @param patientDoctorRelList
     */
    @Override
    public void updatePatientDoctorRelList(List<PatientDoctorRel> patientDoctorRelList) {
        for (PatientDoctorRel patientDoctorRel:patientDoctorRelList) {
            patientDoctorRelDao.update("updatePatientDoctorRel",patientDoctorRel);
        }
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

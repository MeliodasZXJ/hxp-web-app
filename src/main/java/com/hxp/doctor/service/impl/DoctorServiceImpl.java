package com.hxp.doctor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hxp.doctor.dao.DoctorDao;
import com.hxp.doctor.po.Doctor;
import com.hxp.doctor.service.IDoctorService;

/**
 * Created by anpushang on 2016/7/9.
 */
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    private DoctorDao doctorDao;
    @Override
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return doctorDao.delete(id);
    }

    @Override
    public int insert(Doctor doctor) throws Exception {
        return doctorDao.insert(doctor);
    }

    @Override
    public Doctor selectByPrimaryKey(Integer id) throws Exception {
        return doctorDao.get(id);
    }
}

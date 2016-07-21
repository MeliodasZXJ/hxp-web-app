package com.hxp.patient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.patient.dao.PatientCustomerDao;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.service.IPatientCustomerService;
import com.hxp.util.DateUtil;


/**
 * Created by Administrator on 2016/7/12.
 */
@Service
public class PatientCustomerServiceImpl implements IPatientCustomerService {

    @Autowired
    private PatientCustomerDao patientCustomerDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return patientCustomerDao.delete(id);
    }

    @Override
    public int insert(PatientCustomer patientCustomer) {
        patientCustomer.setCreateTime(DateUtil.getDate());
        patientCustomer.setState(1);
        return patientCustomerDao.insert(patientCustomer);
    }

    @Override
    public PatientCustomer selectByPrimaryKey(Long id) {
        return patientCustomerDao.get(id);
    }

    @Override
    public int updateByPrimaryKey(PatientCustomer patientCustomer) {
        return patientCustomerDao.update(patientCustomer);
    }

    /**
     * 根据手机号 密码查询数据，获取患者信息
     * @param patientCustomer
     * @return
     */
    @Override
    public PatientCustomer getByPatientCustomer(PatientCustomer patientCustomer) {
        return patientCustomerDao.get("getPatientCustomer",patientCustomer);
    }

    @Override
    public PatientCustomer updateByPatientCustomer(PatientCustomer patientCustomer) {
        return patientCustomerDao.get("updateByPatientCustomer",patientCustomer);
    }

    /**
     * 根据手机号查询
     * @param patientCustomer
     * @return
     */
    @Override
    public PatientCustomer getByPatientCustomerMobile(PatientCustomer patientCustomer) {
        return patientCustomerDao.get("getByPatientCustomerMobile",patientCustomer);
    }

	@Override
	public PatientCustomer getByPatientMobile(String mobile) {
	
		return  patientCustomerDao.get("getByPatientMobile",mobile);
	}


}

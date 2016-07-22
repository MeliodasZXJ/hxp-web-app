package com.hxp.patient.service;

import java.util.List;

import com.hxp.patient.po.PatientCustomer;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface IPatientCustomerService {

    /**
     * 根据ID删除PatientCustomer
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 患者注册
     * @param patientCustomer
     * @return
     */
    int insert(PatientCustomer patientCustomer);

    /**
     * 根据ID查询PatientCustomer
     * @param id
     * @return
     */
    PatientCustomer selectByPrimaryKey(Long id);

    /**
     * 更新PatientCustomer
     * @param patientCustomer
     * @return
     */
    int updateByPrimaryKey(PatientCustomer patientCustomer);

    /**
     * 查询PatientCustomer（根据手机号 密码查询数据）
     * @param patientCustomer
     * @return
     */
    PatientCustomer getByPatientCustomer(PatientCustomer patientCustomer);

    /**
     * 更新PatientCustomer
     */
    PatientCustomer updateByPatientCustomer(PatientCustomer patientCustomer);

    /**
     * 根据手机号查询
     */
    PatientCustomer getByPatientCustomerMobile(PatientCustomer patientCustomer);

    /**
     * 根据手机号查询
     */
    PatientCustomer getByPatientMobile(String mobile);
    
    
    List<PatientCustomer> getPatientCustomerList(PatientCustomer patientCustomer);

    PatientCustomer selectByPatientById(Long id);
}

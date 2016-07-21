package com.hxp.doctor.service;

import java.util.List;

import com.hxp.doctor.po.DocDoctorHospitalInfo;

/**
 * Created by qinjingyu on 2016/7/13.
 */
public interface IDocDoctorHospitalInfoService {

    /***
     * 获取医院信息
     * @param docDoctorHospitalInfo
     * @return
     */
    List<DocDoctorHospitalInfo> findDocDoctorHospitalInfoList(DocDoctorHospitalInfo docDoctorHospitalInfo);
}

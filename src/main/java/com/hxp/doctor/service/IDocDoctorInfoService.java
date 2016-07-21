package com.hxp.doctor.service;

import java.util.List;

import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.patient.po.PatientCustomer;

/**
 * Created by qinjingyu on 2016/7/12.
 */
public interface IDocDoctorInfoService {

    /***
     *  根据ID删除
     * @param id
     * @return
     * @throws Exception
     */
    int deleteDocDoctorInfoKey(Integer id) throws Exception;

    /***
     * 新增
     * @param doctorInfo
     * @return
     */
    int insert(DocDoctorInfo doctorInfo)throws Exception;

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    DocDoctorInfo selectDocDoctorInfoKey(Integer id)throws Exception;

    /***
     * 查询DocDoctorInfo
     * @param docDoctorInfo
     * @return
     * @throws Exception
     */
    DocDoctorInfo getByDocDoctorInfo(DocDoctorInfo docDoctorInfo)throws Exception;

    /***
     * 查询手机号是否存在
     * @param docDoctorInfo
     * @return
     * @throws Exception
     */
    DocDoctorInfo getDocDoctorInfoByMobile(DocDoctorInfo docDoctorInfo)throws Exception;

    /***
     * 修改DocDoctorInfo
     * @param docDoctorInfo
     * @return
     * @throws Exception
     */
    int updateByResetPassword(DocDoctorInfo docDoctorInfo)throws Exception;

    /***
     * 修改认证信息
     * @param docDoctorInfo
     * @return
     * @throws Exception
     */
    int updateDocDoctorInfoByAttestation(DocDoctorInfo docDoctorInfo)throws Exception;
    
    
    /**
	 * 获取所有医生
	 * 查所有医生时, 
	 * @param ddintd
	 * @return
	 */
	public List<DocDoctorInfoDto> getDoctorInfoList(DocDoctorInfoDto ddid);
	
	
	int updateDoctor(DocDoctorInfo docDoctorInfo);
	
    /**
     * 根据手机号查询
     */
    DocDoctorInfo getByDocMobile(String mobile);

    /***
     * 根据用户名和密码查询
     * @param docDoctorInfo
     * @return
     */
    DoctorDto getDoctorByMobileAndPass(DocDoctorInfo docDoctorInfo);

    /***
     * 修改医生个人信息接口
     * @param doctorDto
     * @return
     */
    Integer updateDoctorInfo(DoctorDto doctorDto);
}

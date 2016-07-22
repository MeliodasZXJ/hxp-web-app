package com.hxp.doctor.service;

import java.util.List;

import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.vo.DoctorVo;
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
     * @param mobile
     * @return
     * @throws Exception
     */
    DocDoctorInfo getDocDoctorInfoByMobile(String mobile)throws Exception;

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
     * doctorType不传值,查名医风采时,传doctorType=1
	 * @param ddid
	 * @return
	 */
	List<DocDoctorInfoDto> getDoctorInfoList(DocDoctorInfoDto ddid);
	
	
    /**
	 * 患者端获取所有医生
     * doctorType不传值,查名医风采时,传doctorType=1
	 * @param ddid
	 * @return
	 */
	List<DocDoctorInfoDto> getDoctorInfoByPatientList(DocDoctorInfoDto ddid);
	
	
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
     * 查找doctorDto对象
     * @param dbDocDoctor
     * @return
     */
    DoctorDto getDoctorByDoctorId(DoctorDto dbDocDoctor);

    /***
     * 修改医生个人信息接口
     * @param doctorVo
     * @return
     */
    Integer updateDoctorInfo(DoctorVo doctorVo);

    /***
     * 查询关注我的患者
     * @param doctorId
     * @return
     */
    List<PatientCustomer> selectCollectionPatient(Long doctorId);
}

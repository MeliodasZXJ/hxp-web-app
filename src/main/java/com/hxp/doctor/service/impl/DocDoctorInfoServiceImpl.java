package com.hxp.doctor.service.impl;

import java.util.List;

import com.hxp.doctor.dto.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.doctor.dao.DocDoctorInfoDao;
import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.util.DateUtil;

/**
 * Created by qinjingyu on 2016/7/12.
 */
@Service
public class DocDoctorInfoServiceImpl implements IDocDoctorInfoService {

    @Autowired
    private DocDoctorInfoDao doctorInfoDao;

    @Override
    public int deleteDocDoctorInfoKey(Integer id) throws Exception {
        return doctorInfoDao.delete(id);
    }

    @Override
    public int insert(DocDoctorInfo doctorInfo) throws Exception {
        doctorInfo.setStatus(0);//认证状态  -1删除 0:未认证,1认证中,2认证未通过,3已认证,4重新认证
        doctorInfo.setCreateTime(DateUtil.getDate());//创建时间
        return doctorInfoDao.insert(doctorInfo);
    }

    @Override
    public DocDoctorInfo selectDocDoctorInfoKey(Integer id) throws Exception {
        return doctorInfoDao.get(id);
    }

    @Override
    public DocDoctorInfo getByDocDoctorInfo(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.get("getByDocDoctorInfo",docDoctorInfo);
    }

    @Override
    public DocDoctorInfo getDocDoctorInfoByMobile(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.get("getDocDoctorInfoByMobile",docDoctorInfo);
    }

    @Override
    public int updateByResetPassword(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.update("updateByResetPassword",docDoctorInfo);
    }

    @Override
    public int updateDocDoctorInfoByAttestation(DocDoctorInfo docDoctorInfo) throws Exception {
        docDoctorInfo.setStatus(1);//认证状态  -1删除 0:未认证,1认证中,2认证未通过,3已认证,4重新认证
        return doctorInfoDao.update("updateByResetPassword",docDoctorInfo);
    }

    //doctorType不传值,查名医风采时,传doctorType=1
	@Override
	public List<DocDoctorInfoDto> getDoctorInfoList(DocDoctorInfoDto ddid) {
		// TODO Auto-generated method stub
		return doctorInfoDao.find(ddid);
	}

	@Override
	public int updateDoctor(DocDoctorInfo docDoctorInfo){
		return doctorInfoDao.update("updateByPrimaryKeySelective",docDoctorInfo);
	}
	/**
	 * 查询数据库
	 */
	@Override
	public DocDoctorInfo getByDocMobile(String mobile) {
		
		return doctorInfoDao.get("getByDocMobile",mobile);
	}

    @Override
    public DoctorDto getDoctorByMobileAndPass(DocDoctorInfo docDoctorInfo) {
        return doctorInfoDao.get("getDoctorByMobileAndPass",docDoctorInfo);
    }

    @Override
    public Integer updateDoctorInfo(DoctorDto doctorDto) {
        return doctorInfoDao.updateByObject("updateDoctorInfo",doctorDto);
    }
}

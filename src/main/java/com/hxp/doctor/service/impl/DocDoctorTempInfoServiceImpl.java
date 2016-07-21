package com.hxp.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxp.doctor.dao.DocDoctorInfoDao;
import com.hxp.doctor.dao.DocDoctorInfoTempDao;
import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.po.DocDoctorInfoTemp;
import com.hxp.doctor.service.IDocDoctorTempInfoService;

@Service
public class DocDoctorTempInfoServiceImpl implements IDocDoctorTempInfoService {
	@Autowired
	private DocDoctorInfoTempDao docDoctorInfoTempDao;
	
	@Autowired
	private  DocDoctorInfoDao docDoctorInfoDao;

	@Override
	public List<DocDoctorInfoTempDto> getAllDocTempInfo(DocDoctorInfoTempDto ddintd) {
		// TODO Auto-generated method stub
		PageHelper.startPage(ddintd.getPageNum(), ddintd.getPageSize());
		List<DocDoctorInfoTempDto> list = docDoctorInfoTempDao.findAll(ddintd);
		return list;
	}

	@Override
	public void delete(Integer tempId) {
		// TODO Auto-generated method stub
		docDoctorInfoTempDao.delete(tempId);
	}

	@Override
	public void updateAuth(DocDoctorInfoTemp docDoctorInfoTemp) {
		// TODO Auto-generated method stub
		if(docDoctorInfoTemp.getStatus()==-1){ //认证不通过
			docDoctorInfoTempDao.update("updateByPrimaryKeySelective",docDoctorInfoTemp);
		}else{
			docDoctorInfoTemp =docDoctorInfoTempDao.get("selectByPrimaryKey", docDoctorInfoTemp.getId());
			DocDoctorInfo doctor = docDoctorInfoDao.get("selectByPrimaryKey", docDoctorInfoTemp.getDocId());
			doctor.setName(docDoctorInfoTemp.getName());
			doctor.setSex(docDoctorInfoTemp.getSex());
			doctor.setIntro(docDoctorInfoTemp.getIntro());
			doctor.setTerritory(docDoctorInfoTemp.getTerritory());
			doctor.setHospitalId(docDoctorInfoTemp.getHospitalId());
			doctor.setDeptId(docDoctorInfoTemp.getDeptId());
			doctor.setPidPath(docDoctorInfoTemp.getPidPath());
			doctor.setHeadPath(docDoctorInfoTemp.getHeadPath());
			doctor.setType(docDoctorInfoTemp.getType());
			docDoctorInfoDao.update("updateByPrimaryKeySelective",doctor);
//			docDoctorInfoTempDao.delete("deleteByPrimaryKey",docDoctorInfoTemp.getId());
			
		}
	}
	
	/**
	 * 插入一条医生修改临时数据
	 * @param docDoctorInfoTemp
	 */
	@Override
	public void insert(DocDoctorInfoTemp docDoctorInfoTemp){
		docDoctorInfoTempDao.insert(docDoctorInfoTemp);
	}

}

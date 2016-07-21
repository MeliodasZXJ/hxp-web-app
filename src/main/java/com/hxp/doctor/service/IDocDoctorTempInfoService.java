package com.hxp.doctor.service;

import java.util.List;

import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.po.DocDoctorInfoTemp;

public interface IDocDoctorTempInfoService {
	
	/**
	 * 获取所有信息
	 * @param ddintd
	 * @return
	 */
	public List<DocDoctorInfoTempDto> getAllDocTempInfo(DocDoctorInfoTempDto ddintd);
	
	public void delete(Integer tempId);
	
	
	/**
	 * 医生认证
	 * @param docDoctorInfoTemp
	 */
	public void updateAuth(DocDoctorInfoTemp docDoctorInfoTemp);
	
	
	/**
	 * 插入一条医生修改临时数据
	 * @param docDoctorInfoTemp
	 */
	public void insert(DocDoctorInfoTemp docDoctorInfoTemp);
	
	
}

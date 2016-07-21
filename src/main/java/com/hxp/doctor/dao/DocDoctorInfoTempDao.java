package com.hxp.doctor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hxp.common.framework.dao.impl.SimpleDaoImpl;
import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.po.DocDoctorInfoTemp;

@Repository
public class DocDoctorInfoTempDao extends SimpleDaoImpl<DocDoctorInfoTemp> {

	public List<DocDoctorInfoTempDto> findAll(DocDoctorInfoTempDto ddintd) {
		return this.find(ddintd);
	}
	
	
}

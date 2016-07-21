package com.hxp.commonality.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.commonality.dao.CommonFeedBackDao;
import com.hxp.commonality.dto.CommonFeedBackDto;
import com.hxp.commonality.po.CommonFeedBack;
import com.hxp.commonality.service.ICommonFeedBackService;
import com.hxp.util.DateUtil;

@Service
public class CommonFeedBackServiceImpl implements ICommonFeedBackService {
	
	 @Autowired
	 private CommonFeedBackDao commonFeedBackDao;
	 
	@Override
	public int deleteByPrimaryKey(Long id) {
		return  commonFeedBackDao.delete("deleteByPrimaryKey",id);
	}

	@Override
	public int insert(CommonFeedBack commonFeedBack) {
		commonFeedBack.setStatus(0);//回馈状态  0,未处理   1，已处理
		commonFeedBack.setCreateTime(DateUtil.getDate());//创建时间
		return commonFeedBackDao.insert(commonFeedBack);
	}

	@Override
	public List<CommonFeedBack> findPatientFeedBackList(CommonFeedBack commonFeedBack) {
		// TODO Auto-generated method stub
		return commonFeedBackDao.find("findPatientFeedBackList", commonFeedBack);
	}

	@Override
	public List<CommonFeedBackDto> findDoctorFeedBackList(CommonFeedBack commonFeedBack) {
		// TODO Auto-generated method stub
		return commonFeedBackDao.find("findDoctorFeedBackList",commonFeedBack);
	}

	@Override
	public void update(CommonFeedBack cfb) {
		// TODO Auto-generated method stub
		commonFeedBackDao.update("updateByPrimaryKeySelective",cfb);
	}


//	@Override
//	public int insertSelective(CommonFeedBack record) {
//
//		return commonFeedBackDao.insert("insertSelective",record);
//	}
//
//	@Override
//	public CommonFeedBack selectByPrimaryKey(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int updateByPrimaryKeySelective(CommonFeedBack record) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int updateByPrimaryKey(CommonFeedBack record) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}

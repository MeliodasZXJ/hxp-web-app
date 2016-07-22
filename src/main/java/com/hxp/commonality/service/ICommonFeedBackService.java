package com.hxp.commonality.service;

import java.util.List;

import com.hxp.commonality.dto.CommonFeedBackDto;
import com.hxp.commonality.po.CommonFeedBack;

public interface ICommonFeedBackService {
	int deleteByPrimaryKey(Long id);

	int insert(CommonFeedBack record);
	//
	// int insertSelective(CommonFeedBack record);
	//
	// CommonFeedBack selectByPrimaryKey(Long id);
	//
	// int updateByPrimaryKeySelective(CommonFeedBack record);
	//
	// int updateByPrimaryKey(CommonFeedBack record);

	
	void update(CommonFeedBack cfb);
	
	
	/**
	 * 查询患者反馈信息
	 * 
	 * @param status
	 *            反馈处理状态
	 * @return
	 */
	public List<CommonFeedBack> findPatientFeedBackList(CommonFeedBack cfb);

	/**
	 * 查询医生反馈信息
	 * 
	 * @param status
	 *            反馈处理状态
	 * @return
	 */
	public List<CommonFeedBackDto> findDoctorFeedBackList(CommonFeedBack cfb);
	
	
	public List<CommonFeedBack> findFeedBackList(CommonFeedBack cfb);

}
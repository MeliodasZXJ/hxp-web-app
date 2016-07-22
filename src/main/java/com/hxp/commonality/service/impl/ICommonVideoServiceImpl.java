package com.hxp.commonality.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.commonality.dao.CommonVideoDao;
import com.hxp.commonality.dto.CommonVideoDto;
import com.hxp.commonality.service.ICommonVideoService;
import com.hxp.commonality.vo.CommonVideoVo;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月20日 上午11:27:16 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
@Service
public class ICommonVideoServiceImpl implements ICommonVideoService {
	
	@Autowired
	private CommonVideoDao commonVideoDao;
	
	@Override
	public List<CommonVideoDto> findCommonVideoList(CommonVideoVo commonVideoVo) {
		return commonVideoDao.find("findCommonVideoByVideoType",commonVideoVo);
	}
	
	
	@Override
	public CommonVideoDto    getCommonVideoByPrimaryKey(CommonVideoVo commonVideoVo){
		return commonVideoDao.get("getCommonVideoByPrimaryKey", commonVideoVo);
				
	}

}

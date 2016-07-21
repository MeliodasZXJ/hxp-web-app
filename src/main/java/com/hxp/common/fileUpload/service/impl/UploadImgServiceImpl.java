package com.hxp.common.fileUpload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.common.fileUpload.dao.UploadImgDao;
import com.hxp.common.fileUpload.po.UploadImg;
import com.hxp.common.fileUpload.service.IUploadImgService;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月15日 下午2:42:03 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
@Service
public class UploadImgServiceImpl implements IUploadImgService {

	@Autowired
	private UploadImgDao uploadImgDao;
	
	@Override
	public int insertUploadImg(UploadImg uploadImg) {
		return uploadImgDao.insert("insertUploadImg",uploadImg);
	}

	@Override
	public UploadImg getUploadImg(UploadImg uploadImg) {
		
		return uploadImgDao.get("getUploadImgById",uploadImg);
	}

}

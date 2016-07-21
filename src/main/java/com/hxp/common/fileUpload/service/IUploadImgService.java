package com.hxp.common.fileUpload.service;

import com.hxp.common.fileUpload.po.UploadImg;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月15日 下午2:40:13 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public interface IUploadImgService {
	 /**
     * 插入图片记录
     * @param rcloudChatLog
     * @return
     */
    int insertUploadImg(UploadImg uploadImg);
    /**
     * 查询图片记录
     * @param uploadImg
     * @return
     */
    UploadImg getUploadImg(UploadImg uploadImg);
}

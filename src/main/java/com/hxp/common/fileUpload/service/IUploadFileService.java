package com.hxp.common.fileUpload.service;

import org.springframework.web.multipart.MultipartFile;

import com.hxp.common.fileUpload.po.UploadImgResponse;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月15日 下午3:08:31 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public interface IUploadFileService {
    /**
     * 保存上传图片
     * @param thumbnail 是否生成缩略图
     * @param files 上传文件数组
     * @return 上传文件保存信息
     * @throws Exception 
     */
    public UploadImgResponse updateForUpload(boolean thumbnail, MultipartFile[] files) throws Exception;

}

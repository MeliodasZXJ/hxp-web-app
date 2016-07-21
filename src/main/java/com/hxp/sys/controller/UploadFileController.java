package com.hxp.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hxp.base.BaseController;
import com.hxp.common.fileUpload.po.UploadImgResponse;
import com.hxp.common.fileUpload.service.IUploadFileService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;



/**
 * Created by liuyang on 2016/7/13.
 */
@RestController
@RequestMapping("/app/v1_6")
public class UploadFileController extends BaseController {

	@Autowired
	private IUploadFileService uploadFileService;


	/**
	 * 上传图片
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/updateFile")
	public CommonResult<Object> updateFile(String token,@RequestParam(value = "files", required = false) MultipartFile[] files,String thumbnail) {

		
		CommonResult<Object> commonResult = new CommonResult<Object>();// 返回通用格式数据
		try {
			
			
			
	          //验证医生端的token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            boolean flag = false;
			if(!StringUtil.isBlank(thumbnail)){
				if(thumbnail.equals("true") || thumbnail.equals("flase")){
					flag = Boolean.valueOf(thumbnail);
				}else{
					commonResult.setResult(ConstantsStatus.SC5001, "thumbnail传数不正确!", false);
					return commonResult;
				}
				
			}
			
			UploadImgResponse uploadImgResponse = uploadFileService.updateForUpload(flag, files);
			commonResult.setResult(ConstantsStatus.SC2000, "上传图片成功", true,uploadImgResponse);
		} catch (Exception e) {
			logger.error("获取专家言论详情出现异常！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "上传图片出现异常!", false);
		}

		return commonResult;

	}


}

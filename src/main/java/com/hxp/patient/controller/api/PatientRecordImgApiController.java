package com.hxp.patient.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientRecordImgService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.DateUtil;
import com.hxp.util.StringUtil;

@RestController
@RequestMapping("/app/v1_6/service/customer")
public class PatientRecordImgApiController extends BaseController {
	@Autowired
	private IPatientRecordImgService patientRecordImgService;

	/**
	 * 关联问题和提问图片
	 * @param token
	 * @param sessionId
	 * @param imageIds
	 * @return
	 */
	@RequestMapping(value = "/savePatientRecordImgList", method = RequestMethod.POST)
	public CommonResult<Object> savePatientRecordImgList(String token, String sessionId, String imageIds) {
		CommonResult<Object> commonResult = null;
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			 //sessionId为空判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "sessionId不能为空!", false);
                return commonResult;
            }

			try {
				
				//图片id为空判断
              if(StringUtil.isBlank(imageIds)) {
	                String[] imgIds=imageIds.split(",");
					// 获取当前时间
					Date currentTime = DateUtil.getCurrentTime();
					
					List<PatientRecordImg> patientRecordImgList = new ArrayList<PatientRecordImg>();
					for(int i=0;i< imgIds.length;i++){
						PatientRecordImg patientRecordImg = new PatientRecordImg();
						patientRecordImg.setImgId(Long.parseLong(imgIds[i]));
						patientRecordImg.setSessionId(sessionId);
						patientRecordImg.setStatus(1);
						patientRecordImg.setCreateTime(currentTime);
						patientRecordImgList.add(patientRecordImg);
					}
					patientRecordImgService.insertPatientRecordImgList(patientRecordImgList);
					commonResult.setResult(ConstantsStatus.SC2000, "写入图片路径成功！", true);
	            }
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("写入图片路径失败！", ex.fillInStackTrace());
			}

		} catch (Exception e) {
			logger.error("写入图片路径失败！", e.fillInStackTrace());
		}
		return commonResult;
	}
	
	/**
	 * 查询提问图片
	 * @param token
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/patientRecordImgList", method = RequestMethod.GET)
	public CommonResult<Object> patientRecordImgList(String token, String sessionId) {
		CommonResult<Object> commonResult = null;
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			 //sessionId为空判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "sessionId不能为空!", false);
                return commonResult;
            }

			try {
				PatientRecordImg patientRecordImg=new PatientRecordImg();
				patientRecordImg.setSessionId(sessionId);
				List<PatientRecordImgDto> patientRecordImgDtoList=patientRecordImgService.findPatientRecordImgList(patientRecordImg);
				commonResult.setResult(ConstantsStatus.SC2000, "获取图片路径成功！", true,patientRecordImgDtoList);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("获取图片路径失败！", ex.fillInStackTrace());
			}

		} catch (Exception e) {
			logger.error("获取图片路径失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

}

package com.hxp.patient.controller.api;

import com.hxp.base.BaseController;
import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientRecordImgService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
			//判断token
			commonResult = validationToken(token);

			//commonResult的 returnStatus值为flase,出现异常,要返回
			if(!commonResult.isReturnStatus()){
				return commonResult;
			}

			 //sessionId为空判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "sessionId不能为空!", false);
                return commonResult;
            }

			//图片id为空判断
		    if(StringUtil.isBlank(imageIds)) {
				patientRecordImgService.insertPatientRecordImgList(sessionId,imageIds);
				commonResult.setResult(ConstantsStatus.SC2000, "写入图片路径成功！", true);
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
			//判断token
			commonResult = validationToken(token);

			//commonResult的 returnStatus值为flase,出现异常,要返回
			if(!commonResult.isReturnStatus()){
				return commonResult;
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

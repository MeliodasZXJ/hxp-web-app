package com.hxp.doctor.controller.api;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.patient.dto.PatientRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.service.IPatientRecordService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * 医生端会诊中心接口 Created by liuyang on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DoctorRecordApiController extends BaseController {

	// 获取问题列表接口
	@Autowired
	private IPatientRecordService patientRecordService;

	/**
	 * 最新提问,我的会诊
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/latestQuestions", method = RequestMethod.POST)
	public CommonResult<Object> latestQuestions(String token, String type) {
		CommonResult<Object> commonResult = null;
		try {
			// 验证医生端的token
			commonResult = validationToken(token);

			// commonResult的 returnStatus值为flase,出现异常,要返回
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			if (StringUtil.isBlank(type)) {
				commonResult.setResult(ConstantsStatus.SC5001, "type不能为空", false);
				return commonResult;
			}

			if (!(type.equals("1") || type.equals("2"))) {
				commonResult.setResult(ConstantsStatus.SC5001, "type值不正确", false);
				return commonResult;
			}

			PageHelper.startPage(getPageNum(), getPageSize());
			DoctorDto doctorInfo = getDoctorByToken(token);
			List<PatientRecordDto> patientRecordList = patientRecordService.findPatientRecordList(doctorInfo.getDoctorId(), Integer.valueOf(type));
			commonResult.setResult(ConstantsStatus.SC2000, "获取数据成功", true, patientRecordList);

		} catch (Exception e) {
			logger.error("获取最新提问失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "获取最新提问出现异常", false);
		}
		return commonResult;
	}

	/**
	 * 会诊中心-最新提问详情
	 *
	 * @param token
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/latestQuestionsDetails", method = RequestMethod.POST)
	public CommonResult<Object> latestQuestionsDetails(String token, String sessionId) {
		CommonResult<Object> commonResult = null;
		try {

			// 验证医生端的token
			/*
			 * commonResult = validationToken(token);
			 * 
			 * //commonResult的 returnStatus值为flase,出现异常,要返回 if
			 * (!commonResult.isReturnStatus()) { return commonResult; }
			 * 
			 * //验证groupId是否为空 if (StringUtil.isBlank(sessionId)) {
			 * commonResult.setResult(ConstantsStatus.SC5001, "房间id不能为空", true,
			 * token); return commonResult; }
			 */

			PatientRecord patientRecord = new PatientRecord();
			patientRecord.setSessionId(sessionId);

			// 从数据库里根据groupId获取详情
			PatientRecordDto patientRecordDto = patientRecordService.getPatientRecordById(sessionId);

			commonResult.setResult(ConstantsStatus.SC2000, "获取提问详情成功", true, patientRecordDto);

		} catch (Exception e) {
			logger.error("获取最新提问详情失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "获取最新提问详情失败!", false);
		}
		return commonResult;
	}

	/**
	 * 会诊中心-最新提问-我的会诊-抢答
	 *
	 * @param token
	 * @param sessionId
	 *            房间id
	 * @return
	 */
	@RequestMapping(value = "/latestQuestionsResponder", method = RequestMethod.GET)
	public CommonResult<Object> latestQuestionsResponder(String token, String sessionId) {
		CommonResult<Object> commonResult = null;
		try {

			// 验证医生端的token
			commonResult = validationToken(token);

			// commonResult的 returnStatus值为flase,出现异常,要返回
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			// 验证groupId是否为空
			if (StringUtil.isBlank(sessionId)) {
				commonResult.setResult(ConstantsStatus.SC5001, "房间id不能为空", false);
				return commonResult;
			}

			// 根据token获取DoctorDto对象
			DoctorDto doctorInfo = getDoctorByToken(token);

			PatientRecord patientRecord = new PatientRecord();
			patientRecord.setSessionId(sessionId);

			// 从数据库里根据groupId获取详情
			patientRecord = patientRecordService.getByPatientRecord(patientRecord);

			if (patientRecord == null) {
				commonResult.setResult(ConstantsStatus.SC5001, "房间id错误", false);
				return commonResult;
			}

			// 进行抢答
			boolean flag = patientRecordService.patientRecordResponder(doctorInfo.getDoctorId(),
					patientRecord.getPatientId(), sessionId);

			if (flag) {
				commonResult.setResult(ConstantsStatus.SC7001, "抢答成功", true);
			} else {
				commonResult.setResult(ConstantsStatus.SC7002, "抢答失败,回答的医生已满", true);
			}

		} catch (Exception e) {
			logger.error("我的会诊-抢答出现异常!", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "我的会诊-抢答出现异常!", false);
		}
		return commonResult;
	}

	/**
	 * 二维码
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/twoDimension", method = RequestMethod.POST)
	public CommonResult<Object> twoDimension(String token, HttpServletRequest request, HttpServletResponse response) {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Headers", "application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Methods","OPTIONS,GET,POST");
		response.addHeader("Allow", "OPTIONS,GET,POST");

		CommonResult<Object> commonResult = null;
		try {

			// 验证医生端的token
			commonResult = validationToken(token);
			// commonResult的 returnStatus值为flase,出现异常,要返回
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			// 根据token获取DoctorDto对象
			DoctorDto doctorInfo = getDoctorByToken(token);

			// 查询医生详细信息

			commonResult.setResult(ConstantsStatus.SC2000, "获取二维码成功", true, doctorInfo);

		} catch (Exception e) {
			logger.error("获取二维码出现异常！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "获取二维码出现异常!", false);
		}
		return commonResult;
	}

/*	private String getReferer(HttpServletRequest request){
		String referer = request.getHeader("Referer");
//		String referer = "http://172.168.1.35:8088hxq/doctor/detailsofthedoctor.html?device=client&doctorUuid=57ab9090fd204192b2ff9069671ef375&&action=gz";
		logger.info("getReferer referer="+referer);
		String regex = "http://[^/]*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(referer);
		if(m.find()){
			String prefixReferer = m.group(0);
			logger.info("getReferer prefixReferer="+prefixReferer);
			return prefixReferer;

		}
		logger.info("getReferer prefixReferer=*");
		return "*";
	}*/

}

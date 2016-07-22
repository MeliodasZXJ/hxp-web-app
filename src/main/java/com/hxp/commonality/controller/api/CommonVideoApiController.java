package com.hxp.commonality.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.commonality.dto.CommonVideoDto;
import com.hxp.commonality.service.ICommonVideoService;
import com.hxp.commonality.vo.CommonVideoVo;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * @author 作者 E-mail: liuyang
 * @date 创建时间：2016年7月20日 上午11:47:56
 * @version 2.0
 * @parameter 查询视频信息
 * @since
 * @return
 */
@RestController
@RequestMapping("/app/v1_6/commonvideo")
public class CommonVideoApiController extends BaseController {

	@Autowired
	private ICommonVideoService commonVideoService;

	/**
	 * 获取视频列表(医生端)
	 * 
	 * @param token  	  医生 token不能为空 用户token
	 * @param videoType  用户类别 0:患者端 1：医生端        
	 * @return
	 */
	@RequestMapping(value = "/getDoctorCommonVideoList")
	public CommonResult<Object> getDoctorCommonVideoList(String token, CommonVideoVo commonVideoVo) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {

			// 验证医生端的token
			commonResult = validationToken(token);

			// commonResult的 returnStatus值为flase,出现异常,要返回
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			
			commonVideoVo.setVideoType(CommonVideoVo.VIDEO_TYPE_DOCTOR);

			
			

			PageHelper.startPage(getPageNum(), getPageSize());
			List<CommonVideoDto> videoList = commonVideoService.findCommonVideoList(commonVideoVo);

			commonResult.setResult(ConstantsStatus.SC2000, "医生端查询视频成功.", true, videoList);

		} catch (Exception e) {
			logger.error("检查用户在线状态失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "医生端查询视频出现异常.", false);
		}
		return commonResult;
	}

	/**
	 * 获取视频列表(患者端)
	 * 
	 * @param token
	 *            用户token
	 * @param videoType
	 *            用户类别 0:患者端 1：医生端
	 * @return
	 */
	@RequestMapping(value = "/getPatientCommonVideoList")
	public CommonResult<Object> getPatientCommonVideoList(CommonVideoVo commonVideoVo) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {

			
			commonVideoVo.setVideoType(CommonVideoVo.VIDEO_TYPE_PATIENT);

			PageHelper.startPage(getPageNum(), getPageSize());
			List<CommonVideoDto> videoList = commonVideoService.findCommonVideoList(commonVideoVo);

			commonResult.setResult(ConstantsStatus.SC2000, "患者端查询视频成功.", true, videoList);

		} catch (Exception e) {
			logger.error("检查用户在线状态失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "患者端查询视频出现异常.", false);
		}
		return commonResult;
	}

	/**
	 * 获取视频详情(医生端)
	 * 
	 * @param token       医生token
	 * @param videoId     视频id
	 * @return
	 */
	@RequestMapping(value = "/getDoctorCommonVideoInfo")
	public CommonResult<Object> getDoCommonVideoInfo(String token, CommonVideoVo commonVideoVo) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {

			// 验证医生端的token
			commonResult = validationToken(token);

			// commonResult的 returnStatus值为flase,出现异常,要返回
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			if (commonVideoVo.getVideoId() == null) {
				commonResult.setResult(ConstantsStatus.SC5001, "视频id不能为空.", false);
				return commonResult;
			}

			DoctorDto doctorDto = getDoctorByToken(token);

			// 设置医生id
			commonVideoVo.setUserId(doctorDto.getDoctorId());
			// 视频的操作者类型 0
			commonVideoVo.setCollectRule(CommonVideoVo.COLLECT_RULE_DOCTOR);

			CommonVideoDto videoInfo = commonVideoService.getCommonVideoByPrimaryKey(commonVideoVo);

			commonResult.setResult(ConstantsStatus.SC2000, "医生端查询视频详情成功.", true, videoInfo);

		} catch (Exception e) {
			logger.error("检查用户在线状态失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "医生端查询视频出现异常.", false);
		}
		return commonResult;
	}

	/**
	 * 获取视频详情(患者端)
	 * 
	 * @param token   token
	 * @param type    类别0:患者,1:医生
	 * @param videoId 视频id
	 * @return
	 */
	@RequestMapping(value = "/getPatientCommonVideoInfo")
	public CommonResult<Object> getCommonVideoInfo(String token, CommonVideoVo commonVideoVo) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {

			if (commonVideoVo.getVideoId() == null) {
				commonResult.setResult(ConstantsStatus.SC5001, "视频id不能为空.", false);
				return commonResult;
			}

			if (!StringUtil.isEmpty(token)) {
				// 验证医生端的token
				commonResult = validationToken(token);

				// commonResult的 returnStatus值为flase,出现异常,要返回
				if (!commonResult.isReturnStatus()) {
					return commonResult;
				}

				PatientCustomer patient = getPatientByToken(token);

				// 设置患者id
				commonVideoVo.setUserId(patient.getId());
			}

			// 视频的操作者类型
			commonVideoVo.setCollectRule(CommonVideoVo.COLLECT_RULE_PATIENT);

			CommonVideoDto videoInfo = commonVideoService.getCommonVideoByPrimaryKey(commonVideoVo);

			commonResult.setResult(ConstantsStatus.SC2000, "患者端查询视频详情成功.", true, videoInfo);

		} catch (Exception e) {
			logger.error("检查用户在线状态失败！", e.fillInStackTrace());
			commonResult.setResult(ConstantsStatus.SC4000, "患者端查询视频详情出现异常.", false);
		}
		return commonResult;
	}

}

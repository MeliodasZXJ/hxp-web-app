package com.hxp.commonality.controller.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.commonality.dto.ArticleInfoDto;
import com.hxp.commonality.po.CommonInfomation;
import com.hxp.commonality.service.ICommonInfomationService;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.sys.po.SysDic;
import com.hxp.sys.service.ISysDicService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

@RestController
@RequestMapping("/app/service/common/article")
public class CommonInfomationApiController extends BaseController {

	@Autowired
	private ISysDicService sysDicService;

	@Autowired
	private ICommonInfomationService commonInfomationService;

	/**
	 * 获取文章标签
	 * 
	 * @return
	 */
	@RequestMapping("/getScreenLabel")
	public CommonResult<Object> getScreenLabel() {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		List<SysDic> list = sysDicService.findDictByCode(ConstantsStatus.ARTICLETYPE);
		commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, list);
		return commonResult;
	}

	/**
	 * 获取文章详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getArticleInfo")
	public CommonResult<Object> getArticleInfo(Long id) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		if (id == null) {
			commonResult.setResult(ConstantsStatus.SC5001, "参数不能为空", false);
			return commonResult;
		}
		CommonInfomation infomation = commonInfomationService.getCommonInfoById(id);
		commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, infomation);
		return commonResult;
	}

	/**
	 * 查询文章列表
	 * 
	 * @param articleInfoDto
	 * @return
	 */
	@RequestMapping("/getArticleInfoList")
	public CommonResult<Object> getArticleInfoList(ArticleInfoDto articleInfoDto) {
		CommonResult<Object> commonResult = validationToken(articleInfoDto.getToken());
		if (!commonResult.isReturnStatus()) {
			return commonResult;
		}
		// 0医生 1患者
		if (articleInfoDto.getCollectRule() == null
				|| !(articleInfoDto.getCollectRule() == 0 || articleInfoDto.getCollectRule() == 1)) {
			commonResult.setResult(ConstantsStatus.SC5001, "当前客户端版本不能为空", false);
			return commonResult;
		}
		if (articleInfoDto.getCollectRule() == 0) {
			DoctorDto doctorDto = getDoctorInfoByToken(articleInfoDto.getToken());
			articleInfoDto.setUserId(doctorDto.getDoctorId());
		} else {
			PatientCustomer patientCustomer = getPatientByToken(articleInfoDto.getToken());
			articleInfoDto.setUserId(patientCustomer.getId());
		}
		
		PageHelper.startPage(getPageNum(),getPageSize());
		List<ArticleInfoDto> infoList = commonInfomationService.getArticleInfoList(articleInfoDto);
		if (CollectionUtils.isEmpty(infoList) && !StringUtils.isBlank(articleInfoDto.getContentTitle())) {
			articleInfoDto.setContentTitle(null);
			PageHelper.startPage(getPageNum(),getPageSize());
			infoList = commonInfomationService.getArticleInfoList(articleInfoDto);
			commonResult.setResult(ConstantsStatus.SC2000, "根据条件查询不到信息，默认返回数据", false, infoList);
			return commonResult;
		}
		commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, infoList);
		return commonResult;
	}

}

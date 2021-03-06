package com.hxp.commonality.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.commonality.po.CommonFeedBack;
import com.hxp.commonality.service.ICommonFeedBackService;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.service.IPatientCustomerService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * 意见反馈
 * @author srn
 */
@RestController
@RequestMapping("/app/v1_6/service/common")
public class CommonFeedBackApiController extends BaseController {

	@Autowired
	ICommonFeedBackService commonFeedBackService;

	@Autowired
	private IPatientCustomerService patientCustomerService;

	@Autowired
	private IDocDoctorInfoService docDoctorInfoService;

	/**
	 * 意见反馈
	 * @param mobile
	 * @param content
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addAdvice",method = RequestMethod.POST )
	public CommonResult<Object> addAdvice(String mobile, String content,Integer type ) {
		CommonResult<Object> commonResult = new CommonResult<Object>();

		//内容为空判断
		if (StringUtil.isBlank(content)) {
			commonResult.setResult(ConstantsStatus.SC5020, "内容不能为空!", false);
			return commonResult;
		}

		//手机号码为空判断
		if (StringUtil.isBlank(mobile)) {
			commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空!", false);
			return commonResult;
		}

		//手机号码格式判断
		if (!StringUtil.isMobilePhone(mobile)){
			commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误!", false);
			return commonResult;
		}

		//创建commonFeedBack对象
		CommonFeedBack commonFeedBack = new CommonFeedBack();
		//判断用户类型1:医生 2:患者 
		if (type==1) {
			//根据手机号查询id
			DocDoctorInfo doc = docDoctorInfoService.getByDocMobile(mobile);
			if (doc!=null) {
				Long docId = doc.getId();
				commonFeedBack.setObjId(docId.toString());
			}else {
				commonResult.setResult(ConstantsStatus.SC5021, "该用户没有注册.", false);
				return commonResult;
			}

		}else if(type==2) {//患者
			//根据手机号查询id	
			PatientCustomer patient = patientCustomerService.getByPatientMobile(mobile);
			if (patient !=null) {
				Long patientId = patient.getId();
				commonFeedBack.setObjId(patientId.toString());
			}
		}
		commonFeedBack.setContent(content);
		commonFeedBack.setMobile(mobile);
		commonFeedBack.setType(type);//用户类型 1:医生 2:患者
		commonFeedBackService.insert(commonFeedBack);
		commonResult.setResult(ConstantsStatus.SC2000, "成功！", true);

		return commonResult;

	}
}

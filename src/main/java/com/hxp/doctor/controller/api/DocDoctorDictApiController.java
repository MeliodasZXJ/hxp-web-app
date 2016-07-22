package com.hxp.doctor.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysDic;
import com.hxp.sys.service.ISysDicService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

/**
 * Created by anpushang on 2016/7/17. 医生字典接口
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor/dict")
public class DocDoctorDictApiController extends BaseController {

	@Autowired
	private ISysDicService sysDicService;

	@RequestMapping(value = "/selectProfessional", method = RequestMethod.POST)
	public CommonResult<Object> selectProfessional() {
		CommonResult<Object> commonResult = new CommonResult<Object>();// 返回通用格式数据
		try {
			List<SysDic> sysDic = sysDicService.findDictByCode(ConstantsStatus.DOCTORZC);
			commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, sysDic);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询医生职称错误", e);
			commonResult.setResult("查询医生职称错误!", false);
			return commonResult;
		}
		return commonResult;
	}
}

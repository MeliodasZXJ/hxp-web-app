package com.hxp.sys.controller.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysVersion;
import com.hxp.sys.service.ISysVersionService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

@RestController
@RequestMapping("/app/v1_6/service/sys")
public class SysVersionController  extends BaseController{

	@Autowired
	private ISysVersionService sysVersionService;

	@RequestMapping(value = "getSysVersion")
	public CommonResult<Object> getSysVersion(SysVersion sysVersion) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		if(StringUtils.isBlank(sysVersion.getVersion()) || sysVersion.getClientType()==null || sysVersion.getPfType()==null){
			commonResult.setResult(ConstantsStatus.SC5001, "参数传入异常，请检查参数", false);
			return  commonResult;
		}
		
		SysVersion selectVersion = sysVersionService.selectVersion(sysVersion);
		if(selectVersion==null){
			commonResult.setResult(ConstantsStatus.SC6001, "系统找不到对应的相关版本信息", false);
			return  commonResult;
		}
		if(sysVersion.getVersion().equals(selectVersion.getVersion())){
			commonResult.setResult(ConstantsStatus.SC2000,"成功",true);
			return  commonResult;
		}
			commonResult.setResult(ConstantsStatus.SC6002, "有新版本,请升级", true,selectVersion);
		return commonResult;
	}
}
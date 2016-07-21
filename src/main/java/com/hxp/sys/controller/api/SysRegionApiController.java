package com.hxp.sys.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysRegion;
import com.hxp.sys.service.ISysRegionService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

@RestController
@RequestMapping("/app/v1_6/service/sys")
public class SysRegionApiController extends BaseController {

	@Autowired
	private ISysRegionService sysRegionService;

	@RequestMapping("/getAllProvince")
	public CommonResult<Object> getAllProvince() {
		CommonResult<Object> cr = new CommonResult<>(ConstantsStatus.SC2000);
		List<SysRegion> list = sysRegionService.getAllProvince();
		cr.setResult(ConstantsStatus.SC2000, "", true, list);
		return cr;
	}
	
	@RequestMapping("/getRegionByPid")
	public CommonResult<Object> getRegionByPid(Long pid){
		CommonResult<Object> cr = new CommonResult<>(ConstantsStatus.SC2000);
		if(pid==null){
			cr.setResult(ConstantsStatus.SC5001,"参数异常,请检查参数",false,null);
			return cr ;
		}
		List<SysRegion> list = sysRegionService.getRegionByPid(pid);
		cr.setResult(ConstantsStatus.SC2000, "", true, list);
		return cr;
	}
}

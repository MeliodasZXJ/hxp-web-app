package com.hxp.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysRegion;
import com.hxp.sys.service.ISysRegionService;

@Controller
@RequestMapping("/admin/sys/region")
public class SysRegionController extends BaseController {

	@Autowired
	private ISysRegionService sysRegionService;

	@RequestMapping("/getAllProvince")
	@ResponseBody
	public List<SysRegion> getAllProvince() {
		List<SysRegion> provinceList = sysRegionService.getAllProvince();
		return provinceList;
	}

	@RequestMapping("/getRegionByPid")
	@ResponseBody
	public List<SysRegion> getRegionByPid(Long pid) {
		List<SysRegion> list = sysRegionService.getRegionByPid(pid);
		return list;
	}
}

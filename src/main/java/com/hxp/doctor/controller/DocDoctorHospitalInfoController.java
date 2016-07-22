package com.hxp.doctor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxp.base.BaseController;
import com.hxp.doctor.po.DocDoctorHospitalInfo;
import com.hxp.doctor.service.IDocDoctorHospitalInfoService;
import com.hxp.sys.po.SysRegion;
import com.hxp.sys.service.ISysRegionService;

@Controller
@RequestMapping("/admin/doctor/hospital")
public class DocDoctorHospitalInfoController extends BaseController {

	@Autowired
	private IDocDoctorHospitalInfoService docDoctorHospitalInfoService;

	@Autowired
	private ISysRegionService sysRegionService;

	@RequestMapping("/getHospitalList")
	public String getHospitalList(ModelMap model) {
		List<SysRegion> provinceList = sysRegionService.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		return "hospital/hospital_list";
	}

	@RequestMapping("/getHospitalListData")
	public String getHospitalListData(DocDoctorHospitalInfo docDoctorHospitalInfo, ModelMap model) {
		PageHelper.offsetPage(docDoctorHospitalInfo.getOffset(), docDoctorHospitalInfo.getLimit());
		List<DocDoctorHospitalInfo> list = docDoctorHospitalInfoService
				.findDocDoctorHospitalInfoList(docDoctorHospitalInfo);
		PageInfo<DocDoctorHospitalInfo> page = new PageInfo<DocDoctorHospitalInfo>(list);
		model.addAttribute("page", page);
		return "hospital/hospital_list_data";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(DocDoctorHospitalInfo docDoctorHospitalInfo) {
		if (docDoctorHospitalInfo.getId() == null) {
			docDoctorHospitalInfoService.insert(docDoctorHospitalInfo);
		} else {
			docDoctorHospitalInfoService.update(docDoctorHospitalInfo);
		}
		return successJson();
	}

	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> del(DocDoctorHospitalInfo docDoctorHospitalInfo) {
		if(docDoctorHospitalInfo.getId()==null){
			return errorJson("id不能为空");
		}
		docDoctorHospitalInfo.setStatus(-1);
		docDoctorHospitalInfoService.update(docDoctorHospitalInfo);
		return successJson();
	}

}

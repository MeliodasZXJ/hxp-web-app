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
import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.po.DocDepartmentInfo;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDepartmentInfoService;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.doctor.service.IDocDoctorTempInfoService;



@Controller
@RequestMapping("/admin/doctor")
public class DocDoctorInfoController extends BaseController {
	@Autowired
	private IDocDoctorInfoService docDoctorInfoService;
	@Autowired
	private IDocDoctorTempInfoService docDoctorTempInfoService;
	
	@Autowired
	private IDocDepartmentInfoService docDepartmentInfoService;
	
	/**
	 * 跳转到医生认证首页
	 * @return
	 */
	@RequestMapping("/getDoctorList")
	public String getDoctorList(ModelMap model){
		List<DocDepartmentInfo> deptList = docDepartmentInfoService.findDocDepartmentInfoList();
		model.addAttribute("deptList", deptList);
		return "doctor/doc_list"; 
	}
	
	/**
	 * 医生认证数据来源
	 * @param dditd
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDoctorListData")
	public String getAllDocTemp(DocDoctorInfoDto ddid,ModelMap model){
		PageHelper.offsetPage(ddid.getOffset(), ddid.getLimit());
		List<DocDoctorInfoDto> list = docDoctorInfoService.getDoctorInfoList(ddid);
		PageInfo page = new PageInfo(list);
		model.addAttribute("page", page);
		return "doctor/doc_list_data";
	}
	
	/**
	 * 认证（不）通过
	 * @param docDoctorInfoTemp
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/authDoctor")
	@ResponseBody
	public Map<String, Object> authDoctor(DocDoctorInfo docDoctorInfo) throws Exception{
		docDoctorInfoService.updateDoctor(docDoctorInfo);
		return successJson();
	}
	
}

package com.hxp.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxp.base.BaseController;
import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;



@Controller
@RequestMapping("/admin/doctor")
public class DocDoctorInfoController extends BaseController {
	@Autowired
	private IDocDoctorInfoService docDoctorInfoService;
	
	/**
	 * 跳转到医生认证首页
	 * @return
	 */
	@RequestMapping("/getDoctorList")
	public String getDoctorList(){
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
		PageHelper.startPage(ddid.getPageNum(), ddid.getPageSize());
		List<DocDoctorInfoDto> list = docDoctorInfoService.getDoctorInfoList(ddid);
		PageInfo page = new PageInfo(list);
		model.addAttribute("page", page);
		return "doctor/doc_list_data";
	}
	
	/**
	 * 认证（不）通过
	 * @param docDoctorInfoTemp
	 * @return
	 */
	@RequestMapping("/authDoctor")
	@ResponseBody
	public String authDoctor(DocDoctorInfo docDoctorInfo){
		docDoctorInfoService.updateDoctor(docDoctorInfo);
		return successJson();
	}
	
}

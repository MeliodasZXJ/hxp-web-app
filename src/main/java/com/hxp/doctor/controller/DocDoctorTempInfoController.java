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
import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.po.DocDoctorInfoTemp;
import com.hxp.doctor.service.IDocDoctorTempInfoService;



@Controller
@RequestMapping("/admin/doctor/temp")
public class DocDoctorTempInfoController extends BaseController {
	@Autowired
	private IDocDoctorTempInfoService docDoctorTempInfoService;
	
	/**
	 * 跳转到医生认证首页
	 * @return
	 */
	@RequestMapping("/getDocTempList")
	public String getDocTempList(){
		return "doctorTemp/doc_temp_list"; 
	}
	
	/**
	 * 医生认证书数据来源
	 * @param dditd
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDocTempListData")
	public String getAllDocTemp(DocDoctorInfoTempDto dditd,ModelMap model){
		PageHelper.startPage(dditd.getPageNum(),dditd.getPageSize());
		 List<DocDoctorInfoTempDto> list = docDoctorTempInfoService.getAllDocTempInfo(dditd);
		 PageInfo page = new PageInfo<>(list);
		model.addAttribute("page", page);
		return "doctorTemp/doc_temp_list_data";
	}
	
	/**
	 * 认证（不）通过
	 * @param docDoctorInfoTemp
	 * @return
	 */
	@RequestMapping("/authDoctor")
	@ResponseBody
	public String authDoctor(DocDoctorInfoTemp docDoctorInfoTemp){
		docDoctorTempInfoService.updateAuth(docDoctorInfoTemp);
		return successJson();
	}
	
}

package com.hxp.commonality.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxp.base.BaseController;
import com.hxp.commonality.service.IGeneralCharsService;
import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.po.DocDoctorHospitalInfo;
import com.hxp.doctor.service.IDocDoctorHospitalInfoService;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.service.IPatientCustomerService;

@Controller
@RequestMapping(value = "admin/general")
public class GeneralInfoController extends BaseController {

	@Autowired
	private IDocDoctorInfoService docDoctorInfoService;
	@Autowired
	private IPatientCustomerService patientCustomerService;
	@Autowired
	private IDocDoctorHospitalInfoService docDoctorHospitalInfoService;

	@Autowired
	private IGeneralCharsService generalCharsService;

	@RequestMapping(value = "/getGeneralInfo")
	public String getGeneralInfo(Model model) {
		DocDoctorInfoDto doctorInfo = new DocDoctorInfoDto();
		List<DocDoctorInfoDto> totalList = docDoctorInfoService.getDoctorInfoList(doctorInfo);// 已认证医生总数
		doctorInfo.setAutyType(0);
		List<DocDoctorInfoDto> notAuthList = docDoctorInfoService.getDoctorInfoList(doctorInfo); // 未认证医生总数
		PatientCustomer patientCustomer = new PatientCustomer();
		List<PatientCustomer> patientList = patientCustomerService.getPatientCustomerList(patientCustomer); // 患者总数
		DocDoctorHospitalInfo docDoctorHospitalInfo = new DocDoctorHospitalInfo();
		List<DocDoctorHospitalInfo> hospitalList = docDoctorHospitalInfoService
				.findDocDoctorHospitalInfoList(docDoctorHospitalInfo); // 医院总数

		model.addAttribute("totalAuthDoctor", totalList.size());
		model.addAttribute("totalNotAuthDoctor", notAuthList.size());
		model.addAttribute("totalPatient", patientList.size());
		model.addAttribute("totalHospital", hospitalList.size());

		return "general/general_info";
	}

	@RequestMapping(value = "/getFeedBackPieChartData")
	@ResponseBody
	public Map<String, Object> getFeedBackPieChartData() {
		Map<String, Object> map = generalCharsService.getFeedBackPieChartData();
		return map;
	}

}

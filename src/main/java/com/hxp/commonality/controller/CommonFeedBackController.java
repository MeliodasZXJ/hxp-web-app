package com.hxp.commonality.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.hxp.commonality.dto.CommonFeedBackDto;
import com.hxp.commonality.po.CommonFeedBack;
import com.hxp.commonality.service.ICommonFeedBackService;

@Controller
@RequestMapping("/admin/common/feedBack")
public class CommonFeedBackController extends BaseController {
	@Autowired
	private ICommonFeedBackService commonFeedBackService;

	/**
	 * 跳转到医生反馈页面
	 * 
	 * @return
	 */
	@RequestMapping("/getDoctorFeedBackList")
	public String getDoctorFeedBackList() {
		return "feedBack/doctor_feed_back_list";
	}

	/**
	 * 查询医生反馈列表
	 * 
	 * @param cfb
	 * @param model
	 * @return
	 */
	@RequestMapping("/getDoctorFeedBackListData")
	public String getFeedBackListData(CommonFeedBack cfb, ModelMap model) {
		PageHelper.offsetPage(cfb.getOffset(), cfb.getLimit());
		List<CommonFeedBackDto> list = new ArrayList<>();
		list = commonFeedBackService.findDoctorFeedBackList(cfb);
		PageInfo page = new PageInfo(list);
		model.addAttribute("page", page);
		return "feedBack/doctor_feed_back_list_data";
	}

	/**
	 * 跳转到患者反馈页面
	 * 
	 * @return
	 */
	@RequestMapping("/getPatientFeedBackList")
	public String getDoctorFeedBackList(String commond) {
		return "feedBack/patient_feed_back_list";
	}

	/**
	 * 查询患者反馈列表
	 * 
	 * @param cfb
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPatientFeedBackListData")
	public String getPatientFeedBackListData(CommonFeedBack cfb, ModelMap model) {
		PageHelper.offsetPage(cfb.getOffset(), cfb.getLimit());
		List<CommonFeedBack> list = new ArrayList<>();
		list = commonFeedBackService.findPatientFeedBackList(cfb);
		PageInfo page = new PageInfo(list);
		model.addAttribute("page", page);
		return "feedBack/patient_feed_back_list_data";
	}
	
	/**
	 * 更新
	 * @param cfb
	 * @return
	 */
	@RequestMapping("/updateFeedBack")
	@ResponseBody
	public Map<String,Object> update(CommonFeedBack cfb){
		cfb.setReplyTime(new Date());
		commonFeedBackService.update(cfb);
		return successJson();
	}

}

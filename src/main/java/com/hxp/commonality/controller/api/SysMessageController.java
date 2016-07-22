package com.hxp.commonality.controller.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.commonality.po.SysMessage;
import com.hxp.commonality.service.ISysMessageService;
import com.hxp.doctor.dto.DoctorDto;

import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

/**
 * 系统消息
 * @author srn
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class SysMessageController extends BaseController{

	@Autowired
	ISysMessageService sysMessageService;
	/**
	 * 获取系统消息列表
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/systemMessageList" )
	@ResponseBody
	public CommonResult<Object> systemMessages(String token) {
		CommonResult<Object> commonResult = null;
		//判断token
		commonResult  =  validationToken(token);
		if(!commonResult.isReturnStatus()){
			return  commonResult;
		}
		//根据token获取docDoctorInfo对象
		DoctorDto doctorDto = getDoctorByToken(token);
		PageHelper.startPage(getPageNum(), getPageSize());
		if(doctorDto == null){
			commonResult.setResult(ConstantsStatus.SC5025, "对象不能为空!", false);
			return commonResult;
		}
		Long id = doctorDto.getDoctorId();
		//根据医生id 获取系统消息列表
		List<SysMessage>  sysMessageList = sysMessageService.getSysMessageList(id);
		if(null == sysMessageList || sysMessageList.size() ==0 ) {
			commonResult.setResult(ConstantsStatus.SC5028, "系统消息列表为空!", false);
			return commonResult;
		}
		commonResult.setResult(ConstantsStatus.SC2000,"查询成功",true,sysMessageList);
		return commonResult;
	}
	
	/**
	 *根据系统消息id，获取内容 未读变已读 
	 */
	@RequestMapping(value = "/sysMessagesIfRead" )
	@ResponseBody
	public CommonResult<Object> systemMessagesIfRead(Integer messagesId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		
		if (messagesId ==null) {
			commonResult.setResult(ConstantsStatus.SC4371, "消息id为空.", false);
			return commonResult;
		}
		//根据id查询消息
		SysMessage sysMessage = sysMessageService.selectByPrimaryKey(messagesId);
		if (sysMessage==null) {
			commonResult.setResult(ConstantsStatus.SC5028, "内容为空.", false);
			return commonResult;
		}
		sysMessage.setStatus(1);
		//更新数据
	   sysMessageService.updateByPrimaryKey(sysMessage);
	   commonResult.setResult(ConstantsStatus.SC2000, "消息已读.", true,sysMessage);
	   return commonResult;
	}
	/**
	 * 根据token 判断已读未读
	 */
	@RequestMapping(value = "/systemMessageRead" )
	@ResponseBody
	public CommonResult<Object> systemMessageRead(String token) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		//判断token
		commonResult  =  validationToken(token);
		if(!commonResult.isReturnStatus()){
			return  commonResult;
		}
		//根据token获取docDoctorInfo对象
		DoctorDto doctorDto = getDoctorByToken(token);
		if(doctorDto == null){
			commonResult.setResult(ConstantsStatus.SC5025, "对象不能为空!", false);
			return commonResult;
		}
		Long id = doctorDto.getDoctorId();
		//根据医生id 获取系统消息列表
		int sysMessageIfRead = sysMessageService.selectBySysMessageCount(id);
		Map<String,Object> map =new HashMap<>();
		map.put("sysMessageIfRead", sysMessageIfRead);
		map.put("status", doctorDto.getStatus());
		if (sysMessageIfRead>0) {
			commonResult.setResult(ConstantsStatus.SC2000, "有未读消息.", true,map);
		}else {
			commonResult.setResult(ConstantsStatus.SC5028, "没有未读消息.", false,map);
		}
		return commonResult;
	}
}

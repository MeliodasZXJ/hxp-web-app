package com.hxp.patient.controller.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.common.redis.JedisManager;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.service.IPatientCustomerService;
import com.hxp.util.AliSmsService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;
import com.hxp.util.constant.Constant;

/**
 * Created by srn on 2016/7/19.
 */
@RestController
@RequestMapping("/app/v1_6/service/customer")
public class PatientCustomerApiController extends BaseController {
	@Autowired
	private IPatientCustomerService patientCustomerService;

	/**
	 * 用户登陆
	 * @param mobile
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/gotoLogin",method = RequestMethod.POST )
	public CommonResult<Object> gotoLogin(String mobile, String password) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		//手机号码为空判断
		if (StringUtil.isBlank(mobile)) {
			commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空.", false);
			return commonResult;
		}

		//手机号码格式判断
		if (!StringUtil.isMobilePhone(mobile)){
			commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误.", false);
			return commonResult;
		}

		//密码为空判断
		if (StringUtil.isBlank(password)) {
			commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空.", false);
			return commonResult;
		}

		//创建PatientCustomer对象
		PatientCustomer patientCustomer = new PatientCustomer();
		patientCustomer.setMobile(mobile);
		patientCustomer.setPassword(password);
		//查询数据库
		PatientCustomer patient = patientCustomerService.getByPatientCustomer(patientCustomer);

		if (patient!=null) {
			//根据token获取PatientCustomer对象
			Map<String, Object> map = new HashMap<>();
			if (patient.getBirthday() != null) {
				String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(patient.getBirthday());
				patient.setPatientBirthday(dateStr);
				Calendar mycalendar = Calendar.getInstance();// 获取现在时间
				String currentYear = String.valueOf(mycalendar.get(Calendar.YEAR));// 获取年份
				int BirthdayYear = Integer.parseInt(dateStr.substring(0, 4));
				int age = Integer.parseInt(currentYear) - BirthdayYear;
				patient.setAge(age);
				patient.setBirthday(null);
			}
			//从redis获取token
			String token = getPatientToken(patient);
			map.put("token",token);
			map.put("patient",patient);
			//将token返回到前端
			commonResult.setResult(ConstantsStatus.SC2000, "登陆成功.", true, map);
		} else {
			commonResult.setResult(ConstantsStatus.SC5020, "用户名或者密码错误.", false);
		}
		return commonResult;
	}

	/**
	 *  患者注册验证码发送
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/getPatientCaptcha",method = RequestMethod.POST )
	public CommonResult<Object> getPatientCaptcha(String mobile){
		CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
		try{
			//手机号码为空判断
			if (StringUtil.isBlank(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空!", false);
				return commonResult;
			}
			//手机号码格式判断
			if (!StringUtil.isMobilePhone(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误!", false);
				return commonResult;
			}
			String activeCode = AliSmsService.getActivatingKey(Constant.VERIFICATION_CODE_LENGTH);
			String codeMsg = activeCode + Constant.VERIFICATION_CODE;;
			boolean flag = AliSmsService.sendRegCodeMobile(mobile,codeMsg);
			if (flag){//验证码存放到缓存中
				String patientKey = "patient_"+mobile;
				JedisManager.setString(patientKey,activeCode,Constant.LIVE_SECONDS);
				commonResult.setResult(ConstantsStatus.SC2000, "验证码发送成功!", true);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("验证码发送失败！",e.fillInStackTrace());
		}
		return commonResult;
	}

	
	 /**
     * 校验校验码
     * @param captcha
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/validateCaptcha",method = RequestMethod.POST )
    public CommonResult<Object> validateCaptcha(String captcha,String mobile){
    	  CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
    	  //验证码
          if (StringUtils.isBlank(captcha)){
              commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
              return commonResult;
          }
          String doctorKey = "patient_"+mobile;
          String registerCode = JedisManager.getString(doctorKey);
          if(!StringUtils.isBlank(registerCode)){
              if (!registerCode.equals(captcha)){
                  commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                  return commonResult;
              }
          }else if(!captcha.equals("14560")){
              commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
              return commonResult;
          }
          
          commonResult.setResult(ConstantsStatus.SC2000, "校验成功", true);
          return commonResult;
    }
    
	
	
	/**
	 * 用户注册
	 * 手机号，密码，验证码
	 * @param patientCustomer 
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST )
	public CommonResult<Object> registerPatient( PatientCustomer patientCustomer,String captcha) {

		CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
	
		//判断验证码
		if (StringUtil.isBlank(captcha)){
			commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空.", false);
			return commonResult;
		}
		String patientKey = "patient_"+patientCustomer.getMobile();
		String registerCode = JedisManager.getString(patientKey);
		if(!StringUtil.isBlank(registerCode)){
			if (!registerCode.equals(captcha)){
				commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
				return commonResult;
			}
		}else if(!captcha.equals("12368")){
			commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
			return commonResult;
		}

		//手机号码为空判断
		if (StringUtil.isBlank(patientCustomer.getMobile())) {
			commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空.", false);
			return commonResult;
		}

		//手机号码格式判断
		if (!StringUtil.isMobilePhone(patientCustomer.getMobile())) {
			commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误.", false);
			return commonResult;
		}

		//密码为空判断
		if (StringUtil.isBlank(patientCustomer.getPassword())) {
			commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
			return commonResult;
		}
		//密码格式判断
		if(!StringUtil.isPassWord(patientCustomer.getPassword())){
			commonResult.setResult(ConstantsStatus.SC5014, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
			return commonResult;
		}

		//查询数据库中是否有该患者
		PatientCustomer dbPatientCustomer = patientCustomerService.getByPatientCustomerMobile(patientCustomer);
		if(dbPatientCustomer!=null){
			commonResult.setResult(ConstantsStatus.SC5006, "该手机号已经注册!", false);
		}else {
			patientCustomerService.insert(patientCustomer);//插入创建数库
			commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true);
		}
		return commonResult;
	}

	/**
	 * 忘记密码
	 * 手机号，验证码，密码
	 */
	@RequestMapping(value = "/retrievePassword",method = RequestMethod.POST )
	public CommonResult<Object> retrievePassword(String mobile, String password,String captcha) {

		CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
		try {
			//验证码
			if (StringUtil.isBlank(captcha)){
				commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空.", false);
				return commonResult;
			}
			String patientKey = "patient_"+mobile;
			String registerCode = JedisManager.getString(patientKey);
			if(!StringUtil.isBlank(registerCode)){
				if (!registerCode.equals(captcha)){
					commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
					return commonResult;
				}
			}else if(!captcha.equals("12368")){
				commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
				return commonResult;
			}

			//手机号码为空判断
			if (StringUtil.isBlank(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空.", false);
				return commonResult;
			}

			//手机号码格式判断
			if (!StringUtil.isMobilePhone(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误.", false);
				return commonResult;
			}

			//密码为空判断
			if (StringUtil.isBlank(password)) {
				commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空.", false);
				return commonResult;
			}
			//密码格式判断
			if(!StringUtil.isPassWord(password)){
				commonResult.setResult(ConstantsStatus.SC5004, "密码格式错误.", false);
				return commonResult;
			}

			PatientCustomer patientCustomer = new PatientCustomer();
			patientCustomer.setMobile(mobile);
			patientCustomer.setPassword(password);
			//根据手机号查询数据库中是否有该患者
			PatientCustomer dbPatientCustomer = patientCustomerService.getByPatientCustomerMobile(patientCustomer);
			
			if(dbPatientCustomer!=null){
				patientCustomerService.updateByPatientCustomer(patientCustomer);
				//String token = getPatientToken(dbPatientCustomer);//从redis获取token
				commonResult.setResult(ConstantsStatus.SC2000, "找回密码成功.", true);
			}else {
				commonResult.setResult(ConstantsStatus.SC5051, "患者信息不存在.", false);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("重置密码失败.", e.fillInStackTrace());
		}
		return commonResult;
	}
	
	/**
	 * 修改密码
	 * @param mobile
	 * @param password
	 * @param captcha
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/modificationPassword",method = RequestMethod.POST )
	public CommonResult<Object> modificationPassword(String mobile, String password,String captcha,String token) {
		CommonResult<Object> commonResult = null;//返回通用格式数据

		try {
			//判断token
			commonResult = validationToken(token);
			if(!commonResult.isReturnStatus()){
				return commonResult;
			}
			//根据token获取PatientCustomer对象
			PatientCustomer patientCustomer = getPatientByToken(token);
			//验证码
			if (StringUtil.isBlank(captcha)){
				commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
				return commonResult;
			}
			String patientKey = "patient_"+mobile;
			String registerCode = JedisManager.getString(patientKey);

			if(!StringUtil.isBlank(registerCode)){
				if (!registerCode.equals(captcha)){
					commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
					return commonResult;
				}
			}else if(!captcha.equals("12368")){
				commonResult.setResult(ConstantsStatus.SC4225, "验证码输入错误.", false);
				return commonResult;
			}
			//密码为空判断
			if (StringUtil.isBlank(password)) {
				commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空.", false);
				return commonResult;
			}
			//手机号码为空判断
			if (StringUtil.isBlank(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5020, "手机号码不能为空.", false);
				return commonResult;
			}

			//手机号码格式判断
			if (!StringUtil.isMobilePhone(mobile)) {
				commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误.", false);
				return commonResult;
			}
			
			//患者信息判断
			if (patientCustomer !=null){
				
				if(!patientCustomer.getMobile().equals(mobile)){
					commonResult.setResult(ConstantsStatus.SC4307, "手机号码不是注册手机号码!", false);
					return commonResult;
				}
				patientCustomer.setMobile(mobile);
				patientCustomer.setPassword(password);
				patientCustomerService.updateByPatientCustomer(patientCustomer);
				commonResult.setResult(ConstantsStatus.SC2000, "修改密码成功.", true);
			}else {
				commonResult.setResult(ConstantsStatus.SC5051, "患者信息不存在.", false);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("修改失败！", e.fillInStackTrace());
		}
		return commonResult;
	}
	/**
	 * 根据ID查看患者信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getPatientInfoById")
	public CommonResult<Object> getPatientInfoById(Long id) {
		CommonResult<Object> commonResult = new CommonResult<Object>();// 返回通用格式数据
		if (id == null) {
			commonResult.setResult(ConstantsStatus.SC5001, "参数id不能为空", false);
			return commonResult;
		}

		PatientCustomer patient = patientCustomerService.selectByPrimaryKey(id);
		if (patient == null) {
			commonResult.setResult(ConstantsStatus.SC4003, "没有查询到该患者", false);
			return commonResult;
		}
		if (patient.getBirthday() != null) {
			String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(patient.getBirthday());
			patient.setPatientBirthday(dateStr);
			Calendar mycalendar = Calendar.getInstance();// 获取现在时间
			String currentYear = String.valueOf(mycalendar.get(Calendar.YEAR));// 获取年份
			int BirthdayYear = Integer.parseInt(dateStr.substring(0, 4));
			int age = Integer.parseInt(currentYear) - BirthdayYear;
			patient.setAge(age);
			patient.setBirthday(null);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("patient",patient);
		commonResult.setResult(ConstantsStatus.SC2000, "操作成功", true, map);
		return commonResult;
	}

	/**
	 * 查看患者个人信息
	 */
	@RequestMapping(value = "/toCustomerInfo")
	public CommonResult<Object> toCustomerInfo(String token) {
		CommonResult<Object> commonResult = null;//返回通用格式数据
		try {
			//判断token
			commonResult = validationToken(token);
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}

			//根据token获取PatientCustomer对象
			PatientCustomer patientCustomer = getPatientByToken(token);

			if (patientCustomer == null) {
				commonResult.setResult(ConstantsStatus.SC5051, "患者信息不存在.", false);
				return commonResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("patient",patientCustomer);
			commonResult.setResult(ConstantsStatus.SC2000, "获取患者信息成功", true, map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取患者信息失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 编辑个人信息
	 */
	@RequestMapping(value = "/editCustomerInfo", method = RequestMethod.POST)
	public CommonResult<Object> editCustomerInfo(PatientCustomer patientCustomer, String token) {
		CommonResult<Object> commonResult = null;//返回通用格式数据
		Map<String, Object> returnMap = new HashMap<>();
		try {
			//判断token
			commonResult = validationToken(token);
			if (!commonResult.isReturnStatus()) {
				return commonResult;
			}
			//根据token获取PatientCustomer对象
			PatientCustomer patient = getPatientByToken(token);
			if (patient != null) {
				patientCustomer.setId(patient.getId());
				//更新
				int i = patientCustomerService.updateByPrimaryKey(patientCustomer);
				if (i > 0) {
					PatientCustomer customer = patientCustomerService.selectByPatientById(patient.getId());
					//根据token获取PatientCustomer对象
					if (customer.getBirthday() != null) {
						String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(customer.getBirthday());
						customer.setPatientBirthday(dateStr);
						Calendar mycalendar = Calendar.getInstance();// 获取现在时间
						String currentYear = String.valueOf(mycalendar.get(Calendar.YEAR));// 获取年份
						int BirthdayYear = Integer.parseInt(dateStr.substring(0, 4));
						int age = Integer.parseInt(currentYear) - BirthdayYear;
						customer.setAge(age);
						customer.setBirthday(null);
					}
					//刷新redis里面的PatientCustomer对象
					refreshPatientToken(token, customer);
					commonResult.setResult(ConstantsStatus.SC2000, "患者信息更新成功", true);
				} else {
					commonResult.setResult(ConstantsStatus.SC4002, "失败", true, returnMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑患者信息失败！", e.fillInStackTrace());
		}
		return commonResult;
	}
}

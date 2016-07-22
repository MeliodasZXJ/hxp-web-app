package com.hxp.util.constant;

public class Constant {
	public final static String HXP_MANAGER_LOGIN_VERFYCODE_KEY = "hxp_manager_login_verfycode_key"; // 登录验证码
	public final static String HXP_MANAGER_BACKPWD_VERFYCODE_KEY = "hxp_manager_backpwd_verfycode_key"; // 找回密码验证码
	public final static String HXP_MANAGER_REG_VERFYCODE_KEY = "hxp_manager_reg_verfycode_key"; // 注册验证码
	public final static String HXP_MANAGER_USER_COOKIE_KEY = "hxp_login_user"; //当前登录用户 // 登陆后sessionKey
	
	

	public final static String DOCTORAUTH_SUCCESS_MESSAGE = "医生您好，您的信息已通过认证，感谢您对好心情医生版的关注，祝您生活愉快！";
	public final static String DOCTORAUTH_FAILED_MESSAGE = "医生您好，您的医生信息未通过认证，请到个人中心重新提交认证申请，详情到系统消息查看未通过原因，祝您生活愉快。";

	public final static String DOCTORAUTH_SUCCESS_MESSAGE_BACKGROUND = "医生您好，您的信息已通过认证，感谢您对好心情医生版的关注，祝您生活愉快！";
	public final static String DOCTORAUTH_FAILED_MESSAGE_BACKGROUND = "医生您好，您的医生信息未通过认证，请到个人中心重新提交认证申请。认证未通过原因:";


	public final static String VERIFICATION_CODE = "请及时使用此验证码。如非本人操作，请忽略此短信。【好心情医疗平台】";
	public final static Integer VERIFICATION_CODE_LENGTH = 6;  //验证码长度
	public final static Integer LIVE_SECONDS = 60000;	//验证码存储时间
	//查询医生的状态
	public final static Integer  AUTY_TYPE = 1;
	//是名医风采
	public final static Integer  YES_DOCTOR_ELEGANT = 1;

}

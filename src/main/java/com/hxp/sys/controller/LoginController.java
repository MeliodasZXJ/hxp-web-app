package com.hxp.sys.controller;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.hxp.base.BaseController;
import com.hxp.util.EncryptionUtil;
import com.hxp.util.constant.Constant;



@Controller
@RequestMapping("/")
public class LoginController extends BaseController {


	@RequestMapping(value = "login.htm")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping(value = "loginOut.htm")
	public String loginOut(){
		return "redirect:login.htm";
	}

	@RequestMapping(value = "index.htm")
	public String index(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "login.do")
	public String login(String verify, String loginName,
			String password, RedirectAttributesModelMap attrModel) {
		if (!StringUtils.hasText(verify)) {
			attrModel.addFlashAttribute("loginName", loginName);
			attrModel.addFlashAttribute("errMsg", "验证码不能为空！");
			return "redirect:login.htm";
		}
		String codeInCookie = getVerifyCode(Constant.HXP_MANAGER_LOGIN_VERFYCODE_KEY);
		if (codeInCookie == null || !codeInCookie.equals(EncryptionUtil.MD5(verify))) {
			attrModel.addFlashAttribute("loginName", loginName);
			attrModel.addFlashAttribute("errMsg", "验证码有误！");
			return "redirect:login.htm";
		}
		if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {
			attrModel.addFlashAttribute("loginName", loginName);
			attrModel.addFlashAttribute("errMsg", "用户名或密码不能为空！");
			return "redirect:login.htm";
		}
		return "redirect:index.htm";
	}

	

	private String getVerifyCode(String common) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (common.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}

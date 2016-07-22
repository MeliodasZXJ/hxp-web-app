package com.hxp.sys.controller;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysUser;
import com.hxp.sys.service.ISysUserService;
import com.hxp.util.EncryptionUtil;
import com.hxp.util.constant.Constant;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	@Autowired
	private ISysUserService sysUserService;

	@RequestMapping(value = "toLogin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value = "admin/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "loginOut")
	public String loginOut() {
		return "redirect:toLogin";
	}

	@RequestMapping(value = "login")
	public String login(String verify, SysUser sysUser, RedirectAttributesModelMap attrModel) {
		attrModel.addFlashAttribute("loginName", sysUser.getLoginName());
		if (StringUtils.isBlank(verify)) {
			attrModel.addFlashAttribute("errMsg", "验证码不能为空！");
			return "redirect:toLogin";
		}
		String codeInCookie = getVerifyCode(Constant.HXP_MANAGER_LOGIN_VERFYCODE_KEY);
		if (codeInCookie == null || !codeInCookie.equals(EncryptionUtil.MD5(verify))) {
			attrModel.addFlashAttribute("errMsg", "验证码有误！");
			return "redirect:toLogin";
		}
		if (StringUtils.isBlank(sysUser.getLoginName()) || StringUtils.isBlank(sysUser.getPassword())) {
			attrModel.addFlashAttribute("errMsg", "用户名或密码不能为空！");
			return "redirect:toLogin";
		}

		SysUser user = sysUserService.getLoginSysUser(sysUser);

		if (user == null) {
			attrModel.addFlashAttribute("errMsg", "用户名不存在！");
			return "redirect:toLogin";
		}
		if (!user.getPassword().equals(EncryptionUtil.MD5(sysUser.getPassword()))) {
			attrModel.addFlashAttribute("errMsg", "密码错误！");
			return "redirect:toLogin";
		}
		setSessionAttribute(Constant.HXP_MANAGER_USER_COOKIE_KEY, user);
		return "redirect:admin/index";
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

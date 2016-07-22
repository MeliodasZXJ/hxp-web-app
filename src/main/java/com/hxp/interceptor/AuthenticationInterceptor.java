package com.hxp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hxp.sys.po.SysUser;
import com.hxp.util.constant.Constant;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("请求地址："+request.getRequestURL());
		String contextPath = request.getContextPath();
		SysUser userInfo = (SysUser) request.getSession().getAttribute(Constant.HXP_MANAGER_USER_COOKIE_KEY);
		if (userInfo == null) {
			log.info("未登录，已拦截...");
			response.sendRedirect(contextPath + "/toLogin");
			return false;
		}
		log.info("已登录，放行...");
		return true;
	}

}

package com.hxp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hxp.sys.po.SysUser;
import com.hxp.util.constant.Constant;

public class AppInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("请求地址："+request.getRequestURL());
		return true;
	}

}

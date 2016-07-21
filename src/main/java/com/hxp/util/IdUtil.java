package com.hxp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdUtil {

	/** 空串"" */
	public final static String EMPTY_STR = "";

	/**
	 * @author
	 * @date 2016年3月12日
	 * @function 创建项目id
	 * @return
	 */
	public static String createProjectId() {
		Date date = new Date();
		// 生成订单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String time = sdf.format(date);
		Random r = new Random();
		String uId = EMPTY_STR;
		boolean b = true;
		while (b) {
			int x = r.nextInt(9999);
			if (x > 1000) {
				uId = time + x;
				b = false;
			}
		}
		return "P" + uId;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(createProjectId());
		System.out.println(createProjectId());

	}
}

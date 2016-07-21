package com.hxp.common.rongCloud.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 *融云相关
 */
public class RongCloudGsonUtil {

	private static Gson gson = new Gson();

	public static String toJson(Object obj, Type type) {		
		return gson.toJson(obj, type);		
	}		
	
	public static Object fromJson(String str,Type type){
		return gson.fromJson(str, type);
	}
}  

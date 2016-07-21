package com.hxp.common.rongCloud.result;

import java.io.Serializable;


/**
 * 对融云server sdk返回的封装
 */
public class RCloudSdkHttpResult implements Serializable {

	private int code;
	private String result;

	public RCloudSdkHttpResult(int code, String result) {
		this.code = code;
		this.result = result;
	}

	public int getHttpCode() {
		return code;
	}

	public String getResult() {
		return result;
	}

	@Override
	public String toString() {
		return String.format("{\"code\":\"%s\",\"result\":%s}", code,
				result);
	}
}

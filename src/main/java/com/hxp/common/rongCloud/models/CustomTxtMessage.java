package com.hxp.common.rongCloud.models;

import com.hxp.common.rongCloud.utils.RongCloudGsonUtil;

import java.io.Serializable;

//自定义消息
public class CustomTxtMessage extends Message implements Serializable{

	private String content;

	public CustomTxtMessage(String content) {
		this.type = "KM:TxtMsg";
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return RongCloudGsonUtil.toJson(this, CustomTxtMessage.class);
	}
}

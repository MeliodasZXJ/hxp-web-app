package com.hxp.common.rongCloud.models;

import java.io.Serializable;

public class MsgObj implements Serializable {
	private String content;
	private String objectName;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgObj() {
		super();
	}
}

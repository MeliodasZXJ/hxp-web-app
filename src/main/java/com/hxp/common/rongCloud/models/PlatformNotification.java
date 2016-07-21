package com.hxp.common.rongCloud.models;

import java.io.Serializable;
import java.util.Map;

public class PlatformNotification implements Serializable {
	private String alert;
	private Map<String, String> extras;

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Map<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

}

package com.hxp.patient.dto;

import java.io.Serializable;

public class PatientRecordImgDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2169597463852807086L;
	private String sessionId;
	private String imgUrl;
	private String thumbnailUrl;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

}

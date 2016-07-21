package com.hxp.patient.po;

import java.io.Serializable;
import java.util.Date;

public class PatientRecordImg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5114163836988543155L;

	private Long id;

	private String sessionId;

	private Long imgId;

	private Date createTime;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId == null ? null : sessionId.trim();
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
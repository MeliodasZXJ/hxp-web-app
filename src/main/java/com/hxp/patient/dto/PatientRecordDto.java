package com.hxp.patient.dto;

import com.hxp.common.fileUpload.po.UploadImg;

import java.io.Serializable;
import java.util.List;

public class PatientRecordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428009022041340909L;

	private String sessionId;
	private String patientId;
	private String name;
	private String sex;
	private String age;
	private String createTime;
	private String content;
	private String status;
	private String headImg;
	private List<PatientRecordImgDto> imgList;


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public List<PatientRecordImgDto> getImgList() {
		return imgList;
	}

	public void setImgList(List<PatientRecordImgDto> imgList) {
		this.imgList = imgList;
	}

}

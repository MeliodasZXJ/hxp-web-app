package com.hxp.commonality.dto;

import java.io.Serializable;

public class CommonFeedBackDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String docName;
	private int sex;
	private String mobile;
	private String hospitalName;
	private String deptName;
	private String replySysUserId;
	private String replySysUserName;
	private String createTime;
	private String replyTime;
	private String content;
	private String replyContent;
	private Integer status;

	public CommonFeedBackDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReplySysUserId() {
		return replySysUserId;
	}

	public void setReplySysUserId(String replySysUserId) {
		this.replySysUserId = replySysUserId;
	}

	public String getReplySysUserName() {
		return replySysUserName;
	}

	public void setReplySysUserName(String replySysUserName) {
		this.replySysUserName = replySysUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
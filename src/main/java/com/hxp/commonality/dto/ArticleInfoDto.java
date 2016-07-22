package com.hxp.commonality.dto;

import java.io.Serializable;

public class ArticleInfoDto implements Serializable {
	private Long id;
	private String contentTitle;
	private Integer collectNum;// 收藏数
	private String createTime;
	private boolean isCollect = false; // 是否被收藏
	private Integer illnessId; // 筛选条件
	private Integer contentType; // 资讯类型
	private Long userId;
	private Integer collectRule;
	private Long objId;
	private String token;
	private String imageUrl;
	private String targetUrl;

	public ArticleInfoDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isCollect() {
		return isCollect;
	}

	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}

	public Integer getIllnessId() {
		return illnessId;
	}

	public void setIllnessId(Integer illnessId) {
		this.illnessId = illnessId;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCollectRule() {
		return collectRule;
	}

	public void setCollectRule(Integer collectRule) {
		this.collectRule = collectRule;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

}

package com.hxp.commonality.vo;

import java.io.Serializable;

import com.hxp.util.StringUtil;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月20日 下午2:17:43 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public class CommonVideoVo implements Serializable {

	//视频类别0 患者
	public static final String VIDEO_TYPE_PATIENT = "0";
	//视频类别1 医生
	public static final String VIDEO_TYPE_DOCTOR = "1";
	
	
	//视频收藏类别1患者
	public static final Integer COLLECT_RULE_PATIENT = 1;
	//视频收藏类别0 医生
	public static final Integer COLLECT_RULE_DOCTOR = 0;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6380574965513273415L;
	
	//视频名称
	private String videoName;
	//是否是热播视频
	private String videoHot;
	//视频类型
	private String videoType;
	
	//视频id
	private Long videoId;
	
	//视频的收藏者id(医生,患者id)
	private Long userId;
	
	//视频的收藏者类别(0医生，1-患者)
	private Integer collectRule;
	
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoHot() {
		return videoHot;
	}
	public void setVideoHot(String videoHot) {
		this.videoHot = videoHot;
	}
	public String getVideoType() {
		if(StringUtil.isEmpty(videoType)){
			return "";
		}else{
			return videoType.trim();
		}
	}
	public void setVideoType(String videoType) {
		this.videoType = videoType;
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
	public Long getVideoId() {
		return videoId;
	}
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	

}

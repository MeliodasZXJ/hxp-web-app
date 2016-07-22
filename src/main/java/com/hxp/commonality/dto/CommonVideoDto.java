package com.hxp.commonality.dto;

import java.io.Serializable;

import com.hxp.commonality.po.CommonVideo;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月20日 上午11:25:36 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public class CommonVideoDto extends CommonVideo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2432345852974646636L;
	
	//评论数量
	private Integer commentNumber;
	//收藏数量
	private Integer collNumber;
	//医生,患者是否收藏这条视频(默认是0,没收藏,1收藏)
	private Integer isCollect;
	
	
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Integer getCollNumber() {
		return collNumber;
	}
	public void setCollNumber(Integer collNumber) {
		this.collNumber = collNumber;
	}
	public Integer getIsCollect() {
		if(isCollect == null){
			isCollect = 0;
		}else if( isCollect >0){
			isCollect = 1;
		}
		return isCollect;
	}
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	
	
}

package com.hxp.common.fileUpload.po;

import java.io.Serializable;
import java.util.Date;

/** 
 * 图片上传类
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月15日 下午2:34:07 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public class UploadImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9149818413691051617L;

	//主键id
	private Long id;
	//图片地址
	private String imgUrl;
	//缩络图id
	private String thumbnaillId;
	//缩络图url
	private String thumbnaillUrl;
	//图片后缀
	private String suffix;
	//是否删除
	private Integer flag;
	//创建时间
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getThumbnaillId() {
		return thumbnaillId;
	}
	public void setThumbnaillId(String thumbnaillId) {
		this.thumbnaillId = thumbnaillId;
	}
	public String getThumbnaillUrl() {
		return thumbnaillUrl;
	}
	public void setThumbnaillUrl(String thumbnaillUrl) {
		this.thumbnaillUrl = thumbnaillUrl;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}

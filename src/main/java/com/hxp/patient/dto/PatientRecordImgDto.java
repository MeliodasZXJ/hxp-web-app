package com.hxp.patient.dto;

import java.io.Serializable;
import java.util.Date;

public class PatientRecordImgDto implements Serializable {
    private static final long serialVersionUID = 2169597463852807086L;
    private String imgUrl;
    private String thumbnailUrl;
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

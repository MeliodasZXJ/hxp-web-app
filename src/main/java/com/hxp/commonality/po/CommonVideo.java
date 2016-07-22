package com.hxp.commonality.po;

import java.io.Serializable;
import java.util.Date;

import com.hxp.base.BaseBean;

public class CommonVideo extends BaseBean implements Serializable {
    private Long id;

    private String uuid;

    private String image;
    // 0：未发布 ,1:已发布
    private String state;

    private String videoAddress;

    private String videoIntroduction;

    private String videoName;

    private String userName;

    private String userIntroduce;
    //是否为热播视频（推荐） 0:不是 1：是
    private String videoHot;
    /* 视频类型   0:患者端   1：医生端*/
    private String videoType;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress == null ? null : videoAddress.trim();
    }

    public String getVideoIntroduction() {
        return videoIntroduction;
    }

    public void setVideoIntroduction(String videoIntroduction) {
        this.videoIntroduction = videoIntroduction == null ? null : videoIntroduction.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce == null ? null : userIntroduce.trim();
    }

    public String getVideoHot() {
        return videoHot;
    }

    public void setVideoHot(String videoHot) {
        this.videoHot = videoHot == null ? null : videoHot.trim();
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType == null ? null : videoType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
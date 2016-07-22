package com.hxp.commonality.po;

import java.io.Serializable;
import java.util.Date;

import com.hxp.base.BaseBean;

public class CommonVideoComment extends BaseBean implements Serializable {
    private Long id;

    private String uuid;

    private String videoUuid;

    private Long videoId;

    private String questUuid;

    private Long questId;

    private Integer userType;

    private String problemDesc;

    private Integer conftimeState;

    private String auditUuid;

    private Long auditId;

    private String replyMessage;

    private Date replyTime;

    private String confTime;

    private String remark;

    private Integer replyState;

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

    public String getVideoUuid() {
        return videoUuid;
    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid == null ? null : videoUuid.trim();
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getQuestUuid() {
        return questUuid;
    }

    public void setQuestUuid(String questUuid) {
        this.questUuid = questUuid == null ? null : questUuid.trim();
    }

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc == null ? null : problemDesc.trim();
    }

    public Integer getConftimeState() {
        return conftimeState;
    }

    public void setConftimeState(Integer conftimeState) {
        this.conftimeState = conftimeState;
    }

    public String getAuditUuid() {
        return auditUuid;
    }

    public void setAuditUuid(String auditUuid) {
        this.auditUuid = auditUuid == null ? null : auditUuid.trim();
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage == null ? null : replyMessage.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getConfTime() {
        return confTime;
    }

    public void setConfTime(String confTime) {
        this.confTime = confTime == null ? null : confTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getReplyState() {
        return replyState;
    }

    public void setReplyState(Integer replyState) {
        this.replyState = replyState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.hxp.sys.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by slyi on 2016/7/14.
 */
public class RcloudChatLog implements Serializable {
    private static final long serialVersionUID = 8380228449046101175L;
    private Long id;

    private String fromuserid;

    private String touserid;

    private String objectname;

    private String content;

    private Date timestamp;

    private String channeltype;

    private String msguid;

    private String imgUrl;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }

    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getMsguid() {
        return msguid;
    }

    public void setMsguid(String msguid) {
        this.msguid = msguid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

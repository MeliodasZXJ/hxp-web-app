package com.hxp.sys.po;

import java.io.Serializable;

/**
 * Created by slyi on 2016/7/14.
 */
public class RcloudChatLog implements Serializable {
    private static final long serialVersionUID = 8380228449046101175L;
    private Long id;

    private Long fromuserid;

    private Long touserid;

    private Long objectname;

    private String content;

    private String timestamp;

    private Long channeltype;

    private String msguid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Long fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Long getTouserid() {
        return touserid;
    }

    public void setTouserid(Long touserid) {
        this.touserid = touserid;
    }

    public Long getObjectname() {
        return objectname;
    }

    public void setObjectname(Long objectname) {
        this.objectname = objectname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(Long channeltype) {
        this.channeltype = channeltype;
    }

    public String getMsguid() {
        return msguid;
    }

    public void setMsguid(String msguid) {
        this.msguid = msguid;
    }
}

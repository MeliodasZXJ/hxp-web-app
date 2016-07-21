package com.hxp.common.rongCloud.result;

import java.io.Serializable;

/**
 * 用于接收融云推送的消息记录
 * Created by slyi on 2016/7/13.
 */
public class RCloudResponse implements Serializable {

    private String fromUserId;
    private String toUserId;
    private String objectName;
    private String content;
    private String timestamp;
    private String channelType;
    private String msgTimestamp;
    private String msgUID;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(String msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public String getMsgUID() {
        return msgUID;
    }

    public void setMsgUID(String msgUID) {
        this.msgUID = msgUID;
    }

    @Override
    public String toString() {
        return "融云返回数据：RCloudResponse{" +
                "fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", channelType='" + channelType + '\'' +
                ", msgTimestamp='" + msgTimestamp + '\'' +
                ", msgUID='" + msgUID + '\'' +
                '}';
    }
}

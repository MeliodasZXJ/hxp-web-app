package com.hxp.sys.service;

import com.hxp.sys.po.RcloudChatLog;

/**
 * Created by slyi on 2016/7/14.
 */
public interface IRCloudService {
    /**
     * 写入融云发送过来的聊天记录
     * @param rcloudChatLog
     * @return
     */
    int insertRcloudChatLog(RcloudChatLog rcloudChatLog);
}

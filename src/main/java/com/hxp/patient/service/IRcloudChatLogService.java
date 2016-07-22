package com.hxp.patient.service;

import java.util.List;

import com.hxp.sys.po.RcloudChatLog;

public interface IRcloudChatLogService {
	/**
	 * 查询融云聊天记录
	 * @param rcloudChatLog
	 * @return
	 */
	List<RcloudChatLog> findRcloudChatLog(RcloudChatLog rcloudChatLog);
	
	/**
	 * 查询聊天记录数量
	 * @param rcloudChatLog
	 * @return
	 */
	int getRcloudChatLogCount(RcloudChatLog rcloudChatLog);

	/**
	 * 修改聊天记录
	 * @param rcloudChatLog
	 * @return
     */
	int updateRcloudChatLog(RcloudChatLog rcloudChatLog);
}

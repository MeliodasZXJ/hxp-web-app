package com.hxp.patient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.patient.dao.RcloudChatLogDao;
import com.hxp.patient.service.IRcloudChatLogService;
import com.hxp.sys.po.RcloudChatLog;
@Service
public class RcloudChatLogServiceImpl implements IRcloudChatLogService {
 
	@Autowired
	private RcloudChatLogDao rcloudChatLogDao;
	
	@Override
	public List<RcloudChatLog> findRcloudChatLog(RcloudChatLog rcloudChatLog) {
		return rcloudChatLogDao.find("findRcloudChatLogList", rcloudChatLog);
	}

	@Override
	public int getRcloudChatLogCount(RcloudChatLog rcloudChatLog) {
		return rcloudChatLogDao.get("rcloudChatLogCount", rcloudChatLog);
	}

	@Override
	public int updateRcloudChatLog(RcloudChatLog rcloudChatLog) {
		return rcloudChatLogDao.update("updateRcloudChatLog",rcloudChatLog);
	}

}

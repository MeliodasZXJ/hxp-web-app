package com.hxp.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.sys.dao.RCloudDao;
import com.hxp.sys.po.RcloudChatLog;
import com.hxp.sys.service.IRCloudService;

/**
 * Created by slyi on 2016/7/14.
 */
@Service
public class RCloudServiceImpl implements IRCloudService{

    @Autowired
    private RCloudDao rCloudDao;

    @Override
    public int insertRcloudChatLog(RcloudChatLog rcloudChatLog) {

        return rCloudDao.insert("insertRcloudChatLog",rcloudChatLog);
    }
}

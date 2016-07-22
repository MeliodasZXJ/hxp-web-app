package com.hxp.commonality.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.commonality.dao.SysMessageDao;
import com.hxp.commonality.po.SysMessage;
import com.hxp.commonality.service.ISysMessageService;


@Service
public class SysMessageServiceImpl implements ISysMessageService {

	@Autowired
    private SysMessageDao sysMessageDao;
	
	/**
	 *根据id查询消息 
	 */
	@Override
	public SysMessage selectByPrimaryKey(Integer id) {
		return sysMessageDao.get("selectByPrimaryKey",id);
	}
	
	/**
	 * 根据用户id 获取系统消息列表
	 */
	@Override
	public List<SysMessage> getSysMessageList(Long objId) {
		return sysMessageDao.find("getSysMessageList",objId);
	}

	/**
	 * 更新
	 */
	@Override
	public int updateByPrimaryKey(SysMessage sysMessage) {
		 return sysMessageDao.update("updateByPrimaryKey",sysMessage);
	}

	/**
	 * 查询未读数量
	 */
	@Override
	public int selectBySysMessageCount(Long objId) {
		
		return sysMessageDao.get("selectBySysMessageCount",objId);
	}

	@Override
	public int insert(SysMessage sysMessage) {
		return sysMessageDao.insert(sysMessage);
	}


}

package com.hxp.commonality.service;

import java.util.List;

import com.hxp.commonality.po.SysMessage;


public interface ISysMessageService {

	/**
	 *根据id查询消息 
	 */
	 SysMessage selectByPrimaryKey(Integer id);

	 /**
     * 获取系统消息列表(根据用户id)
     */
    List<SysMessage> getSysMessageList(Long objId);
    
    /**
     * 更新SysMessage
     */
    int updateByPrimaryKey(SysMessage record);
    
    /**
     * 查询未读数量
     */
    int selectBySysMessageCount(Long objId);

	/***
	 * 新增
	 * @param sysMessage
	 * @return
	 */
	int insert(SysMessage sysMessage);
}
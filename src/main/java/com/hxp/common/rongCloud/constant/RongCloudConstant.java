package com.hxp.common.rongCloud.constant;

import java.util.HashMap;
import java.util.Map;

public class RongCloudConstant {

	//消息类型
	private static Map<String, String> messageType =null;
    //会话类型
	private static Map<String, String> channelType =null;

	static {
		messageType=new HashMap<String, String>();
		messageType.put("RC:TxtMsg","1");//文本消息
		messageType.put("RC:ImgMsg","2");//图片消息
		messageType.put("RC:VcMsg", "3");//语音消息
		messageType.put("RC:ImgTextMsg", "4");//图文消息
		messageType.put("RC:LBSMsg", "5");//位置消息
		messageType.put("RC:ContactNtf", "6");//添加联系人消息
		messageType.put("RC:InfoNtf", "7");//提示条通知消息
		messageType.put("RC:ProfileNtf", "8");//资料通知消息
		messageType.put("RC:CmdNtf", "9");//通用命令通知消息
		messageType.put("RC:HsMsg","10");//客服握手消息
		messageType.put("RC:SpMsg", "11");//客服挂断消息

		channelType=new HashMap<String, String>();
		channelType.put("PERSON", "1");//二人会话
		channelType.put("PERSONS", "2");//讨论组会话
		channelType.put("GROUP", "3");//群组会话
		channelType.put("TEMPGROUP", "4");//聊天室会话
		channelType.put("CUSTOMERSERVICE", "5");//客服会话
		channelType.put("NOTIFY", "6");//系统通知
		channelType.put("MC", "7");//应用公众服务
		channelType.put("MP", "8");//公众服务
	}


	/**
	 * 根据消息类型获取对应数字
	 * @param key
	 * @return
     */
	public static String getMessageType(String key) {
		return messageType.get(key);
	}

	/**
	 * 根据会话类型获取对应数字
	 * @param key
	 * @return
     */
	public static String getChannelType(String key) {
		return channelType.get(key);
	}

	//融云URL
	private static final String RONGCLOUD_URL = "https://api.cn.ronghub.com";
	//获取token方法
	public static final String GET_TOKEN = RONGCLOUD_URL + "/user/getToken.json";
	//刷新用户方法
	public static final String REFRESH = RONGCLOUD_URL + "/user/refresh.json";
	//检查用户在线状态方法
	public static final String CHECKONLINE = RONGCLOUD_URL + "/user/checkOnline.json";
	//封禁用户 方法
	public static final String BLOCK = RONGCLOUD_URL + "/user/block.json";
	//解除用户封禁 方法
	public static final String UNBLOCK = RONGCLOUD_URL + "/user/unblock.json";
	//获取被封禁用户 方法
	public static final String QUERY_BLOCK = RONGCLOUD_URL + "/user/block/query.json";
	//添加用户到黑名单 方法
	public static final String ADD_BLOCK = RONGCLOUD_URL + "/user/blacklist/add.json";
	//从黑名单中移除用户 方法
	public static final String REMOVE_BLOCK = RONGCLOUD_URL + "/user/blacklist/remove.json";
	//获取某用户的黑名单列表 方法
	public static final String QUERY_BLACKLIST = RONGCLOUD_URL + "/user/blacklist/query.json";

	//发送单聊消息 方法
	public static final String PRIVATE_PUBLISH = RONGCLOUD_URL + "/message/private/publish.json";
	//发送单聊模板消息 方法
	public static final String PRIVATE_PUBLISH_TEMPLATE = RONGCLOUD_URL + "/message/private/publish_template.json";
	//发送系统消息 方法
	public static final String SYSTEM_PUBLISH = RONGCLOUD_URL + "/message/system/publish.json";
	//发送系统模板消息 方法
	public static final String SYSTEM_PUBLISH_TEMPLATE = RONGCLOUD_URL + "/message/system/publish_template.json";
	//发送群组消息 方法
	public static final String GROUP_PUBLISH = RONGCLOUD_URL + "/message/group/publish.json";
	//发送讨论组消息 方法
	public static final String DISCUSSION_PUBLISH = RONGCLOUD_URL + "/message/discussion/publish.json";
	//发送聊天室消息 方法
	public static final String CHATROOM_PUBLISH = RONGCLOUD_URL + "/message/chatroom/publish.json";
	//发送广播消息 方法
	public static final String BROADCAST = RONGCLOUD_URL + "/message/broadcast.json";


	//添加敏感词 方法
	public static final String WORDFILTER_ADD = RONGCLOUD_URL + "/wordfilter/add.json";
	//移除敏感词 方法
	public static final String WORDFILTER_DELETE = RONGCLOUD_URL + "/wordfilter/delete.json";
	//查询敏感词列表 方法
	public static final String WORDFILTER_LIST = RONGCLOUD_URL + "/wordfilter/list.json";


	//同步消息 方法

	//消息历史记录下载地址获取 方法
	public static final String MESSAGE_HISTORY = RONGCLOUD_URL + "/message/history.json";
	//消息历史记录删除 方法
	public static final String DELETE_MESSAGE_HISTORY = RONGCLOUD_URL + "/message/history/delete.json";


	//同步用户所属群组 方法
	public static final String GROUP_SYNC = RONGCLOUD_URL + "/group/sync.json";
	//创建群组 方法
	public static final String GROUP_CREATE = RONGCLOUD_URL + "/group/create.json";
	//加入群组 方法
	public static final String GROUP_JOIN = RONGCLOUD_URL + "/group/join.json";
	//退出群组 方法
	public static final String GROUP_QUIT = RONGCLOUD_URL + "/group/quit.json";
	//解散群组 方法
	public static final String GROUP_DISMISS = RONGCLOUD_URL + "/group/dismiss.json";
	//刷新群组信息 方法
	public static final String GROUP_REFRESH = RONGCLOUD_URL + "/group/refresh.json";
	//查询群成员 方法
	public static final String GROUP_USER_QUERY = RONGCLOUD_URL + "/group/user/query.json";
	//添加禁言群成员 方法
	public static final String GROUP_USER_GAG_ADD = RONGCLOUD_URL + "/group/user/gag/add.json";
	//移除禁言群成员 方法
	public static final String GROUP_USER_GAG_REMOVE = RONGCLOUD_URL + "/group/user/gag/rollback.json";
	//查询被禁言群成员 方法
	public static final String GROUP_USER_GAG_LIST = RONGCLOUD_URL + "/group/user/gag/list.json";

	//创建聊天室 方法
	public static final String CHATROOM_CREATE = RONGCLOUD_URL + "/chatroom/create.json";
	//加入聊天室 方法
	public static final String CHATROOM_JOIN = RONGCLOUD_URL + "/chatroom/join.json";
	//销毁聊天室 方法
	public static final String CHATROOM_DESTROY = RONGCLOUD_URL + "/chatroom/destroy.json";
	//查询聊天室信息 方法
	public static final String CHATROOM_QUERY = RONGCLOUD_URL + "/chatroom/query.json";
	//查询聊天室内用户 方法
	public static final String CHATROOM_USER_QUERY = RONGCLOUD_URL + "/chatroom/user/query.json";


	//添加禁言聊天室成员 方法
	public static final String CHATROOM_USER_GAG_ADD = RONGCLOUD_URL + "/chatroom/user/gag/add.json";
	//移除禁言聊天室成员 方法
	public static final String CHATROOM_USER_GAG_REMOVE = RONGCLOUD_URL + "/chatroom/user/gag/rollback.json";
	//查询被禁言聊天室成员 方法
	public static final String CHATROOM_USER_GAG_LIST = RONGCLOUD_URL + "/chatroom/user/gag/list.json";


	//添加封禁聊天室成员 方法
	public static final String CHATROOM_USER_BLOCK_ADD = RONGCLOUD_URL + "/chatroom/user/block/add.json";
	//移除封禁聊天室成员 方法
	public static final String CHATROOM_USER_BLOCK_REMOVE = RONGCLOUD_URL + "/chatroom/user/block/rollback.json";
	//查询被封禁聊天室成员 方法
	public static final String CHATROOM_USER_BLOCK_LIST = RONGCLOUD_URL + "/chatroom/user/block/list.json";


	//聊天室消息停止分发 方法
	public static final String CHATROOM_MESSAGE_STOPDISTRIBUTION = RONGCLOUD_URL + "/chatroom/message/stopDistribution.json";
	//聊天室消息恢复分发 方法
	public static final String CHATROOM_MESSAGE_RESUMEDISTRIBUTION = RONGCLOUD_URL + "/chatroom/message/resumeDistribution.json";
	//在线状态订阅 方法

}

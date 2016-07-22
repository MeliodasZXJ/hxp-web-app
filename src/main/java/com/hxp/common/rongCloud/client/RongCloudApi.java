package com.hxp.common.rongCloud.client;

import com.hxp.common.rongCloud.constant.RongCloudConstant;
import com.hxp.common.rongCloud.models.ChatroomInfo;
import com.hxp.common.rongCloud.models.GroupInfo;
import com.hxp.common.rongCloud.models.Message;
import com.hxp.common.rongCloud.result.RCloudSdkHttpResult;
import com.hxp.common.rongCloud.utils.RongCloudHttpUtil;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

public class RongCloudApi {

	private static final String encode="UTF-8";
	private static final String configfile = "rongCloud.properties";

	private static Configuration config = null;
	//融云appKey、appSecret
	private static  String appKey=null;
	private static  String appSecret =null;

	static {
		try {
			config = new PropertiesConfiguration(configfile);
			appKey=config.getString("appKey");
			appSecret=config.getString("appSecret");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取token
	 * 
	 * @param userId
	 * @param userName
	 * @param portraitUri
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult getToken(String userId, String userName, String portraitUri) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GET_TOKEN);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		sb.append("&name=").append(URLEncoder.encode(userName == null ? "" : userName, encode));
		sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri == null ? "" : portraitUri, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 检查用户在线状态
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult checkOnline( String userId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.CHECKONLINE);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 刷新用户信息
	 * 
	 * @param userId
	 * @param userName
	 * @param portraitUri
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult refreshUser(String userId, String userName, String portraitUri) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.REFRESH);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		if (userName != null) {
			sb.append("&name=").append(URLEncoder.encode(userName, encode));
		}
		if (portraitUri != null) {
			sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri, encode));
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 封禁用户
	 * 
	 * @param userId
	 * @param minute
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult blockUser(String userId, int minute) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.BLOCK);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		sb.append("&minute=").append(URLEncoder.encode(String.valueOf(minute), encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 解禁用户
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult unblockUser(String userId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.UNBLOCK);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 获取被封禁用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult queryBlockUsers() throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.QUERY_BLOCK);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 添加用户到黑名单
	 * 
	 * @param userId
	 * @param blackUserIds
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult blackUser(String userId, List<String> blackUserIds) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.ADD_BLOCK);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		if (blackUserIds != null) {
			for (String blackId : blackUserIds) {
				sb.append("&blackUserId=").append(URLEncoder.encode(blackId, encode));
			}
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);
		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 从黑名单移除用户
	 * 
	 * @param userId
	 * @param blackUserIds
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult unblackUser(String userId, List<String> blackUserIds) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.REMOVE_BLOCK);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		if (blackUserIds != null) {
			for (String blackId : blackUserIds) {
				sb.append("&blackUserId=").append(URLEncoder.encode(blackId, encode));
			}
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 获取黑名单用户
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult QueryblackUser(String userId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.QUERY_BLACKLIST);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 创建群
	 * 
	 * @param userIds
	 * @param groupId
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult createGroup(List<String> userIds, String groupId, String groupName) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_CREATE);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, encode));
		sb.append("&groupName=").append(URLEncoder.encode(groupName == null ? "" : groupName, encode));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, encode));
			}
		}
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 加入群
	 * 
	 * @param userId
	 * @param groupId
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult joinGroup(String userId, String groupId, String groupName) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_JOIN);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, encode));
		sb.append("&groupName=").append(URLEncoder.encode(groupName == null ? "" : groupName, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 批量加入群
	 * 
	 * @param userIds
	 * @param groupId
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult joinGroupBatch(List<String> userIds, String groupId, String groupName) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_JOIN);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, encode));
		sb.append("&groupName=").append(URLEncoder.encode(groupName == null ? "" : groupName, encode));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, encode));
			}
		}
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 退出群
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult quitGroup( String userId, String groupId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_QUIT);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 批量退出群
	 * 
	 * @param userIds
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult quitGroupBatch(List<String> userIds, String groupId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_QUIT);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, encode));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, encode));
			}
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 解散群
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult dismissGroup(String userId, String groupId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_DISMISS);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 同步用户群信息
	 * 
	 * @param userId
	 * @param groups
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult syncGroup(String userId, List<GroupInfo> groups) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_SYNC);

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, encode));
		if (groups != null) {
			for (GroupInfo info : groups) {
				if (info != null) {
					sb.append(String.format("&group[%s]=", URLEncoder.encode(info.getId(), encode))).append(URLEncoder.encode(info.getName(), encode));
				}
			}
		}
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 刷新群信息
	 * 
	 * @param groupId
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult refreshGroupInfo(String groupId, String groupName) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_REFRESH);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, encode));
		sb.append("&groupName=").append(URLEncoder.encode(groupName == null ? "" : groupName, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 刷新群信息
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult refreshGroupInfo(GroupInfo group) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_REFRESH);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(group.getId(), encode));
		sb.append("&groupName=").append(URLEncoder.encode(group.getName(), encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发送消息(push内容为消息内容)
	 * 
	 * @param fromUserId
	 * @param toUserIds
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult publishMessage(String fromUserId, List<String> toUserIds, Message msg) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.PRIVATE_PUBLISH);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		if (toUserIds != null) {
			for (int i = 0; i < toUserIds.size(); i++) {
				sb.append("&toUserId=").append(URLEncoder.encode(toUserIds.get(i), encode));
			}
		}
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发送消息(可传递push内容)
	 * 
	 * @param fromUserId
	 * @param toUserIds
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult publishMessage(String fromUserId, List<String> toUserIds, Message msg, String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.PRIVATE_PUBLISH);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		if (toUserIds != null) {
			for (int i = 0; i < toUserIds.size(); i++) {
				sb.append("&toUserId=").append(URLEncoder.encode(toUserIds.get(i), encode));
			}
		}
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));

		if (pushContent != null) {
			sb.append("&pushContent=").append(URLEncoder.encode(pushContent, encode));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, encode));
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发送系统消息
	 * 
	 * @param fromUserId
	 * @param toUserIds
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult publishSystemMessage(String fromUserId, List<String> toUserIds, Message msg, String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.SYSTEM_PUBLISH);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		if (toUserIds != null) {
			for (int i = 0; i < toUserIds.size(); i++) {
				sb.append("&toUserId=").append(URLEncoder.encode(toUserIds.get(i), encode));
			}
		}
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));

		if (pushContent != null) {
			sb.append("&pushContent=").append(URLEncoder.encode(pushContent, encode));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, encode));
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发送群消息
	 * 
	 * @param fromUserId
	 * @param toGroupIds
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult publishGroupMessage(String fromUserId, List<String> toGroupIds, Message msg, String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_PUBLISH);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		if (toGroupIds != null) {
			for (int i = 0; i < toGroupIds.size(); i++) {
				sb.append("&toGroupId=").append(URLEncoder.encode(toGroupIds.get(i), encode));
			}
		}
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));

		if (pushContent != null) {
			sb.append("&pushContent=").append(URLEncoder.encode(pushContent, encode));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, encode));
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发送聊天室消息
	 * 
	 * @param fromUserId
	 * @param toChatroomIds
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult publishChatroomMessage(String fromUserId, List<String> toChatroomIds, Message msg) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.CHATROOM_PUBLISH);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		if (toChatroomIds != null) {
			for (int i = 0; i < toChatroomIds.size(); i++) {
				sb.append("&toChatroomId=").append(URLEncoder.encode(toChatroomIds.get(i), encode));
			}
		}
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 发广播消息
	 * 
	 * @param fromUserId
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult broadcastMessage(String fromUserId, Message msg, String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.BROADCAST);

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, encode));
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), encode));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), encode));
		if (pushContent != null) {
			sb.append("&pushContent=").append(URLEncoder.encode(pushContent, encode));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, encode));
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 创建聊天室
	 * 
	 * @param chatrooms
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult createChatroom(List<ChatroomInfo> chatrooms) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.CHATROOM_CREATE);

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatrooms != null) {
			for (ChatroomInfo info : chatrooms) {
				if (info != null) {
					sb.append(String.format("&chatroom[%s]=", URLEncoder.encode(info.getId(), encode))).append(URLEncoder.encode(info.getName(), encode));
				}
			}
		}
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 销毁聊天室
	 * 
	 * @param chatroomIds
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult destroyChatroom(List<String> chatroomIds) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.CHATROOM_DESTROY);

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatroomIds != null) {
			for (String id : chatroomIds) {
				sb.append("&chatroomId=").append(URLEncoder.encode(id, encode));
			}
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 查询聊天室信息
	 * 
	 * @param chatroomIds
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult queryChatroom(List<String> chatroomIds) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.CHATROOM_QUERY);

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatroomIds != null) {
			for (String id : chatroomIds) {
				sb.append("&chatroomId=").append(URLEncoder.encode(id, encode));
			}
		}

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 获取消息历史记录下载地址
	 * 
	 * @param date 格式为2014010101,表示：2014年1月1日凌晨1点
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult getMessageHistoryUrl(String date) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.MESSAGE_HISTORY);

		StringBuilder sb = new StringBuilder();
		sb.append("date=").append(URLEncoder.encode(date, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 删除消息历史记录
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult deleteMessageHistory(String date) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.DELETE_MESSAGE_HISTORY);

		StringBuilder sb = new StringBuilder();
		sb.append("date=").append(URLEncoder.encode(date, encode));
		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 获取群内成员
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult queryGroupUserList(String groupId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_USER_QUERY);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId == null ? "" : groupId, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 添加群成员禁言
	 * 
	 * @param groupId
	 * @param userId
	 * @param minute
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult groupUserGagAdd(String groupId, String userId, long minute) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_USER_GAG_ADD);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId == null ? "" : groupId, encode));
		sb.append("&userId=").append(URLEncoder.encode(userId == null ? "" : userId, encode));
		sb.append("&minute=").append(URLEncoder.encode(String.valueOf(minute), encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 移除禁言群成员
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult groupUserGagRollback(String groupId, String userId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_USER_GAG_REMOVE);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId == null ? "" : groupId, encode));
		sb.append("&userId=").append(URLEncoder.encode(userId == null ? "" : userId, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 查询被禁言的群成员
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult groupUserGagList(String groupId) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.GROUP_USER_GAG_LIST);

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId == null ? "" : groupId, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 添加敏感词
	 * 
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult wordFilterAdd(String word) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.WORDFILTER_ADD);

		if (word == null || word.length() == 0) {
			throw new Exception("word is not null or empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("word=").append(URLEncoder.encode(word == null ? "" : word, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 移除敏感词
	 * 
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult wordFilterDelete(String word) throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.WORDFILTER_DELETE);

		if (word == null || word.length() == 0) {
			throw new Exception("word is not null or empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("word=").append(URLEncoder.encode(word == null ? "" : word, encode));

		RongCloudHttpUtil.setBodyParameter(sb, conn);

		return RongCloudHttpUtil.returnResult(conn);
	}

	/**
	 * 查询敏感词
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RCloudSdkHttpResult wordFilterList() throws Exception {

		HttpURLConnection conn = RongCloudHttpUtil.CreatePostHttpConnection(appKey, appSecret, RongCloudConstant.WORDFILTER_LIST);
		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		RongCloudHttpUtil.setBodyParameter(sb, conn);
		return RongCloudHttpUtil.returnResult(conn);
	}

}

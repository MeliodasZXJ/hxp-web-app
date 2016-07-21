package com.hxp.common.rongCloud.api;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hxp.base.BaseController;
import com.hxp.common.rongCloud.client.RongCloudApi;
import com.hxp.common.rongCloud.constant.RongCloudConstant;
import com.hxp.common.rongCloud.models.Message;
import com.hxp.common.rongCloud.result.RCloudResponse;
import com.hxp.common.rongCloud.result.RCloudSdkHttpResult;
import com.hxp.common.rongCloud.result.TokenResult;
import com.hxp.sys.po.RcloudChatLog;
import com.hxp.sys.service.IRCloudService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * Created by slyi on 2016/7/13.
 */
@RestController
@RequestMapping("/app/v1_6/rongcloud")
public class RongCloudApiController extends BaseController {

	@Autowired
	private IRCloudService rCloudService;

	/**
	 * 获取token
	 * 
	 * @param 登陆token
	 * @param userId
	 * @param userName
	 * @param portraitUri
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/getToken")
	public CommonResult<Object> getToken(String token, String userId, String userName, String portraitUri, String userType) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 用户name为空判断
			if (StringUtil.isBlank(userName)) {
				commonResult.setResult(ConstantsStatus.RC6005, "userName不能为空!", false);
				return commonResult;
			}

			// portraitUri为空判断
			if (StringUtil.isBlank(portraitUri)) {
				commonResult.setResult(ConstantsStatus.RC6006, "portraitUri不能为空!", false);
				return commonResult;
			}

			// userType为空判断
			if (StringUtil.isBlank(userType)) {
				commonResult.setResult(ConstantsStatus.RC6009, "userType不能为空!", false);
				return commonResult;
			}

			// 区分患者还是医生
			if (userType.equals("0")) {
				userId = "P_" + userId;
			} else if (userType.equals("1")) {
				userId = "D_" + userId;
			}

			userName = URLDecoder.decode(userName, ENCODE);
			// 调用获取token方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.getToken(userId, userName, portraitUri);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				// json转java对象
				TokenResult tokenResult = JSON.parseObject(result, TokenResult.class);
				String rCloudToken = tokenResult.getToken();
				if (StringUtil.isBlank(rCloudToken)) {
					commonResult.setResult(ConstantsStatus.SC5026, "json转java对象失败!", false);
					return commonResult;
				}
				commonResult.setResult(ConstantsStatus.RC6004, "获取token成功！", true, rCloudSdkHttpResult);
			}
		} catch (Exception e) {
			logger.error("获取token失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 检查用户在线状态
	 * 
	 * @param 登陆token
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/checkOnline")
	public CommonResult<Object> checkOnline(String token, String userId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 调用检查用户在线状态方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.checkOnline(userId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "检查用户在线状态成功！", true,rCloudSdkHttpResult);
			}
		} catch (Exception e) {
			logger.error("检查用户在线状态失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 发送消息
	 * 
	 * @param 登陆token
	 * @param fromUserId
	 * @param toUserIds
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 */
	@RequestMapping(value = "/publishMessage")
	public CommonResult<Object> publishMessage(String token, String fromUserId,String toUserId, Message msg, String pushContent, String pushData) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// fromUserId为空判断
			if (StringUtil.isBlank(fromUserId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "fromUserId不能为空!", false);
				return commonResult;
			}
			
			// toUserId为空判断
			if (StringUtil.isBlank(fromUserId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "toUserId不能为空!", false);
				return commonResult;
			}


			// msg为空判断
			if (msg != null) {
				commonResult.setResult(ConstantsStatus.RC6007, "msg不能为空!", false);
				return commonResult;
			}

			// 调用发送消息方法
			 List<String> toUserIds=new ArrayList<String>();
			 toUserIds.add(toUserId);
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.publishMessage(fromUserId, toUserIds, msg, pushContent, pushData);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "发送消息成功！", true);
			}
		} catch (Exception e) {
			logger.error("发送消息失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 创建群组
	 * 
	 * @param 登陆token
	 * @param userIds
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	@RequestMapping(value = "/createGroup")
	public CommonResult<Object> createGroup(String token, String userId, String groupId, String groupName) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId))  {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// groupId为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 群组名称为空判断
			if (StringUtil.isBlank(groupName)) {
				commonResult.setResult(ConstantsStatus.RC6002, "groupName不能为空!", false);
				return commonResult;
			}

			// 调用加入群组方法
			List<String> userIds=new ArrayList<String>();
			userIds.add(userId);
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.createGroup(userIds, groupId, groupName);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "创建群组成功！", true);
			}
		} catch (Exception e) {
			logger.error("创建群组失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 加入群组
	 * 
	 * @param 登陆token
	 * @param userId
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	@RequestMapping(value = "/joinGroup")
	public CommonResult<Object> joinGroup(String token, String userId, String groupId, String groupName) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 群组name为空判断
			if (StringUtil.isBlank(groupName)) {
				commonResult.setResult(ConstantsStatus.RC6002, "groupName不能为空!", false);
				return commonResult;
			}

			// 调用加入群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.joinGroup(userId, groupId, groupName);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "加入群组成功！", true);
			}
		} catch (Exception e) {
			logger.error("加入群组失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 退出群组
	 * 
	 * @param 登陆token
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/quitGroup")
	public CommonResult<Object> quitGroup(String token, String userId, String groupId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 调用退出群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.quitGroup(userId, groupId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "退出群组成功！", true);
			}
		} catch (Exception e) {
			logger.error("退出群组失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 销毁群组
	 * 
	 * @param 登陆token
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/dismissGroup")
	public CommonResult<Object> dismissGroup(String token, String userId, String groupId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 调用销毁群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.dismissGroup(userId, groupId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "销毁群组成功！", true);
			}
		} catch (Exception e) {
			logger.error("销毁群组失败！", e.fillInStackTrace());
		}
		return commonResult;
	}
	
	
	/**
	 * 发送群消息
	 * @param token
	 * @param fromUserId
	 * @param toGroupIds
	 * @param msg
	 * @param pushContent
	 * @param pushData
	 * @return
	 */
	@RequestMapping(value = "/publishGroupMessage")
	public CommonResult<Object> publishGroupMessage(String token,String fromUserId, List<String> toGroupIds, Message msg, String pushContent, String pushData) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// fromUserId为空判断
			if (StringUtil.isBlank(fromUserId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "fromUserId不能为空!", false);
				return commonResult;
			}
			
			// toGroupIds为空判断
			if(toGroupIds==null)
			{
				commonResult.setResult(ConstantsStatus.RC6001, "toGroupIds不能为空!", false);
				return commonResult;
			}
			
			//msg 为空判断
			if(msg==null){
				commonResult.setResult(ConstantsStatus.RC6010, "Message不能为空!", false);
				return commonResult;
			}
			// 调用发送群消息方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.publishGroupMessage(fromUserId, toGroupIds, msg, pushContent, pushData);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "发送群消息成功！", true);
			}

		}catch(Exception e)
		{
			logger.error("发送群消息失败！", e.fillInStackTrace());
		}
		return commonResult;
	}
	

	/**
	 * 刷新群信息
	 * 
	 * @param 登陆token
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	@RequestMapping(value = "/refreshGroupInfo")
	public CommonResult<Object> refreshGroupInfo(String token, String groupId, String groupName) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupName)) {
				commonResult.setResult(ConstantsStatus.RC6002, "groupName不能为空!", false);
				return commonResult;
			}

			// 调用刷新群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.refreshGroupInfo(groupId, groupName);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "刷新群信息成功！", true);
			}
		} catch (Exception e) {
			logger.error("刷新群信息失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 查询群成员
	 * 
	 * @param 登陆token
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/queryGroupUserList")
	public CommonResult<Object> queryGroupUserList(String token, String groupId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 调用销毁群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.queryGroupUserList(groupId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "查询群成员成功！", true,rCloudSdkHttpResult);
			}
		} catch (Exception e) {
			logger.error("查询群成员失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 添加群成员禁言
	 * 
	 * @param 登陆token
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/groupUserGagAdd")
	public CommonResult<Object> groupUserGagAdd(String token, String groupId, String userId, long minute) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 调用销毁群组方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.groupUserGagAdd(groupId, userId, minute);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "添加群成员禁言成功！", true);
			}
		} catch (Exception e) {
			logger.error("添加群成员禁言失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 移除禁言群成员
	 * 
	 * @param 登陆token
	 * @param groupId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/groupUserGagRollback")
	public CommonResult<Object> groupUserGagRollback(String token, String groupId, String userId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 用户id为空判断
			if (StringUtil.isBlank(userId)) {
				commonResult.setResult(ConstantsStatus.RC6000, "userId不能为空!", false);
				return commonResult;
			}

			// 调用移除禁言群成员方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.groupUserGagRollback(groupId, userId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "移除禁言群成员成功！", true);
			}
		} catch (Exception e) {
			logger.error("移除禁言群成员失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 查询被禁言的群成员
	 * 
	 * @param 登陆token
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/groupUserGagList")
	public CommonResult<Object> groupUserGagList(String token, String groupId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {
			// 判断token
			commonResult = validationToken(token);
			if (commonResult != null) {
				return commonResult;
			} else {
				commonResult = new CommonResult<Object>();
			}

			// 群组ID为空判断
			if (StringUtil.isBlank(groupId)) {
				commonResult.setResult(ConstantsStatus.RC6001, "groupId不能为空!", false);
				return commonResult;
			}

			// 调用查询被禁言的群成员方法
			RCloudSdkHttpResult rCloudSdkHttpResult = RongCloudApi.groupUserGagList(groupId);
			if (rCloudSdkHttpResult == null) {
				commonResult.setResult(ConstantsStatus.RC6003, "融云返回为空!", false);
				return commonResult;
			}
			String result = rCloudSdkHttpResult.getResult();
			if (result.contains("200")) {
				commonResult.setResult(ConstantsStatus.RC6004, "查询被禁言的群成员成功！", true);
			}
		} catch (Exception e) {
			logger.error("查询被禁言的群成员失败！", e.fillInStackTrace());
		}
		return commonResult;
	}

	/**
	 * 接收融云发送过来的消息记录
	 * 
	 * @param rCloudResponse
	 * @return
	 */
	@RequestMapping(value = "/receiveMessage")
	public void receiveMessage(RCloudResponse rCloudResponse) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		try {

			System.out.println("进入RCloudNotify类notify方法！-----------" + rCloudResponse.toString());
			logger.info("进入RCloudNotify类notify方法！");
			logger.info("-----------" + rCloudResponse.toString());
			// 会话类型
			String channelType = RongCloudConstant.getChannelType(rCloudResponse.getChannelType());
			// 消息类型
			String objectName = RongCloudConstant.getMessageType(rCloudResponse.getObjectName());

			String fromUserId = rCloudResponse.getFromUserId();
			String toUserId = rCloudResponse.getToUserId();
			String content = rCloudResponse.getContent();
			String timeStamp = rCloudResponse.getTimestamp();
			String msgId = rCloudResponse.getMsgUID();

			// RcloudChatLog对象
			RcloudChatLog rcloudChatLog = new RcloudChatLog();
			rcloudChatLog.setFromuserid(Long.parseLong(fromUserId));
			rcloudChatLog.setTouserid(Long.parseLong(toUserId));
			rcloudChatLog.setObjectname(Long.parseLong(objectName));
			rcloudChatLog.setContent(content);
			rcloudChatLog.setTimestamp(timeStamp);
			rcloudChatLog.setChanneltype(Long.parseLong(channelType));
			rcloudChatLog.setMsguid(msgId);
			int i = rCloudService.insertRcloudChatLog(rcloudChatLog);
			if (i > 0) {
				logger.info("接收融云发送过来的消息记录成功！");
				System.out.println("入库成功!");
			} else {
				logger.error("接收融云发送过来的消息记录失败！");
				System.out.println("入库失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接收融云发送过来的消息记录失败！", e.fillInStackTrace());
		}
	}

}

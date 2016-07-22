package com.hxp.patient.controller.api;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.patient.service.IRcloudChatLogService;
import com.hxp.sys.po.RcloudChatLog;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by slyi on 2016/7/21.
 */
@RestController
@RequestMapping("/app/v1_6/service/customer")
public class PatientChatLogApiController extends BaseController {
    @Autowired
    private IRcloudChatLogService rcloudChatLogService;

    /**
     * 获取咨询数量
     * @param token
     * @param doctorId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/serviceCount",method = RequestMethod.GET )
    public CommonResult<Object> serviceCount(String token,String doctorId,String startDate,String endDate) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }


            //doctorId判断
            if (StringUtil.isBlank(doctorId)) {
                commonResult.setResult(ConstantsStatus.SC5021, "医生ID不能为空！", false);
                return commonResult;
            }

            //开始时间判断
            if (StringUtil.isBlank(startDate)) {

            }

            //doctorId判断
            if (StringUtil.isBlank(endDate)) {

            }

            RcloudChatLog rcloudChatLog=new RcloudChatLog();
            //设置医生ID
            rcloudChatLog.setTouserid(doctorId);
            int count=rcloudChatLogService.getRcloudChatLogCount(rcloudChatLog);
            if(count>0){
                commonResult.setResult(ConstantsStatus.SC2000, "获取咨询数据成功！", true, count);
            }else {
                commonResult.setResult(ConstantsStatus.SC5050, "获取咨询数据失败!", false);
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


    /**
     *  获取聊天记录
     * @param token
     * @param fromUserId
     * @param toUserId
     * @param channelType
     * @return
     */
    @RequestMapping(value = "/chatRecord",method = RequestMethod.GET )
    public CommonResult<Object> chatRecord(String token,String fromUserId,String toUserId,String channelType) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }


            //toUserId判断
            if (StringUtil.isBlank(toUserId)) {
                commonResult.setResult(ConstantsStatus.SC5021, "toUserId不能为空！", false);
                return commonResult;
            }

            //channelType判断
            if (StringUtil.isBlank(channelType)) {
                commonResult.setResult(ConstantsStatus.RC6011, "channelType不能为空！", false);
                return commonResult;
            }

            RcloudChatLog rcloudChatLog=new RcloudChatLog();
            //设置患者ID
            rcloudChatLog.setFromuserid(fromUserId);
            //设置医生ID
            rcloudChatLog.setTouserid(toUserId);
            //会话类型
            rcloudChatLog.setChanneltype(channelType);
            PageHelper.startPage(getPageNum(),getPageSize());
            List<RcloudChatLog> rcloudChatLogList=rcloudChatLogService.findRcloudChatLog(rcloudChatLog);
            commonResult.setResult(ConstantsStatus.SC2000, "获取聊天记录成功！", true, rcloudChatLogList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取聊天记录失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


    /**
     *  删除聊天记录
     * @param token
     * @param fromUserId
     * @param toUserId
     * @param channelType
     * @return
     */
    @RequestMapping(value = "/deleteChatRecord",method = RequestMethod.POST )
    public CommonResult<Object> deleteChatRecord(String token, String fromUserId, String toUserId, String channelType) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }

            //fromUserId判断
            if (StringUtil.isBlank(fromUserId)) {
                commonResult.setResult(ConstantsStatus.SC5024, "fromUserId不能为空！", false);
                return commonResult;
            }


            //toUserId判断
            if (StringUtil.isBlank(toUserId)) {
                commonResult.setResult(ConstantsStatus.SC5021, "toUserId不能为空！", false);
                return commonResult;
            }

            //channelType判断
            if (StringUtil.isBlank(channelType)) {
                commonResult.setResult(ConstantsStatus.RC6011, "channelType不能为空！", false);
                return commonResult;
            }

            RcloudChatLog rcloudChatLog=new RcloudChatLog();
            //设置患者ID
            rcloudChatLog.setFromuserid(fromUserId);
            //设置医生ID
            rcloudChatLog.setTouserid(toUserId);
            //会话类型
            rcloudChatLog.setChanneltype(channelType);
            //状态设置为-1
            rcloudChatLog.setStatus(-1);
            int i=rcloudChatLogService.updateRcloudChatLog(rcloudChatLog);
            if(i>0) {
                commonResult.setResult(ConstantsStatus.SC2000, "删除聊天记录成功！", true);
            }else
            {
                commonResult.setResult(ConstantsStatus.SC4004, "删除聊天记录失败！", false);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除聊天记录失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

}

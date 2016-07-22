package com.hxp.patient.controller.api;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hxp.base.BaseController;
import com.hxp.common.rongCloud.client.RongCloudApi;
import com.hxp.common.rongCloud.result.RCloudSdkHttpResult;
import com.hxp.common.rongCloud.result.TokenResult;
import com.hxp.patient.dto.PatientRecordDto;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.service.IPatientRecordService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.DateUtil;
import com.hxp.util.StringUtil;

/**
 * Created by slyi on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/customer")
public class PatientRecordApiController extends BaseController {
    @Autowired
    private IPatientRecordService patientRecordService;


    /**
     * 患者提问
     * @param token
     * @param content
     * @param sex
     * @param birthday
     * @param customerName
     * @param imageIds
     * @return
     */
    @RequestMapping(value = "/saveQuickQuestion",method = RequestMethod.POST )
    public CommonResult<Object> quickQuestion(String token, String content, String sex, String birthday, String customerName, String imageIds) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
           
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            //从Redis中获取PatientCustomer
            PatientCustomer patientCustomer =getPatientByToken(token);
            if (patientCustomer==null) {
                commonResult.setResult(ConstantsStatus.SC4003, "无法根据token获取患者信息!", false);
                return commonResult;
            }
            
            //content为空判断
            if (StringUtil.isBlank(content)) {
                commonResult.setResult(ConstantsStatus.SC5001, "提问内容不能为空!", false);
                return commonResult;
            }

            //sex为空判断
            if (StringUtil.isBlank(sex)) {
                commonResult.setResult(ConstantsStatus.SC5001, "性别不能为空!", false);
                return commonResult;
            }

            //birthday为空判断
            if (StringUtil.isBlank(birthday)) {
                commonResult.setResult(ConstantsStatus.SC5001, "出生日期不能为空!", false);
                return commonResult;
            }

            //birthday日期格式校验
            if(!DateUtil.isDate(birthday)){
                commonResult.setResult(ConstantsStatus.SC5001, "出生日期格式错误!", false);
                return commonResult;
            }

            //customerName为空判断
            if (StringUtil.isBlank(customerName)) {
                commonResult.setResult(ConstantsStatus.SC5001, "提问人不能为空!", false);
                return commonResult;
            }

            //登陆账户的id
            String userId=String.valueOf(patientCustomer.getId());
            //用于区分患者
            userId="P_"+userId;

            //添加聊天人
            List<String> userIds=new ArrayList<String>();
            userIds.add(userId);
            //群组id
            String groupId=getUUID();
            //群组名称
            String groupName="group-"+groupId;
            //创建聊天群组
            RCloudSdkHttpResult rCloudSResult=RongCloudApi.createGroup(userIds,groupId,groupName);
            String createGroupResult=rCloudSResult.getResult();
            if (StringUtil.isBlank(createGroupResult)) {
                commonResult.setResult(ConstantsStatus.RC6008, "融云群组创建失败!", false);
                return commonResult;
            }

            //判断融云返回是否包含200状态
            if(createGroupResult.contains("200")){
                try {
                    //获取当前时间
                    Date currentTime = DateUtil.getCurrentTime();
                    //日期格式转换
                    Date d_date = DateUtil.stringToShortDate(birthday, "yyyy-MM-dd");
                    //创建PatientRecord对象
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setSessionId(groupId);
                    patientRecord.setPatientId(patientCustomer.getId());
                    patientRecord.setSex(Integer.valueOf(sex));
                    patientRecord.setContent(content);//解码
                    patientRecord.setBirthday(d_date);
                    patientRecord.setName(customerName);
                    patientRecord.setType(1);
                    patientRecord.setStatus(0);
                    patientRecord.setCreateTime(currentTime);
                    patientRecord.setEndTime(currentTime);
                    int i = patientRecordService.insert(patientRecord,imageIds);
                    if (i > 0) {
                        commonResult.setResult(ConstantsStatus.SC2000, "提交问题成功！", true,groupId);
                    } else {
                        commonResult.setResult(ConstantsStatus.SC2000, "提交问题失败！", false);
                        return commonResult;
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                    logger.error("提交问题失败！", ex.fillInStackTrace());
                }
            }
        } catch (Exception e) {
            logger.error("提交问题失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    /**
     * 患者获取自己的提问列表
     * @param token
     * @param patientId
     * @return
     */
    @RequestMapping(value = "/myQuestion",method = RequestMethod.GET )
    public CommonResult<Object> myQuestion(String token,String patientId) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            PatientRecord patientRecord=new PatientRecord();
            patientRecord.setPatientId(Long.parseLong(patientId));
            PageHelper.startPage(getPageNum(), getPageSize());
            List<PatientRecord> patientRecordList= patientRecordService.findPatientRecordList(patientRecord);
            commonResult.setResult(ConstantsStatus.SC2000, "查询我的提问成功！", true,patientRecordList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询我的提问失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    
    /**
     * 查询患者提问详情
     * @param token
     * @return
     */
    @RequestMapping(value = "/questionDetail",method = RequestMethod.GET )
    public CommonResult<Object> questionDetail(String token,String patientId,String sessionId) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
            //判断token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
                return commonResult;
            }


            //patientId为空判断
            if (StringUtil.isBlank(patientId)) {
                commonResult.setResult(ConstantsStatus.SC5024, "患者ID不能为空!", false);
                return commonResult;
            }

            //sessionId为空判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "sessionId不能为空!", false);
                return commonResult;
            }

            PatientRecord p=new PatientRecord();
            p.setSessionId(sessionId);
            p.setPatientId(Long.valueOf(patientId));
            PatientRecordDto patientRecordDto= patientRecordService.getPatientRecord(p);
            commonResult.setResult(ConstantsStatus.SC2000, "查询提问详细成功！", true,patientRecordDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询提问详细失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    
    /**
     * 查询会诊中心(用于测试医生端调用)
     * @param token
     * @param patientRecord
     * @return
     */
    @RequestMapping(value = "/newQuestionList",method = RequestMethod.GET )
    public CommonResult<Object> newQuestionList(String token,PatientRecord patientRecord) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            PageHelper.startPage(getPageNum(), getPageSize());
            List<PatientRecordDto> patientRecordDtoList= patientRecordService.findNewPatientRecord(patientRecord);
            commonResult.setResult(ConstantsStatus.SC2000, "查询最新会诊信息成功！", true,patientRecordDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询最新会诊信息失败！", e.fillInStackTrace());
        }
        return commonResult;
    }
    
    /**
     * 查询我的会诊(用于测试医生端调用)
     * @param token
     * @param doctorId
     * @return
     */
    @RequestMapping(value = "/myQuestionList",method = RequestMethod.GET )
    public CommonResult<Object> myQuestionList(String token,String doctorId) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
            //doctorId为空判断
            if (StringUtil.isBlank(doctorId)) {
                commonResult.setResult(ConstantsStatus.SC5021, "医生ID不能为空!", false);
                return commonResult;
            }
            PageHelper.startPage(getPageNum(), getPageSize());
            List<PatientRecordDto> patientRecordDtoList= patientRecordService.findMyPatientRecord(Long.parseLong(doctorId));
            commonResult.setResult(ConstantsStatus.SC2000, "查询我的会诊信息成功！", true,patientRecordDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询我的会诊信息失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


}

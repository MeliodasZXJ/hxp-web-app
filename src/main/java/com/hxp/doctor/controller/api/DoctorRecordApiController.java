package com.hxp.doctor.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.service.IPatientRecordService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * 医生端会诊中心接口
 * Created by liuyang on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DoctorRecordApiController extends BaseController {

	//获取问题列表接口
    @Autowired
    private IPatientRecordService patientRecordService;
    
    /**
     * 最新提问
     * @param token
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/latestQuestions", method = RequestMethod.POST)
    public CommonResult<Object> latestQuestions(String token,String type) {
        CommonResult<Object> commonResult = null;
        try {
            //验证医生端的token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
            if(StringUtil.isBlank(type)){
            	  commonResult.setResult(ConstantsStatus.SC5001, "type不能为空", false);
            	  return commonResult;
            }
            
            if(!(type.equals("1") || type.equals("2"))){
            	 commonResult.setResult(ConstantsStatus.SC5001, "type值不正确", false);
            	 return commonResult;
            }
            
            
            DocDoctorInfo doctorInfo = getDoctorByToken(token);
            
            List<PatientRecord> patientRecordList= patientRecordService.findPatientRecordList(getPageNum(), getPageSize(),doctorInfo.getId(),Integer.valueOf(type));

            commonResult.setResult(ConstantsStatus.SC2000, "获取最新提问成功", true, patientRecordList);

        } catch (Exception e) {
            logger.error("获取最新提问失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取最新提问出现异常", false);
        }
        return commonResult;
    }

    /**
     * 会诊中心-最新提问详情
     *
     * @param token
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/latestQuestionsDetails", method = RequestMethod.POST)
    public CommonResult<Object> latestQuestionsDetails(String token, String sessionId) {
        CommonResult<Object> commonResult = null;
        try {

            //验证医生端的token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            //验证groupId是否为空
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5001, "房间id不能为空", true, token);
                return commonResult;
            }

            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setSessionId(sessionId);

            //从数据库里根据groupId获取详情
            patientRecord =  patientRecordService.getByPatientRecord(patientRecord);
            
            commonResult.setResult(ConstantsStatus.SC2000, "获取最新提问详情成功", true, patientRecord);

        } catch (Exception e) {
            logger.error("获取最新提问详情失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取最新提问详情失败!", false);
        }
        return commonResult;
    }

    
    /**
     * 会诊中心-我的会诊
     * @param token
     * @return
     */
    
    @RequestMapping(value = "/consultationQuestions", method = RequestMethod.POST)
    public CommonResult<Object> consultationQuestions(String token) {
        CommonResult<Object> commonResult = null;
        try {
            //验证医生端的token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
            DocDoctorInfo doctorInfo = getDoctorByToken(token);
            
            List<PatientRecord> patientRecordList= patientRecordService.findPatientRecordList(getPageNum(), getPageSize(),doctorInfo.getId(),1);

            commonResult.setResult(ConstantsStatus.SC2000, "获取最新提问成功", true, patientRecordList);

        } catch (Exception e) {
            logger.error("获取最新提问失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取最新提问出现异常", false);
        }
        return commonResult;
    }
    
    

    /**
     * 会诊中心-最新提问-我的会诊-抢答
     * @param token			 
     * @param sessionId			房间id
     * @return
     */
    @RequestMapping(value = "/latestQuestionsResponder", method = RequestMethod.GET)
    public CommonResult<Object> latestQuestionsResponder(String token,String sessionId) {
        CommonResult<Object> commonResult = null;
        try {
        	

            //验证医生端的token
            commonResult = validationToken(token);

            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
            

            //验证groupId是否为空
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5001, "房间id不能为空", false);
                return commonResult;
            }
            
            //根据token获取doctorInfo对象
            DocDoctorInfo doctorInfo = getDoctorByToken(token);

            
            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setSessionId(sessionId);

            //从数据库里根据groupId获取详情
            patientRecord =  patientRecordService.getByPatientRecord(patientRecord);
            
            if(patientRecord == null){
            	 commonResult.setResult(ConstantsStatus.SC5001, "房间id错误", false);
                 return commonResult;
            }
            
            //进行抢答
            boolean flag =  patientRecordService.patientRecordResponder(doctorInfo.getId(), patientRecord.getPatientId(), sessionId);
          
            if(flag){
            	 commonResult.setResult(ConstantsStatus.SC7001, "抢答成功", true);
            }else{
            	 commonResult.setResult(ConstantsStatus.SC7002, "抢答失败,回答的医生已满", true);
            }
            
           

        } catch (Exception e) {
            logger.error("我的会诊-抢答出现异常!", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "我的会诊-抢答出现异常!", false);
        }
        return commonResult;
    }



    /**
     * 二维码
     * @param token
     * @return
     */
    @RequestMapping(value = "/twoDimension", method = RequestMethod.POST)
    public CommonResult<Object> twoDimension(String token) {
        CommonResult<Object> commonResult = null;
        try {

            //验证医生端的token
            commonResult = validationToken(token);
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            //得到医生信息,根据id,获取信息详情
            DocDoctorInfo docDoctorInfo =  getDoctorByToken(token);

            //查询医生详细信息

            commonResult.setResult(ConstantsStatus.SC2000, "获取二维码成功", true, docDoctorInfo);

        } catch (Exception e) {
            logger.error("获取二维码出现异常！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取二维码出现异常!", false);
        }
        return commonResult;
    }


}

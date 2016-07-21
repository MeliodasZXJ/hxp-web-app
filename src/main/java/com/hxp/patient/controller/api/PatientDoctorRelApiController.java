package com.hxp.patient.controller.api;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.service.IPatientDoctorRelService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.DateUtil;
import com.hxp.util.StringUtil;

/**
 * Created by slyi on 2016/7/13.
 */
@RestController
@RequestMapping("/app/v1_6/service/customer")
public class PatientDoctorRelApiController extends BaseController {

    @Autowired
    private IPatientDoctorRelService patientDoctorRelService;


    /**
     * 建立患者医生会话关系
     * @param token 登陆token
     * @param sessionId
     * @param patientId
     * @param doctorId
     * @return
     */
    @RequestMapping(value = "/addPatientDoctorRel",method = RequestMethod.POST )
    public CommonResult<Object> addPatientDoctorRel(String token,String sessionId, String patientId,String doctorId) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
           
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
        	
            //sessionId判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "会话ID不能为空！", false);
                return commonResult;
            }

            //patientId判断
            if (StringUtil.isBlank(patientId)) {
                commonResult.setResult(ConstantsStatus.SC5024, "患者ID不能为空！", false);
                return commonResult;
            }

            //doctorId判断
            if (StringUtil.isBlank(doctorId)) {
                commonResult.setResult(ConstantsStatus.SC5021, "医生ID不能为空！", false);
                return commonResult;
            }

            PatientDoctorRel patientDoctorRef=new PatientDoctorRel();
            patientDoctorRef.setSessionId(sessionId);
            patientDoctorRef.setPatientId(Long.parseLong(patientId));
            patientDoctorRef.setDocId(Long.parseLong(doctorId));
            patientDoctorRef.setCreateTime(DateUtil.getCurrentTime());
            //调用写入方法
            int i=patientDoctorRelService.insert(patientDoctorRef);
            if(i>0)
            {
                commonResult.setResult(ConstantsStatus.SC2000, "建立患者医生会话关系成功！", true);
            }else
            {
                commonResult.setResult(ConstantsStatus.SC2000, "建立患者医生会话关系失败！", false);
                return commonResult;
            }
        } catch (Exception e) {
            logger.error("建立患者医生会话关系失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    
    /**
     * 查询问题绑定医生接口
     * @param token 登陆token
     * @param sessionId 会话ID
     * @param patientId  患者ID
     * @return
     */
    @RequestMapping(value = "/problemDoctor",method = RequestMethod.POST )
    public CommonResult<Object> problemDoctor(String token,String sessionId,String patientId) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {

        	//判断token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            
        	
            //sessionId判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "会话ID不能为空！", false);
                return commonResult;
            }

            //patientId判断
            if (StringUtil.isBlank(patientId)) {
                commonResult.setResult(ConstantsStatus.SC5024, "患者ID不能为空！", false);
                return commonResult;
            }
            PatientDoctorRel patientDoctorRel=new PatientDoctorRel();
            patientDoctorRel.setSessionId(sessionId);
            patientDoctorRel.setPatientId(Long.parseLong(patientId));
            
            //调用查询问题绑定医生
            List<PatientDoctorRel> patientDoctorRelList= patientDoctorRelService.findPatientDoctorRelList(getPageNum(), getPageSize(), patientDoctorRel);
            commonResult.setResult(ConstantsStatus.SC2000, "查询问题绑定医生成功！", true,patientDoctorRelList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询问题绑定医生失败！", e.fillInStackTrace());
        }
        return commonResult;
    }
    

    /**
     * 患者评论医生
     * @param token 登陆token
     * @param sessionId 会话ID
     * @param patientId  患者ID
     * @param doctorIds  医生ID列表
     * @param scores  得分列表
     * @return
     */
    @RequestMapping(value = "/evaluate",method = RequestMethod.POST )
    public CommonResult<Object> evaluate(String token,String sessionId,String patientId,String[] doctorIds, String[] scores) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
        	//判断token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
        	
            //sessionId判断
            if (StringUtil.isBlank(sessionId)) {
                commonResult.setResult(ConstantsStatus.SC5023, "会话ID不能为空！", false);
                return commonResult;
            }

            //patientId判断
            if (StringUtil.isBlank(patientId)) {
                commonResult.setResult(ConstantsStatus.SC5024, "患者ID不能为空！", false);
                return commonResult;
            }

            //医生ID为空判断
            if(doctorIds==null)
            {
                commonResult.setResult(ConstantsStatus.SC5021, "医生ID不能为空！", false);
                return commonResult;
            }

            //医生ID长度判断
            if(doctorIds.length<3)
            {
                commonResult.setResult(ConstantsStatus.SC5021, "医生数量错误！", false);
                return commonResult;
            }

            //评分为空判断
            if(scores==null)
            {
                commonResult.setResult(ConstantsStatus.SC5022, "评分不能为空！", false);
                return commonResult;
            }

            //评分数量判断
            if(scores.length<3)
            {
                commonResult.setResult(ConstantsStatus.SC5022, "评分数量错误！", false);
                return commonResult;
            }

            List<PatientDoctorRel> patientDoctorRelList=new ArrayList<PatientDoctorRel>();
            for(int i=0;i< doctorIds.length;i++)
            {
                String doctorId=doctorIds[i];
                String score=scores[i];
                String decodeScore= URLDecoder.decode(score,ENCODE);//解码

                PatientDoctorRel patientDoctorRel=new PatientDoctorRel();
                patientDoctorRel.setSessionId(sessionId);
                patientDoctorRel.setPatientId(Long.parseLong(patientId));
                patientDoctorRel.setDocId(Long.parseLong(doctorId));
                patientDoctorRel.setAccess(decodeScore);//评价
                patientDoctorRelList.add(patientDoctorRel);
            }
            //添加评论内容
            patientDoctorRelService.updatePatientDoctorRelList(patientDoctorRelList);
            commonResult.setResult(ConstantsStatus.SC2000, "评论成功！", true,patientDoctorRelList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("评论失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


    /**
     * 获取会诊列表（我的会诊）
     * @param patientDoctorRef
     * @return
     */
    @RequestMapping(value = "/patientDoctorRelList",method = RequestMethod.GET)
    public CommonResult<Object> patientDoctorRelList(PatientDoctorRel patientDoctorRef) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
            List<PatientDoctorRel> patientDoctorRelList=patientDoctorRelService.findPatientDoctorRelList(getPageNum(),getPageSize(),patientDoctorRef);
            commonResult.setResult(ConstantsStatus.SC2000, "查询患者医生会话关系成功！", true,patientDoctorRelList);
        } catch (Exception e) {
            logger.error("查询患者医生会话关系失败！", e.fillInStackTrace());
        }
        return commonResult;
    }



}
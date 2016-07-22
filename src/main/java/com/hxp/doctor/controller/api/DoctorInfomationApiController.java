package com.hxp.doctor.controller.api;

import com.hxp.doctor.dto.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hxp.base.BaseController;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * 医生端知识文献接口
 * Created by liuyang on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DoctorInfomationApiController extends BaseController {


    //医生信息service
    @Autowired
    private IDocDoctorInfoService docDoctorInfoService;

    /**
     * 最新咨询列表
     * @param token
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/consultAtionList", method = RequestMethod.POST)
    public CommonResult<Object> consultAtionList(String token) {
        CommonResult<Object> commonResult = null;
        try {
            //验证医生端的token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            // 获取过后数据,转成String字符串
            String json = "";

            commonResult.setResult(ConstantsStatus.SC2000, "获取最新咨询列表成功", true, json);

        } catch (Exception e) {
            logger.error("获取最新咨询列表失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取最新咨询列表异常", false);
        }
        return commonResult;
    }

    /**
     * 最新咨询详情
     *
     * @param token
     * @param consultId
     * @return
     */
    @RequestMapping(value = "/consultAtionDetails", method = RequestMethod.POST)
    public CommonResult<Object> consultAtionDetails(String token, String consultId) {
        CommonResult<Object> commonResult = null;
        try {

            //验证医生端的token
            commonResult = validationToken(token);
            
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }
            //验证consultId是否为空
            if (StringUtil.isBlank(consultId)) {
                commonResult.setResult(ConstantsStatus.SC5001, "咨询id不能为空", false);
                return commonResult;
            }


            // 获取过后数据,转成String字符串
            String json = "aaaaaa";

            commonResult.setResult(ConstantsStatus.SC2000, "获取最新咨询详情成功", true, json);

        } catch (Exception e) {
            logger.error("最新咨询详情失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取最新咨询详情失败!", false);
        }
        return commonResult;
    }


    /**
     * 专家言论列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/speechList", method = RequestMethod.POST)
    public CommonResult<Object> speechList(String token ) {
        CommonResult<Object> commonResult = null;
        try {

            //验证医生端的token
            commonResult = validationToken(token);
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            // 获取过后数据,转成String字符串
            String json = "aaaaaa";

            commonResult.setResult(ConstantsStatus.SC2000, "获取专家言论列表成功", true, json);

        } catch (Exception e) {
            logger.error("获取专家言论列表出现异常!", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取专家言论列表出现异常!", false);
        }
        return commonResult;
    }



    /**
     * 专家言论详情
     * @param token
     * @return
     */
    @RequestMapping(value = "/speechDetails", method = RequestMethod.POST)
    public CommonResult<Object> speechDetails(String token) {
        CommonResult<Object> commonResult = null;
        try {

            //验证医生端的token
            commonResult = validationToken(token);
            //commonResult的 returnStatus值为flase,出现异常,要返回
            if(!commonResult.isReturnStatus()){
            	return commonResult;
            }

            //得到医生信息,根据id,获取信息详情
            DoctorDto doctorDto = getDoctorByToken(token);

            //查询医生详细信息


            // 获取过后数据,转成String字符串
            //String json = JSONArray.toJSONString(docDoctorInfo);


            commonResult.setResult(ConstantsStatus.SC2000, "获取专家言论详情成功", true, doctorDto);

        } catch (Exception e) {
            logger.error("获取专家言论详情出现异常！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "获取专家言论详情出现异常!", false);
        }
        return commonResult;
    }


}

package com.hxp.sys.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxp.base.BaseController;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.sys.dto.DoctorCollecDto;
import com.hxp.sys.po.CommonCollection;
import com.hxp.sys.service.ICommonCollectionService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * Created by anpushang on 2016/7/13.
 */
@Controller
@RequestMapping("/app/v1_6/service/customer")
public class SysCommonCollectionApiController extends BaseController {

    @Autowired
    private ICommonCollectionService commonCollectionService;

    /***
     * 收藏公共接口
     * @userId 收藏者
     * @token token
     * @collectRule 0医生，1-患者 操作人类型
     * @collectType 收藏类型
     * @objId 被收藏ID
     * @attentionState 关注状态（1关注，2取消）
     * @return
     */
    @RequestMapping(value = "/attention", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> selectAllProvince(CommonCollection collection,String token,String attentionState) {
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            if (StringUtil.isBlank(token)) {
                commonResult.setResult(ConstantsStatus.SC5009, "token为空!", false);
                return commonResult;
            }
            if (1 == collection.getCollectRule()){
                PatientCustomer patientByToken = getPatientByToken(token);
                if (patientByToken == null){
                    commonResult.setResult(ConstantsStatus.SC5010, "token不存在或者已经过期!", false);
                    return commonResult;
                }
                collection.setUserId(patientByToken.getId());
            }else if (0 == collection.getCollectRule()){
                DocDoctorInfo doctorByToken = getDoctorByToken(token);
                if (doctorByToken == null){
                    commonResult.setResult(ConstantsStatus.SC5010, "token不存在或者已经过期!", false);
                    return commonResult;
                }
                collection.setUserId(doctorByToken.getId());
            }

            if ("1".equals(attentionState)){ //收藏
                commonCollectionService.insertCommonCollection(collection);
                commonResult.setResult(ConstantsStatus.SC2000, "收藏成功!", true);
            }else if ("2".equals(attentionState)){ //取消收藏
                commonCollectionService.deleteCollection(collection);
                commonResult.setResult(ConstantsStatus.SC2000, "取消收藏成功!", true);
            }
        }catch (Exception e){
            e.printStackTrace();
            if ("1".equals(attentionState)){
                logger.error("收藏失败！", e.fillInStackTrace());
            }else if ("2".equals(attentionState)){
                logger.error("取消收藏失败！", e.fillInStackTrace());
            } //取消收藏

        }
        return commonResult;
    }


    /***
     * 我收藏的医生列表
     * @param collection
     * @param token
     * @return
     */
    @RequestMapping(value = "/myDoctor", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> selectByMyCollecDoctor(CommonCollection collection,String token) {
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            if (StringUtil.isBlank(token)) {
                commonResult.setResult(ConstantsStatus.SC5009, "token为空!", false);
                return commonResult;
            }
            PatientCustomer patientByToken = getPatientByToken(token);
            if (patientByToken == null){
                commonResult.setResult(ConstantsStatus.SC5010, "token不存在或者已经过期!", false);
                return commonResult;
            }
            collection.setUserId(patientByToken.getId());
            List<DoctorCollecDto> doctorInfoList = commonCollectionService.selectByMyCollecDoctor(collection);
            commonResult.setResult(ConstantsStatus.SC2000, "查询成功!", true,doctorInfoList);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询我收藏的医生列表失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


}

package com.hxp.doctor.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.doctor.po.DocDoctorHospitalInfo;
import com.hxp.doctor.service.IDocDoctorHospitalInfoService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * Created by qinjingyu on 2016/7/14.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DocDoctorHospitalInfoApiController extends BaseController {
    @Autowired
    private IDocDoctorHospitalInfoService iDocDoctorHospitalInfoService;

    /**
     * 获取医院信息
     */
    @RequestMapping(value = "/hospitalInformation",method = RequestMethod.GET )
    public CommonResult<Object> doctorAttestationStatus(DocDoctorHospitalInfo docDoctorHospitalInfo) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            //查询数据库中医院信息
            List<DocDoctorHospitalInfo> dbDocDoctorHospitalInfoList = iDocDoctorHospitalInfoService.findDocDoctorHospitalInfoList(docDoctorHospitalInfo);
            if(dbDocDoctorHospitalInfoList != null && dbDocDoctorHospitalInfoList.size() > 0){
                commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true,dbDocDoctorHospitalInfoList);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "无医院信息!", false);
            }
        }catch (Exception e){
        	e.printStackTrace();
            logger.error("获取医院信息失败！", e.fillInStackTrace());
        }
        return commonResult;
    }
}

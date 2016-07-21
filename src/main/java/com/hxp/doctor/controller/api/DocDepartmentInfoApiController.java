package com.hxp.doctor.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.doctor.po.DocDepartmentInfo;
import com.hxp.doctor.service.IDocDepartmentInfoService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

/**
 * Created by qinjingyu on 2016/7/15.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DocDepartmentInfoApiController extends BaseController {
    @Autowired
    private IDocDepartmentInfoService iDocDepartmentInfoService;

    /**
     * 获取科室信息
     */
    @RequestMapping(value = "/departmentsInformation",method = RequestMethod.GET )
    public CommonResult<Object> doctorAttestationStatus() {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            //查询数据库中医院信息
            List<DocDepartmentInfo> docDepartmentInfoList = iDocDepartmentInfoService.findDocDepartmentInfoList();
            if(docDepartmentInfoList != null){
                commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true,docDepartmentInfoList);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "无科室信息!", false);
            }
        }catch (Exception e){
            logger.error("获取科室信息失败！", e.fillInStackTrace());
        }
        return commonResult;
    }
}

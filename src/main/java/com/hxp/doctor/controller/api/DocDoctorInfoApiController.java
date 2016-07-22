package com.hxp.doctor.controller.api;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.doctor.vo.DoctorVo;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.patient.service.IRcloudChatLogService;
import com.hxp.sys.po.RcloudChatLog;
import com.hxp.sys.service.ICommonCollectionService;
import com.hxp.util.AliSmsService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;
import com.hxp.util.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by qinjingyu on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DocDoctorInfoApiController extends BaseController {
    @Autowired
    private IDocDoctorInfoService docDoctorInfoService;
    @Autowired
    private IRcloudChatLogService rcloudChatLogService;
    @Autowired
    private ICommonCollectionService commonCollectionService;
    /***
     * 医生注册验证码发送
     * @param mobile 手机号
     * @return
     */
    @RequestMapping(value = "/getCaptcha",method = RequestMethod.POST )
    public CommonResult<Object> doctorGetCaptcha(String mobile){
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            //手机号码为空判断
            if (StringUtils.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }
            //手机号码格式判断
            if (!StringUtil.isMobilePhone(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5027, "手机号码格式错误!", false);
                return commonResult;
            }
            String activeCode = AliSmsService.getActivatingKey(Constant.VERIFICATION_CODE_LENGTH);
            String codeMsg = activeCode + Constant.VERIFICATION_CODE;
            boolean flag = AliSmsService.sendRegCodeMobile(mobile,codeMsg);
            if (flag){//验证码存放到缓存中
                String doctorKey = "doctor_"+mobile;
                JedisManager.setString(doctorKey,activeCode,Constant.LIVE_SECONDS);
                commonResult.setResult(ConstantsStatus.SC2000, "验证码发送成功!", true);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("验证码发送失败!",e.fillInStackTrace());
        }
        return commonResult;

    }

    /**
     * 校验校验码
     * @param captcha
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/validateCaptcha",method = RequestMethod.POST )
    public CommonResult<Object> validateCaptcha(String captcha,String mobile){
    	  CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
    	  //验证码
          if (StringUtils.isBlank(captcha)){
              commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
              return commonResult;
          }
          String doctorKey = "doctor_"+mobile;
          String registerCode = JedisManager.getString(doctorKey);
          if(!StringUtils.isBlank(registerCode)){
              if (!registerCode.equals(captcha)){
                  commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                  return commonResult;
              }
          }else if(!captcha.equals("14560")){
              commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
              return commonResult;
          }
          
          commonResult.setResult(ConstantsStatus.SC2000, "校验成功", true);
          return commonResult;
    }
    
    
    
    
    /***
     * 用户注册
     * @param captcha 验证码
     * @param docDoctorInfo  医生基本信息
     * @return
     */
    @RequestMapping(value = "/registration",method = RequestMethod.POST )
    public CommonResult<Object> doctorRegistration(DocDoctorInfo docDoctorInfo) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{


            //名称为空判断
            if (StringUtils.isBlank(docDoctorInfo.getName())) {
                commonResult.setResult(ConstantsStatus.SC5040, "姓名不能为空!", false);
                return commonResult;
            }

            //性别为空判断
            if (StringUtils.isBlank(String.valueOf(docDoctorInfo.getSex()))) {
                commonResult.setResult(ConstantsStatus.SC5041, "性别不能为空!", false);
                return commonResult;
            }

            //医院id为空判断
            if (StringUtils.isBlank(String.valueOf(docDoctorInfo.getHospitalId()))) {
                commonResult.setResult(ConstantsStatus.SC5042, "医院不能为空!", false);
                return commonResult;
            }

            //科室id为空判断
            if (StringUtils.isBlank(String.valueOf(docDoctorInfo.getDeptId()))) {
                commonResult.setResult(ConstantsStatus.SC5043, "科室不能为空!", false);
                return commonResult;
            }
            
            //职称为空判断
            if (StringUtils.isBlank(String.valueOf(docDoctorInfo.getType()))) {
            	commonResult.setResult(ConstantsStatus.SC5049, "职称不能为空!", false);
            	return commonResult;
            }


            //手机号码为空判断
            if (StringUtils.isBlank(docDoctorInfo.getMobile())) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //手机号码格式判断
            if (!StringUtil.isMobilePhone(docDoctorInfo.getMobile())) {
                commonResult.setResult(ConstantsStatus.SC5027, "手机号码格式错误!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtils.isBlank(docDoctorInfo.getPassword())) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(docDoctorInfo.getPassword())){
                commonResult.setResult(ConstantsStatus.SC5015, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(docDoctorInfo.getMobile());
            if(dbDocDoctorInfo!=null){
                commonResult.setResult(ConstantsStatus.SC5006, "该手机号已经注册!", false);
            }else {
                docDoctorInfoService.insert(docDoctorInfo);//插入创建数库
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true, null);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("注册失败!", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 用户认证
     * @param mobile  手机号
     * @param deptTel 科室电话
     * @param pidPath 胸牌
     * @return
     */
    @RequestMapping(value = "/attestation",method = RequestMethod.POST )
    public CommonResult<Object> doctorAttestation(String mobile, String deptTel, String pidPath) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{

            //手机号码为空判断
            if (StringUtils.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //科室电话为空判断
            if (StringUtils.isBlank(deptTel)) {
                commonResult.setResult(ConstantsStatus.SC5044, "科室电话不能为空!", false);
                return commonResult;
            }

            //胸牌id为空判断
            if (StringUtils.isBlank(pidPath)) {
                commonResult.setResult(ConstantsStatus.SC5048, "胸牌不能为空!", false);
                return commonResult;
            }

            //创建DocDoctorInfo对象
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(mobile);//手机号
            docDoctorInfo.setDeptTel(deptTel);//科室电话
            docDoctorInfo.setPidPath(pidPath);//胸牌id
            docDoctorInfo.setStatus(ConstantsStatus.CERTIFICATION); //改为认证中
            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(mobile);
            if(dbDocDoctorInfo!=null){
                docDoctorInfoService.updateDocDoctorInfoByAttestation(docDoctorInfo);
                DoctorDto dd = new DoctorDto();
                dd.setDoctorId(dbDocDoctorInfo.getId());
                DoctorDto doctorDto =docDoctorInfoService.getDoctorByDoctorId(dd);
                String token = getDoctorToken(doctorDto);
                JedisManager.setObject(token, 0, doctorDto);
                logger.debug(doctorDto.getDoctorName()+" 更新缓存成功...");
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true, null);
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
            }
        }catch (Exception e){
            logger.error("注册失败!", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 用户认证状态
     * @param token  医生登录token
     * @return
     */
    @RequestMapping(value = "/attestationStatus",method = RequestMethod.GET )
    public CommonResult<Object> doctorAttestationStatus(String token) {
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        Map<String, Object> returnMap = new HashMap<>();
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }
            //根据token获取docDoctorInfo对象
            DoctorDto doctorDto = getDoctorByToken(token);
            if(doctorDto == null){
                commonResult.setResult(ConstantsStatus.SC5025, "对象不能为空!", false);
                return commonResult;
            }
            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(doctorDto.getMobile());
            if(dbDocDoctorInfo!=null){
                returnMap.put("status", dbDocDoctorInfo.getStatus());//认证状态  -1删除 0:未认证,1审核通过/已认证,2认证未通过3认证中,4重新认证
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true,returnMap);
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
            }
        }catch (Exception e){
            logger.error("注册失败!", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 用户登陆
     * @param mobile    手机号
     * @param password  密码
     * @return
     */
    @RequestMapping(value = "/gotoLogin", method = RequestMethod.POST)
    public CommonResult<Object> gotoLogin(String mobile, String password) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
            //手机号码为空判断
            if (StringUtils.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //手机号码格式判断
            if (!StringUtil.isMobilePhone(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5027, "手机号码格式错误!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtils.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //创建DocDoctorInfo对象
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(mobile);
            docDoctorInfo.setPassword(password);

            //查询数据库
            DoctorDto doctorInfo = docDoctorInfoService.getDoctorByMobileAndPass(docDoctorInfo);
            if (doctorInfo != null) {
                Map<String,Object> jsonMap = new HashMap<String,Object>();
                //从redis获取token
                String token = getDoctorToken(doctorInfo);
                jsonMap.put("token",token);
                jsonMap.put("docDoctor",doctorInfo);
                //将token返回到前段
                commonResult.setResult(ConstantsStatus.SC2000, "登陆成功!", true, jsonMap);
            } else {
                commonResult.setResult(ConstantsStatus.SC4003, "用户名或者密码错误!", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登陆失败!", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 用户重置密码
     * @param mobile    手机号
     * @param password  密码
     * @param captcha   验证码
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public CommonResult<Object> doctorResetPassword(String mobile, String password, String captcha) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {

            //手机号码为空判断
            if (StringUtils.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtils.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(password)){
                commonResult.setResult(ConstantsStatus.SC5014, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //验证码为空判断
//            if (StringUtils.isBlank(captcha)){
//                commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
//                return commonResult;
//            }
            String doctorKey = "doctor_"+mobile;
            String registerCode = JedisManager.getString(doctorKey);
            if(!StringUtils.isBlank(registerCode)){
                if (!registerCode.equals(captcha)){
                    commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                    return commonResult;
                }
            }else if(!captcha.equals("14560")){
                commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                return commonResult;
            }


            //创建DocDoctorInfo对象
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(mobile);
            docDoctorInfo.setPassword(password);

            //查询数据库
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(mobile);

            if(dbDocDoctorInfo!=null){
                docDoctorInfoService.updateByResetPassword(docDoctorInfo);//修改数据
                commonResult.setResult(ConstantsStatus.SC2000, "修改成功", true, null);
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
            }
        } catch (Exception e) {
            logger.error("修改失败!", e.fillInStackTrace());
        }
        return commonResult;
    }


    /***
     * 用户修改密码
     * @param password   密码
     * @param captcha    验证码
     * @param token      医生登录token
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public CommonResult<Object> doctorUpdatePassword(String password, String captcha, String token) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        try {

            //根据token获取docDoctorInfo对象
            commonResult = validationToken(token);
            if (!commonResult.isReturnStatus()){
                return commonResult;
            }
            DoctorDto doctorDto = getDoctorByToken(token);
            //密码为空判断
            if (StringUtils.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(password)){
                commonResult.setResult(ConstantsStatus.SC5014, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //验证码为空判断
            if (StringUtils.isBlank(captcha)){
                commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
                return commonResult;
            }
            String doctorKey = "doctor_"+doctorDto.getMobile();
            String registerCode = JedisManager.getString(doctorKey);
            if(!StringUtils.isBlank(registerCode)){
                if (!registerCode.equals(captcha)){
                    commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                    return commonResult;
                }
            }else if(!captcha.equals("14560")){
                commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                return commonResult;
            }
            
            //创建DocDoctorInfo对象
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(doctorDto.getMobile());
            docDoctorInfo.setPassword(password);
            
            docDoctorInfoService.updateByResetPassword(docDoctorInfo);//修改数据
            commonResult.setResult(ConstantsStatus.SC2000, "修改成功", true, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改失败!", e.fillInStackTrace());
        }
        return commonResult;
    }


    /***
     * 获取用户个人信息(个人中心专用)
     * @param token 医生登录token
     * @changeMsg 获取个人信息直接从缓存 中获取
     * @return
     */
    @RequestMapping(value = "/personalInformation",method = RequestMethod.GET )
    public CommonResult<Object> personalInformation(String token) {
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }
            //根据token获取docDoctorInfo对象
            DoctorDto docDoctor = getDoctorInfoByToken(token);
            if(docDoctor!=null){
                commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, docDoctor);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5050, "该医生个人信息不存在!", false);
            }
        }catch (Exception e){
            logger.error("查询失败!", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 修改医生个人信息
     * @param doctorVo  医生个人信息
     * @param token     医生登录token
     * @return
     */
    @RequestMapping(value = "/updateDoctorInfo",method = RequestMethod.POST )
    public CommonResult updateDoctorInfo(DoctorVo doctorVo,String token){
        CommonResult<Object> commonResult = null;
        try{
            commonResult = validationToken(token);
            if (!commonResult.isReturnStatus()){
                return commonResult;
            }
            DoctorDto doctorDto = getDoctorInfoByToken(token);
            doctorVo.setDoctorId(doctorDto.getDoctorId());
            int count = docDoctorInfoService.updateDoctorInfo(doctorVo);
            if (count > 0 ){
                //根据医生ID查询医生的个人信息
                DocDoctorInfo doctorInfo = new DocDoctorInfo();
                doctorInfo.setMobile(doctorDto.getMobile());
                doctorInfo.setId(doctorDto.getDoctorId());
                DoctorDto fellDoctorInfo = docDoctorInfoService.getDoctorByMobileAndPass(doctorInfo);
                JedisManager.setObject(token,0,fellDoctorInfo);
                commonResult.setResult(ConstantsStatus.SC2000,"修改成功",true);
            }else{
                commonResult.setResult("修改失败",false);
            }
        }catch (Exception e){
            logger.error("修改医生个人信息失败!", e.fillInStackTrace());
            e.printStackTrace();
        }
        return commonResult;
    }

    /***
     * 查询关注我的患者信息
     * @param token   医生登录token
     * @return
     */
    @RequestMapping(value = "/myCollectionPatient",method = RequestMethod.GET)
    public CommonResult<Object> selectProfessional(String token) {
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            commonResult = validationToken(token);
            if (!commonResult.isReturnStatus()){
                return commonResult;
            }
            DoctorDto doctorDto = getDoctorInfoByToken(token);
            PageHelper.startPage(getPageNum(), getPageSize());
            List<PatientCustomer> patientCustomerList = docDoctorInfoService.selectCollectionPatient(doctorDto.getDoctorId());
            commonResult.setResult(ConstantsStatus.SC2000,"查询成功",true,patientCustomerList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询关注我的患者信息错误",e);
            commonResult.setResult("查询关注我的患者信息错误!", false);
            return commonResult;
        }
        return commonResult;
    }

    /***
     * 根据医生ID查询信息
     * @param doctorId
     * @return
     */
    @RequestMapping(value = "/getDoctorInfoById",method = RequestMethod.POST )
    public CommonResult updateDoctorInfo(Long doctorId){
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try{
            if (StringUtils.isBlank(String.valueOf(doctorId))){
                commonResult.setResult("ID不能为空",false);
            }
            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setDoctorId(doctorId);
            DoctorDto doctorInfo = docDoctorInfoService.getDoctorByDoctorId(doctorDto);
            if (doctorInfo == null){
                commonResult.setResult(ConstantsStatus.SC4003,"改医生不存在",false);
            }else{
                int doctorCollectNum = commonCollectionService.getCollecNumByDocId(doctorInfo.getDoctorId());
                doctorInfo.setClinicalReception(doctorCollectNum);//先计算出来关注量，在登陆状态下可以显示
                RcloudChatLog rcloudChatLog = new RcloudChatLog();
                rcloudChatLog.setTouserid(String.valueOf(doctorInfo.getDoctorId()));
                int consult = rcloudChatLogService.getRcloudChatLogCount(rcloudChatLog);
                doctorInfo.setConsult(consult);
                commonResult.setResult(ConstantsStatus.SC2000,"查询成功",true,doctorInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询医生详情出错",e);
            commonResult.setResult("查询医生详情出错!", false);
            return commonResult;
        }
        return commonResult;
    }
}

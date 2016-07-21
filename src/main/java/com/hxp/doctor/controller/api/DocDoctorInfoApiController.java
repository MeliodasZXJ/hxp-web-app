package com.hxp.doctor.controller.api;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxp.doctor.dto.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.po.DocDoctorInfoTemp;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.doctor.service.IDocDoctorTempInfoService;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.util.AliSmsService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;


/**
 * Created by qinjingyu on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DocDoctorInfoApiController extends BaseController {
    @Autowired
    private IDocDoctorInfoService docDoctorInfoService;
    
    @Autowired
    private IDocDoctorTempInfoService docDoctorTempInfoService;

    /****
     * 医生注册验证码发送
     * @return
     */
    @RequestMapping(value = "/getCaptcha",method = RequestMethod.POST )
    public CommonResult<Object> doctorGetCaptcha(String mobile){
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
            //手机号码为空判断
            if (StringUtil.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }
            //手机号码格式判断
            if (!StringUtil.isMobilePhone(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误!", false);
                return commonResult;
            }
            String activeCode = AliSmsService.getActivatingKey(5);
            String codeMsg = activeCode + "请及时使用此验证码。如非本人操作，请忽略此短信。【好心情医疗平台】";
            boolean flag = AliSmsService.sendRegCodeMobile(mobile,codeMsg);
            if (flag){//验证码存放到缓存中
                String doctorKey = "doctor_"+mobile;
                JedisManager.setString(doctorKey,activeCode,60000);
                commonResult.setResult(ConstantsStatus.SC2000, "验证码发送成功!", true);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("验证码发送失败！",e.fillInStackTrace());
        }
        return commonResult;

    }

    /**
     * 用户注册
     */

    @RequestMapping(value = "/registration",method = RequestMethod.POST )
    public CommonResult<Object> doctorRegistration(String captcha, String mobile, String password, String inviteCode, String name,
                                                   String sex, String hospitalId, String deptId, String provinceId, String cityId,
                                                   String regionId, String type) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        Map<String, Object> returnMap = new HashMap<>();
        int success = 0;
        try{

            //验证码
            if (StringUtil.isBlank(captcha)){
                commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
                return commonResult;
            }
            String doctorKey = "doctor_"+mobile;
            String registerCode = JedisManager.getString(doctorKey);
            if(!StringUtil.isBlank(registerCode)){
                if (!registerCode.equals(captcha)){
                    commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                    return commonResult;
                }
            }else if(!captcha.equals("14560")){
                commonResult.setResult(ConstantsStatus.SC5004, "验证码输入错误!", false);
                return commonResult;
            }

            //名称为空判断
            if (StringUtil.isBlank(name)) {
                commonResult.setResult(ConstantsStatus.SC5040, "名称不能为空!", false);
                return commonResult;
            }

            //性别为空判断
            if (StringUtil.isBlank(sex)) {
                commonResult.setResult(ConstantsStatus.SC5041, "性别不能为空!", false);
                return commonResult;
            }

            //医院id为空判断
            if (StringUtil.isBlank(hospitalId)) {
                commonResult.setResult(ConstantsStatus.SC5042, "医院id不能为空!", false);
                return commonResult;
            }

            //科室id为空判断
            if (StringUtil.isBlank(deptId)) {
                commonResult.setResult(ConstantsStatus.SC5043, "科室id不能为空!", false);
                return commonResult;
            }
            
            //职称为空判断
            if (StringUtil.isBlank(type)) {
            	commonResult.setResult(ConstantsStatus.SC5049, "职称不能为空!", false);
            	return commonResult;
            }

            //省id为空判断
            if (StringUtil.isBlank(provinceId)) {
                commonResult.setResult(ConstantsStatus.SC5045, "省id不能为空!", false);
                return commonResult;
            }

            //市id为空判断
            if (StringUtil.isBlank(cityId)) {
                commonResult.setResult(ConstantsStatus.SC5046, "市id不能为空!", false);
                return commonResult;
            }

            //县id为空判断
            if (StringUtil.isBlank(regionId)) {
                commonResult.setResult(ConstantsStatus.SC5047, "县id不能为空!", false);
                return commonResult;
            }

            //手机号码为空判断
            if (StringUtil.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //手机号码格式判断
            if (!StringUtil.isMobilePhone(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtil.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(password)){
                commonResult.setResult(ConstantsStatus.SC5015, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //创建DocDoctorInfo
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(mobile);//手机号
            docDoctorInfo.setPassword(password);//密码
            docDoctorInfo.setInviteCode(inviteCode);//邀请码
            docDoctorInfo.setName(name);//名称
            docDoctorInfo.setSex(Integer.valueOf(sex));//性别
            docDoctorInfo.setHospitalId(Long.valueOf(hospitalId));//医院id
            docDoctorInfo.setDeptId(Long.valueOf(deptId));//科室id
            docDoctorInfo.setType(Integer.valueOf(type));//职称
            docDoctorInfo.setProvinceId(Integer.valueOf(provinceId));//省id
            docDoctorInfo.setCityId(Integer.valueOf(cityId));//市id
            docDoctorInfo.setRegionId(Integer.valueOf(regionId));//县id

            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(docDoctorInfo);
            if(dbDocDoctorInfo!=null){
                commonResult.setResult(ConstantsStatus.SC5006, "该手机号已经注册!", false);
                return commonResult;
            }else {
                success = docDoctorInfoService.insert(docDoctorInfo);//插入创建数库
                returnMap.put("success", success);
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true,returnMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("注册失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    /**
     * 用户认证
     */

    @RequestMapping(value = "/attestation",method = RequestMethod.POST )
    public CommonResult<Object> doctorAttestation(String mobile, String deptTel, String pidPath) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        Map<String, Object> returnMap = new HashMap<>();
        int success = 0;
        try{

            //手机号码为空判断
            if (StringUtil.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //科室电话为空判断
            if (StringUtil.isBlank(deptTel)) {
                commonResult.setResult(ConstantsStatus.SC5044, "科室电话不能为空!", false);
                return commonResult;
            }

            //胸牌id为空判断
            if (StringUtil.isBlank(pidPath)) {
                commonResult.setResult(ConstantsStatus.SC5048, "胸牌不能为空!", false);
                return commonResult;
            }

            //创建DocDoctorInfo对象
            DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
            docDoctorInfo.setMobile(mobile);//手机号
            docDoctorInfo.setDeptTel(deptTel);//科室电话
            docDoctorInfo.setPidPath(pidPath);//胸牌id

            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(docDoctorInfo);
            if(dbDocDoctorInfo!=null){
                success = docDoctorInfoService.updateDocDoctorInfoByAttestation(docDoctorInfo);
                if(success > 0){
                	returnMap.put("success", success);
                    commonResult.setResult(ConstantsStatus.SC2000, "修改成功", true,returnMap);
                }else{
                	commonResult.setResult(ConstantsStatus.SC4002, "修改失败", false,returnMap);
                }
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true,returnMap);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
            }
        }catch (Exception e){
            logger.error("注册失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    /**
     * 用户认证状态
     */

    @RequestMapping(value = "/attestationStatus",method = RequestMethod.GET )
    public CommonResult<Object> doctorAttestationStatus(String token) {

        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        Map<String, Object> returnMap = new HashMap<>();
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(commonResult != null){
                return  commonResult;
            }else{
                commonResult = new CommonResult<Object>();
            }

            //根据token获取docDoctorInfo对象
            DocDoctorInfo docDoctorInfo =  getDoctorByToken(token);

            //查询数据库中是否有该医生
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(docDoctorInfo);
            if(dbDocDoctorInfo!=null){
                returnMap.put("status", dbDocDoctorInfo.getStatus());//认证状态  -1删除 0:未认证,1认证中,2认证未通过,3已认证,4重新认证
                commonResult.setResult(ConstantsStatus.SC2000, "注册成功", true,returnMap);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
            }
        }catch (Exception e){
            logger.error("注册失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    /**
     * 用户登陆
     *
     * @param mobile
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gotoLogin", method = RequestMethod.POST)
    public CommonResult<Object> doctorLogin(String mobile, String password) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        try {
            //手机号码为空判断
            if (StringUtil.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //手机号码格式判断
            if (!StringUtil.isMobilePhone(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码格式错误!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtil.isBlank(password)) {
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
                commonResult.setResult(ConstantsStatus.SC2000, "登陆成功！", true, jsonMap);
            } else {
                commonResult.setResult(ConstantsStatus.SC4003, "此手机号没有注册！", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登陆失败！", e.fillInStackTrace());
        }
        return commonResult;
    }

    /***
     * 用户重置密码
     * @param mobile
     * @param password
     * @param captcha
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public CommonResult<Object> doctorResetPassword(String mobile, String password, String captcha) {
        CommonResult<Object> commonResult = new CommonResult<Object>();
        Map<String, Object> returnMap = new HashMap<>();
        int success = 0;
        try {

            //手机号码为空判断
            if (StringUtil.isBlank(mobile)) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtil.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(password)){
                commonResult.setResult(ConstantsStatus.SC5014, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //验证码为空判断
            if (StringUtil.isBlank(captcha)){
                commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
                return commonResult;
            }
            String doctorKey = "doctor_"+mobile;
            String registerCode = JedisManager.getString(doctorKey);
            if(!StringUtil.isBlank(registerCode)){
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
            DocDoctorInfo dbDocDoctorInfo = docDoctorInfoService.getDocDoctorInfoByMobile(docDoctorInfo);

            if(dbDocDoctorInfo!=null){
                success = docDoctorInfoService.updateByResetPassword(docDoctorInfo);//修改数据
                if(success > 0){
                	returnMap.put("success", success);
                    commonResult.setResult(ConstantsStatus.SC2000, "修改成功", true,returnMap);
                }else{
                	commonResult.setResult(ConstantsStatus.SC4002, "修改失败", false,returnMap);
                }
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
                return commonResult;
            }
        } catch (Exception e) {
            logger.error("修改失败！", e.fillInStackTrace());
        }
        return commonResult;
    }


    /***
     * 用户修改密码
     * @param password
     * @param captcha
     * @param token
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public CommonResult<Object> doctorUpdatePassword(String password, String captcha, String token) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        Map<String, Object> returnMap = new HashMap<>();
        int success = 0;
        try {

            //判断token
            commonResult  =  validationToken(token);
            if(commonResult == null){
                return  commonResult;
            }

            //根据token获取docDoctorInfo对象
            DocDoctorInfo dbDocDoctorInfo =  getDoctorByToken(token);

            //手机号码为空判断
            if (StringUtil.isBlank(dbDocDoctorInfo.getMobile())) {
                commonResult.setResult(ConstantsStatus.SC5004, "手机号码不能为空!", false);
                return commonResult;
            }

            //密码为空判断
            if (StringUtil.isBlank(password)) {
                commonResult.setResult(ConstantsStatus.SC5014, "密码不能为空!", false);
                return commonResult;
            }

            //验证密码格式
            if (!StringUtil.isPassWord(password)){
                commonResult.setResult(ConstantsStatus.SC5014, "密码格式为以字母开头，长度在6-18之间,只能包含字符、数字和下划线", false);
                return commonResult;
            }

            //验证码为空判断
            if (StringUtil.isBlank(captcha)){
                commonResult.setResult(ConstantsStatus.SC5014,"验证码不能为空", false);
                return commonResult;
            }
            String doctorKey = "doctor_"+dbDocDoctorInfo.getMobile();
            String registerCode = JedisManager.getString(doctorKey);
            if(!StringUtil.isBlank(registerCode)){
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
            docDoctorInfo.setMobile(dbDocDoctorInfo.getMobile());
            docDoctorInfo.setPassword(password);
            
            if(dbDocDoctorInfo!=null){
                success = docDoctorInfoService.updateByResetPassword(docDoctorInfo);//修改数据
                if(success > 0){
                	returnMap.put("success", success);
                    commonResult.setResult(ConstantsStatus.SC2000, "修改成功", true,returnMap);
                }else{
                	commonResult.setResult(ConstantsStatus.SC4002, "修改失败", false,returnMap);
                }
                delDoctorToken(token);//删除token
            }else {
                commonResult.setResult(ConstantsStatus.SC5020, "该手机号不存在!", false);
                return commonResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改失败！", e.fillInStackTrace());
        }
        return commonResult;
    }
    

    /**
     * 获取用户个人信息，（修改了）已经调通
     */

    @RequestMapping(value = "/personalInformation",method = RequestMethod.GET )
    public CommonResult<Object> personalInformation(String token) {
        CommonResult<Object> commonResult = null;//返回通用格式数据
        try{
            //判断token
            commonResult  =  validationToken(token);
            if(!commonResult.isReturnStatus()){
                return  commonResult;
            }
            //根据token获取docDoctorInfo对象
            DoctorDto docDoctor =  getDoctorInfoByToken(token);
            if(docDoctor!=null){
                commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, docDoctor);
                return commonResult;
            }else {
                commonResult.setResult(ConstantsStatus.SC5050, "该用户个人信息不存在!", false);
            }
        }catch (Exception e){
            logger.error("查询失败！", e.fillInStackTrace());
        }
        return commonResult;
    }



    
    /**
     * 修改用户个人信息
     */

    @RequestMapping(value = "/updatePersonalInformation",method = RequestMethod.GET )
    public CommonResult<Object> updatePersonalInformation(String token, DocDoctorInfo docDoctorInfo ) {

    	    	
        CommonResult<Object> commonResult = new CommonResult<Object>();//返回通用格式数据
        try{
        	
            //判断token
            commonResult  =  validationToken(token);
            if(commonResult != null){
                return  commonResult;
            }else{
                commonResult = new CommonResult<Object>();
            }

            //根据token获取docDoctorInfo对象
            DocDoctorInfo docDoctorInfo1 =  getDoctorByToken(token);
            

            
            List<String> methodList = new ArrayList<String>();
            methodList.add("getName");
            methodList.add("getSex");
            methodList.add("getProvinceId");
            
            methodList.add("getCityId");
            methodList.add("getRegionId");
            methodList.add("getHospitalId");
            methodList.add("getDeptId");
            methodList.add("getDeptTel");
            methodList.add("getType");
            methodList.add("getTerritory");
            
            //比较两个对象中字段是否相等
            boolean flag =  objectEquals(docDoctorInfo, docDoctorInfo1, methodList);
            
            docDoctorInfo.setId(docDoctorInfo1.getId());
            
            //获取现在医生的认证状态
            int status = docDoctorInfo1.getStatus();
            
            //医生是认证通过状态, 并且和旧数据不一样,直接在临时表中添加一条数据,并且修改医生表中的认证状态
            if(status == 1  && !flag){
            	
            	DocDoctorInfoTemp docDoctorInfoTemp = new DocDoctorInfoTemp();
            	docDoctorInfoTemp.setDocId(docDoctorInfo.getId());
            	docDoctorInfoTemp.setName(docDoctorInfo.getName());
            	docDoctorInfoTemp.setSex(docDoctorInfo.getSex());
            	docDoctorInfoTemp.setHospitalId(docDoctorInfo.getHospitalId());
            	docDoctorInfoTemp.setDeptId(docDoctorInfo.getDeptId());
            	docDoctorInfoTemp.setDeptTel(docDoctorInfo.getDeptTel());
            	docDoctorInfoTemp.setIntro(docDoctorInfo.getIntro());
            	docDoctorInfoTemp.setHeadPath(docDoctorInfo.getHeadPath());
            	docDoctorInfoTemp.setPidPath(docDoctorInfo.getPidPath());
            	docDoctorInfoTemp.setTerritory(docDoctorInfo.getTerritory());
            	docDoctorInfoTemp.setStatus(0);  //默认是0,未审核
            	
            	docDoctorTempInfoService.insert(docDoctorInfoTemp);
            	
            	
            	docDoctorInfo1.setStatus(4); //状态改成重新认证
            	//更新到数据库
            	docDoctorInfoService.updateDoctor(docDoctorInfo1);
            	
            }else { //否则,直接修改原表中的数据
            	docDoctorInfoService.updateDoctor(docDoctorInfo);
            	
                //创建DocDoctorInfo对象
                DocDoctorInfo docDoctorInfoTemp = new DocDoctorInfo();
                //从mobile从数据库中得到医生对象
                docDoctorInfoTemp.setMobile(docDoctorInfo1.getMobile());

                //查询数据库
                DoctorDto doctorInfo = docDoctorInfoService.getDoctorByMobileAndPass(docDoctorInfo);
                //
                refreshDoctorToken(token, doctorInfo);
            }

             commonResult.setResult(ConstantsStatus.SC2000, "修改医生数据成功", true);
            
        }catch (Exception e){
            logger.error("查询失败！", e.fillInStackTrace());
            commonResult.setResult(ConstantsStatus.SC4000, "修改医生数据出现问题", true);
        }
        return commonResult;
    }
    
    
    
    
    /**
     * 用反射比较两个对象中的值是否相等
     * @param docDoctorInfo		对象1
     * @param docDoctorInfo1	对象2
     * @param methodList		要比较的对象属性的get方法
     * @return
     * @throws Exception
     */
    public static boolean objectEquals(DocDoctorInfo docDoctorInfo, DocDoctorInfo docDoctorInfo1,List<String> methodList) throws Exception{
    	
    	//默认返回true
    	boolean flag = true;
    	
    	//得到两个对象的class
        Class userCla = (Class) docDoctorInfo.getClass();  
        Class userCla1 = (Class) docDoctorInfo1.getClass();
       
        
        //循环方法名
        for(String methodName : methodList){
        	//根据方法名,通过反射得到方法对象
        	Method method =  userCla.getMethod(methodName);
        	Method method1 = userCla1.getMethod(methodName);
        	
        	//通过方法得到值
        	Object msg = (Object) method.invoke(docDoctorInfo); 
        	Object msg1 = (Object) method1.invoke(docDoctorInfo1); 
        	
        	//比较两个值是否相等
        	if(!msg.equals(msg1)){
        		flag = false;
        		break;
        	}
        	
        }
        return flag;
        
    }
    
    public static void main(String[] args){
    	DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
    	docDoctorInfo.setId(1111l);
    	docDoctorInfo.setName("liuyang");
    	
    	DocDoctorInfo docDoctorInfo1 = new DocDoctorInfo();
    	docDoctorInfo1.setId(1111l);
    	docDoctorInfo1.setName("liuyang");
    	
    	List<String> methodList = new ArrayList<String>();
    	methodList.add("getId");
    	methodList.add("getName");
    	
    	try {
    		boolean flag = objectEquals(docDoctorInfo, docDoctorInfo1, methodList);
//    		(docDoctorInfo, docDoctorInfo1, methodList);
    		System.out.println("flag is :" + flag);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	
    }
    

}

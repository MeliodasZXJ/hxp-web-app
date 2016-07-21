package com.hxp.base;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hxp.doctor.dto.DoctorDto;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;
import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * Created by anpushang on 2016/7/10.
 */
public class BaseController {

    protected final Logger logger = Logger.getLogger(BaseController.class);

    protected static final String ENCODE="UTF-8";
    private static final int seconds=0; //redis过期时间  上线需要改成 1800
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String sortBy;   //排序字段名
    protected String sortOrder;   //排序升序？倒序 asc,desc

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }


    public int getPageSize() {
        int pageSize;
        if("".equals(request.getParameter("pageSize"))||request.getParameter("pageSize")==null){
            pageSize = 10;//默认每页显示10
        }else{
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        return pageSize;
    }

    public int getPageNum() {
        int pageNum;
        if("".equals(request.getParameter("pageNum"))||request.getParameter("pageNum")==null){
            pageNum = 1;
        }else{
            pageNum=Integer.parseInt(request.getParameter("pageNum"));
        }
        return pageNum;

    }

    public String getSortBy(){
        this.sortBy = request.getParameter("sort");
        return this.sortBy;
    }

    public String getSortOrder(){
        this.sortOrder = request.getParameter("order");
        return this.sortOrder;
    }

    /***
     * 得到患者用户的token，用户信息已经缓存到了redis
     * @param patientCustomer
     * @return
     */
    public String getPatientToken(PatientCustomer patientCustomer){
        String token = "P_"+getUUID();
        JedisManager.setObject(token,seconds,patientCustomer);
        return token;
    }

    /**
     * 根据token刷新PatientCustomer
     * @param token
     * @param newPatientCustomer
     * @return
     */
    public void refreshPatientToken(String token,PatientCustomer newPatientCustomer){
        JedisManager.setObject(token,seconds,newPatientCustomer);
    }


    
    /**
     * 根据token刷新DoctorDto
     * @param token
     * @param DoctorDto
     * @return
     */
    public void refreshDoctorToken(String token,DoctorDto docDoctorDto){
        JedisManager.setObject(token,seconds,docDoctorDto);
    }
    

    /**
     * 删除患者用户的token
     * @param token
     * @return
     */
    public void delPatientToken(String token){
        JedisManager.delString(token);
    }

    /***
     * 根据token获取患者信息
     * @return
     */
    public PatientCustomer getPatientByToken(String token){
        PatientCustomer patientCustomer = (PatientCustomer)JedisManager.getObject(token);
        return patientCustomer;
    }

    /***
     * 得到医生用户的token，用户信息已经缓存到了redis
     * @param object
     * @return
     */
    public String getDoctorToken(Object object){
        String token = "D_"+getUUID();
        JedisManager.setObject(token,seconds,object);
        return token;
    }

    /**
     * 删除医生用户的token
     * @param token
     * @return
     */
    public void delDoctorToken(String token){
        JedisManager.delString(token);
    }


    /**
     * 验证医生端(患者端)的token
     * @param token
     * @return
     */
    public  CommonResult<Object> validationToken(String token){
        CommonResult<Object> commonResult = new CommonResult<Object>();
        if(StringUtil.isBlank(token)){
            commonResult.setResult(ConstantsStatus.SC5009, "token不能为空!", false);
            return commonResult;
        }
        //根据token获取对象,对象为空,token不存在，或者已经过期
        Object object =  (Object)getObjectByToken(token);
        if(object == null){
            commonResult.setResult(ConstantsStatus.SC5010, "token不存在或者已经过期!", false);
            return commonResult;
        }

        return commonResult;

    }

    
    /***
     * 根据token获取信息
     * @return
     */
    public Object getObjectByToken(String token){
        Object object = JedisManager.getObject(token);
        return object;
    }
    
    /***
     * 根据token获取医生信息
     * @return
     */
    public DocDoctorInfo getDoctorByToken(String token){
        DocDoctorInfo docDoctorInfo = (DocDoctorInfo)JedisManager.getObject(token);
        return docDoctorInfo;
    }

    /***
     * 根据token获取医生信息
     * @return
     */
    public DoctorDto getDoctorInfoByToken(String token){
        DoctorDto docDoctorInfo = (DoctorDto)JedisManager.getObject(token);
        return docDoctorInfo;
    }



    public String getUUID(){
       return UUID.randomUUID().toString().replace("-","");
    }
    
    protected String resultJson(String... kvs) {
		JSONObject json = new JSONObject();
		if (kvs == null || kvs.length == 0) {
			return json.toString();
		}
		for (int i = 0, len = kvs.length; (i + 1) < len; i += 2) {
			json.put(kvs[i], kvs[i + 1]);
		}
		return json.toString();
	}

	protected String successJson() {
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json.toString();
	}

	protected String successJson(String... kvs) {
		JSONObject json = new JSONObject();
		json.put("success", true);
		if (kvs == null || kvs.length == 0) {
			return json.toString();
		}
		for (int i = 0, len = kvs.length; (i + 1) < len; i += 2) {
			json.put(kvs[i], kvs[i + 1]);
		}
		return json.toString();
	}

	protected static String listJson(List list) {
		JSONObject json = new JSONObject();
		return json.toJSONString(list, true);
	}

	protected static String mapJson(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject.toString();
	}

	protected String errorJson(String msg) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("msg", msg);
		return json.toString();
	}

	protected String errorJson(Object msg) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("msg", msg);
		return json.toString();
	}

}

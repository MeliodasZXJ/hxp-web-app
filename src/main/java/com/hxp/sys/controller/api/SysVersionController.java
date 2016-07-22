package com.hxp.sys.controller.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysVersion;
import com.hxp.sys.service.ISysVersionService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

@RestController
@RequestMapping("/app/v1_6/service/sys")
public class SysVersionController  extends BaseController{

	@Autowired
	private ISysVersionService sysVersionService;

	@RequestMapping(value = "getSysVersion")
	public CommonResult<Object> getSysVersion(SysVersion sysVersion) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		if(StringUtils.isBlank(sysVersion.getVersion()) || sysVersion.getClientType()==null || sysVersion.getPfType()==null){
			commonResult.setResult(ConstantsStatus.SC5001, "参数传入异常，请检查参数", false);
			return  commonResult;
		}
		
		//根据clientType 与 pfType 找到最新版本
		SysVersion selectVersion = sysVersionService.selectVersion(sysVersion);
		if(selectVersion==null){
			commonResult.setResult(ConstantsStatus.SC6001, "系统找不到对应的相关版本信息", false);
			return  commonResult;
		}
		
		
		boolean forceUpdate =  getForceUpdate(selectVersion.getMinVersion(), sysVersion.getVersion());
		
		if(forceUpdate){
			selectVersion.setForceUpdate(forceUpdate);
			commonResult.setResult(ConstantsStatus.SC6002, "有新版本,请升级", true,selectVersion);
		}else{
			commonResult.setResult(ConstantsStatus.SC2000,"成功",true);
		}
		return  commonResult;
		
	}
	
	/**
	 * 比较两个版本
	 * @param versionNew
	 * @param versionOld
	 * @return
	 */
	public static boolean getForceUpdate(String versionNew,String versionOld){
		boolean flag = false;
		
		
		versionNew = versionNew.replaceAll("\\.", "");
		versionOld = versionOld.replaceAll("\\.", "");
		
		if(Integer.valueOf(versionNew) >= Integer.valueOf(versionOld)){
			flag = true;
		}
		
		return flag;
		
	/*	//用户传入的版本号与更新的最低版本号一样,就要强制更新
		if(versionNew.equals(versionOld)){
			flag = true;
			return flag; 
		}
		
		//否则将字符串截取成数组,进行比较
		String[] verNewL = versionNew.split("\\.");
		String[] verOldL = versionOld.split("\\.");
		for (int i = 0; i < verOldL.length; i++) {
			try {
				if(Integer.valueOf(verOldL[i]) < Integer.valueOf(verNewL[i])){
					flag = true;
					break;
				}
			} catch (Exception e) {
				flag = false;
			}
		}
		
		return flag;*/
	}
}
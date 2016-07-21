package com.hxp.sys.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxp.base.BaseController;
import com.hxp.sys.po.SysDicInfo;
import com.hxp.sys.service.ISysDicInfoService;
import com.hxp.sys.service.ISysDicTypeInfoService;

@Controller
@RequestMapping("/dic")
public class SysDicInfoController extends BaseController {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(SysDicInfoController.class);
	@Autowired
	public ISysDicInfoService sysDicInfoService;
	@Autowired
	public ISysDicTypeInfoService sysDicTypeInfoService;

	/**
	 * 跳转到树首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "dic_Info.jsp")
	public String getDicInfo() {
		return "dicInfo/dic_Info";
	}

	/**
	 * 获取树数据
	 * 
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "getTreeData.do")
	@ResponseBody
	public String getTreeData(Integer pid) throws UnsupportedEncodingException {
		String treeData = sysDicTypeInfoService.selectSysDicType();
		return treeData;
	}

	@RequestMapping(value = "getDicInfoList.do")
	@ResponseBody
	public String getDicInfoList(String typeCode) {
		String json = "";
		if (!"".equals(typeCode)) {
			List<SysDicInfo> list = sysDicInfoService.selectSysDicInfoByTypeCode(typeCode);
			return  listJson(list);
		}
		return json;
	}

	/**
	 * 新增字典值
	 * 
	 * @param dicInfo
	 * @return
	 */
	@RequestMapping(value = "saveDicInfo.do")
	@ResponseBody
	public String saveDicInfo(SysDicInfo record) {
		try {
			sysDicInfoService.insert(record);
		} catch (Exception e) {
			return errorJson("新增失败,请稍后再试或联系管理员.");
		}
		return successJson();
	}

	/**
	 * 修改字典值
	 * 
	 * @param dicInfo
	 * @return
	 */
	@RequestMapping(value = "editDicInfo.do")
	@ResponseBody
	public String editDicInfo(SysDicInfo record) {
		try {
			sysDicInfoService.update(record);
		} catch (Exception e) {
			return errorJson("编辑失败,请稍后再试或联系管理员.");
		}

		return successJson();

	}

	/**
	 * 删除字典值-逻辑删
	 * 
	 * @param dicInfo
	 * @return
	 */
	@RequestMapping(value = "delDicInfo.do")
	@ResponseBody
	public String delDicInfo(Integer id) {
		try {
			//更新status 為-1
		} catch (Exception e) {
			return errorJson("服务器开小差了,请稍后再试!");
		}
		return successJson();
	}
}

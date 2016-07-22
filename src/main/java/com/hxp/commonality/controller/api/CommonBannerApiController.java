package com.hxp.commonality.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxp.base.BaseController;
import com.hxp.commonality.po.CommonBanner;
import com.hxp.commonality.service.ICommonBannerService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;

@RestController
@RequestMapping("/app/v1_6/CommonBanner/")
public class CommonBannerApiController extends BaseController {

	@Autowired
	private ICommonBannerService commonBannerService;

	/**
	 * 获取轮播图
	 * 
	 * @return
	 */
	@RequestMapping("/getBannerListByBrandType")
	public CommonResult<Object> getScreenLabel(Integer brandType) {
		CommonResult<Object> commonResult = new CommonResult<Object>();
		if (brandType == null) {
			commonResult.setResult(ConstantsStatus.SC5001, "轮播图类型不能为空", false);
			return commonResult;
		}
		List<CommonBanner> list = commonBannerService.getBannerListByBrandType(brandType);
		commonResult.setResult(ConstantsStatus.SC2000, "查询成功", true, list);
		return commonResult;
	}

}

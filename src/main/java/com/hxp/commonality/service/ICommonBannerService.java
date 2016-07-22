package com.hxp.commonality.service;

import java.util.List;

import com.hxp.commonality.po.CommonBanner;

public interface ICommonBannerService {
	List<CommonBanner> getBannerListByBrandType(Integer brandType);
}
package com.hxp.commonality.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.commonality.dao.CommonBannerDao;
import com.hxp.commonality.po.CommonBanner;
import com.hxp.commonality.service.ICommonBannerService;

@Service
public class CommonBannerServiceImpl implements ICommonBannerService {

	@Autowired
	private CommonBannerDao commonBannerDao;

	@Override
	public List<CommonBanner> getBannerListByBrandType(Integer brandType) {
		return commonBannerDao.find("getBannerListByBrandType",brandType);
	}

}

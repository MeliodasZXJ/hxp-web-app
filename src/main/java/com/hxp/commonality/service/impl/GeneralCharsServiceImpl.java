package com.hxp.commonality.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.commonality.po.CharsBean;
import com.hxp.commonality.po.CommonFeedBack;
import com.hxp.commonality.service.ICommonFeedBackService;
import com.hxp.commonality.service.IGeneralCharsService;

@Service
public class GeneralCharsServiceImpl implements IGeneralCharsService {

	@Autowired
	private ICommonFeedBackService commonFeedBackService;

	@Override
	public Map<String, Object> getFeedBackPieChartData() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 回馈状态 0,未处理 1,已处理
		CommonFeedBack cfb = new CommonFeedBack();
		cfb.setStatus(0);
		List<CommonFeedBack> noAuthList = commonFeedBackService.findFeedBackList(cfb);
		cfb.setStatus(1);
		List<CommonFeedBack> authList = commonFeedBackService.findFeedBackList(cfb);
		List<CharsBean> list = new ArrayList<>();
		list.add(new CharsBean("未处理", noAuthList.size()));
		list.add(new CharsBean("已处理", authList.size()));
		map.put("series", list);
		return map;
	}

}

package com.hxp.commonality.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hxp.commonality.dao.CommonInfomationDao;
import com.hxp.commonality.dto.ArticleInfoDto;
import com.hxp.commonality.po.CommonInfomation;
import com.hxp.commonality.service.ICommonInfomationService;
import com.hxp.sys.po.CommonCollection;

@Service
public class CommonInfomationServiceImpl implements ICommonInfomationService {

	@Autowired
	private CommonInfomationDao commonInfomationDao;

	@Override
	public CommonInfomation getCommonInfoById(Long id) {
		// TODO Auto-generated method stub

		return commonInfomationDao.get("selectByPrimaryKey", id);
	}

	@Override
	public List<ArticleInfoDto> getArticleInfoList(ArticleInfoDto articleInfoDto) {
		// TODO Auto-generated method stub

		List<ArticleInfoDto> list = commonInfomationDao.find("findArticleList", articleInfoDto);
		
		if (!CollectionUtils.isEmpty(list)) {
			CommonCollection cc = null;
			for (ArticleInfoDto article : list) {
				cc = new CommonCollection();
				cc.setObjId(article.getId());
				cc.setCollectRule(articleInfoDto.getCollectRule());
				cc.setUserId(articleInfoDto.getUserId());
				CommonCollection collectlist = commonInfomationDao.get("getArticleIsCollect", cc);
				if (collectlist != null) {
					article.setCollect(true);
				}
			}
		}

		return list;
	}

}

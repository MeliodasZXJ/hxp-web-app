package com.hxp.commonality.service;

import java.util.List;

import com.hxp.commonality.dto.ArticleInfoDto;
import com.hxp.commonality.po.CommonInfomation;

public interface ICommonInfomationService {

	/**
	 * 获取文章详情
	 * 
	 * @param id
	 * @return
	 */
	CommonInfomation getCommonInfoById(Long id);

	/**
	 * 获取文章列表
	 * 
	 * @param articleInfoDto
	 * @return
	 */
	List<ArticleInfoDto> getArticleInfoList(ArticleInfoDto articleInfoDto);
	
	
	
	
}

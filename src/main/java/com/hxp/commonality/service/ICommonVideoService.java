package com.hxp.commonality.service;

import java.util.List;

import com.hxp.commonality.dto.CommonVideoDto;
import com.hxp.commonality.vo.CommonVideoVo;

public interface ICommonVideoService {
	/**
	 * 查询视频列表
	 * @param commonVideoDto
	 * @return
	 */
	List<CommonVideoDto> findCommonVideoList(CommonVideoVo commonVideoVo);
	
	/**
	 * 根据视频查询视频信息
	 * @param commonVideoVo
	 * @return
	 */
	CommonVideoDto    getCommonVideoByPrimaryKey(CommonVideoVo commonVideoVo);
}

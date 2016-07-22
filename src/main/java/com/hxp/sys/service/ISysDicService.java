package com.hxp.sys.service;

import java.util.List;

import com.hxp.sys.po.SysDic;

public interface ISysDicService {

	List<SysDic> findAllDictType();

	List<SysDic> findDictByCode(String code);

}

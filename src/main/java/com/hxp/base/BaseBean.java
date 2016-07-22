package com.hxp.base;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseBean {
	@JSONField(serialize = false)
	public Integer limit =10 ;
	@JSONField(serialize = false)
	public Integer offset =1 ;
	@JSONField(serialize = false)
	public String order ;
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}

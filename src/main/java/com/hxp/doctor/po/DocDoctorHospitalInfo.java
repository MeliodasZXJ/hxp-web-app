package com.hxp.doctor.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.hxp.base.BaseBean;
import com.hxp.sys.po.SysRegion;

/**
 * Created by liuyang on 2016/7/12.
 */
public class DocDoctorHospitalInfo extends BaseBean implements Serializable {

	private static final long serialVersionUID = -8964962870623397466L;

	private Long id;

	private String name;

	private String uuid;

	private Integer hospitallevel;

	private Integer provinceId;

	private Integer cityId;

	private String regionId;

	private String address;

	private String mark;

	private Integer status;

	private Date createTime;

	@JSONField(serialize = false)
	private SysRegion province;
	@JSONField(serialize = false)
	private SysRegion city;
	@JSONField(serialize = false)
	private SysRegion region;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getHospitallevel() {
		return hospitallevel;
	}

	public void setHospitallevel(Integer hospitallevel) {
		this.hospitallevel = hospitallevel;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark == null ? null : mark.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SysRegion getProvince() {
		return province;
	}

	public void setProvince(SysRegion province) {
		this.province = province;
	}

	public SysRegion getCity() {
		return city;
	}

	public void setCity(SysRegion city) {
		this.city = city;
	}

	public SysRegion getRegion() {
		return region;
	}

	public void setRegion(SysRegion region) {
		this.region = region;
	}

}
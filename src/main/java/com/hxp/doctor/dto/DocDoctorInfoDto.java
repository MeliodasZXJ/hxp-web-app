package com.hxp.doctor.dto;

import java.io.Serializable;

import com.hxp.base.BaseBean;

public class DocDoctorInfoDto extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String mobile;
	private String name;
	private int sex;
	private String intro;
	private Long deptId;
	private String deptName;
	private Long hospitalId;
	private String hospitalName;
	private String headPath;
	private String pidPath;
	private int type;
	private Integer doctorType; // 名医风采
	private String careers;
	private Integer status;
	private String territory;
	private String createTime;
	private Integer autyType;
	private Integer cityId;
	private Integer provinceId;
	private Integer regionId;
	private Long collectId;
	private Long patientId;   //患者id,患者登录后,通过这个id查询医生信息,判断是否登录

	private Integer clinicalReception; //粉丝量

	private Integer consult; //咨询量
	private String mark; //备注

	public DocDoctorInfoDto() {
	}

	public Integer getClinicalReception() {
		return clinicalReception;
	}

	public void setClinicalReception(Integer clinicalReception) {
		this.clinicalReception = clinicalReception;
	}

	public Integer getConsult() {
		return consult;
	}

	public void setConsult(Integer consult) {
		this.consult = consult;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getPidPath() {
		return pidPath;
	}

	public void setPidPath(String pidPath) {
		this.pidPath = pidPath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCareers() {
		return careers;
	}

	public void setCareers(String careers) {
		this.careers = careers;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(Integer doctorType) {
		this.doctorType = doctorType;
	}

	public Integer getAutyType() {
		return autyType;
	}

	public void setAutyType(Integer autyType) {
		this.autyType = autyType;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Long getCollectId() {
		return collectId;
	}

	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}

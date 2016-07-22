package com.hxp.doctor.dto;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/16.
 */
public class DoctorDto implements Serializable {
    private static final long serialVersionUID = -6368874078707012480L;

    private Long doctorId;

    private String doctorUUID;

    private String mobile;

    private String pidPath;

    private String doctorName;

    private String headPath;

    private String deptTel;

    private int sex;

    private String territory;

    private String intro;

    private String hospitalName;

    private Integer hospitalId;

    private String deptName;

    private String professional;

    private Integer profId;

    private Integer deptId;

    private String cityName;

    private String regionName;


    private String provinceName;

    private Integer status;


    private Integer clinicalReception; //粉丝量

    private Integer consult; //咨询量

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

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getProfId() {
        return profId;
    }

    public void setProfId(Integer profId) {
        this.profId = profId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    private boolean isAttention; //是否被关注

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean isAttention) {
        this.isAttention = isAttention;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorUUID() {
        return doctorUUID;
    }

    public void setDoctorUUID(String doctorUUID) {
        this.doctorUUID = doctorUUID;
    }

    public String getPidPath() {
        return pidPath;
    }

    public void setPidPath(String pidPath) {
        this.pidPath = pidPath;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getDeptTel() {
        return deptTel;
    }

    public void setDeptTel(String deptTel) {
        this.deptTel = deptTel;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


}

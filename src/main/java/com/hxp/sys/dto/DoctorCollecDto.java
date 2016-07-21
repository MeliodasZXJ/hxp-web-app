package com.hxp.sys.dto;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/13.
 */
public class DoctorCollecDto implements Serializable {
    private static final long serialVersionUID = 3250286384533901403L;


    //头像
    private Integer docId;
    private String headPath;

    private String docName;
    private String territory;//医生擅长

    private String hospitalName;
    private String professional;//医生职称
    private String deptName;

    private Integer clinicalReception; //粉丝量

    private Integer consult; //咨询量

    private String intro;//简介

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}

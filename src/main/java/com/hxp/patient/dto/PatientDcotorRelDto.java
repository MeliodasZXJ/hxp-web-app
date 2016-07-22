package com.hxp.patient.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by slyi on 2016/7/21.
 */
public class PatientDcotorRelDto implements Serializable {
    private String sessionId;

    private Long patientId;

    private Long docId;

    private String docName;

    private String docHeadPath;

    private String hospitalName;

    private String access;

    private Date createTime;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocHeadPath() {
        return docHeadPath;
    }

    public void setDocHeadPath(String docHeadPath) {
        this.docHeadPath = docHeadPath;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.hxp.patient.vo;

import java.io.Serializable;

/**
 * Created by slyi on 2016/7/21.
 */
public class PatientDoctorRefVo implements Serializable {

    private String sessionId;

    private Long patientId;

    private String doctorIds;

    private String scores;


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

    public String getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(String doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}

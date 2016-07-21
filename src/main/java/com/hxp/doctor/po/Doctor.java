package com.hxp.doctor.po;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/9.
 */
public class Doctor implements Serializable {
    private static final long serialVersionUID = 4384729183062964778L;
    private Integer id;

    private String docName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}

package com.hxp.sys.po;

import java.io.Serializable;
import java.util.Date;

public class SysVersion implements Serializable{

	private static final long serialVersionUID = -5687885293412360661L;

	private Integer id;

    private Integer clientType;

    private Integer pfType;

    private String version;

    private String url;

    private Integer status;

    private Date createTime;

    private String forceupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getPfType() {
        return pfType;
    }

    public void setPfType(Integer pfType) {
        this.pfType = pfType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public String getForceupdate() {
        return forceupdate;
    }

    public void setForceupdate(String forceupdate) {
        this.forceupdate = forceupdate == null ? null : forceupdate.trim();
    }
}
package com.hxp.sys.po;

import java.io.Serializable;
import java.util.Date;

public class CommonCollection implements Serializable{

    private Long id;

    private Long userId;

    private Integer collectRule;

    private Integer collectType;

    private Long objId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCollectRule() {
        return collectRule;
    }

    public void setCollectRule(Integer collectRule) {
        this.collectRule = collectRule;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
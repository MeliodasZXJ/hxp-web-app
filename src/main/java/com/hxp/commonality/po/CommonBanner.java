package com.hxp.commonality.po;

import java.io.Serializable;

import com.hxp.base.BaseBean;

public class CommonBanner extends BaseBean implements Serializable{
    private Long id;

    private Integer state;

    private Integer brandType;

    private String aduuid;

    private Integer sort;

    private String url;

    private String imageUrl;

    private String imageNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBrandType() {
        return brandType;
    }

    public void setBrandType(Integer brandType) {
        this.brandType = brandType;
    }

    public String getAduuid() {
        return aduuid;
    }

    public void setAduuid(String aduuid) {
        this.aduuid = aduuid == null ? null : aduuid.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getImageNote() {
        return imageNote;
    }

    public void setImageNote(String imageNote) {
        this.imageNote = imageNote == null ? null : imageNote.trim();
    }
}
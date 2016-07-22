package com.hxp.util;

import java.io.Serializable;

/**
 * Created by anpushang on 2016/7/10.
 * 返回通用格式数据
 */
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 3713874654852996944L;
    //返回状态码
    private String code;
    //数据
    private T value;
    //返回状态码描述
    private String message;
    //只有value为list时才有值
    private int total;
    //返回状态 true正确 false出错
    private boolean returnStatus;

    public CommonResult() {
        this(ConstantsStatus.SC4000,true,"服务端错误");
    }

    public void setResult(Integer code, String message, boolean returnStatus, T value, int total) {
        this.code = String.valueOf(code);
        this.value = value;
        this.message = message;
        this.total = total;
        this.returnStatus = returnStatus;
    }

    public void setResult(Integer code, String message, boolean returnStatus, T value) {
        this.code = String.valueOf(code);
        this.value = value;
        this.message = message;
        this.returnStatus = returnStatus;
    }

    public void setResult(String message, boolean returnStatus) {
        this.message = message;
        this.returnStatus = returnStatus;
    }

    public void setResult(Integer code, String message, boolean returnStatus) {
        this.code = String.valueOf(code);
        this.message = message;
        this.returnStatus = returnStatus;
    }

    public CommonResult(Integer code,boolean returnStatus,String message) {
        this.code = String.valueOf(code);
        this.returnStatus = returnStatus;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }
}

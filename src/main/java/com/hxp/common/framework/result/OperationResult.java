/**
 * Copyright (c) 2012
 */
package com.hxp.common.framework.result;



/**
 * 用于Object到JSON序列化的对象结构体定义
 */
public class OperationResult {

    //全局成功标识代码
    public final static String SUCCESS = "100000";

    //全局未知错误标识代码
    public final static String FAILURE = "999999";

    /** 标识操作结果类型 */
    public enum OPERATION_RESULT_TYPE {
        //"操作处理成功。前端一般是绿色的短暂气泡提示")
        success,

        //"偶尔用于标识业务处理基本完成，但是其中存在一些需要注意放在message或data中的提示信息。前端一般是黄色的气泡提示")
        warning,

        //"操作处理失败。前端一般是红色的长时间或需要用户主动关闭的气泡提示")
        failure,

        //"本次提交中止，反馈用户进行确认。前端一般会弹出一个供用户'确认'操作的对话框，然后用户主动确认之后会自动再次发起请求并跳过确认检查进行后续业务处理")
        confirm
    }

    /** 返回success或failure操作标识 */
    private String type;

    /** 成功：100000，其他标识错误 */
    private String code;

    /** 国际化处理的返回JSON消息正文，一般用于提供failure错误消息 */
    private String message;

    /** 补充的业务数据 */
    private Object data;

    /** 标识redirect路径 */
    private String redirect;

    public static OperationResult buildSuccessResult(String message, Object data) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.success, message, data);
    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildSuccessResult() {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.success, null);
    	    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildSuccessResult(String message) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.success, message);
    	    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildSuccessResult(Object data) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.success, "success", data);
    	    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildWarningResult(String message, Object data) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.warning, message, data);
    	    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildFailureResult(String message) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.failure, message);
    	    	or.setCode(FAILURE);
        return or;
    }

    public static OperationResult buildFailureResult(String message, Object data) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.failure, message, data);
    	    	or.setCode(FAILURE);
        return or;
    }

    public static OperationResult buildConfirmResult(String message, Object data) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.confirm, message, data);
    	    	or.setCode(SUCCESS);
        return or;
    }

    public static OperationResult buildConfirmResult(String message) {
    	OperationResult or=new OperationResult(OPERATION_RESULT_TYPE.confirm, message, null);
    	or.setCode(SUCCESS);
        return or;
    }

    public OperationResult(OPERATION_RESULT_TYPE type, String message) {
        this.type = type.name();
        this.message = message;
    }

    public OperationResult(OPERATION_RESULT_TYPE type, String message, Object data) {
        this.type = type.name();
        this.message = message;
        this.data = data;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
    
    
}

/**
 *封装提示框
 */
$.extend({
	/**
	 * 成功提示
	 */
	success : function(msg) {
		layer.msg(msg, {
			icon : 1,
			time : 1000
		});
	},
	success : function(msg, callBack) {
		layer.msg(msg, {
			icon : 1,
			time : 1000
		}, callBack);
	},
	/**
	 * 信息提示
	 */ 
	info : function(msg) {
		layer.msg(msg);
	},
	/**
	 * 错误提示
	 */
	error : function(msg) {
		layer.msg(msg, {
			icon : 2,
			time : 1000
		});
	},
	error : function(msg, callBack) {
		layer.msg(msg, {
			icon : 2,
			time : 1000
		}, callBack);
	},
	/**
	 * 警告提示
	 */
	warn : function(msg) {
		layer.msg(msg, {
			icon : 0,
			time : 1000
		});
	},
	

	
});



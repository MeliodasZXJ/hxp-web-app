/**
 * 表单验证的全局设置
 */
$(function() {
	
	// 提醒的样式
	$.validator.setDefaults({
		ignore: "" // 开启hidden验证,1.9版本后默认关闭 
	});
	// 验证时的规则
	// $.metadata.setType("attr", "validate");

	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	// 例子：
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	Date.prototype.Format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
})
function serilNumFormatter(value, row, index) {
	if(row.pageNum!=undefined){
		return (row.pageNum-1)*row.pageSize+index+ 1;
	}
		
	return index+ 1;
}
function dateTimeFormatter(value, row, index) {
	return new Date(value).toLocaleString().replace(/:\d{1,2}$/, ' ');
}
/*******************************************************************************
 * 清空表单中的数据
 * 
 * @param formId
 */
function resetFormInfo(formId) {
	$("#" + formId).find(':input').not(':button, :submit, :reset').val('');
}

/*******************************************************************************
 * 
 * @param target
 */

function toLength(target, max) {
	var text = target.value.slice(0, max), len = 0, maxLength = max, currLength = max;
	target.value = text;
	if ("" !== text) {
		len = text.length;
		currLength = maxLength - len;
	}

	if (currLength < 0) {
		currLength = 0;
	}
	$(target).wrap();
	$(target).next("span").remove();
	$(
			'<span >您还可以输入 <span style="color:green">' + currLength
					+ '</span> 个字</span>').insertAfter(target);
}

function checkItems(typeCode, checkedValue) {
	var flag = false;
	$("input[type='checkbox'][name='" + typeCode + "']").each(function() {
		if ($(this).val() == checkedValue) {
			// $.layerMsg("该类型已存在",{icon:3,time:1500});
			$.layerMsg("该类型已存在", {
				icon : 0,
				time : 1500
			});
			return flag = true;
		}
	});
	return flag;
}
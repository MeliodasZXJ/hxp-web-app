/**
 * 材料列表
 */
var pageIndex ;
$(function() {
	loadPatientFbList();
	$("#feedBackForm").validate(
			{
				rules : {
					'replyContent' : {
						required:true
					}
				},
				submitHandler : function(form) {
					var options = {
						url : "updateFeedBack",
						type : "post",
						dataType : "json",
						success : function(data) {
							if (data.success) {
								$.success('操作成功',function() {
										layer.close(pageIndex);
										search();
								});
							} else {
								$.error(data.msg);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$.warn('服务忙,请稍后再试.');
						}
					};
					$(form).ajaxSubmit(options);
				},
				invalidHandler : function(form, validator) {
					return false;
				}

			});

});


function processData(value) {
	return eval(value);
}
function loadPatientFbList() {

	$('#patientFbListTable').bootstrapTable({
		method : 'post',
		url : "getPatientFeedBackListData",
		height : $(window).height() - 100,
		striped : true,
		minimumCountColumns : 2,
		smartDisplay : true,
		toolbar : "#patientFbListTableToolbar",
		pagination : true,
		sidePagination : "server",
		pageNumber : 1,
		pageSize : 5,
		pageList : [ 5, 10, 20 ],
		queryParamsType : "limit",
		contentType : "application/x-www-form-urlencoded",
		columns : [ {
			field : 'id',
			title : 'ID',
			visible : false
		}, {
			title : '序号',
			align : 'center',
			width : '3%',
			formatter : serilNumFormatter
		}, {
			field : 'mobile',
			title : '联系方式',
			align : 'center',
			width : '8%'
		}, {
			field : 'content',
			title : '反馈内容',
			align : 'center',
			width : '20%',
			visible : false
		}, {
			field : 'createTime',
			title : '反馈时间',
			align : 'center',
			width : '10%'
		}, {
			field : 'replyContent',
			title : '回复内容',
			align : 'center',
			width : '20%',
			visible : false
		}, {
			field : 'replyName',
			title : '回复人',
			align : 'center',
			width : '5%'
		}, {
			field : 'replyTime',
			title : '回复时间',
			align : 'center',
			width : '10%'
		}, {
			field : 'status',
			title : '状态',
			align : 'center',
			visible : false
		}, {
			field : 'operstatus',
			title : '处理状态',
			align : 'center',
			width : '10%'
		}, {
			title : '操作',
			align : 'center',
			width : '15%',
			valign : 'middle',
			formatter : operateFormatter
		} ]
	});

}

function search() {

	$('#patientFbListTable').bootstrapTable('refresh', {
		query : {
			status : $("select[name='status']").val()
		}
	});

}
function operateFormatter(value, row, index) {
	if (row.status == 0) { // 未处理
		return '<a class="edit"  href="javascript:void(0)" onClick="reply('+index+')" title="立即处理">立即处理</a>';
	}
	// 已处理
	return '<a class="edit"  href="javascript:void(0)" onClick="reply('
			+ index + ')" title="查看详情">查看详情</a>';
}

function reply(index){
	var data = $('#patientFbListTable').bootstrapTable('getData');
	$("#handleMobile").html(data[index].mobile);
	$("#handleContent").html(data[index].content);
	$("#handleId").val(data[index].id);
	if(data[index].status==0){
		$("#replyContentWrite").css("display","");
		$("#replyContentRead").css("display","none");
		$("#btnStatus").css("display","");
	}else{
		$("#replyContentRead").html(data[index].replyContent);
		$("#replyContentWrite").css("display","none");
		$("#replyContentRead").css("display","");
		$("#btnStatus").css("display","none");
	}
	pageIndex = layer.open({
		type : 1,
		title : '反馈处理',
		shadeClose : true,
		shade : 0.5,
		area : [ '800px', '600px' ],
		content : $('#handleFb')
	});
}

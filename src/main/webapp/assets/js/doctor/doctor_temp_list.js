/**
 * 材料列表
 */
$(function() {
	loadDocTempList();
	$("#docDoctorInfoTemp").validate(
		{
			rules : {
				'replyContent' : {
					required:true
				}
			},
			submitHandler : function(form) {
				var options = {
					url : "authDoctor",
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
})

function processData(value) {
	return eval(value);
}
function loadDocTempList() {

	$('#doctorTempListTable').bootstrapTable({
		method : 'post',
		url : "getDocTempListData",
		height : $(window).height() - 100,
		striped : true,
		minimumCountColumns : 2,
		smartDisplay : true,
		toolbar : "#doctorTempInfoTableToolbar",
		pagination : true,
		sidePagination : "server",
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		queryParamsType : "limit",
		queryParams:queryParams,
		contentType : "application/x-www-form-urlencoded",
		columns : [ {
			field : 'id',
			title : 'ID',
			visible : false
		}, {
			title : '序号',
			align : 'center',
			width : '5%',
			formatter : serilNumFormatter
		}, {
			field : 'mobile',
			title : '手机号',
			align : 'center',
			width : '10%'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '10%'
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			width : '8%'
		}, {
			field : 'hospitalName',
			title : '医院',
			align : 'center',
			width : '20%'
		}, {
			field : 'deptName',
			title : '科室',
			align : 'center',
			width : '10%'
		}, {
			field : 'careers',
			title : '职称',
			align : 'center',
			width : '10%'
		}, {
			field : 'statusName',
			title : '认证状态',
			align : 'center',
			width : '10%'
		},{
			field : 'mark',
			title : '备注',
			align : 'center',
			width : '10%'
		},  {
			field : 'docId',
			visible : false
		}, {
			field : 'headPath',
			visible : false
		}, {
			field : 'pidPath',
			visible : false
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

	$('#doctorTempListTable').bootstrapTable('refresh', {
		query : {
			mobile : $("input[name='mobile']").val(),
			name : $("input[name='name']").val(),
			hospitalName : $("input[name='hospitalName']").val(),
			deptName : $("input[name='deptName']").val(),
			limit:10,
			offset:0
		}
	});

}


function queryParams(params) {
	 
	return {
		mobile : $("input[name='mobile']").val(),
		name : $("input[name='name']").val(),
		hospitalName : $("input[name='hospitalName']").val(),
		deptName : $("input[name='deptName']").val(),
		limit:params.limit,
		offset:params.offset
	}
}
function operateFormatter(value, row, index) {
	return [
			'<a class="edit"  href="javascript:void(0)" onClick="searchImg('
					+ index + ')" title="查看图片">',
			'<i class="glyphicon glyphicon-search">查看图片</i>',
			'</a>    ',
			'<a class="edit"  href="javascript:void(0)" onClick="reply('+index+')" title="立即处理">立即处理</a>'
			 ]
			.join('');
}

function replyStatus(index){
	if(index==1)
	{
		alert("认证通过");
	}else {
		alert("认证不通过");
	}
	var id=$("#handleId").val();
	var mark=$("#mark").val();
	$.ajax({
		type: "POST",   //访问WebService使用Post方式请求
		url: "authDoctor", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data:{status:index,mark:mark,id:id},  //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到  "status="+index+"&mark="+mark+"&id="+id
		dataType: 'json',   //WebService 会返回Json类型
		success: function(data) {
			alert(data);
			if (data.success) {
				$.success('操作成功',function() {
					layer.close(pageIndex);
					search();
				});
			} else {
				$.error(data.msg);
			}
		}
	});
}


function reply(index){
	var data = $('#doctorTempListTable').bootstrapTable('getData');
	$("#handleMobile").html(data[index].mobile);
	$("#handleId").val(data[index].id);
	$("#docIds").val(data[index].docId);
	$("#mark").val(data[index].mark);
	$("#replyContentWrite").css("display","");
	//$("#replyContentRead").css("display","");
	$("#btnStatus").css("display","");
	pageIndex = layer.open({
		type : 1,
		title : '认证处理',
		shadeClose : true,
		shade : 0.5,
		area : [ '800px', '600px' ],
		content : $('#handleRZ')
	});
}

function  searchImg(index){
	var data = $('#doctorTempListTable').bootstrapTable('getData');
	$("#handleHeadPath").attr("src",data[index].headPath);
	$("#handlePidPath").attr("src",data[index].pidPath);
	pageIndex = layer.open({
		type : 1,
		title : '查看证件',
		shadeClose : true,
		shade : 0.5,
		area : [ '300px', '300px' ],
		content : $('#handleImg')
	});
}

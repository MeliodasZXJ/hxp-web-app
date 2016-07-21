/**
 * 材料列表
 */
$(function() {
	loadDocList();
})

function processData(value) {
	return eval(value);
}
function loadDocList() {

	$('#doctorListTable').bootstrapTable({
		method : 'post',
		url : "getDoctorListData",
		height : $(window).height() - 100,
		striped : true,
		minimumCountColumns : 2,
		smartDisplay : true,
		toolbar : "#doctorTempInfoTableToolbar",
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
			width : '5%',
			formatter : serilNumFormatter
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
		}, {
			field : 'docId',
			visible : true,
			visible : false
		}, {
			field : 'pageNum',
			visible : false
		}, {
			field : 'pageSize',
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

	$('#doctorListTable').bootstrapTable('refresh', {
		query : {
			name : $("input[name='name']").val(),
			hospitalName : $("input[name='hospitalName']").val(),
			deptName : $("input[name='deptName']").val()
		}
	});

}
function operateFormatter(value, row, index) {
	return [
			'<a class="edit"  href="javascript:void(0)" onClick="searchImg('
					+ row.id + ')" title="查看图片">',
			'<i class="glyphicon glyphicon-search">查看图片</i>',
			'</a>    ',
			'<a class="ok" href="javascript:void(0)" onClick="auth('
					+ row.id +',3)" title="认证通过">',
			'<i class="glyphicon glyphicon-ok-circle">认证通过</i>',
			'</a>',
			'<a class="remove" href="javascript:void(0)" onClick="auth('
			+ row.id+ ',2)" title="认证不通过">',
			'<i class="glyphicon glyphicon-remove-circle">认证不通过</i>', '</a>    ', ]
			.join('');
}

function auth(id,status) {
	var confirmMessage="";
	if(status==1){
		confirmMessage="确定认证通过么?";
	}else{
		confirmMessage="确定认证不通过么?";
	}
	layer.confirm(confirmMessage, {
		btn : [ '确定', '取消' ]
	}, function() {
		$.getJSON("authDoctor", { 
			id : id,
			status:status
		}, function(data) {
			if (data.success) {
				$.success("操作成功", function() {
					search();
				});
			} else {
				$.error(data.msg);
			}

		});
	}, function() {
		$.warn("您取消了操作");
	});

}

/**
 * 字典相关js
 */
var pageIndex ;
$(function() {
	$.ajaxSetup({
		async : false
	});
	$("#tree_dicType").treeview({
		color : "#428bca",
		data : getTreeData(0),
		onNodeSelected : function(e, o) {
			$("#addDicInfoBtn").css("display","");
			$("input[name='typeCode']").val(o.code);
			$('#treeInfoTable').bootstrapTable('refresh', {
				url : 'getDicInfoList.do?typeCode=' + o.code
			});
			if(o.modifyState==0){
				$("#addDicInfoBtn").css("display","none");
			}
		}
	});
	loadTreeInfo();

	$("#addDicInfoForm").validate(
					{
						rules : {
							'name' : {
								required:true
							},
							'sort':{
								required:true,
								digits:true
							}
						},
						submitHandler : function(form) {
							var options = {
								url : "saveDicInfo.do",
								type : "post",
								dataType : "json",
								success : function(data) {
									if (data.success) {
										
										$.success('操作成功',function() {
												layer.close(pageIndex);
												$('#treeInfoTable').bootstrapTable('refresh',{url : 'getDicInfoList.do?typeCode='+ $("input[name='typeCode']").val()});
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

function loadTreeInfo(code) {

	$('#treeInfoTable')
			.bootstrapTable(
					{
						method : 'get', // 这里要设置为get，不知道为什么 设置post获取不了
						url : "getDicInfoList.do?typeCode=",
						height :  $(window).height() - 200,
						striped : true,
						minimumCountColumns : 2,
						smartDisplay : true,
						search : true,
						toolbar : "#treeInfoTableToolbar",
						columns : [
								{
									field : 'id',
									title : 'ID',
									visible : false
								},
								{
									title : '序号',
									align : 'center',
									width : '10',
									formatter : serilNumFormatter
								},
								{
									field : 'name',
									title : '名称',
									align : 'center',
									width : '100',
									searchable : true,
									editable : {
										type : 'text',
										title : '修改名称',
										validate : function(value) {
											var data = $('#treeInfoTable')
											.bootstrapTable('getData'), index = $(
											this).parents('tr').data(
											'index');
											if(data[index].shopId==0){
												return '系统内置项,不允许修改';
											}
											value = $.trim(value);
											if (!value) {
												return '该项必填';
											}

											var info = '';

											$.post("editDicInfo.do", {
												id : data[index].id,
												name : value
											}, function(data) {
												if (!data.success) {
													info = data.msg;
												}
											}, "json");
											return info;
										}
									}
								},
								{
									field : 'sort',
									title : '排序',
									align : 'center',
									width : '10',
									editable : {
										type : 'text',
										title : '修改排序',
										validate : function(value) {
											value = $.trim(value);
											var data = $('#treeInfoTable')
											.bootstrapTable('getData'), index = $(
											this).parents('tr').data(
											'index');
											if(data[index].shopId==0){
												return '系统内置项,不允许修改';
											}
											if (!value) {
												return '该项必填';
											}
											if (!(/^\d+$/g.test(value))) {
												return '必须为数字';
											}

											

											var info = '';

											$.post("editDicInfo.do", {
												id : data[index].id,
												sort : value
											}, function(data) {
												if (data.success) {
													$('#treeInfoTable').bootstrapTable('refresh',{url : 'getDicInfoList.do?typeCode='+ $("input[name='typeCode']").val()});
												}else{
													info = data.msg;
												}
												
											}, "json");
											return info;
										}
									}
								}, {
									field : 'createTime',
									title : '创建时间',
									align : 'center',
									width : '10',
									formatter : dateTimeFormatter
								}, {
									title : '操作',
									field : 'shopId',
									align : 'center',
									width : '10',
									valign : 'middle',
									formatter : operateFormatter
								} ]
					});

}

function addDicInfo() {
	if ($("input[name='typeCode']").val() == '') {
		$.warn("请先选择左侧字典类型后再试！");
		return;
	}
	
	$("input[name='name']").val('');
	$("input[name='sort']").val('');
	pageIndex = layer.open({
		type : 1,
		title : '新增字典值',
		shadeClose : true,
		shade : 0.5,
		area : [ '400px', '300px' ],
		content : $('#addDicInfo')
	});

}

function operateFormatter(value, row, index) {
	return ['<a class="remove" href="javascript:void(0)" onClick="delRow('
					+ row.id + ')" title="删除">',
			'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}
function delRow(id) {

	layer.confirm('确定删除吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.getJSON("delDicInfo.do", {
			id : id
		}, function(data) {
			if (data.success) {
				$.success("操作成功", function() {
					$("#treeInfoTable").bootstrapTable('remove', {
						field : 'id',
						values : [ id ]
					});
				});
			} else {
				$.error(data.msg);
			}

		});
	}, function() {
		$.warn("您取消了删除操作");
	});

}

function getTreeData(pid) {
	var t;
	$.ajax({
		type : "post",
		url : "getTreeData.do",
		data : {
			pid : pid
		},
		async : false,
		success : function(data) {
			t = data;
		}
	});

	return t;
}

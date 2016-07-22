/**
 * 材料列表
 */
function selectProvince(obj){
		if ($("#"+obj+"ProvinceId").val() != '') {
					$.getJSON(ctx+"/admin/sys/region/getRegionByPid?pid="
							+ $("#"+obj+"ProvinceId").val(), function(
							data) {
						var s = "<option value=''>选择市</option>";
						$.each(data, function(i, item) {
							s += "<option value='" + item.id + "'>" + item.name+ "</option>";
						});
						$("#"+obj+"CityId").html(s);
						$("#"+obj+"RegionId").html("<option value=''>选择区</option>");
					});
			} else {
				$("#"+obj+"CityId").html("<option value=''>选择市</option>");
				$("#"+obj+"RegionId").html("<option value=''>选择区</option>");
			}

}

function selectCity(obj){
			
				if ($("#"+obj+"CityId").val() != '') {
					$.getJSON(ctx+"/admin/sys/region/getRegionByPid?pid="
							+ $("#"+obj+"CityId").val(), function(data) {
						var s = "<option value=''>选择区</option>";
						$.each(data, function(i, item) {
							s += "<option value='" + item.id + "'>" + item.name + "</option>";
						});
						$("#"+obj+"RegionId").html(s);
					});
				} else {
					$("#"+obj+"RegionId").html("<option value=''>选择区</option>");
				}
	
}


var pageIndex ;
$(function() {
	loadHopitalList();
	
	$("#hospitalForm").validate(
			{
				rules : {
					'provinceId' : {
						required:true
					},
					'cityId' : {
						required:true
					},
					'regionId' : {
						required:true
					},
					'name' : {
						required:true
					}
				},
				submitHandler : function(form) {
					var options = {
						url : "save",
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
function loadHopitalList() {

	$('#hopitalListTable').bootstrapTable({
		method : 'post',
		url : "getHospitalListData",
		height : $(window).height() - 100,
		striped : true,
		minimumCountColumns : 2,
		smartDisplay : true,
		toolbar : "#hopitalListTableToolbar",
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
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '10%'
		}, {
			field : 'provinceId',
			visible : false
		}, {
			field : 'provinceName',
			title : '省',
			align : 'center',
			width : '20%'
		}, {
			field : 'cityId',
			visible : false
		}, {
			field : 'cityName',
			title : '市',
			align : 'center',
			width : '10%'
		}, {
			field : 'regionId',
			visible : false
		}, {
			field : 'regionName',
			title : '区/县',
			align : 'center',
			width : '10%'
		}, {
			field : 'address',
			title : '详细地址',
			align : 'center',
			width : '10%'
		}, {
			field : 'mark',
			title : '备注',
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

	$('#hopitalListTable').bootstrapTable('refresh', {
		query : {
			name : $("#searchName").val(),
			provinceId:$("#searchProvinceId").val(),
			cityId:$("#searchCityId").val(),
			regionId:$("#searchRegionId").val(),
			limit:10,
			offset:0
		}
	});

}

function queryParams(params) {
	 
	return {
		name : $("#searchName").val(),
		provinceId:$("#searchProvinceId").val(),
		cityId:$("#searchCityId").val(),
		regionId:$("#searchRegionId").val(),
		limit:params.limit,
		offset:params.offset
	}
}

function operateFormatter(value, row, index) {
	return [
			'<a class="edit"  href="javascript:void(0)" onClick="edit('
					+ index + ')" title="修改">',
			'<i class="glyphicon glyphicon-edit">修改</i>',
			'</a>    ',
			'<a class="remove" href="javascript:void(0)" onClick="del('
			+ row.id+ ')" title="删除">',
			'<i class="glyphicon glyphicon-remove">删除</i>', '</a>    ', ]
			.join('');
}

function del(id) {
	layer.confirm("确认删除吗?", {
		btn : [ '确定', '取消' ]
	}, function() {
		$.getJSON("del", { 
			id : id
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

function  edit(index){
	var data = $('#hopitalListTable').bootstrapTable('getData');
	$("#handleId").val(data[index].id);
	$("#handleName").val(data[index].name);
	$("#handleProvinceId").val(data[index].provinceId);
	$.ajaxSettings.async = false;  
	selectProvince('handle');
	$("#handleCityId").val(data[index].cityId);
	selectCity('handle');
	$("#handleRegionId").val(data[index].regionId);
	$("#handleAddress").val(data[index].address);
	$("#handleMark").val(data[index].mark);
	
	
	pageIndex = layer.open({
		type : 1,
		title : '医院信息',
		shadeClose : true,
		shade : 0.5,
		area : [ '700px', '400px' ],
		content : $('#handleHospital')
	});
}

function add(){
	$("#handleId").val("");
	$("#handleName").val("");
	$("select[name='cityId']").html("<option value=''>选择市</option>");
	$("select[name='regionId']").html("<option value=''>选择区</option>");
	$("#handleAddress").val("");
	$("#handleMark").val("");
	pageIndex = layer.open({
		type : 1,
		title : '医院信息',
		shadeClose : true,
		shade : 0.5,
		area : [ '800px', '600px' ],
		content : $('#handleHospital')
	});
}

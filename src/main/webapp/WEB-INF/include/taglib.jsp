<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!-- bootstrap-table end-->
<script type="text/javascript">
    var ctx = "${ctx}";
</script>

<link href="${ctx}/assets/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="${ctx}/assets/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${ctx}/assets/css/animate.min.css" rel="stylesheet">
<link href="${ctx}/assets/css/style.min.css?v=4.0.0" rel="stylesheet">
<link href="${ctx}/assets/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
<link href="${ctx}/assets/css/plugins/bootstrap-table/bootstrap-editable.css" rel="stylesheet">

<script src="${ctx}/assets/js/jquery.min.js?v=2.1.4"></script>

<script src="${ctx}/assets/js/bootstrap.min.js?v=3.3.5"></script>
<script src="${ctx}/assets/js/content.min.js?v=1.0.0"></script>
<script src="${ctx}/assets/js/plugins/jqueryForm/jquery-form.js"></script>
<script src="${ctx}/assets/js/plugins/layer/layer.js"></script>
<script src="${ctx}/assets/js/plugins/layer/extend/layer.ext.js"></script>
<script src="${ctx}/assets/js/plugins/layer/layerMessage.js"></script>
<!-- validate start -->
<script src="${ctx}/assets/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/assets/js/plugins/validate/validate_extends.js"></script>
<script src="${ctx}/assets/js/plugins/validate/messages_zh.min.js"></script>
<!-- validate end -->

<!-- self setting start -->
<script src="${ctx}/assets/js/setting_global.js"></script>
<!-- self setting end -->
<!-- bootstrap-table start-->
<script src="${ctx}/assets/js/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/assets/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>
<script src="${ctx}/assets/js/plugins/bootstrap-table/extensions/editable/bootstrap-editable.js"></script>
<script src="${ctx}/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>


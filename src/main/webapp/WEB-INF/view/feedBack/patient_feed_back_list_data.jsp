<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
{ "total":${page.total}, "rows":[
<c:forEach items="${page.list }" var="fb" varStatus="index">
		{"id":"${fb.id}","mobile":"${fb.mobile }","replyContent":"${fb.replyContent }","content":"${fb.content }","createTime":"${fb.createTime }","replyTime":"${fb.replyTime }","replyName":"${fb.replySysUserName }","status":"${fb.status }","operstatus":"<c:if test="${fb.status==0}">未处理</c:if><c:if test="${fb.status==1}">已处理</c:if>"}
		<c:if test="${!index.last}">,</c:if>
</c:forEach>
] }

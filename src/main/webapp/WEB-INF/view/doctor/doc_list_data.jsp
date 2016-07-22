<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
{                                                      
	"total":${page.total},                                                   
	"rows":[  
	<c:forEach items="${page.list }" var="doctor" varStatus="index">
		{"id":"${doctor.id}","mobile":"${doctor.mobile}","name":"${doctor.name }","sex":"<c:if test="${doctor.sex==0}">女</c:if><c:if test="${doctor.sex==1}">男</c:if>","hospitalName":"${doctor.hospitalName}","deptName":"${doctor.deptName }","careers":"${doctor.careers}","statusName":"<c:if test="${doctor.status==0}">未认证（证件不全）</c:if><c:if test="${doctor.status==1}">已认证</c:if><c:if test="${doctor.status==2}">认证未通过</c:if><c:if test="${doctor.status==3}">待认证</c:if><c:if test="${doctor.status==4}">重新认证中</c:if>","status":"${doctor.status }","mark":"${doctor.mark }","headPath":"${doctor.headPath }","pidPath":"${doctor.pidPath }"}
		<c:if test="${!index.last}">,</c:if>
	</c:forEach>
	]                                                          
}                                                           

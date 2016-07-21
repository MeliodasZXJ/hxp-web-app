<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
{                                                      
	"total":${page.total},                                                   
	"rows":[  
	<c:forEach items="${page.list }" var="doctor" varStatus="index">
		{"id":"${doctor.id}","docId":"${doctor.docId}","name":"${doctor.name }","sex":"<c:if test="${doctor.sex==0}">女</c:if><c:if test="${doctor.sex==1}">男</c:if>","hospitalName":"${doctor.hospitalName}","deptName":"${doctor.deptName }","careers":"${doctor.careers}","statusName":"<c:if test="${doctor.status==1}">未审核</c:if><c:if test="${doctor.status==-1}">审核未通过</c:if>","pageNum":"${page.pageNum}","pageSize":"${page.pageSize}"}
		<c:if test="${!index.last}">,</c:if>
	</c:forEach>
	]                                                          
}                                                           

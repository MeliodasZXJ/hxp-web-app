<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
{                                                      
	"total":${page.total},                                                   
	"rows":[  
	<c:forEach items="${page.list }" var="material" varStatus="index">
		{"id":"${material.id}","name":"${material.name }","supplierName":"${material.supplier.name }","typeName":"${material.materialType.name}","stock":"${material.stock}","unit":"<fmt:formatNumber value='${material.unitPrice}' type='currency' pattern='#.###' />/${material.unitType.name }","sale":"<fmt:formatNumber value='${material.salePrice}' type='currency' pattern='#.###' />/${material.unitType.name }","brand":"${material.brand}","specifications":"${material.specifications}","pageNum":"${page.pageNum}","pageSize":"${page.pageSize}"}
		<c:if test="${!index.last}">,</c:if>
	</c:forEach>
	]                                                          
}                                                           

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
{                                                      
	"total":${page.total},                                                   
	"rows":[  
	<c:forEach items="${page.list }" var="hospital" varStatus="index">
		{"id":"${hospital.id}","name":"${hospital.name }","provinceId":"${hospital.provinceId}","provinceName":"${hospital.province.name}","cityId":"${hospital.cityId}","cityName":"${hospital.city.name}","regionId":"${hospital.regionId }","regionName":"${hospital.region.name }","address":"${hospital.address}","mark":"${hospital.mark}"}
		<c:if test="${!index.last}">,</c:if>
	</c:forEach>
	]                                                          
}                                                           

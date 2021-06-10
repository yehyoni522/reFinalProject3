<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>

<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<div align="center">
		<div id="login">
			<c:if test="${empty sessionScope.loginuser}">
				<button type="button" class="btn btn-secondary" onclick="location.href='<%=ctxPath%>/login.sam'">로그인</button>
			</c:if>
			<c:if test="${not empty sessionScope.loginuser}">
				<button type="button" class="btn btn-secondary" onclick="location.href='<%=ctxPath%>/logout.sam'">로그아웃</button>
			</c:if>
		</div>
		<div id="logo">
			<a href="<%=ctxPath%>/admin/index.sam"><img src="<%= ctxPath%>/resources/images/logo.png"  style="width:100%; cursor:pointer;"></a>
		</div>
		
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>
<style>

</style>

<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<div align="center">
		<div id="login">
			<c:if test="${empty sessionScope.loginuser}">
				<button type="button" class="btn btn-secondary" onclick="location.href='<%=ctxPath%>/login.sam'">로그인</button>
			</c:if>
			<c:if test="${not empty sessionScope.loginuser}">
			<c:if test="${sessionScope.loginuser.identity == 0}">
				<a href="<%= ctxPath%>/mypage/mypage.sam">${sessionScope.loginuser.name}</a>님 &nbsp;&nbsp;
			</c:if>
			<c:if test="${sessionScope.loginuser.identity == 1}">
				<a href="<%= ctxPath%>/mypage/mypage.sam">${sessionScope.loginuser.name}</a>교수 &nbsp;&nbsp;
			</c:if>
			<c:if test="${sessionScope.loginuser.identity == 2}">
				<a href="">관리자페이지</a> &nbsp;&nbsp;
			</c:if>
				<button type="button" class="btn btn-secondary" onclick="location.href='<%=ctxPath%>/logout.sam'">로그아웃</button>
		
			</c:if>
		</div>
		<div id="logo">
			<a href="<%=ctxPath%>/index.sam"><img src="<%= ctxPath%>/resources/images/logo.png"  style="width:100%; cursor:pointer;"></a>
		</div>
		
</div>
<c:if test="${not empty sessionScope.loginuser}">
	<nav class="navbar navbar-default">
	  <ul class="nav navbar-nav" >
	    <li><a style =" text-align:center; width: 350px;" href="<%=ctxPath%>/index.sam">HOME</a></li>
	    <li><a style =" text-align:center; width: 350px;" href="<%=ctxPath%>/board/list.sam?categoryno=1">커뮤니티</a></li>
	    <li><a style =" text-align:center; width: 350px;" href="<%=ctxPath%>/board/list.sam?categoryno=4">공지사항</a></li>
	    <li><a style =" text-align:center; width: 350px;" href="<%=ctxPath%>/board/list.sam?categoryno=5">Q&A</a></li>
	  </ul>
	</nav>
</c:if>

<c:if test="${empty sessionScope.loginuser}">
</c:if>

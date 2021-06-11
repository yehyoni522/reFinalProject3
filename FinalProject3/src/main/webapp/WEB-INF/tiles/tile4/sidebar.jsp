<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>
<style type="text/css">
div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	padding-top: 50px;
	min-height: 600px;
}

li.tiles4 {
	list-style: none;
	margin-top:30px;
	font-size: 17pt;
}
</style>    

<div id="adminside" >
	<br> 
	<br>
	<ul>
	<c:if test="${sessionScope.subno != null}">
		<li class="tiles4"><a href="<%=ctxPath%>/lesson/notice.sam?fk_subno=${sessionScope.subno}">공지사항</a></li>
		<li class="tiles4"><a href="<%=ctxPath%>/class/materialList.sam?subno=${sessionScope.subno}">수업계획서</a></li>
		<li class="tiles4"><a href="<%=ctxPath%>/class/materialList.sam?subno=${sessionScope.subno}">수업자료</a></li>
		<li class="tiles4"><a href="<%=ctxPath%>/class/qnaBoard.sam?subno=${sessionScope.subno}">과제게시판</a></li>
		<li class="tiles4"><a href="<%=ctxPath%>/class/qnaBoard.sam?subno=${sessionScope.subno}">질문게시판</a></li>
		<li class="tiles4"><a href="<%=ctxPath%>/lesson/quizlist.sam?subno=${sessionScope.subno}">쪽지시험</a></li>
		<c:if test="${sessionScope.loginuser.identity eq 0}">
			<li class="tiles4"><a href="<%=ctxPath%>/lesson/attendance.sam?subno=${sessionScope.subno}">출석</a></li>
		</c:if>
		<c:if test="${sessionScope.loginuser.identity eq 1}">
			<li class="tiles4"><a href="<%=ctxPath%>/lesson/attendanceadmin.sam?subno=${sessionScope.subno}">출석</a></li>
		</c:if>
	</c:if>
	</ul>	
</div>
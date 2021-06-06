<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</style>    

<div id="adminside" >
	<span style="font-family: 'Noto Sans KR', sans-serif;">관리자 페이지 홈</span>
	<br>
	<br>
	<span>회원/교수진 관리</span>
	<ul>
		<li>학생 관리</li>
		<li>교수진 관리</li>
		<li>활동중지 회원</li>
	</ul>
	<br>
	<span>게시글 관리</span>
	<ul>
		<li>게시글 관리</li>
		<li>댓글 관리</li>
		<li>스팸글 관리</li>
	</ul>
	<br>
	<a href="<%=ctxPath%>/admin/readingRoomBook.sam"><span>열람실 관리</span></a>	
	<br><br>
	<span>수업 관리</span>	
	<ul>
		<li>수업 개설</li>
	</ul> 	
</div>
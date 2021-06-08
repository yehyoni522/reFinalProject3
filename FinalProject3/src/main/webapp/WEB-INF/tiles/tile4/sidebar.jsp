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
		<li class="tiles4"><a href="">공지사항</a></li>
		<li class="tiles4"><a href="">수업계획서</a></li>
		<li class="tiles4"><a href="">수업자료</a></li>
		<li class="tiles4"><a href="">과제게시판</a></li>
		<li class="tiles4"><a href="">질문게시판</a></li>
		<li class="tiles4"><a href="">쪽지시험</a></li>
		<li class="tiles4"><a href="">출석</a></li>
	</ul>	
</div>
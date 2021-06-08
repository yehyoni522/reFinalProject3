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
	<a href="<%=ctxPath%>/admin/index.sam"><span style="font-family: 'Noto Sans KR', sans-serif;">학사 일정 관리</span></a>
	<br> 
	<br>
	<span>회원/교수진 관리</span>
	<ul>
		<li><a href="<%=ctxPath%>/admin/memberRegister.sam">회원 등록</a></li>
		<li><a href="<%=ctxPath%>/admin/student.sam">학생 관리</a></li>
		<li><a href="<%=ctxPath%>/admin/professor.sam">교수진 관리</a></li>
	</ul>
	<br>
	<span>게시글 관리</span>
	<ul>
		<li><a href="<%=ctxPath%>/admin/boardlist.sam">게시글 관리</a></li>
		<li><a href="<%=ctxPath%>/admin/commentList.sam">댓글 관리</a></li>
		<li><a href="<%=ctxPath%>/board/list.sam?categoryno=4">공지사항</a></li>
		<li><a href="<%=ctxPath%>/board/list.sam?categoryno=5">Q&A</a></li>
	</ul>
	<br>
	<a href="<%=ctxPath%>/admin/readingRoomBook.sam"><span>열람실 관리</span></a>	
	<br><br>
	<span>수업 관리</span>	
	<ul>
		<li>수업 목록</li>
		<li><a href="<%=ctxPath%>/admin/addSubject.sam">수업 개설</a></li>
	</ul> 	
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

.subjectStyle {
	font-weight: bolder;
	cursor: pointer;
} 
                   
.headerName{
	text-align:center;
	font-weight: bolder;
}

.headerCategoty{
	display: inline-block; 
	padding-bottom:5px; 
	border-bottom: 1px gray solid; 
	font-size: 13pt; 
	font-weight: bold;
}

th, td {text-align:center;}   

th{
	background-color: #f2f2f2;
	
}

tr{
	border-top: solid 0.5px #bfbfbf;
	border-bottom: solid 0.5px #bfbfbf;
	height:50px;
}

div#btn-board{
	margin-top: 10px;
	display:inline-block;
	float:right;
}
	
.btn-board{
 	width:100px;
	border:0.5px solid #bfbfbf;
 	font-weight: bold;
 	margin-left:10px;	 	
 	margin-right:5px;
 	padding:5px;
 	border-radius: 5px;
 	height: 30px;
}

	
	tr.list:hover{
		background-color: #fafafa;
	}

</style>

<script type="text/javascript">

$(function(){
	if(${requestScope.qnaList == null}) {
		console.log("텅빔");
	}
});
	   
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;홈
	</div>

	<h1 class="headerName">${sessionScope.subject}</h1>
	<br><br>
	<div style='display:inline-block; float:left; width: 43%;'>
	<h4 style="text-align: left; font-weight: bold;">| <a href="<%=ctxPath%>/lesson/notice.sam?subno=${sessionScope.subno}">공지사항</a></h4>
			<table style="width:500px;">
			<tr>
				<th>글제목</th>
				<th>작성일자</th>
			</tr>
			<c:forEach var="notice" items="${requestScope.noticeList}"> 
				<tr>
					<td>${notice.subject}</td>
					<td>${notice.regdate}</td>
				</tr>			
			</c:forEach>
			
		</table>
	</div>
	
	<div style='display:inline-block; float:right; width: 43%;'>
			
		<h4 style="text-align: left; font-weight: bold;">| <a href="<%=ctxPath%>/class/qnaBoard.sam?subno=${sessionScope.subno}">질문게시판</a></h4>
		<table style="width:500px;">
			<tr>
				<th>글제목</th>
				<th>작성일자</th>
			</tr>
			<c:forEach var="qna" items="${requestScope.qnaList}"> 
				<tr>
					<td>${qna.subject}</td>
					<td>${qna.regdate}</td>
				</tr>			
			</c:forEach>
		</table>
		</div>		
	
	<div style="clear: both;">
	<h4 style="text-align: left; font-weight: bold;  margin-top:-100px;" align="center" >| <a href="<%=ctxPath%>/class/qnaBoard.sam?subno=${sessionScope.subno}">수업자료</a></h4>
	<table style="width:1000px;">
			<tr>
				<th>글제목</th>
				<th>작성일자</th>
			</tr>
			<c:forEach var="material" items="${requestScope.materialList}"> 
				<tr>
					<td>${material.subject}</td>
					<td>${material.regdate}</td>
				</tr>			
			</c:forEach>
		</table>
	</div>
</div>




<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

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
	height:40px;
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

	$(document).ready(function(){
		
		
	});
	
	
	   
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">


	<h1 class="headerName">${requestScope.subject}</h1>
	
	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;과제게시판
	</div>

	<h3 class="headerName">과제게시판</h3>
	<br>
	<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
		<tr style="border-bottom: 1px #e6e6e6 solid;">
			<th style="width: 10%;">No.</th>
			<th style="width: 40%;">제목</th>
			 
			<c:choose>
				<%-- 학생일 경우 제출상태, 점수 컬럼 --%>
				<c:when test="${sessionScope.loginuser.identity eq '0'}">
					<th style="width: 10%;">제출</th>
					<th style="width: 10%;">점수</th>
				</c:when>
			
				<%-- 교수일 경우 제출개수 컬럼  --%>
				<c:when test="${sessionScope.loginuser.identity eq '1'}">
					<th style="width: 20%;">제출</th>
				</c:when>
			</c:choose>
			
			<th style="width: 20%;">마감일</th>			
		</tr>
			
		<c:forEach var="assgnVO" items="${requestScope.assignmentList}" varStatus="status"> 
			<tr class="list">
				<td>${assgnVO.assgnno}</td>
				<td style="text-align:left; font-weight: bold;"><a>${assgnVO.subject}</a></td>				
				
				
				<c:choose>
					<%-- 학생일 경우 제출상태, 점수 컬럼 --%>
					<c:when test="${sessionScope.loginuser.identity eq '0'}">					
						<c:if test="${(assgnVO.status eq '0') || (assgnVO.status eq null)}"><td>X</td></c:if>
						<c:if test="${assgnVO.status eq '1'}"><td>O</td></c:if>		
						
						<c:choose>
							<c:when test="${assgnVO.status eq null}">
								<td>비공개</td>
							</c:when>							
							<c:otherwise>
								<td>${assgnVO.score}</td>
							</c:otherwise>
						</c:choose>	
					</c:when>
					
					<%-- 교수일 경우 제출개수 컬럼 --%>
					<c:when test="${sessionScope.loginuser.identity eq '1'}">
						<td>${assgnVO.submitCount}</td>
					</c:when>
				</c:choose>
			
				<!-- 마감일 null(미정)일 경우 비공개 -->
				<c:choose>
					<c:when test="${assgnVO.deadline eq null}">
						<td>비공개</td>
					</c:when>
					<c:otherwise>
						<td>${assgnVO.deadline}</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	
	</table>
	
	<%-- <c:if test="${sessionScope.loginuser.identity eq '1'}"> --%>
		<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/class/assignmentAdd.sam'"/>
		</div>
	<%-- </c:if> --%>
       <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

</div>




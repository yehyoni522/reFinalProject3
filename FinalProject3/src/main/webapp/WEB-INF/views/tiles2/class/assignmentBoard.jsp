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
		
		$("span.subject").bind("mouseover",function(event){
			var $target=$(event.target);
			$target.addClass("subjectStyle");
		});
		
		$("span.subject").bind("mouseout",function(event){
			var $target=$(event.target);
			$target.removeClass("subjectStyle");			
		});
		
		$("span.subject").click(function(){		
			var assgnno = $(this).parent().prev().text();
			var frm = document.goViewFrm;
			frm.assgnno.value = assgnno;
			
			frm.method = "get";
	   	    frm.action = "<%= ctxPath%>/class/assignmentView.sam";
	   	    frm.submit();
		});
		
		
	});// end of function goView(assgnno) {}-----------------------
	   	
	   
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;과제게시판
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 과제게시판</h3>
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
				<c:when test="${(sessionScope.loginuser.identity eq '1')||(sessionScope.loginuser.identity eq '2')}">
					<th style="width: 20%;">제출</th>
				</c:when>
			</c:choose>
			
			<th style="width: 20%;">마감일</th>			
		</tr>
		
		<c:forEach var="assgnVO" items="${requestScope.assignmentList}" varStatus="status"> 
		
			<tr class="list">
				<td>${assgnVO.assgnno}</td>
				<td style="text-align:left;">
					<%-- 첨부파일이 없는 경우 시작 --%>
						<c:if test="${empty assgnVO.fileName}">
							<span class="subject" >${assgnVO.subject}</span>
						</c:if>
					<%-- 첨부파일이 없는 경우 끝 --%>
					
					<%-- 첨부파일이 있는 경우 시작 --%>
						<c:if test="${not empty assgnVO.fileName}">
							<span class="subject">${assgnVO.subject}</span>&nbsp;<img src="<%=ctxPath %>/resources/images/disk.gif"/>
						</c:if>
					<%-- 첨부파일이 있는 경우 끝 --%>
				</td>				
				
				
				<c:choose>
					<%-- 학생일 경우 제출상태, 점수 컬럼 --%>
					<c:when test="${sessionScope.loginuser.identity eq '0'}">					
						<c:if test="${assgnVO.status eq null || assgnVO.status eq '0'}"><td>X</td></c:if>
						<c:if test="${assgnVO.status eq '1'}"><td>O</td></c:if>		
						
						<c:choose>
							<c:when test="${assgnVO.score eq null || assgnVO.score eq '0'}">
								<td>비공개</td>
							</c:when>							
							<c:otherwise>
								<td>${assgnVO.score}</td>
							</c:otherwise>
						</c:choose>	
					</c:when>
					
					<%-- 교수일 경우 제출개수 컬럼 --%>
					<c:when test="${(sessionScope.loginuser.identity eq '1')||(sessionScope.loginuser.identity eq '2')}">					
						<td>${assgnVO.submitCount}/${requestScope.totalPerson}</td>
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
	
	<c:if test="${sessionScope.loginuser.identity eq '1'}">
		<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/class/assignmentAdd.sam'"/>
		</div>
	</c:if> 
       <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

	<form name="goViewFrm">	
		<input type="hidden" name="assgnno" />
	</form>



</div>




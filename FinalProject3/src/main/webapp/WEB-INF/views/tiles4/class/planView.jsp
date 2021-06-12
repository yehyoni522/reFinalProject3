<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

th{
	text-align:center;
	border:solid 0.5px #bfbfbf;}   

td{
	text-align:left;
	padding:0 15px;
	border:solid 0.5px #bfbfbf;
}

th{
	background-color: #f2f2f2;
	
}

tr.top{
	border-top: 2px #b3b3b3 solid;
}
tr.bottom{
	border-bottom: 1.5px #b3b3b3 solid;
}


tr{
	border-top: solid 0.5px #bfbfbf;
	border-bottom: solid 0.5px #bfbfbf;
	height:40px;
}

td.comment {text-align: center;}

div#btn-board{
	align-items: center; 
	margin-top: 10px;
	margin-bottom: 30px;
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

.small {
	width:40px;
	height: 26px;
}
 .long {width: 80%;}
 
.moveSubject {
	display: inline-block;
	padding:15px 30px;
	width: 200px;
}

.left {
	float: left;
}

.right {
	float: right;
}

.move{
	display:inline-block; 
	font-weight:bold;
}

.move:hover{
	text-decoration: underline;
	cursor: pointer;
}

</style>

<script type="text/javascript">

	$(document).ready(function(){
 
		
		
		
	});
	

	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">


	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;수업계획서
	</div>
	
	<h1 class="headerName">${sessionScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 수업계획서 상세</h3>

	<hr>
			
				<table id="table" style="width:100%; border:1.5px #b3b3b3 solid;">
					<tr class="top">
						<th rowspan="4" style="width:15%;">강의과목</th>
						<th style="width:15%;">교과목코드</th>
						<td style="width:27.5%;">${requestScope.InfoVO.subno}</td>
						<th style="width:15%;">과목명</th>
						<td style="width:27.5%;">${requestScope.InfoVO.subname}</td>
					</tr>
					<tr>
						<th>개설대학</th>
						<td>${requestScope.InfoVO.subcol}&nbsp;${requestScope.InfoVO.submajor}</td>
						
						<th>개설학기</th>
						<td>${requestScope.InfoVO.semester}학기</td>
					</tr>				
					<tr>
						<th>강의시간</th>
						<td>${requestScope.InfoVO.day}&nbsp;${requestScope.InfoVO.time}</td>
						<th>학점</th>
						<td>${requestScope.InfoVO.credit}학점</td>
					</tr>
					<tr class="bottom">
						<th>성적평가</th>
						<td colspan="3" class="comment">출석 10% 과제 30% 쪽지시험 10% 중간고사 30% 기말고사 40%</td>
					</tr>
					
					<tr class="top">
						<th rowspan="2">담당교수</th>
						<th>교수명</th>
						<td>${requestScope.InfoVO.name}</td>
						<th>소속</th>
						<td>${requestScope.InfoVO.col}&nbsp;${requestScope.InfoVO.major}</td>
					</tr>
					<tr class="bottom">
						<th>이메일</th>
						<td>${requestScope.InfoVO.email}</td>
						
						<th>전화번호</th>
						<td>${requestScope.InfoVO.mobile}</td>
					</tr>						
					</table>
					<br>
	
				
				<table id="table" style="width:100%; border:1.5px #b3b3b3 solid;">
					<tr class="top bottom">
						<th colspan="100%">주차별 강의 계획 </th>
					</tr>
					
					<tr>
						<th style="width:15%;">주차</th> 
						<th style="width:57.5%;">수업내용</th>
						<th style="width:27.5%;">교재범위 및 과제물</th>
					</tr>
					
					<c:if test="${empty requestScope.PlanVO}">
						<tr><td colspan="100%" class="comment">등록된 주차별 강의 계획이 없습니다.</td></tr>
					</c:if>
					
					<c:forEach var="PlanVO"  items="${requestScope.PlanVO}"  varStatus="status"> 
						<tr>
							<th>${PlanVO.planno}주차</th>
							<td>${PlanVO.content}</td>
							<td>${PlanVO.etc}</td>
						</tr>	
					</c:forEach> 
					</table> 
			
				

	
	
	
	<div id="btn-board">
	
	<c:if test="${empty requestScope.PlanVO}">
		<button type="button" class="btn-board" id="planEdit" onclick="javascript:location.href='<%= ctxPath%>/class/planAddEdit.sam?subno=${sessionScope.subno}'">등록</button>
	</c:if>
	<c:if test="${not empty requestScope.PlanVO}">
		<button type="button" class="btn-board" id="planEdit" onclick="javascript:location.href='<%= ctxPath%>/class/planAddEdit.sam?subno=${sessionScope.subno}'">수정</button>
	</c:if>	
			

	</div>

	

    
    
    

 
 
 
</div>




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
			
			var quizname = $(this).text();
			var frm = document.quizinfoFrm;
			// alert(quizname);
			frm.quizname.value = quizname;
	
			frm.method = "post";
	   	    frm.action = "<%= ctxPath%>/lesson/quizView.sam";
	   	    frm.submit();
		});
		
	});// end of function goView(assgnno) {}-----------------------
	   	
	   
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="quizcontainer" style="margin-top: 150px;" >

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;쪽지시험
	</div>

	<h1 class="headerName">시험</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 쪽지시험</h3>
	<br>
	<form name="quizinfoFrm" >
		<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			<tr style="border-bottom: 1px #e6e6e6 solid;">
				<th style="width: 10%;">No.</th>
				<th style="width: 40%;">시험명</th>
				<th style="width: 15%;">과목명</th>
				<th style="width: 15%;">교수</th>	
				<th style="width: 10%;">시험현황</th>
				<th style="width: 10%;">시험날짜</th>						
			</tr>
			
			<c:forEach var="quizvo" items="${requestScope.quizvoList}" varStatus="status">		 
				<tr class="list">
					<td>${status.count}<input class="quizno" type="hidden" value="${quizvo.quizno}" name="quizno"/></td>
					<td style="text-align:left;">
						<span class="subject" >${quizvo.quizname}</span>
						<input type="hidden" name="quizname" />
					</td>
					<td>${quizvo.subname}</td>							
					<td>${quizvo.name}</td>
					<td>작업중</td>
					<td>${quizvo.quizday}</td>
				</tr>
			</c:forEach>
			
		</table>
	</form>
 	<c:if test="${sessionScope.loginuser.identity eq '1'}">
		<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" onclick="location.href='<%=ctxPath%>/lesson/addquiz.sam'"/>
		</div>
	</c:if>  
       <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

</div>




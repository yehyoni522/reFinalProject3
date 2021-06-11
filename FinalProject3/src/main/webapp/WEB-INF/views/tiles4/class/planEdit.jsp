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

textarea:focus{     
	outline: none; 
}

textarea{
	width:100%; 
	border: 0; 
	padding:5px;
	resize: none;
}

</style>

<script type="text/javascript">

	$(document).ready(function(){
 
		$("#planAdd").click(function(){
			goPlan();
			
			var frm = document.planform;
			frm.action="<%=ctxPath%>/class/planAdd.sam";
			frm.method="post";
			frm.submit();  
		});
		
		$("#planEdit").click(function(){
			goPlan();
			
			 var frm = document.planform;
			frm.action="<%=ctxPath%>/class/planEdit.sam";
			frm.method="post";
			frm.submit(); 
		});
		
	});
	
	function goPlan(){
		
		var plannoArr = new Array();
		var contentArr = new Array();
		var etcArr = new Array();
		
		for(var i=1; i<=16; i++){
			
			if($("[name=content"+i+"]").val().trim()==""){
				$("[name=content"+i+"]").val(" ");
			}
			if($("[name=etc"+i+"]").val().trim()==""){
				$("[name=etc"+i+"]").val(" ");
			}
			plannoArr.push( $("[name=planno"+i+"]").val() );
			contentArr.push( $("[name=content"+i+"]").val() );
			etcArr.push( $("[name=etc"+i+"]").val() );
			
			
		}
		
		var planno=plannoArr.join();
		var content=contentArr.join();
		var etc=etcArr.join();
		
		var html="<input type='hidden' name='planno' value='"+planno+"'/>";
		html+="<input type='hidden' name='content' value='"+content+"'/>";
		html+="<input type='hidden' name='etc' value='"+etc+"'/>";		
		
		$("div.planform").html(html);
	}
	
	
	
	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;수업계획서
	</div>
	
	<h1 class="headerName">${sessionScope.subject}</h1>
	<br>
	 <c:if test="${empty requestScope.PlanVO}">
		<h3 style="text-align: left; font-weight: bold;">| 수업계획서 등록</h3>
	</c:if>
	<c:if test="${not empty requestScope.PlanVO}">
		<h3 style="text-align: left; font-weight: bold;">| 수업계획서 수정</h3>
	</c:if>	
	

	<hr>

				<table id="table" style="width:100%; border:1.5px #b3b3b3 solid;">
	
					<tr>
						<th style="width:15%;">주차</th>
						<th colspan="3" style="width:57.5%;">수업내용</th>
						<th style="width:27.5%;">교재범위 및 과제물</th>
					</tr>
					<c:if test="${empty requestScope.PlanVO}">
						<c:forEach var="i" begin="1" end="16">
							<tr>
								<th>${i}주차<input type="hidden" name="planno${i}" value="${i}"/></th>
								<td colspan="3"><textarea name="content${i}" rows="2"></textarea></td>
								<td><textarea name="etc${i}" rows="2"></textarea></td>
							</tr>
						</c:forEach>										
					</c:if>
					
					<c:if test="${not empty requestScope.PlanVO}">
					<c:forEach var="PlanVO" items="${requestScope.PlanVO}"  varStatus="status"> 
					<tr>
						<th>${PlanVO.planno}주차<input type="hidden" name="planno${PlanVO.planno}" value="${PlanVO.planno}"/></th>
						<td colspan="3"><textarea name="content${PlanVO.planno}" rows="2" >${PlanVO.content}</textarea></td>
						<td><textarea name="etc${PlanVO.planno}" rows="2">${PlanVO.etc}</textarea></td>
					</tr>	
					</c:forEach>	 
					</c:if>	
				</table>


				
	
	
	
	<div id="btn-board">
		<c:if test="${empty requestScope.PlanVO}">
			<button type="button" class="btn-board" id="planAdd">등록 완료</button>
		</c:if>
		<c:if test="${not empty requestScope.PlanVO}">
			<button type="button" class="btn-board" id="planEdit">수정 완료</button>
		</c:if>				
	</div>


    
    
    <form name="planform">
    	<div class="planform"></div>
    </form>

 
 
 
</div>




    

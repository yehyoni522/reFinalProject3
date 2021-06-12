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

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
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
			var fk_subno = $(this).parent().prev().text();
			var fk_perno = $(this).prev().val();
			
			var frm = document.goViewFrm;
			frm.fk_subno.value = fk_subno;
			frm.fk_perno.value = fk_perno;
			
			frm.method = "get";
	   	    frm.action = "<%= ctxPath%>/lesson/noticeView.sam";
	   	    frm.submit();
		});
		
		$("div#displayList").hide();

	
});	// end of $(document).ready(function(){}
	   	
	
	function gonoticeView(seq){
		
		var frm = document.goViewFrm;
		frm.seq.value=seq;
		frm.fk_subno.value = "${fk_subno}";
		frm.method="get";
		frm.action="<%= ctxPath%>/lesson/noticeView.sam";
		frm.submit();
	} // end of function noticeView(seq){}
	   
	
</script>   

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;공지사항
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 공지사항</h3>
	<br>

	<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
		<tr style="border-bottom: 1px #e6e6e6 solid;">
			<th style="width: 10%;">No.</th>
			<th style="width: 40%;">제목</th>
			<th style="width: 20%;">작성일</th>			
		</tr>			
		<c:forEach var="lenotivo" items="${requestScope.lenotivo}" varStatus="status"> 
			<tr class="list">
				<td>${lenotivo.seq}</td>
				<td style="text-align:center;">			
					<c:if test="${empty lenotivo.fileName}">
						<span class="subject" onclick="gonoticeView('${lenotivo.seq}')">${lenotivo.subject}</span>				
					</c:if>           
					<c:if test="${not empty lenotivo.fileName}">
						<span class="subject" onclick="gonoticeView('${lenotivo.seq}')">${lenotivo.subject}</span>&nbsp;<img src="<%=ctxPath%>/resources/images/disk.gif" /> 					
					</c:if>
				</td>	
				<td>${lenotivo.regDate}</td>
			</tr>
		</c:forEach>	
		
	</table>
	
	<c:if test="${sessionScope.loginuser.identity eq 1 }">
	<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/lesson/noticeAdd.sam?fk_subno=${fk_subno}'"/>
	</div>
    </c:if>
	
    <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

	<form name="goViewFrm">
		<input type="hidden" name="seq" />
		<input type="hidden" name="gobackURL" value="${requestScope.gobackURL}" />
		<input type="hidden" name="fk_subno" value="${fk_subno}"/>
	</form>

</div>




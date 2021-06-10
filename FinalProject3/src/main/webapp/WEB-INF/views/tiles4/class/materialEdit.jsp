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


 .long {width: 80%;}

</style>

<script type="text/javascript">

	$(document).ready(function(){
		

		// 완료버튼
		$("button#btnUpdate").click(function(){
			// 글제목 유효성 검사
			var subjectVal = $("input#subject").val().trim();
			if(subjectVal == "") {
			   alert("글제목을 입력하세요!!");
			   return;
			}
      
			// 폼(form) 을 전송(submit)
			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/class/materialEditEnd.sam";
			frm.submit();
			
		});
	});
	
	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;수업자료
	</div>
	
	<h1 class="headerName">${sessionScope.subject}</h1>
	

	<h3 style="text-align: left; font-weight: bold;">| 수정</h3>
	<hr>


	<form name="editFrm"> 
 
		<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			<tr>
			   <th style="width:200px;">제목</th>
			   <td>
			      <input type="text" name="subject" id="subject" class="long" value="${requestScope.mtrvo.subject}"/>       
			   </td>
			</tr>
			
			<tr>
			   <th>내용</th>
			   <td>
			      <textarea rows="10" cols="100" style="width:80%; height: 612px;" name="content" id="content" >${requestScope.mtrvo.content}</textarea>       
			   </td>
			</tr>
			<tr>
	            <th>파일첨부</th>
	            <td style="padding-left:15px; text-align: left;">
	            <c:if test="${requestScope.mtrvo.fileSize != null}">
		            <a href="<%= ctxPath%>/class/materialDownload.sam?mtrno=${requestScope.mtrvo.mtrno}">
						${requestScope.mtrvo.orgFilename}
					</a>
					<span style="font-size: 9pt;">(<fmt:formatNumber value="${requestScope.mtrvo.fileSize}" pattern="#,###" />bytes)</span>												
				</c:if>
				<c:if test="${requestScope.mtrvo.fileSize == null}">	
				</c:if>
	            </td> 
	          
        	</tr>

	</table>
      
	<div id="btn-board">
		<button type="button" class="btn-board" id="btnUpdate">수정 완료</button>
		<button type="button"class="btn-board" onclick="javascript:history.back()">취소</button>
	</div>
      
	<input type="hidden" name="mtrno" value="${requestScope.mtrvo.mtrno}"/>
   </form>

</div>




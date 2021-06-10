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

th{text-align:center;}   

td{
	text-align:left;
	padding:0 15px;
}

th{
	background-color: #fafafa;
	
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
 
		
		// 과제글 삭제버튼
		$("button#mtrDelete").click(function(){
			var bool = confirm("삭제하시겠습니까?");
					
			if(bool){
				var frm = document.mtrDelFrm;
				
				frm.method = "post";
		   	    frm.action = "<%= ctxPath%>/class/materialDelete.sam";
		   	    frm.submit();
			}
		});
		
		
	});
	

	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;수업자료
	</div>
	
	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 수업자료 상세</h3>

	<hr>
	<c:if test="${empty requestScope.mtrvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
	<c:choose>
		<c:when test="${(sessionScope.loginuser.identity == '0')||(sessionScope.loginuser.identity == '1')||(sessionScope.loginuser.identity == '2')}">
			<c:if test="${not empty requestScope.mtrvo}">
				<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
					<tr>
					   <th style="width:200px;">제목</th>
					   <td style="font-weight: bolder;">
					   		${requestScope.mtrvo.subject}
					   </td>
					</tr>
					<tr>
					   <th>게시일시</th>
					   <td>
					     	${requestScope.mtrvo.regDate}
					   </td>
					</tr>					
					<tr>			  
					   <td colspan="2">
					      	<p style="word-break: break-all; padding:15px;">
					      		${requestScope.mtrvo.content}
					      	</p>
					   </td>
					</tr>
					<%-- === #150. 파일첨부 타입 추가하기 === --%>
					<tr>
						<th>첨부파일</th>
						<td>
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
			</c:if>
	</c:when>
	</c:choose>
	
	<br>
	
	<c:set var="gobackURL2" value='${fn:replace(requestScope.gobackURL, "&", " ")}'/>
	<div style="display:inline-block; ">		
		<div class="moveSubject left" >	
			<c:if test="${requestScope.mtrvo.previousseq != null}">
				<span class="move left"><i class="fas fa-chevron-left"></i>&nbsp;&nbsp;<span onclick="javascript:location.href='materialView.sam?mtrno=${requestScope.mtrvo.previousseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${gobackURL2}'">${requestScope.mtrvo.previoussubject}</span></span>
			</c:if>
			<c:if test="${requestScope.mtrvo.previousseq == null}">
				<span class=" left"><i class="fas fa-chevron-left"></i>&nbsp;&nbsp;...</span>
			</c:if>
		</div>
	
	
		<div class="moveSubject right" >
			<c:if test="${requestScope.mtrvo.nextseq != null}">
				<span class="move right" ><span onclick="javascript:location.href='materialView.sam?mtrno=${requestScope.mtrvo.nextseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${gobackURL2}'">${requestScope.mtrvo.nextsubject}</span>&nbsp;&nbsp;<i class="fas fa-chevron-right"></i></span>
			</c:if>
			<c:if test="${requestScope.mtrvo.nextseq == null}">
				<span class=" right" >...&nbsp;&nbsp;<i class="fas fa-chevron-right"></i></span>
			</c:if>
				
		</div>
	</div>
	
	
	
	<div id="btn-board">
		<c:if test="${(sessionScope.loginuser.identity == '1')||(sessionScope.loginuser.identity == '2')}">
			<button type="button" class="btn-board" id="mtrEdit" onclick="javascript:location.href='<%= ctxPath%>/class/materialEdit.sam?mtrno=${requestScope.mtrvo.mtrno}'">수정</button>
			<button type="button" class="btn-board" id="mtrDelete">삭제</button>
		</c:if>
			<button type="button"class="btn-board" onclick="javascript:location.href='<%= ctxPath%>/class/materialList.sam'">목록</button>		
	</div>
	
    <br><br>
    
    
    
    

 
 	<form name="mtrDelFrm">	
		<input type="hidden" name="mtrno" value="${requestScope.mtrvo.mtrno}"/>
	</form>
 
 
 
</div>




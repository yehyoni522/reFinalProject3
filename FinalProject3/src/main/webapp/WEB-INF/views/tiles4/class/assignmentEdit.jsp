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
		
		$("[name=deleteFile]").change(function(){
	        if($("[name=deleteFile]").is(":checked")){
	        	$("[name=deleteFile]").val("O");
	        	
	        }else{
	        	$("[name=deleteFile]").val("X");	        	
	        }
	    });
		

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
			frm.action = "<%= ctxPath%>/class/assignmentEditEnd.sam";
			frm.submit();
			
		});
	});
	
	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;과제게시판
	</div>
	
	<h1 class="headerName">${sessionScope.subject}</h1>
	

	<h3 style="text-align: left; font-weight: bold;">| 수정</h3>
	<hr>


	<form name="editFrm" enctype="multipart/form-data"> 
 
		<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			<tr>
			   <th style="width:200px;">제목</th>
			   <td>
			      <input type="text" name="subject" id="subject" class="long" value="${requestScope.assignmentVO.subject}"/>       
			   </td>
			</tr>
			<tr>
			   <th>마감일&nbsp;<span style="color:red;">*</span></th>
			   <td>
			     	<span style="font-weight: bold;">기존 설정 과제 마감일 : ${requestScope.assignmentVO.deadline}</span>
			     	<span style="color:red; font-size: 10pt;">* 수정 시 "재설정" 해주셔야 합니다!</span>
					<br><input type="date" name="deadline" >&nbsp;* 마감 시간은 해당 일자의 <span style="color:red;">00:00(자정)시</span>로 입력되므로 확인 후 입력해주세요!
					<br>* 미설정 시 '<span style="color:blue;">비공개</span>'처리 됩니다.
			   </td>
			</tr>
			<tr>
			   <th>내용</th>
			   <td>
			      <textarea rows="10" cols="100" style="width:80%; height: 612px;" name="content" id="content" >${requestScope.assignmentVO.content}</textarea>       
			   </td>
			</tr>
			<%-- === #150. 파일첨부 타입 추가하기 === --%>
			<tr>
	            <th>파일첨부<br><span style="font-size: 9pt;">(파일개수는 1개만 가능)</span></th>
	            <td style="padding-left:15px; text-align: left;">
	            <c:if test="${requestScope.assignmentVO.fileSize != null}">
		            <a href="<%= ctxPath%>/class/assgndownload.sam?assgnno=${requestScope.assignmentVO.assgnno}">
						${requestScope.assignmentVO.orgFilename}
					</a>
					<span style="font-size: 9pt;">(<fmt:formatNumber value="${requestScope.assignmentVO.fileSize}" pattern="#,###" />bytes)</span>	
					<span style="font-size: 10pt; color:red;">&nbsp;삭제&nbsp;</span><input type="checkbox" name="deleteFile" value="X"/>											
					<input type="file" name="attach" />
				</c:if>
				<c:if test="${requestScope.assignmentVO.fileSize == null}">	
					<input type="file" name="attach" />
				</c:if>
	            </td>
        	</tr>

	</table>
      
	<div id="btn-board">
		<button type="button" class="btn-board" id="btnUpdate">수정 완료</button>
		<button type="button"class="btn-board" onclick="javascript:history.back()">취소</button>
	</div>
      
	<input type="hidden" name="assgnno" value="${requestScope.assignmentVO.assgnno}"/>
   </form>

</div>




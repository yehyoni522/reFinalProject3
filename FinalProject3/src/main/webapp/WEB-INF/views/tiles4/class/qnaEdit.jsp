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


 .long {width: 80%;}

</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		 //전역변수
	    var obj = [];
	    
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "content",
	        sSkinURI: "<%= request.getContextPath() %>/resources/smarteditor/SmartEditor2Skin.html",
	        htParams : {
	            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseModeChanger : true,
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
			
			//id가 content인 textarea에 에디터에서 대입
	          obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	        
      
			// 폼(form) 을 전송(submit)
			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/class/qnaEditEnd.sam";
			frm.submit();
			
		});
	});
	
	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;질문게시판
	</div>
	
	<h1 class="headerName">${sessionScope.subject}</h1>
	

	<h3 style="text-align: left; font-weight: bold;">| 수정</h3>
	<hr>


	<form name="editFrm"> 
 
		<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			<tr>
			   <th style="width:200px;">제목</th>
			   <td>
			      <input type="text" name="subject" id="subject" class="long" value="${requestScope.qnavo.subject}"/>       
			   </td>
			</tr>	
			<tr>
			   <th>내용</th>
			   <td>
			      <textarea rows="10" cols="100" style="width:80%; height: 612px;" name="content" id="content" >${requestScope.qnavo.content}</textarea>       
			   </td>
			</tr>

	
	</table>
      
	<div id="btn-board">
		<button type="button" class="btn-board" id="btnUpdate">수정 완료</button>
		<button type="button"class="btn-board" onclick="javascript:history.back()">취소</button>
	</div>
      
	<input type="hidden" name="qnano" value="${requestScope.qnavo.qnano}"/>
	<input type="hidden" name="fk_perno" value="${requestScope.qnavo.fk_perno}"/>
   </form>

</div>




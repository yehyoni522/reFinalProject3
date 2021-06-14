<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

.move{
	display:inline-block; 
	font-weight:bold;
	margin: 15px 30px;
	
}

.move:hover{
	text-decoration: underline;
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


 .long {width: 80%;}

</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		// 과제글 삭제버튼
		$("button#noticeDelete").click(function(){
			var bool = confirm("삭제하시겠습니까?");
					
			if(bool){
				
				var frm = document.DelFrm;				
				frm.method = "post";
		   	    frm.action = "<%= ctxPath%>/lesson/noticeDeleteEnd.sam";
		   	    frm.submit();
			}
		});
		   
		var perno = ${sessionScope.loginuser.perno};

		var loginuser = ${sessionScope.loginuser.identity};

		if(loginuser=='1'){ 		// 교수가 로그인 했을 경우

		}
		
		<%-- === #167. 스마트 에디터 구현 시작 === --%>
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
	     <%-- === 스마트 에디터 구현 끝 === --%>

	      $("button#write").click(function(){
	    	  
	    	  // 글제목 유효성 검사
	          var subjectVal = $("input#subject").val().trim();
	          if(subjectVal == "") {
	             alert("글제목을 입력하세요!!");
	             return;
	          }
	          
	          /* // 글내용 유효성 검사(스마트에디터 사용 안 할시)
	          var contentVal = $("input#content").val().trim();
	          
	          if(contentVal == "") {
	             alert("글내용을 입력하세요!!");
	             return;
	          } */
	         
	          <%-- === 스마트 에디터 구현 시작 === --%>
	        	//id가 content인 textarea에 에디터에서 대입
	          obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	          <%-- === 스마트 에디터 구현 끝 === --%>
	          
	        
	         <%-- === 스마트에디터 구현 시작 === --%>
	         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거
	         var contentval = $("textarea#content").val();
	             
	      	  // === 확인용 ===
	         // alert(contentval); // content에 내용을 아무것도 입력치 않고 쓰기할 경우 알아보는것.
	         // "<p>&nbsp;</p>" 이라고 나온다.
	          
	          
	          // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
	          // 글내용 유효성 검사 
	          if(contentval == "" || contentval == "<p>&nbsp;</p>") {
	             alert("글내용을 입력하세요!!");
	             return;
	          }
	          
	          // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
	          contentval = $("textarea#content").val().replace(/<p><br><\/p>/gi, "<br>"); //<p><br></p> -> <br>로 변환
	      /*    
	                  대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
	          ==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
	                       그리고 뒤의 gi는 다음을 의미합니다.

	             g : 전체 모든 문자열을 변경 global
	             i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
	      */    
	          contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
	          contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
	          contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
	      
	          $("textarea#content").val(contentval);
	      
	       <%-- === 스마트에디터 구현 끝 === --%>
	          
	          // 폼(form) 을 전송(submit)
	          var frm = document.answerFrm;
	          frm.method = "POST"; // 파일 첨부는 무조건 포스트!!!!!
	          frm.action = "<%= ctxPath%>/class/qnaAddEnd.sam";
	          frm.submit();   
	          
	      });
		

		
	}); // end of $(document).ready(function(){}


	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;공지사항
	</div>
	
	<h1 class="headerName">공지사항</h1>
	<br>
	<hr>
	<c:if test="${empty requestScope.lenotivo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>	
	<c:if test="${not empty requestScope.lenotivo}">
		<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			<tr>
			   <th style="width:200px;">제목</th>
			   <td style="font-weight: bolder;">
			   		${requestScope.lenotivo.subject}
			   </td>
			</tr>
			<tr>
				<th>첨부파일</th>
			   <td>
	     	   		<c:if test="${requestScope.lenotivo.orgFilename != null}"> 
					<a href="<%=ctxPath%>/lesson/download.sam?seq=${requestScope.lenotivo.seq}&fk_subno=${lenotivo.fk_subno}"> ${requestScope.lenotivo.orgFilename} 
			    	<span>(<fmt:formatNumber value="${requestScope.lenotivo.fileSize}" pattern="#,###"/>&nbsp;byte)</span></a>
					</c:if>
			   </td>
			</tr>	
			<tr>
			   <th>게시일</th>
			   <td>
			     	${requestScope.lenotivo.regDate}
			   </td>
			</tr>					
			<tr>			  
			   <td colspan="2">
			      	<p style="word-break: break-all; padding:15px;">
			      		${requestScope.lenotivo.content}
			      	</p>
			   </td>
			</tr>			
	</table>
	</c:if>
	
	<div id="btn-board">
	<c:if test="${ (sessionScope.loginuser.perno eq requestScope.lenotivo.fk_perno)}">
			<button type="button" class="btn-board" id="noticeEdit" onclick="javascript:location.href='<%= ctxPath%>/lesson/noticeEdit.sam?seq=${requestScope.lenotivo.seq}'">수정</button>
			<button type="button" class="btn-board" id="noticeDelete" >삭제</button>
	</c:if>
			<button type="button"class="btn-board" onclick="javascript:location.href='<%= ctxPath%>/lesson/notice.sam?fk_subno=${lenotivo.fk_subno}'">목록</button>		
	</div>   
 
 	<form name="DelFrm">	
		<input type="hidden" name="seq" value="${requestScope.lenotivo.seq}"/>
		<input type="hidden" name="fk_subno" value="${requestScope.lenotivo.fk_subno}"/>
	</form> 
 
 
 
</div>




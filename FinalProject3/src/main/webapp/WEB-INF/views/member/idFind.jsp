<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
	#div_name {
		width: 80%;
		height: 10%;
		margin-left: 10%;
		position: relative;
		margin-top: 30px;
		font-weight: bold;
		margin-bottom: 20px;
	}
	
	#div_email {
		width: 80%;
		height: 10%;
		margin-top: 10px;
		margin-bottom: 8%;
		margin-left: 10%;
		position: relative;
		font-weight: bold;
	}
	
	#div_findResult {
		width: 70%;
		height: 15%;
		margin-top: 60px;
		margin-bottom: 10px;
		margin-left: 8%;		
		position: relative;
	}
	
	#div_btnFind {
		width: 80%;
		height: 14%;
		margin-left: 9%;
		position: relative;
	}
	button#btnFind {
		background-color: #666;
		color: white;
		padding: 14px 20px;
		margin: 12px 0;
		border: none;
		cursor: pointer;
		height:60px;
		width:70%;
	}
	button#btnEnd{
		background-color: #f2f2f2;
		color: black;
		border: none;
		cursor: pointer;
		height:60px;
		width:70%;
	}
	input{
		
		height: 40px;
		width: 50%;
	}

</style>       
<script type="text/javascript">

	$(document).ready(function(){
		
		var check = "${check}";
		
		if(check==1){
			alert("입력하신 정보는 존재하지 않습니다.");
			return;
		}
			


	$("button#btnFind").click(function(){
		// 성명 및 e메일에 대한 유효성 검사(정규표현식)는 생략하겠습니다.
		 	var name = $("input#name").val().trim();
			var email = $("input#email").val().trim();
			
			if(name == "") {
				alert("성명를 입력하세요!!");
				$("input#name").val("");
				$("input#name").focus();
				return;  // goLogin() 함수 종료
			}
			
			if(email == "") {
				alert("이메일을 입력하세요!!");
				$("input#email").val("");
				$("input#email").focus();
				return;  // goLogin() 함수 종료
			}
		var frm = document.idFindFrm;
		frm.action = "<%= ctxPath%>/idFindEnd.sam";
		frm.method = "post";
		frm.submit();
		
	});
	
});// end of $(document).ready(function(){})----------------------------------

</script>

<form name="idFindFrm">

	<h1 align="center" style="margin-top: 100px;"> 아이디 찾기</h1>
	<c:if test="${check != 0}">
	   <div id="div_name" align="center" style="margin-top: 50px;">
	     	 성명:<input style="margin-left:17px;" type="text" name="name" id="name" size="15" placeholder="홍길동" autocomplete="off" required />
	   </div>
   
	   <div id="div_email" align="center">
	      Email:<input style="margin-left:10px;"type="text" name="email" id="email" size="15" placeholder="abc@def.com" autocomplete="off" required />
	   </div>
	   
	   <div id="div_btnFind" align="center">
	   		<button type="button" class="btn btn-success" id="btnFind">찾기</button>
	   </div>
	    <div id="div_btnFind" align="center" style="display:inline-block; background: gary;">
	   		<button id="btnEnd" type="button"  onClick='window.close()'>닫기</button>
	   </div>
   </c:if>
   <c:if test="${check == 0}">
	   <div id="div_findResult" align="center">
	   	  ID :<span style="font-size: 16pt;padding-left:50px; font-weight: bold;">${requestScope.perno}</span> 
	   </div>
	   <div id="div_btnFind" align="center">
   		<button type="button" id="btnEnd" onClick='window.close()'>닫기</button>
   </div>
   </c:if>
   
   
</form>
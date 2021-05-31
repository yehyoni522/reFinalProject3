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
		margin-left: 14%;		
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
	button#btnConfirmCode{
		background-color: #666;
		color: white;
		border: none;
		cursor: pointer;
		height:60px;
		width:70%;
	}
	input{
		
		height: 40px;
		width: 50%;
	}
	button#btnUpdate{
		background-color: #666;
		color: white;
		border: none;
		cursor: pointer;
		height:60px;
		width:50%;
	}
</style>       
<script type="text/javascript">

	$(document).ready(function(){
		
		var check = "${check}";
		
		if(check == 1){
			alert("입력하신 정보는 존재하지 않습니다.");
			return;
		}
			


	$("button#btnFind").click(function(){
		// 성명 및 e메일에 대한 유효성 검사(정규표현식)는 생략하겠습니다.
		 	var userid = $("input#userid").val().trim();
		 	var name = $("input#name").val().trim();
			var email = $("input#email").val().trim();
			
			if(userid == "") {
				alert("아이디를 입력하세요!!");
				$("input#userid").val("");
				$("input#userid").focus();
				return;  // goLogin() 함수 종료
			}
			if(name== "") {
				alert("성명을 입력하세요!!");
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
		var frm = document.pwdFindFrm;
		frm.action = "<%= ctxPath%>/personRegisterGo.sam";
		frm.method = "post";
		frm.submit();
		
	});
	
	// == 인증하기 ==
	$("button#btnConfirmCode").click(function(){
		
		var frm = document.verifyCertificationFrm;
		
		frm.userCertificationCode.value = $("input#input_confirmCode").val();
		
		frm.action = "<%= ctxPath%>/verifyCertification.sam";
		frm.method = "POST";
		frm.submit();
	});	
	
	//비밀번호 변경
	$("input#pwd2").keyup(function(event){
		if(event.keyCode == 13) {
			gopwdUpdate(); 
		}	
	});
	$("button#btnUpdate").click(function(){
		
		var pwd = $("input#pwd").val();
		var pwd2 = $("input#pwd2").val();
		
		// var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
		// 또는
		var regExp = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
		// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		var bool = regExp.test(pwd);
		
		if(!bool) {
			alert("암호는 8글자 이상 15글자 이하에 영문자,숫자,특수기호가 혼합되어야만 합니다.");
			$("input#pwd").val("");
			$("input#pwd2").val("");
			return;   // 종료
		}
		else if(bool && pwd != pwd2) {
			alert("암호가 일치하지 않습니다.");
			$("input#pwd").val("");
			$("input#pwd2").val("");
			return;   // 종료
		}
		else {
			gopwdUpdate();
		}
	});
		
	function gopwdUpdate() {
		
		var frm = document.pwdUpdateEndFrm;
	 	frm.action = "<%= ctxPath%>/pwdUpdateEnd.sam";
	 	frm.method = "POST";
	 	frm.submit();
	}// end of function
	
<%-- 	if("${requestScope.n == 1}") {
		alert("비밀번호 변경 되었습니다.");
		location.href="<%= ctxPath%>/login.sam";
	} --%>
	
});// end of $(document).ready(function(){})----------------------------------

</script>

<form name="pwdFindFrm">

	<h1 align="center" style="margin-top: 40px;"> 회원등록</h1>
	<c:if test="${check != 0 && in !=1}">
	   <div id="div_name" align="center" style="margin-top: 50px;">
	   
	     	  	 아이디:<input style="margin-left:6px;" type="text" name="userid" id="userid" size="15" placeholder="학번/교번" autocomplete="off" required />
	   </div>
   		
   		<div id="div_name" align="center">
	   
	     	  	 이름:<input style="margin-left:18px;" type="text" name="name" id="name" size="15" placeholder="성명" autocomplete="off" required />
	   </div>
   	
	   <div id="div_email" align="center">
	      Email:<input style="margin-left:10px;"type="text" name="email" id="email" size="15" placeholder="abc@def.com" autocomplete="off" required />
	   </div>
	   
	   <div id="div_btnFind" align="center">
	   		<button type="button" class="btn btn-success" id="btnFind">인증하기</button>
	   </div>
	    <div id="div_btnFind" align="center" style="display:inline-block; background: gary;">
	   		<button id="btnEnd" type="button"  onClick='window.close()'>닫기</button>
	   </div>
   </c:if>

   <c:if test="${check == 0}">
   	   <c:if test="${requestScope.isUserExist2 == false}">  
   	   	  <span style="color: red;">사용자 정보가 없습니다.</span>
   	   </c:if>
   	  <c:if test="${requestScope.isUserExist2 == true && requestScope.sendMailSuccess == true}">  
   	   <br> 
   	   	  <span style="margin-left: 50px; font-weight:bold; font-size: 13pt;">입력하신 Email로 인증코드가 발송되었습니다.</span><br>
   	   	  <br>
   	      <span style="margin-left: 115px; font-size: 13pt;">인증코드를 입력해주세요.</span>
   	      <input type="text" style="margin-top:10px; margin-left: 100px;" name="input_confirmCode" id="input_confirmCode" required />
   	      <br><br>
   	       <div id="div_btnFind" align="center">
	   		<button type="button" id="btnConfirmCode">인증하기</button>
	   </div>
	    <div id="div_btnFind" align="center" style="display:inline-block; background: gary;">
	   		<button id="btnEnd" type="button"  onClick='window.close()'>닫기</button>
	   </div>
   	   </c:if>
   	   
   	   <c:if test="${requestScope.isUserExist2 == true && requestScope.sendMailSuccess == false}">  
   	   	  <span style="color: red;">메일발송이 실패했습니다.</span>
   	   </c:if>
   	      

   </c:if>
  
   
</form>
<c:if test="${in == 1}">
<form name="pwdUpdateEndFrm">

	<h2 align="center" style="height:50px;">회원암호 등록</h2>
	 		<div class="idSearchForm">
	 		
				<div id="div_name" align="center" >
	     	  	<span style="margin-right: 120px;">새 암호</span><br>
	     	  	<input type="password" class="findinput" placeholder="새 암호를 입력해주세요" id="pwd" name="pwd" maxlength="20" placeholder="PASSWORD" required>
	   			</div>	
	   			
				 <div id="div_email" align="center">
	    			<span style="margin-right: 80px;">새 암호 확인</span><br>
	    			<input type="password" class="findinput" placeholder="새 암호를 다시 한번 입력해주세요 " id="pwd2" maxlength="20" placeholder="PASSWORD" required>
	   			</div>		
						
		
					<input type="hidden" name="userid" value="${requestScope.userid}" />

			   <div id="div_btnUpdate" align="center">
			   	  <button type="button" class="pwdSearchSubmit" id="btnUpdate">암호변경하기</button>
			   </div> 

		   
	  		</div>
		</form>
</c:if> 
<form name="verifyCertificationFrm">
    <input type="hidden" name="userid" value="${requestScope.userid}"/> 
	<input type="hidden" name="userCertificationCode" />
</form>

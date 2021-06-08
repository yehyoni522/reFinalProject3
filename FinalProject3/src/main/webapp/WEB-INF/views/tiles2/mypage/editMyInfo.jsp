<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>
<!DOCTYPE html>

<meta charset="UTF-8">
<title>회원정보수정</title>
<style type="text/css">
body{
   font-family: 'Noto Sans KR', sans-serif;
}
.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	margin-bottom: 30px;
 
}
.contents{
	width: 80%;
	margin-left: 100px;
}
div#sub{
	background-color: #E5E5E5;
	padding-top: 15px;
	padding-bottom: 10px;
}
span.star {
		color: red;
		font-weight: bold;
		font-size: 13pt;
	}
table#tblMemberRegister {		
		width: 98%;          
		margin: 10px;			
	}  
	
	th#th {				
		text-align: center;		
		font-size: 30pt; 
		font-family: 'Papyrus', Fantasy; 

	}	
	
	td {
		line-height: 30px;
		padding-top: 8px;
		padding-bottom: 8px;
		
	}
	input.text{
		border : solid 0px red;
		border-bottom: solid 1.5px gray;
	}
	
	span.star {
		color: red;
		font-weight: bold;
		font-size: 13pt;
	}
	
	span.error {
		color: red;
		margin-left: 10px;
		font-size: 9.5pt;
		font-weight: bold;
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	

	$("span.error").hide();	
			
	$("input#pwd").blur(function(){
		
		var regExp = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
		// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		var bool = regExp.test($(this).val());
					
		if(!bool){
			// 암호가 정규표현식에 위배된 경우				
			$(this).parent().find(".error").show();
			$(this).focus();
		}
		else{
			// 암호가 정규표현식에 맞는 경우
			$(this).parent().find(".error").hide();
		}
	}); // 아이디가 pwd 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$("input#pwdcheck").blur(function(){
		
		var pwd = $("input#pwd").val();
		var pwdcheck = $(this).val(); 
		
		if(pwd != pwdcheck){
			// 암호와 암호확인값이 틀린 경우				
			$(this).parent().find(".error").show();
			$("input#pwd").focus();				
		}
		else{
			// 암호와 암호확인값이 같은 경우
			$(this).parent().find(".error").hide();
		}
	}); // 아이디가 pwdcheck 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
	
	$("input#email").blur(function(){
		
		var regExp = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
		var bool = regExp.test($(this).val());
		
		if(!bool){
			// 이메일이 정규표현식에 위배된 경우
			$(this).parent().find(".error").show();
			$(this).focus();				
		}
		else{
			// 이메일이 정규표현식에 맞는 경우								
			$(this).parent().find(".error").hide();				
		}
	}); // 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	
});

	
	//Function Declaration
	function goEdit(){		
		
		var flagBool = false;
		
		$(".requiredInfo").each(function(index, item){
			
			var data = $(item).val().trim();
			if(data == ""){
				flagBool = true;
				return false;
			}
			
			if(flagBool){
				alert("*표시된 필수입력란은 모두 입력하셔야 합니다.");
				return;
			}
			else{
				var frm = document.editFrm;
				frm.method = "POST";
				frm.action = "<%= ctxPath%>/mypage/editEnd.sam";
				frm.submit();
			}
		});
		
	}// end of function goEdit(){}--------------------

</script>
<div class="contents" >
	
		
		<div class="admsubtitle" >
			<span >회원정보수정</span>
		</div>
		<hr style="border: solid 1px #E5E5E5;">
		<div id="sub">
		<ul>
			<li>
			회원정보를 변경하는 화면입니다.
			</li>
			<li>
			<span style="color:red; font-weight: bold;">*</span> 는 필수입력 항목입니다.
			</li>
		</ul>
		
		</div>
		
<form name="editFrm">
	
	<table id="tblMemberRegister">
		
		<tbody>
			
			<tr>
				<td style="width: 20%; font-weight: bold;">성명&nbsp;</td>
				<td style="width: 80%; text-align: left;">
					<input type="text" name="name" style=" font-weight: bold;" id="name" class="text" value="${sessionScope.loginuser.name}" class="requiredInfo" readonly/>						
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">학번&nbsp;</td>
				<td style="width: 80%; text-align: left;">
					<input type="text" name="perno" style=" font-weight: bold;" id="name" class="text"  value="${sessionScope.loginuser.perno}" class="requiredInfo" readonly/>						
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">단과대학&nbsp;</td>
				<td style="width: 80%; text-align: left;">
					<input type="text" name="school"  style=" font-weight: bold;" id="name" class="text"  value="${requestScope.nameCol}" class="requiredInfo" readonly/>						
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">학과&nbsp;</td>
				<td style="width: 80%; text-align: left;">
					<input type="text" name="major"  style=" font-weight: bold;" id="name" class="text"  value="${requestScope.nameMaj}" class="requiredInfo" readonly/>						
				</td>
			</tr>
			<tr>
				<td style="font-weight: bold;">비밀번호&nbsp;<span class="star">*</span></td>
				<td style="text-align: left;"><input type="password" name="pwd" id="pwd" class="requiredInfo text" />&nbsp;
					<span class="info">영문 대소문자/숫자/특수문자, 8자~15자</span>
					<span class="error">비밀번호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
				</td>			
			</tr>
			<tr>
				<td style="font-weight: bold;">비밀번호확인&nbsp;<span class="star">*</span></td>
				<td style="text-align: left;"><input type="password" id="pwdcheck" class="requiredInfo text" />
					<span class="error">비밀번호가 일치하지 않습니다.</span>
				</td>				
			</tr>
			<tr>
				<td style="font-weight: bold;">이메일&nbsp;<span class="star">*</span></td>
				<td style="text-align: left;"><input type="text" name="email" id="email" value="${sessionScope.loginuser.email}" class="requiredInfo text" placeholder="ex) kim123@naver.com"/>
				</td>
				
			</tr>
			<tr>
				<td style="font-weight: bold;">연락처<span class="star">*</span></td>
				<td style="text-align: left;">
					<input class="text"  type="text" id="hp1" name="hp1" size="6" maxlength="3" value="010" readonly/>&nbsp;-&nbsp;
					<input class="text"  type="text" id="hp2" name="hp2" size="6" maxlength="4" value="${fn:substring(sessionScope.loginuser.mobile, 4, 7)}"/>&nbsp;-&nbsp;
					<input class="text"  type="text" id="hp3" name="hp3" size="6" maxlength="4" value="${fn:substring(sessionScope.loginuser.mobile, 9, 13)}"/>								
				</td>
			</tr>	
			<tr>
				<td style="width: 20%; font-weight: bold;">주소<span class="star">*</span></td>
				<td style="width: 80%; text-align: left;">
					<input type="text" name="address" id="name" class="text" placeholder="주소" style="width: 350px;" value="${sessionScope.loginuser.address}" class="requiredInfo"/>		
					<span class="error">주소를 입력하세요</span><br>				
				</td>
			</tr>		
			
		</tbody>
	</table>
	<div align="center" style="margin-top:50px;" ><button  type="submit" class="btn btn-info" onclick="goEdit()">수정</button>&nbsp;&nbsp;<button type="reset" class="btn btn-secondary" >취소</button></div>		
</form>	
</div>
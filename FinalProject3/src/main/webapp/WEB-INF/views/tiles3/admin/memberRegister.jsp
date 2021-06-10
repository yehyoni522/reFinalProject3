<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<%
    String ctxPath = request.getContextPath();
%>
<style type="text/css">
    
div#adminhome {
	min-height: 700px;
	padding-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
}

div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	min-height: 600px;
}
    
div#admincontent {
	float: left;
	padding: 0 50px 0 50px;
	width: 80%;
}    

.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 
}
div#adimnCalendar{
	margin-left:20px;
	width: 90%;
	min-height: 40%;
}
div#calsub{
	margin:30px 0px 10px 0px;
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	}

h3 {
 font-weight: bold;
}
table#tblMemberRegister {
	width: 70%;
	border: hidden;          
	margin: 10px;
}  
  
table#tblMemberRegister #th {
	height: 40px;
	text-align: center;
	font-size: 14pt;
}
  
table#tblMemberRegister td {
	line-height: 10px;
	padding-top: 8px;
	padding-bottom: 8px;
}

/* registerMemberForm.jsp css 끝 */


/* registerSuccess.jsp css 시작 */
 
div#registerSuccessContainer{
	border: solid 0px blue;
	margin: 0 auto;
	margin-left: 480px;
	margin-bottom: 50px;
	padding-top: 50px;
	width: 40%;
}

div#registerSuccessContainer > div{
	border: solid 0px red;
	margin-top: 20px;
	text-align: center;
}

table#registerSuccessTable {
	border: solid 0px blue;
	margin-top: 10px;
}

table#registerSuccessTable tr{
	line-height: 150%;	
}

table#registerSuccessTable img{
	border-radius: 50%
}

table#registerSuccessTable td{
	border: solid 0px blue;
	font-size: 10pt;
	text-align: center;	
}

td#tdImg{
	padding-left: 15px;
	width:30%;
}

td.column{
	width: 28%; 
}

div#registerSuccessContainer button{
	width: 80px;
	height: 30px;
	background-color: #666;
	color: #fff;
}

</style>

<script type="text/javascript">
$(document).ready(function(){
$("span.error").hide();



$("input#email").blur(function(){
	
	//	var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	//  또는	
		var regExp = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
		// 이메일 정규표현식 객체 생성	
	
		var bool = regExp.test($(this).val());
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
			$("table#tblMemberRegister :input").prop("disabled",true);
			$(this).prop("disabled",false);
		
			$(this).parent().find(".error").show();
			$(this).focus();
		}
		else {
			// 이메일이 정규표현식에 맞는 경우
			$("table#tblMemberRegister :input").prop("disabled",false);
			$(this).parent().find(".error").hide();
		}
		
	}); // 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.

});

function goRegister(){
	
	var frm = document.registerFrm;
	frm.action = "<%=ctxPath%>/admin/memberRegisterend.sam";
	frm.method = "post";
	frm.submit();
	
}

</script>
<div id="adminhome">
	
	<div id="admincontent">
	
		<div class="admsubtitle">
			<span >회원 등록 페이지</span>
		</div>
		<div class="row" id="divRegisterFrm">
   <div class="col-md-12" align="center">
   <form name="registerFrm">
   
   <table id="tblMemberRegister">
      <thead>
         <tr>
         <th colspan="2" id="th"> <h2>회원등록</h2> <hr></th>
      </tr>
      </thead>
      <tbody>
        <tr>
         <td style="width: 20%; font-weight: bold;">구분&nbsp;<span class="star">*</span></td>
           <td><select name="identity" id="identity">
           		<option value = "0">학생 </option>
	           <option value = "1">교수 </option>
	           <option value = "2">관리자 </option>
           		</select>  	
          </td>
      </tr>
          <tr>
         <td style="width: 20%; font-weight: bold;">학과&nbsp;<span class="star">*</span></td>
           <td><select name="fk_majseq" id="fk_majseq">
           		<option value = "100">컴퓨터공학과 </option>
	           <option value = "200">정보통신공학과 </option>
	           <option value = "300">전기공학과</option>
	           	<option value = "400">전자공학과 </option>
	           <option value = "600">수학교육과</option>
	           <option value = "700">영어교육과</option>
           		</select>  	
          </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">회원번호&nbsp;<span class="star">*</span></td>
           <td><input type="text" name="perno" id="perno" class="requiredInfo" />
           <input type ="radio" id="gender" name="gender" value ="1" />남 
           <input type ="radio" id="gender" name="gender" value ="2" />여
           </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">성명&nbsp;<span class="star">*</span></td>
         <td style="width: 80%; text-align: left;">
             <input type="text" name="name" id="name" class="requiredInfo" /> 
         </td>
      </tr>
        <tr>
         <td style="width: 20%; font-weight: bold;">생년원일&nbsp;<span class="star">*</span></td>
         <td style="width: 80%; text-align: left;">
             <input type="text" name="birthday" id="birthday" class="requiredInfo" /> 
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">이메일&nbsp;<span class="star">*</span></td>
         <td style="width: 80%; text-align: left;"><input type="text" name="email" id="email" class="requiredInfo" placeholder="abc@def.com" /> 
             <span class="error">이메일 형식에 맞지 않습니다.</span>
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">연락처</td>
         <td style="width: 80%; text-align: left;">
             <input type="text" id="hp1" name="hp1" size="6" maxlength="3" value="010" readonly />&nbsp;-&nbsp;
             <input type="text" id="hp2" name="hp2" size="6" maxlength="4" />&nbsp;-&nbsp;
             <input type="text" id="hp3" name="hp3" size="6" maxlength="4" />
             <span class="error">휴대폰 형식이 아닙니다.</span>
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">주소</td>
         <td style="text-align: left;">
            <input type="text" id="memberaddress" name="address" size="40" placeholder="주소" />
            <span class="error">주소를 입력하세요</span>
         </td>
      </tr>
          <tr style="text-align: center;">
         <td colspan="2" style="line-height: 30px;">
            <button type="button" id="btnRegister" style="background-color: rgb(94, 94, 94); color: white; border:none; width: 100px; height: 40px;  border-radius: 5px;" onClick="goRegister();">등록</button> 
           <button type="button" id="btnRegister" style="background-color: rgb(224, 224, 224); border:none; width: 100px; height: 40px; margin-left: 5%; border-radius: 5px;" onClick="goBack();">취소</button> 
         </td>
      </tr>
   
      </tbody>
   </table>
   </form>
   </div>
</div>
	<div>
	
	</div>
</div>
	
	
		

</div>
</div>
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
var idcheck=false;
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
	$("input#perno").blur(function(){
		
			var regExp =/^[0-9]*$/;
			// 숫자만입력
		
			var bool = regExp.test($(this).val());
			
			if(!bool) {
				// 암호가 정규표현식에 위배된 경우
				$("table#tblMemberRegister :input").prop("disabled",true);
				$(this).prop("disabled",false);
				$("#checkMessage").css('color', 'red'); 
				$("#checkMessage").html("숫자만 입력가능합니다 ");
				$(this).val("");
			}
			else {
				// 암호가 정규표현식에 맞는 경우
				$("table#tblMemberRegister :input").prop("disabled",false);
				$(this).parent().find(".error").hide();
				$("#checkMessage").css('color', 'black'); 
			}
			
		}); // 아이디가 pwd 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.	
	
	
});


function goRegister(){
		if(idcheck==false){
			alert('회원번호를 확인해주세요')
            return;
		}

    	if($('#fk_majseq').val()==''){
    		 alert('학과를 선택 해주세요.')
             return;
    	}
    	    if ($('#perno').val() == '') {
                alert('회원번호를 입력 해주세요.')
                return;
          }
    		var radioCheckedLength = $("input:radio[name=gender]:checked").length;
    		
    		if(radioCheckedLength == 0) {
    			alert("성별을 선택하셔야 합니다.");
    			return; // 종료
    		}
    		
    	    if ($('#name').val() == '') {
                alert('이름을 입력 해주세요.')
                return;
          }
    	    if ($('#birthday').val() == '') {
                alert('생년월일을 입력 해주세요.')
                return;
          }
    	     if ($('#email').val() == '') {
                 alert('이메일을 입력 해주세요.')
                 return;
           }
    	     if ($('#hp2').val()== '' ||$('#hp3').val()  == '') {
                 alert('전화번호를 입력 해주세요.')
                 return;
           }
    	     if ($('#memberaddress').val() == '') {
                 alert('주소을 입력 해주세요.')
                 return;
           }
     
   
   
			var frm = document.registerFrm;
			frm.action = "<%=ctxPath%>/admin/memberRegisterend.sam";
			frm.method = "post";
			frm.submit();
     
}


function idCheckFunction(){
	var perno = $("#perno").val();
	$.ajax({ 
		type : 'POST', 
		url :"<%= ctxPath%>/admin/memberidCheck.sam",
		dataType:"json",
		data : {perno : perno},
		async: false,
		success : function(json){
			
			if(json.n == 1){ 
				$("#checkMessage").html("사용중인 회원번호입니다. 다른 회원번호를 입력해 주세요"); 
				$("#checkMessage").css('color', 'red'); 
				idcheck=false;
			} 
			else { 
				$("#checkMessage").html("사용할 수 있는 아이디입니다.");
				$("#checkMessage").css('color', 'black'); 
				idcheck=true;
			} 
				

			} 
		}) }


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
         <td style="width: 20%; font-weight: bold;">구분&nbsp;</td>
           <td><select name="identity" id="identity">
           		<option value = "0">학생 </option>
	           <option value = "1">교수 </option>
	           <option value = "2">관리자 </option>
           		</select>  	
          </td>
      </tr>
          <tr>
         <td style="width: 20%; font-weight: bold;">학과&nbsp;</td>
           <td><select name="fk_majseq" id="fk_majseq">
           <option value= "">학과를 선택해주세요 </option>
           	<optgroup label="공과대학">
	           		<option value = "100">컴퓨터공학과 </option>
		           <option value = "200">정보통신공학과 </option>
		           <option value = "300">전기공학과</option>
	           		<option value = "400">전자공학과 </option>
	       </optgroup>
	           
	       	<optgroup label="사범대학">
	       	 <option value = "500">국어교육과</option>
	           <option value = "600">수학교육과</option>
	           <option value = "700">영어교육과</option>
	           </optgroup>
           </select>  	
          </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">회원번호&nbsp;</td>
           <td><input type="text" name="perno" id="perno" onkeyup="idCheckFunction();" class="requiredInfo" />
           <span id="checkMessage" style="font-weight:bold"></span>
           </td>
      </tr>
       <tr>
         <td style="width: 20%; font-weight: bold;">성별&nbsp;</td>
           <td>
          
           <input type ="radio" id="gender" name="gender" value ="1" />남 &nbsp;
           <input type ="radio" id="gender" name="gender" value ="2" />여
           </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">성명</td>
         <td style="width: 80%; text-align: left;">
             <input type="text" name="name" id="name" class="requiredInfo" /> 
         </td>
      </tr>
        <tr>
         <td style="width: 20%; font-weight: bold;">생년월일&nbsp;</td>
         <td style="width: 80%; text-align: left;">
             <input type="text" name="birthday" id="birthday" class="requiredInfo" placeholder="1990-01-01" /> 
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">이메일&nbsp;</td>
         <td style="width: 80%; text-align: left;"><input type="text" name="email" id="email" class="requiredInfo" placeholder="abc@def.com" /> 
             <span class="error">이메일 형식에 맞지 않습니다.</span>
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">연락처</td>
         <td style="width: 80%; text-align: left;">
             <input class="requiredInfo" type="text" id="hp1" name="hp1" size="6" maxlength="3" value="010" readonly />&nbsp;-&nbsp;
             <input class="requiredInfo" type="text" id="hp2" name="hp2" size="6" maxlength="4" />&nbsp;-&nbsp;
             <input class="requiredInfo" type="text" id="hp3" name="hp3" size="6" maxlength="4" />
             <span class="error">휴대폰 형식이 아닙니다.</span>
         </td>
      </tr>
      <tr>
         <td style="width: 20%; font-weight: bold;">주소</td>
         <td style="text-align: left;">
            <input class="requiredInfo" type="text" id="memberaddress" name="address" size="40" placeholder="주소" />
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
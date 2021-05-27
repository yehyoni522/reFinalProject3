<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String ctxPath = request.getContextPath();
%>
<style>
	#div_name {
		width: 80%;
		height: 10%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_email {
		width: 80%;
		height: 10%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_findResult {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 8%;		
		position: relative;
	}
	
	#div_btnFind {
		width: 80%;
		height: 20%;
		margin-bottom: 5%;
		margin-left: 9%;
		position: relative;
	}
	input{
		height: 30px;
	}
</style>       
<script type="text/javascript">

$(document).ready(function(){
	
	var method = "${requestScope.method}";
//  console.log("확인용 method => " + method);  // "GET" 또는 "POST" 
	
    if(method == "GET") {
    	$("div#div_findResult").hide();
    }
    else if(method == "POST"){
    	$("input#name").val("${requestScope.name}");
    	$("input#email").val("${requestScope.email}");
  
    }


	$("button#btnFind").click(function(){
		// 성명 및 e메일에 대한 유효성 검사(정규표현식)는 생략하겠습니다.
		
		var frm = document.idFindFrm;
		frm.action = "<%= ctxPath%>/idFindEnd.sam";
		frm.method = "post";
		frm.submit();
		
	});
	
});// end of $(document).ready(function(){})----------------------------------

</script>

<form name="idFindFrm">
	<br>
	<h1 align="center"> 아이디 찾기</h1>
	<br>
   <div id="div_name" align="center">
     	 성명 <input type="text" name="name" id="name" size="15" placeholder="홍길동" autocomplete="off" required />
   </div>
   
   <div id="div_email" align="center">
      email <input type="text" name="email" id="email" size="15" placeholder="abc@def.com" autocomplete="off" required />
   </div>
   
   <div id="div_findResult" align="center">
   	  ID : <span style="color: red; font-size: 16pt; font-weight: bold;">${requestScope.userid}</span> 
   </div>
   
   <div id="div_btnFind" align="center">
   		<button type="button" class="btn btn-success" id="btnFind">찾기</button>
   </div>
   
</form>
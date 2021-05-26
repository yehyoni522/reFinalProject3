<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String ctxPath = request.getContextPath();
%>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
div.loginView{
	text-align:center;
	border: solid 0px red;
	margin: 0 auto;
	margin-left: 400px;
	padding-top: 40px;
	height: 500px;
	width: 600px;
}

button.loginSubmit {
	background-color: #666;
	color: white;
	padding: 14px 20px;
	margin: 12px 0;
	border: none;
	cursor: pointer;
	height:60px;
	width:80%;
}

input.form-control{
	height:50px;
	width: 80%;
	margin-top: 10px;
	margin-left:60px;
}

button.memberRegister{
	background-color: #666;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width:80%;
}

button.idSearch{
	background-color: #f2f2f2;
	color: black;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width:40%;

}

button.pwdSearch{
	background-color: #f2f2f2;
	color: black;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width:40%;
}

input.idsearchInput{
	height:50px;
	width:50%;
	margin:10px 20px;
}

div.idSearchForm{
	background-color: #f2f2f2;
	text-align:center;
	border: solid 0px red; 
	margin: 0 auto;
	margin-left: 400px;
	margin-top: 20px;
	padding-top: 50px;
	height: 500px;
	width: 600px;
}

button.idSearchSubmit{
	background-color: #666;
	margin:20px 90px;
	color: white;
	border: none;
	cursor: pointer;
	height:50px;
	width:30%;
	font-size: 18pt;

}
button.pwdSearchSubmit{
	background-color: #666;
	border:solid 3px red;
	color: white;
	border: none;
	cursor: pointer;
	height:50px;
	width:20%;
	font-size: 13pt;
}
button.emailCon{
	background-color: #666;
	margin:20px 90px;
	color: white;
	border: none;
	cursor: pointer;
	height:50px;
	width:30%;
	font-size: 18pt;
	
}
ul {
    border: solid 0px red;
 	margin-top:100px;
    list-style-type: none;
    padding: 0 auto;
}

label.title{
	display :inline-block;
	width:180px;
	font-size:14pt;

}

li{
   	width:80%;
}
   
input.findinput{
	height: 35px;
	width: 50%;
	margin-top:10px;
	margin-bottom: 10px;
}

</style>
<script type="text/javascript">

	$(document).ready(function(){

		$("button#btnSubmit").click(function(){
			goLogin(); // 로그인 시도한다.
		});
		
		$("input#loginPwd").keyup(function(event){
			if(event.keyCode == 13) {  // 암호입력란에 엔터를 했을 경우 
				goLogin(); // 로그인 시도한다.
			}	
		});
	});// end of $(document).ready()--------------------------------------

	
	// === 로그인 처리 함수 === //
	function goLogin() {
		 //alert("로그인 시도함");
		 var loginUserid = $("input#loginUserid").val().trim();
		var loginPwd = $("input#loginPwd").val().trim();
		
		if(loginUserid == "") {
			alert("아이디를 입력하세요!!");
			$("input#loginUserid").val("");
			$("input#loginUserid").focus();
			return;  // goLogin() 함수 종료
		}
		
		if(loginPwd == "") {
			alert("암호를 입력하세요!!");
			$("input#loginPwd").val("");
			$("input#loginPwd").focus();
			return;  // goLogin() 함수 종료
		}
		
		var frm = document.loginFrm;
		frm.action = "<%= ctxPath%>/loginEnd.sam";
	    frm.method = "post";
		frm.submit();
		
	}// end of function goLogin()-----------------------------------------
	

</script>
<div id="contents"> 
	<div id="loginContents">
		<div class="loginView"> 
			<h1 style="font-weight: bold;"> 로그인</h1>
			<br>
		    <form name="loginFrm">
			    <h3 style="text-align: center;"></h3>
			    <div class="form-group">
					<input type="text" id="loginUserid" class="form-control" placeholder="Username" name="userid" maxlength="20">
					<br>
			    	<input type="password" id="loginPwd"  class="form-control" placeholder="●●●●" name="pwd" maxlength="20">
			    	
			    	
				</div>
				<input type="checkbox" id="saveid" name="saveid"/><label style="font-weight: normal;" for="saveid">&nbsp;아이디저장</label>
				<span style ="margin-left:240px;"><a>등록하기</a><a>/&nbsp;비밀번호찾기</a></span>
				<br>	
			    <button type="button" id="btnSubmit" class="loginSubmit">로그인</button>

			</form>
		</div>
	</div>
</div>

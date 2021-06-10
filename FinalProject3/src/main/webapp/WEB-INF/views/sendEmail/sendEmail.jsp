<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
    //     /MyMVC 
%>   
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
	button {
		margin-top:15px;
	}
</style>
<script type="text/javascript">
	$(function() {
		//console.log("xmcn : " + opener.document.getElementById('parentEmail').value);
		$('input#emailAddress').val(opener.document.getElementById('parentEmail').value);
	});
	
	function goSendEmail() {
		var Frm = document.emailFrm;
		Frm.action = "<%=ctxPath%>/admin/sendEmailEnd.sam";
		Frm.method="get";
		Frm.submit();
	}
</script>
<h2 align="center">이메일 전송</h2>
 <div class = "domain" >
  	<h3> 내용 </h3> 
 </div> 
 <div class = "domain" align="center"> 
 	<form name="emailFrm">
 		<textarea class = "memo" id="memo" name = "memo" rows = "8" cols = "60" placeholder="내용을 입력하세요."></textarea>
 		<input type="hidden" id="emailAddress" name="emailAddress">
 		<button class="btn btn-secondary" onclick="goSendEmail();">전송</button> 
 		<button type="reset" class="btn btn-secondary">취소</button> 
 	</form>
	
 </div>
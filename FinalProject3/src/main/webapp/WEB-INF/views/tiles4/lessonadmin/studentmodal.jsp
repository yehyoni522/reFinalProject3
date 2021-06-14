<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">


	span.signmodal {
		border: 1px red solid;
		font-size: 17pt;
	}

</style>

</head>

<style>

</style>

<script type="text/javascript">

	function signgo(){
		
		var frm = document.inputsignFrm;
		
		if(frm.randomsign.value == ""){
			alert("번호를 입력해주세요!!");
			frm.randomsign.focus();
			return;
		}	
		
		$.ajax({
			url:"<%=ctxPath%>/lesson/studentsign.sam",
			type:"post",
			data:{"subject":"${sessionScope.subject}",
				  "fk_perno":"${requestScope.fk_perno}",
				  "randomsign":frm.randomsign.value},
			dataType:"json",
		   	success:function(json) {
		   		
		   		if(json.n == 1){
		   			alert("출석에 성공하였습니다.");
		   			$("span.remove").remove();
		   			
		   			html = "이미 출석을 완료하셨습니다.";
		   			
		   			$("span.signmodal").html(html);
		   			return;
		   		}
		   		else if(json.n == 2){
		   			alert("이미 출석을 완료하셨습니다.");
		   			
					$("span.remove").remove();   			
		   			html = "이미 출석을 완료하셨습니다.";		   			
		   			$("span.signmodal").html(html);
		   			
		   			return;
		   		}
		   		else {
		   			alert("번호를 확인해주세요");
		   			frm.randomsign.focus();
		   			return;
		   		}
		   		
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});	
		
	} // end of function signgo(){}

</script>

<body>
	
<div align="center" style="padding-top: 55px;">
	<form name="inputsignFrm">
	<span class="signmodal" style="border: 0px solid;"><input name="randomsign" type="text" /></span>
	<span class="remove"><button type="button" onclick="signgo()" >입력</button></span>
	<span></span>
	</form>
</div>   

</body>
</html>
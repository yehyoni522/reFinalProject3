<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
	//	/Semiproject
%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">


.green_window {
	display: inline-block;
	width: 200px;
	border: 3px solid #2ECC71;
	border-radius: 20px;
}
.input_text {
	width: calc( 100% - 40px );
	margin: 6px 7px;
	border: 0;
	font-weight: bold;
	font-size: 12px;
	outline: none;
	border-radius: 20px;
}
.sch_smit {
	width: 40px; height: 30px;
	margin-top: 5px; border: 0;
	vertical-align: top;
	background: #2ECC71;
	color: white;
	border-radius: 20px;
	cursor: pointer;
	font-size: 12px;
}
.sch_smit:hover {
	background: #27AF61;
}
textarea {
	border: 3px solid #2ECC71;
}

</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		
	});
	
	// === 학번/교수번호 찾기 === //
	function goSearch() {
		
		var input_text = $("input.input_text").val().trim();
		if(input_text == "") {
			
			$("div#error").html("학번/교수번호를 입력하세요!!");
		
			return; // 종료
		}
		
		<%-- var form_data = $("form[name=addWriteFrm]").serialize();
		
		$.ajax({
			url:"<%= ctxPath%>/addComment.action",
			data:form_data,
			type:"post",
			dataType:"json",
			success:function(json){ // 정상이라면 n값은 1 오류발생시 n값은 0
				
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		}); --%>
		
	}// end of function goAddWrite(){}--------------------------

</script>

</head>
<body>

<form name="idFindFrm" style="margin-top: 20px;">
   <div id="div_name" align="center">
      <span class='green_window'>
			<input type='text' class='input_text' />
		</span>
			<button type='submit' class='sch_smit' onclick="goSearch()">검색</button>
   </div>
   <div id="error"></div>
</form>

<table id="table2" style="margin-top: 2%; margin-bottom: 3%;">
      <thead>
      <tr>
        
      </tr>
      </thead>
      <tbody id="commentDisplay"></tbody>
   </table>

</body>
</html>
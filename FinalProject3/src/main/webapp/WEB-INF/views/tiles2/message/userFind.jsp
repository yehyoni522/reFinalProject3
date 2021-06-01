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

table.type03 {
  font-size: 13px;
  border-collapse: collapse;
  text-align: center;
  line-height: 1.3;
  border-top: 1px solid #ccc;
  border-left: 3px solid #369;
  margin : 20px 10px;
}
table.type03 th {
  width: 147px;
  padding: 7px;
  font-weight: bold;
  vertical-align: top;
  color: #153d73;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid #ccc;

}
table.type03 td {
  width: 349px;
  padding: 7px;
  vertical-align: top;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}

.input_newColor {
    background-color : #ffff99;
}



</style>

<script type="text/javascript">


	$(document).ready(function(){
		$("input.input_text").val("${requestScope.receiver}");
		goSearch();
		
		$("input:checkbox[name=checked]").each(function(index, item){ 
			
			var bChecked = $(item).prop("checked");
			
			if (bChecked) {	// 체크표시가 안되어있는 상품일 경우 반복문 종료
				$(item).addClass("input_newColor");
				console.log("체크");
			}
			else{
				$(item).removeClass("input_newColor");
				console.log("체크품");
			}
		});
		
		$("input:checkbox[name=checked]").click(function(){
			$(item).addClass("input_newColor");
		});

	});

	
	// === 학번/교수번호 찾기 === //
	function goSearch() {
		$("div#error").hide();
		
		var input_text = $("input.input_text").val().trim();
		if(input_text == "") {
			$("div#error").html("학번/교수번호를 입력하세요!!");
			$("div#error").show();
			return; // 종료
		}
		
		var input =  $("input[name=text]").val();
		
		// 사람 번호 검색
		$.ajax({
			url:"<%= ctxPath%>/message/searchPerson.sam",
			data:{"perno": input},
			type:"post",
			dataType:"json",
			success:function(json){ /* // 정상이라면 n값은 1 오류발생시 n값은 0 */
				
				var html = "";
				
				if(json.name != null) {
					
						html += "<tr>";
						html += "<td style='text-align: center;'><input name='checked' type='checkbox' value='"+json.perno+"'></td>";
						html += "<td>"+ json.identity +"</td>";
						html += "<td>"+ json.nameMaj +"</td>";
						html += "<td>"+ json.name +"</td>";
						html += "</tr>";
					
				}
				else {
					html += "<tr>";
					html += "<td colspan='4''>정확한 주소를 입력해주세요</td>";
					html += "</tr>";
				}
				
				$("tbody#commentDisplay").html(html);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		}); 
		
	}// end of function goAddWrite(){}--------------------------

</script>

</head>
<body>

<form name="idFindFrm" style="margin-top: 20px;">
   <div id="div_name" align="center">
      <span class='green_window'>
			<input type='text' name="text" class='input_text'/>
			
		</span>
			<button type='submit' class='sch_smit' onclick="goSearch()">검색</button>
   </div>
   <div id="error"></div>
</form>

	<table class="type03">
      <thead>
     <tr>
		    <th style="text-align: center; width: 10%;">선택</th>
			<th style="text-align: center;">구분</th>
			<th style="text-align: center;">학과</th>
			<th style="text-align: center;">이름</th>
		</tr>
      </thead>
      <tbody id="commentDisplay"></tbody>
   </table>
   
   
   

</body>
</html>
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

.sch_smit_1 {
	width: 60px; height: 30px;
	margin-top: 5px; border: 0;
	vertical-align: top;
	background: #205890;
	color: white;
	border-radius: 20px;
	cursor: pointer;
	font-size: 12px;
}
.sch_smit_1:hover {
	background: #173F67;
}
div#add{
 border: solid 1px #ccc; width: 530px; height: 50px;
 border-left: 3px solid #369;
 font-size: 13px;
 margin-bottom: 50px;
 overflow: auto;
}
div.addBox{
 margin-left:8pt;
}
input#delete{
	width: 15px; 
	height: 15px;
	font-size: 10px;
	padding-right: 0;
	padding-left: 0;
}

</style>

<script type="text/javascript">


	$(document).ready(function(){
		$("input.input_text").val("${requestScope.receiver}");
		goSearch();
		
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
					

					 var text = '"'+json.name+'"';
					 
					 
						html += "<tr>";
						html += "<td><input id='choice' onclick='add("+json.perno+","+text+")' style='font-size:10px;' type='button' value='추가'></td>";
						html += "<td>"+ json.perno +"</td>";
						html += "<td>"+ json.identity +"</td>";
						html += "<td>"+ json.nameMaj +"</td>";
						html += "<td>"+ json.name +"</td>";
						html += "</tr>";
					
				}
				else {
					html += "<tr>";
					html += "<td colspan='5''>\'"+input+"\'에 대한 정보가 없습니다. 정확한 주소를 입력해주세요</td>";
					html += "</tr>";
				}
				
				$("tbody#commentDisplay").html(html);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
			
		}); 
		
	}// end of function goAddWrite(){}--------------------------
	
	// 받는사람 박스에 출력하기
	var str ="";
	var perno_es ="";
	var name_es ="";
	var withNames ="";
	function add(perno, name){
		
		str+="<tr id="+perno+">";
		str+="<td>"+name+"&nbsp;</td>";
		str+="<td>("+perno+")&nbsp;&nbsp;</td>";
		str+="<td><input id='delete' type='button' onclick='del("+perno+")' value='X'/></td>";
		str+="</tr>";
		
		perno_es += perno+",";
		name_es += name+",";
		withNames += name+"("+perno+")&nbsp;";		
		
		$("div#add").html(str); //아래 출력
		$("input[name=withNames]").val(withNames); // 이름과같이있는 문자열 출력(부모창에)
		$("input[name=perno_es]").val(perno_es); // 나중에 보내기 위해 번호만 따로 모아둠
		$("input[name=name_es]").val(name_es); // 나중에 보내기 위해 이름만 따로 모아둠

		$("input.input_text").val(""); // 비워놓는다.
	}
	
	// 삭제하기
	function del(perno){
		$("tr#"+perno+"").remove();
		var perno_es = $("input[name=perno_es]").val();
		
		
		perno_es = perno_es.replace(perno,"");
		perno_es = perno_es.replace(",","");
		
		$("input[name=perno_es]").val(perno_es);
		
		
	}

</script>

</head>
<body>

<form name="idFindFrm" style="margin-top: 20px;">
   <div id="div_name" align="center">
      <span class='green_window'>
			<input type='text' name="text" class='input_text'/>
		</span>
			<button type='button' class='sch_smit' onclick="goSearch()">검색</button>
			
   </div>
   <div id="error" style="color: red;font-size: 12px; margin-top:5px; margin-left:180px;"></div>
   
</form>

	<table class="type03">
      <thead>
     <tr>
		    <th style="text-align: center; width: 10%;">선택</th>
		    <th style="text-align: center;">학번/교번</th>
			<th style="text-align: center;">구분</th>
			<th style="text-align: center;">학과</th>
			<th style="text-align: center;">이름</th>
		</tr>
      </thead>
      <tbody id="commentDisplay"></tbody>
   </table>
   

	<div class="addBox" id="receiverID" style="font-size: 13px; font-weight: bold; color: #205890;">받는사람</div>
    <div class="addBox" id="add"> </div>
    <input type="hidden" name="withNames" id="someID"/>
	<input type="hidden" name="perno_es" id="someID_2" />
	<input type="hidden" name="name_es" id="someID_3" />
   
   	
  
  
   
   

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

table.quiz {
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  border-top: 1px solid #ccc;
  margin : 20px 10px;
}
table.quiz th {
  width: 200px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
}

table.quiz td {
  width: 450px;
  padding: 10px;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
}

.nextline{
  background-color: #e6e6e6;	
}

button {
	border-radius: 10%;
	border: none;
	color: white;
	font-weight: bold;
	margin: 0 3px 0 3px;
}

</style>

<script type="text/javascript">

	var cnt = 0;
	var i = 0;

	function addquiz(){			
		
		cnt ++;
		
		for(i=0; i<cnt; i++){
		
		html = "";	
		
		html += "<tr class='remove"+i+"'><td class='nextline' colspan='2'></td></tr>"
		
		html += "<tr class='remove"+i+"'><th scope='row'>"+(i+1)+"번 문제</th><td><input type='hidden' name='qzno"+i+"' value='"+(i+1)+"'>정답<input type='text' class='confirm' name='quizanswer"+i+"' style='width: 50px; margin-left:10px;' /></input><input type='hidden' name='cnt' value="+cnt+" /></td></tr>";
		html += "<tr class='remove"+i+"'><th scope='row'>문제 내용</th><td><input type='text' class='confirm' name='qzcontent"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><th scope='row'>1.</th><td><input type='text' class='confirm' name='answerfirst"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><th scope='row'>2.</th><td><input type='text' class='confirm' name='answersecond"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><th scope='row'>3.</th><td><input type='text' class='confirm' name='answerthird"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><th scope='row'>4.</th><td><input type='text' class='confirm' name='answerfourth"+i+"'></input></td></tr>";
		
		}
		
		$(html).appendTo("table");	
		
		// console.log(cnt);
		// console.log(i);
		
	}; // end of function addquiz(){}
	
	function removequiz() {
		
		console.log(cnt);
		console.log(i);
		
		$("tr.remove"+(i-1)+"").remove();
		
		if (cnt != 0){
			cnt = cnt -1;
			i = i - 1;
		}
		
	} // end of function removequiz() {}
	
 	function quizaddEnd() {
 		
 		var quiznameVal = $("input#quizname").val().trim();
 		var confirmVal = $("input.confirm").val().trim();
 		
 		if(quiznameVal != "" && confirmVal != ""){		
	 		var frm = document.addquizFrm; 		
	 		frm.method = "post";
	 		frm.action = "<%= ctxPath%>/lesson/quizaddEnd.sam";
	        frm.submit();
 		}
		else{
			alert("시험명과 문제는 공백이 올 수 없습니다.");
			return;
		}
        
 	} // end of function quizaddEnd() {}

</script>

<div align="center">
	
	<div align="left" style="font-weight: bold; font-size: 10pt; margin-left: 200px; cursor: pointer; text-decoration: underline;" onclick="location.href='<%=ctxPath%>/lesson/quizlist.sam'">작성 취소</div>
	<div style="font-weight: bold; font-size: 14pt;">쪽지시험 문제 작성</div>
	
	<form name="addquizFrm">

		<table class="quiz">
		
		  <tr>
		    <th scope="row">시험명</th>
			<td> 
				<%-- 시험명 입력 --%>
				<input type="text" id="quizname" name="quizname" style="width: 250px;"></input>						
			</td>
		  </tr>
		  
		</table>	
	</form>
	
	<div align="center">
		<%-- 문제 추가 시 cnt 값이 1 올라가고  --%>
		<button type="button" onclick="addquiz()" style=" background-color: #00e600;">문제 추가</button>
		<button type="button" onclick="removequiz()" style=" background-color: #ff471a;">문제 삭제</button>
		<button type="button" onclick="quizaddEnd()" style=" background-color: #bfbfbf;">작성 완료</button>
	</div>	
	
	
</div> 
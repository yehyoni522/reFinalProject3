<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>   

<style type="text/css">

.quizform {
	width: 70%;
	min-height:200px;
	border:1px solid black;
	padding-top: 50px;
}

.quiztitle {
	font-weight: bold;
} 

table {
	width: 70%;
	
	
}

table, tr, td {
	border-collapse : collapse;	
	margin: auto;
	height: 40px;
} 	
.quiztitle {
	width: 80px;
	/* // background-color: #d8d8da; */
	
}

input {
	width: 100%;
	height: 40px;
	
}
tr {
	
}

.no {	
	border: none;
}


</style>

<script type="text/javascript">

	var cnt = 0;
	var i = 0;
	
	function addquiz(){			
			
		cnt ++;
		
		for(i=0; i<cnt; i++){
			
		$("tr.addquiz").remove();
		$("tr.addquiz").removeClass("addquiz");		
		
		html = "";	
		
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>"+(i+1)+"번 문제</td><td><input type='hidden' name='qzno"+i+"' value='"+i+"'>정답<input type='text' name='quizanswer"+i+"' style='width: 50px; margin-left:10px;' /></input><input type='hidden' name='cnt' value="+cnt+" /></td></tr>";
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>문제 내용</td><td><input type='text' name='qzcontent"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>1.</td><td><input type='text' name='answerfirst"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>2.</td><td><input type='text' name='answersecond"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>3.</td><td><input type='text' name='answerthird"+i+"'></input></td></tr>";
		html += "<tr class='remove"+i+"'><td class='quizaddform"+i+" quiztitle'>4.</td><td><input type='text' name='answerfourth"+i+"'></input></td></tr>";
		
		html += "<tr class='addquiz'><td class='11' colspan='2' align='center'><span><button type='button' onclick='addquiz()''>문제 추가하기</button></span><span><button type='button' onclick='removequiz()''>문제 삭제하기</button></span></td></tr>";
		
		
		
		}
		
		$(html).appendTo("table");	
		
		// console.log(cnt);
		// console.log(i);
		
	};
	
 	function removequiz() {
		
 		
 		
		console.log(cnt);
		console.log(i);
		
		$("tr.remove"+(i-1)+"").remove();
		
		if (cnt != 0){
			cnt = cnt -1;
			i = i - 1;
		}
		
	} 
 	
 	function quizaddEnd() {
 		
 		var frm = document.addquizFrm;
 		
 		frm.method = "post";
 		frm.action = "<%= ctxPath%>/lesson/quizaddEnd.sam";
        frm.submit();
 		
 	}
	
</script>

<div style="margin: 0 50px 30px 50px; font-family: 'Noto Sans KR', sans-serif;" align="center">

	<div align="left" style="color: #0099cc; font-weight: bold; text-decoration: underline;">시험 작성 취소</div>

	<h2 style="margin-bottom: 20px;" >쪽지시험 문제 작성</h2>

	<div align="left" class="quizform">
		
		<form name="addquizFrm">
		
			<table class="quizstart">
			
				<tr>
					<td class="quiztitle">시험명</td>
					<td> 
						<input type="text" name="quizname"></input>						
					</td>
				</tr>
				<tr class="no">
					<td class="no" colspan="2" style="width: 30px; height: 30px; "></td>
				</tr>
				<tr class="addquiz">
					<td class="11" colspan="2" align="center">
						<span><button type="button" onclick="addquiz()">문제 추가하기</button></span>
						<span><button type="button" onclick="removequiz()">문제 삭제하기</button></span>					
					</td>
				</tr>		
						
			</table>
				<div align="center"><button type="button" onclick="quizaddEnd()">작성완료</button></div>			
		</form>
		
	</div>


</div>


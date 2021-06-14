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
 
  margin : 20px 10px;
}
table.quiz th {
  width: 200px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border : 1px solid #ccc;
  width: 20%;
}

table.quiz td {
  width: 450px;
  padding: 10px;
  vertical-align: top;
  border : 1px solid #ccc;
 
}

.nextnextline {
	border: none;
}


</style>

<script type="text/javascript">

	$(function(){
		console.log(${requestScope.check} + " ${requestScope.check} == 1 ");
	}); 

	function goSubmit(){
		
		if(${requestScope.fk_quizno} == 2){
		
		var frm = document.studentansFrm;
		
		alert("시험지 제출에 성공하였습니다..");
		
		frm.method = "post";
		frm.action = "<%= ctxPath%>/lesson/quizViewEnd.sam";		
		frm.submit();
		
		}
		else {
			alert("이미 완료한 시험입니다.");
			return;
			
		}
		
	} // end of function goSubmit(){}

</script>

<div align="center" style="margin-top: 200px; text-align: center; width: 60%; margin-left: 450px; min-height: 500px;"> 
	<%-- 시험명으로 검색해온 과목테이블의 과목명 --%>
	<h3 style="margin-bottom: 20px;"> ${subjectvo.subname}</h3>
	<h4 style="text-align: left; font-weight: bold; margin-left: 15px;">| 쪽지시험</h4>
	<form name="studentansFrm">

		<table class="quiz">
		
		  <tr>
		    <th scope="row">시험명</th>
		    <%-- 쪽지시험 페이지에서 가져온 과목명 --%>
		    <td>${requestScope.quizname}</td>
		  </tr>
		  
		  <%-- 시험명으로 검색해온 쪽지시험_문제 테이블의 행 --%>
		  <c:forEach var="questionvo" items="${requestScope.questionList}" varStatus="status">
			  <tr>
			    <td class="nextline" colspan="2"></td>
			  </tr>
			  <tr>
			    <th scope="row">문제 <span class="qzno">${questionvo.qzno}</span></th>
			    <td>${questionvo.qzcontent}</td>
			  </tr>
			  <tr>
			    <td colspan="2">
			    	 <input type="hidden" name="seqno${status.index}" value="${questionvo.qzno}" />
			    	 <input type="hidden" name="qzno${status.index}" value="${questionvo.qzno}" />
			    	 <input type="radio" name="questionno${status.index}" value="1">&nbsp;&nbsp;${questionvo.answerfirst} <br>
			    	 <input type="radio" name="questionno${status.index}" value="2">&nbsp;&nbsp;${questionvo.answersecond}<br>
			    	 <input type="radio" name="questionno${status.index}" value="3">&nbsp;&nbsp;${questionvo.answerthird}<br>
			    	 <input type="radio" name="questionno${status.index}" value="4">&nbsp;&nbsp;${questionvo.answerfourth}<br>
			    </td>
			  </tr>
		  </c:forEach>
		  <tr><td style="border: none;">
		  	<input type="hidden" name="quizname" value="${requestScope.quizname}">
		  	<input type="hidden" name="perno" value="${sessionScope.loginuser.perno}">
		  </td></tr>
		</table>	
	</form>
	
	<c:if test="${sessionScope.loginuser.identity eq '0'}">
		<div align="center"><button type="button" onclick="goSubmit()">제출하기</button></div>
	</c:if>
	<c:if test="${sessionScope.loginuser.identity eq '1'}">
		<div align="center"><button type="button" onclick="javascript:location.href='<%= ctxPath%>/lesson/quizlist.sam">뒤로가기</button></div>
	</c:if>
	
</div> 
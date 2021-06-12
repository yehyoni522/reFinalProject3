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

	function goSubmit(){
		
		var frm = document.studentansFrm;
		
		frm.method = "post";
		frm.action = "<%= ctxPath%>/lesson/quizViewEnd.sam";
		frm.submit();
		
		
	} // end of function goSubmit(){}

</script>

<div align="center" style="margin-top: 100px; text-align: center; width: 60%; margin-left: 450px;"> 
	<%-- 시험명으로 검색해온 과목테이블의 과목명 --%>
	<h4>${subjectvo.subname}</h4>
	
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
		  <tr><td style="border: none;"><input type="hidden" name="quizname" value="${requestScope.quizname}"></td></tr>
		</table>	
	</form>
	
	<div align="center"><button type="button" onclick="goSubmit()">제출하기</button></div>
	
</div> 
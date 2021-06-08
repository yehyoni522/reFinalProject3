<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>    

<style type="text/css">

table.studentList {
  border-collapse: separate;
  border-spacing: 1px;
  text-align: center;
  line-height: 1.5;
  margin: 20px 10px;
  width: 60%;
  font-size: 12pt;
}
table.studentList th {
  width: 155px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  color: #fff;
  background: #a8a4a4;
  text-align: center;
}
table.studentList td {
  width: 155px;
  padding: 10px;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
  background: #eee;
}
button {
  border-radius: 10%;
  border: none;
  color: white;
  font-weight: bold;
  margin: 0 3px 0 3px;
  font-size: 18pt;
}


</style>

<script>

function outputsign(){
	
	
	
} // end of function outputsign(){}


</script>

<div align="center">

	<div align="center"><h2>출석</h2></div>
	
	<div align="center">
		<button type="button" onclick="outputsign()" style="background-color: #00e600;">출석시작</button>
		<button type="button" onclick="outputsign()" style="background-color: #bfbfbf;">출석현황</button>

	</div>
	<div align="right" style="width: 60%; margin-top: 10px;">		
		<%-- <select>
			<c:forEach>
				<option>1주차&nbsp;&nbsp;</option>
			</c:forEach>
		</select> --%>
	</div>

	<table class="studentList">
	
	  <thead>
	  <tr>
	    <th scope="cols">No</th>
	    <th scope="cols">학번</th>
	    <th scope="cols">이름</th>
	    <th scope="cols">출석시간</th>
	  </tr>
	  </thead>
	  
	  <tbody>
		<%--   <c:forEach>
			  <tr>
			    <td>status.count 값</th>
			    <td>tbl_person 테이블의 perno</th>
			    <td>tbl_person 테이블의 name</th>
			    <td>tbl_inputatdc 테이블의 status</th>
			  </tr>
		  </c:forEach>
 --%>
	  </tbody>
	</table>

</div>
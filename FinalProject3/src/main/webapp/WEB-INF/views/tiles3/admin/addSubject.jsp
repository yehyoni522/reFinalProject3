<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<style type="text/css">
    
div#adminhome {
	min-height: 700px;
	padding-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
}

div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	min-height: 600px;
}
    
div#admincontent {
	float: left;
	padding: 0 50px 0 50px;
	width: 80%;
}    

.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 
}

h3 {
 font-weight: bold;
}
</style>
<div id="adminhome">
	
	<div id="admincontent">
	
	<div class="admsubtitle">
		<span >수업 관리</span>
	</div>
	
	<div>
		<form>
			<table>
				<tr>
					<th>수업명</th>
					<td><input type="text"></td>
				</tr>
				<tr>
					<th>교수번호</th>
					<td><input type="text"></td>
				</tr>
				<tr>
					<th>학과번호</th>
					<td><input type="text"></td>
				</tr>
				<tr>
					<th>학점</th>
					<td><input type="number"></td>
				</tr>
				<tr>
					<th>학기</th>
					<td><input type="number"></td>
				</tr>
				<tr>
					<th>날짜</th>
					<td>
					<select>
						<option>요일</option>
						<option>월</option>
						<option>화</option>
						<option>수</option>
						<option>목</option>
						<option>금</option>
					</select>
					<input type="time">&nbsp;&nbsp;~&nbsp;&nbsp;<input type="time"></td>
				</tr>
			</table>
			
			<button>등록</button>
			<button>취소</button>
		</form>
		
	</div>
	
	
	
		

</div>
</div>
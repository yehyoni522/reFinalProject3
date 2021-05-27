<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
    //     /MyMVC 
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

div.admintitlesearch {
	float: right;
	margin-bottom: 30px;
}


.adminsbopt {
	float: left;
	margin: 0 15px 0 15px;
}

div.admtitleoptions {
	border-bottom: 3px solid #b0b0b5; 
	border-top: 3px solid #b0b0b5; 
	height: 50px;	
	padding: 10px 10px 0 10px;
	
}

.adminsearchoption {
	margin: 0 5px; 0 5px;
}

.admsubtsp {
	font-size: 12pt;
	margin: 0 20px 0 280px;
	font-weight:bold;
}

table {
	clear: both;
	width: 100%;
}

th {
	padding-right: 15px;
	height: 27px;
}

td {
	padding-right: 15px;
	margin-bottom: 50px;
}

tr {
	height: 27px;
	border-bottom: 1px solid #b0b0b5;
}

.admthtd {
	width: 30px;
}

.admth {
	margin-right: 70px;
}

.admthtdall {
	width: 100px;
}

.thall {
	font-weight: bold;
	font-size: 11pt;
}

</style>

<script>
	$(function(){
		
		
		
	});

</script>

<div id="adminhome">
	
	<div id="adminside" >
		<span style="font-family: 'Noto Sans KR', sans-serif;">관리자 페이지 홈</span>
		<br>
		<br>
		<span>회원/교수진 관리</span>
		<ul>
			<li>학생 관리</li>
			<li>교수진 관리</li>
			<li>활둥중지 회원</li>
		</ul>
		<br>
		<span>게시글 관리</span>
		<ul>
			<li>게시글 관리</li>
			<li>댓글 관리</li>
			<li>스팸글 관리</li>
		</ul>
		<br>
		<span>열람실 관리</span>		
		<br>
		<span>수업 관리</span>	
		<ul>
			<li>수업 개설</li>
		</ul> 	
	</div>
	
	<div id="admincontent">
	
		<div class="admsubtitle">
			<span >학생관리</span>
		</div>
		
		<div class="admintitlesearch">
			<select style="width: 100px;">
				<option>이름</option>
				<option>아이디</option>
				<option>이메일</option>
			</select>
			<input type="text" id="searchWord" name="searchWord"/>    
     		<input type="text" style="display: none;"/>
     		<button type="button" onclick="goSearch();">검색</button>  
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="admtitleoptions">
			<div class="adminsbopt">
				<select class="adminsearchoption">
					<option>단과대학</option>
				</select>
				<select class="adminsearchoption">
					<option>학과</option>
				</select>
				<select class="adminsearchoption">
					<option>학번</option>
				</select>
			</div>
			
			<span class="admsubtsp">선택된 학생</span>
			<button>게시판 활동 중지</button>
			<button>열람실 이용 중지</button>
			<button>이메일 보내기</button>
			<button>메세지 보내기</button>
		</div>
				
		<div style="clear: both;">
			<table>
				<thead>
					<tr style="font-weight: bold;">
						<th class="admthtd"><input type="checkbox"/></th>
						<th class="thall" style="margin-right: 70px; width: 30px;">No</th>
						<th class="admthtdall thall">단과대학</th>
						<th class="admthtdall thall">학과</th>
						<th class="admthtdall thall">학번</th>
						<th class="admthtdall thall">성함</th>
						<th class="admthtdall thall">아이디</th>
						<th class="admthtdall thall">휴대전화</th>
						<th class="admthtdall thall">이메일</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach begin="0" step="1" end="15"  >			
						<tr>
							<td class="admthtd"><input type="checkbox"/></td>
							<td style="margin-right: 70px; width: 30px;">d</td>
							<td class="admthtdall">단과대학</td>
							<td class="admthtdall">d</td>
							<td class="admthtdall">d</td>
							<td class="admthtdall">d</td>
							<td class="admthtdall">d</td>
							<td class="admthtdall">d</td>
							<td class="admthtdall">d</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">페이지 이동 링크</div>
		</div>
						
	</div>

</div>
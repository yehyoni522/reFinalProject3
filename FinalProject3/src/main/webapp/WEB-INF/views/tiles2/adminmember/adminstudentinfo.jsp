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

div.backadminstudent {

	clear: both;
 	font-size: 10pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	margin-bottom: 50px;

}

h4 {
	
	border-left:3px solid #b0b0b5;
 	font-weight:bold;	
 	padding-left: 5px;
	margin-top: 30px;
}

table {
	border-collapse: collapse;
	border:1px solid red;
	margin-bottom: 10px;
	width: 100%;
}

td, tr, th {
	border:1px solid #b0b0b5;
	width: 150px;
}


</style>

<script>

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
	
		<div class="backadminstudent">
			<span>학생 관리 목록 돌아가기</span>
		</div>
		
		<h4> 학생 상세 정보 </h4>
		<div>
			<table>
				<tbody>
					<tr>
						<td rowspan="3"></td>
						<td>학번</td>
						<td></td>
						<td>성명</td>
						<td></td>
						<td>생년월일</td>
						<td></td>
					</tr>
					<tr>
						<td>소속 및 학년</td>
						<td colspan="3"></td>						
						<td>아이디</td>
						<td></td>
					</tr>
					<tr>
						<td>연락처</td>
						<td></td>
						<td>이메일</td>
						<td></td>
						<td>주소</td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<button>게시판 활동 중지</button>
			<button>열람실 이용 중지</button>
			<button>이메일 보내기</button>
			<button>메세지 보내기</button>
		</div>
		
		<h4> 수강 내역 </h4>
		<div>
			<select>
				<option>수강학기</option>
			</select>
			<table>
				<thead>
					<tr>
						<th>수강학기</th>
						<th>과목번호</th>
						<th>교과목명</th>
						<th>교수</th>
						<th>총점</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>3-1</td>
						<td>0524</td>
						<td>경영학원론</td>
						<td>이수영</td>
						<td>50/100</td>
					</tr>
				</tbody>
			</table>
			<div align="center">페이지 이동 링크</div>
		</div>
		
		<h4> 작성한 게시글 / 댓글 </h4>
		<div>
			<table>
				<thead>
					<tr>
						<th>No</th>
						<th>게시판 명</th>
						<th>제목</th>
						<th>작성일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>자유게시판 </td>
						<td>오늘 수업 듣기 싫다 </td>
						<td>2021-05-01 </td>
					</tr>
				</tbody>
			</table>	
			<div align="center">페이지 이동 링크</div>
		</div>
		
		<h4> 열람실 이용 내역 </h4>
		<div>
			<span>누적 보증금액 : 500원</span>
			<table>
				<thead>
					<tr>
						<th>No</th>
						<th>날짜</th>
						<th>열람실</th>
						<th>좌석</th>
						<th>이용시간</th>
						<th>보증금 반환</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>2021-04-02</td>
						<td>제 1 열람실</td>
						<td>D25</td>
						<td>11:00~13:00</td>
						<td>O</td>
					</tr>
				</tbody>
			</table>
			<div align="center">페이지 이동 링크</div>			
		</div>
						
	</div>

</div>
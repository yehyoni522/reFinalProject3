<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<title>마이페이지</title>
<style type="text/css">
body{
   font-family: 'Noto Sans KR', sans-serif;
}
tr#score > th{
	background-color: #ccc;
	text-align: center;
	
}
.scoretable{
	text-align: center;
}

.msgHead{
	margin-left: 100px;
	margin-right: 100px;
	font-size: 20px;
	display: inline-block;
}
span#title{
	font-size:20px;
	color: #3498DB;
	margin-bottom: 30px;
	font-weight: bold;
}

a#msgNew{
	 border-radius: 50%;
	 background-color: red;
	 color: white;
	 width: 30px;
	 height: 30px;
	 display: inline-block;
	 margin-left: 50px;
	 margin-top: -20px;
	 float:right;
	 font-size: 17px;
	 text-align: center;
	 padding-top:2px;
	 font-weight: bold;
	 font-style: normal;
}
.belongHead{
	display: inline-block;
	width: 600px;
	margin: 60px;
	
}
.belongHead2{
	border-top: solid 2px #E5E5E5;
	border-bottom: solid 2px #E5E5E5;
	height: 200px;
	margin-top: 10px;
}

table.type04 {
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  margin : 10px 10px;
  font-size: 15px;
}
table.type04 th {
  width: 160px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
 
}
table.type04 td {
  width: 250px;
  padding: 10px;
  vertical-align: top;

}

.image{
	position:relative;
	display: inline-block;
	cursor: pointer;
}
.text{
	position: absolute;
	top: 10px;
	left: 10px;
	color: #fff;
}

dfn::after {
  content: attr(data-info);
  display: inline;
  position: absolute;
  top: 40px; left: 0;
  opacity: 0;
  width: 150px;
  font-size: 13px;
  font-weight: 700;
  line-height: 1.5em;
  padding: 0.5em 0.8em;
  background: rgba(0,0,0,0.8);
  color: #fff;
  pointer-events: none; /* This prevents the box from apearing when hovered. */
  transition: opacity 250ms, top 250ms;
}
dfn::before {
  content: '';
  display: block;
  position: absolute;
  top: 50px; left: 40px;
  opacity: 0;
  width: 0; height: 0;
  border: solid transparent 5px;
  border-bottom-color: rgba(0,0,0,0.8);
  transition: opacity 250ms, top 250ms;
}
dfn:hover {z-index: 2;} /* Keeps the info boxes on top of other elements */
dfn:hover::after,
dfn:hover::before {opacity: 1;}
dfn:hover::after {top: 60px;}
dfn:hover::before {top: 50px;}

.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	margin-bottom: 30px;
 
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		setInterval(function(){
			  $(".text").toggle();
			}, 600);
		
		
	});
	
	function goInbox(){
		location.href="<%= ctxPath%>/message/inbox.sam";
	}
	function goEdit(){
		location.href="<%= ctxPath%>/mypage/edit.sam";
	}
</script>

<div class="msgHead">
	<span style="font-weight: bold; font-size: 30px; color:#3498DB;">${requestScope.name}<c:if test="${requestScope.identity == 0}">(학생)</c:if><c:if test="${requestScope.identity == 1}">(교수)</c:if>
	</span><span style="font-weight: bold; font-size: 30px; ">님의 마이페이지 </span>
	
	
	<c:if test="${requestScope.nonReadCount == 0}">
		<button onclick="goInbox()" style="font-weight: bold; font-size: 20px;">쪽지함</button><%-- <img src="<%= ctxPath%>/resources/images/mypage/message.png" onclick="goInbox()" style="width:60px; height:60px; cursor: pointer; "> --%>
	</c:if>
	
	<c:if test="${requestScope.nonReadCount > 0}">
		<div class="image">
		<button onclick="goInbox()" style="font-weight: bold; font-size: 20px;">쪽지함</button><%-- <img src="<%= ctxPath%>/resources/images/mypage/message.png" onclick="goInbox()" style="width:60px; height:60px; "> --%>
		<dfn data-info="쪽지함을 클릭하여 &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;메세지를 확인하세요!">
		<a class="text"  id="msgNew" onclick="goInbox()">
			${requestScope.nonReadCount}
		</a>
		</dfn>
	</div>
	</c:if>
	
	
	
</div>
	<hr style="border: solid 1px #E5E5E5;">

	<div class="belongHead" >
	
		
		<div class="admsubtitle" >
			<span >나의회원정보</span><button style="margin-left: 350px; font-size: 15px; " onclick="goEdit()">회원정보수정</button>
		</div>

	
	<div class="belongHead2" >

		
		<c:if test="${requestScope.identity == 0}">
		
			<table class="type04">
			 <tr>
			    <td rowspan="5" style="padding-left:30px;"><img src="<%= ctxPath%>/resources/images/mypage/profile1.png" style="width:150px; height:150px;"></td>
			  </tr>
			  <tr>
			    <th scope="row">● 성명</th>
			    <td>${requestScope.name}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 학과</th>
			    <td>${requestScope.nameMaj}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 학번</th>
			    <td>${requestScope.perno}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 이메일</th>
			    <td>${requestScope.email}</td>
			  </tr>
			</table>
		
		</c:if>
		
		<c:if test="${requestScope.identity == 1}">
		
			<table class="type04">
			 <tr>
			    <th rowspan="5" style="padding-left:30px;"><img src="<%= ctxPath%>/resources/images/mypage/profile1.png" style="width:160px; height:160px;"></th>
			  </tr>
			  <tr>
			    <th scope="row">● 성명</th>
			    <td>${requestScope.name}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 담당학과</th>
			    <td>${requestScope.nameMaj}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 교번</th>
			    <td>${requestScope.perno}</td>
			  </tr>
			  <tr>
			    <th scope="row">● 이메일</th>
			    <td>${requestScope.email}</td>
			  </tr>
			</table>
			
		</c:if>
	</div>

</div>


<div class="belongHead">
	<div class="admsubtitle" >
			<span >열람실예약내역</span>
		</div>
	<div class="belongHead2">
	</div>
</div>


<c:if test="${requestScope.identity == 0}">

<div class="belongHead" style="width:88%;">
	<div class="admsubtitle" >
			<span >과목별성적조회</span>
		</div>- 2021년도 봄학기
	<div class="belongHead2" >
		<table class="table table-hover" >
		
	      <thead class="scoretable">
	      <tr id="score"> 
	        <th>과목번호</th>
	        <th style="width: 30%;">과목명</th>
	        <th>개설학기</th>
	        <th>담당교수</th>
	        <th>총점</th>
	        <th>세부성적조회</th>
	      </tr>
	    </thead>
    
	    <tbody class="scoretable">
	      <tr id="tr_1" style="color: #055AC1;">
	        <td>1</td>
	        <td >경영학원론</td>
	        <td>2021년도 1학기</td>
	        <td>이수영</td>
	        <td>50/100</td>
	        <td><button>세부성적조회하기</button></td>
	      </tr>
	    </tbody>
	    
	    <tbody class="scoretable">
	      <tr id="tr_1" style="color: #055AC1;">
	        <td>2</td>
	        <td >경영학원론</td>
	        <td>2021년도 1학기</td>
	        <td>이수영</td>
	        <td>50/100</td>
	        <td><button>세부성적조회하기</button></td>
	      </tr>
	    </tbody>
	    
	    <tbody class="scoretable">
	      <tr id="tr_1" style="color: #055AC1;">
	        <td>3</td>
	        <td >경영학원론</td>
	        <td>2021년도 1학기</td>
	        <td>이수영</td>
	        <td>50/100</td>
	        <td><button>세부성적조회하기</button></td>
	      </tr>
	    </tbody>

	  </table>
	
	</div>
</div>

</c:if>
<c:if test="${requestScope.identity == 1}">

<div class="belongHead" style="width:88%;">
	<div class="admsubtitle" >
			<span >수업조회 - 2021년도 봄학기</span>
		</div>
	<div class="belongHead2" >
		<table class="table table-hover" >
		
	      <thead class="scoretable">
	      <tr id="score"> 
	        <th>과목번호</th>
	        <th style="width: 20%;">과목명</th>
	        <th>개설학기</th>
	        <th>수강인원</th>
	        <th>현재진행차수</th>
	        <th>최근진행과제/시험</th>
	      </tr>
	    </thead>
    
	    <tbody class="scoretable">
	      <tr id="tr_1" style="color: #055AC1;">
	        <td>1</td>
	        <td >경영학원론</td>
	        <td>2021년도 1학기</td>
	        <td>10명</td>
	        <td>15차수</td>
	        <td>경영학이란 무엇인가 책읽고 보고서작성</td>
	      </tr>
	    </tbody>
	    

	  </table>
	
	</div>
</div>

</c:if>
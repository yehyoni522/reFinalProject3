<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
    //     /MyMVC 
%>

<script src="<%= ctxPath%>/resources/js/jquery.min.js"></script>
<script src="<%= ctxPath%>/resources/js/jquery.js-calendar.js"></script>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">

<style type="text/css">
	div#calendar{
		float: left; 
		width: 42%;
		height:600px;
		padding:10px;
		margin-left: 100px;
	}
	div#logincheck{
		border-left: 1px solid black; 
		float: left; 
		height:600px;
		width: 42%; 
		padding: 20px 0px 0px 50px;
		
	}
	div#mainBoard{ 
		float: left; 
		margin-left: 120px;
        width: 80%; 
        }
        
     table {
      width: 100%;
     border-bottom: 1px solid #F5F5F5;
      }

      thead{
      	text-align:center;
        border-bottom: 1px solid #bcbcbc;
		font-size: 15pt;
		padding: 80px 0px 5px 0px;
      }
          
     th{
      	text-align:center;
      }
      
	.sidenav{
	  height: 100%;
	  width: 0;
	  position: fixed;
	  z-index: 1;
	  top: 0;
	  left: 0;
	  background-color: #111;
	  overflow-x: hidden;
	  transition: 0.5s;
	  padding-top: 60px;
	}
	
	.sidenav a {
	  padding: 8px 20px 8px 32px;
	  text-decoration: none;
	  font-size: 23px;
	  color: #818181;
	  display: block;
	  transition: 0.3s;
	  text-align: right;
	}
	
	.sidenav a:hover {
	  color: #f1f1f1;
	}
	
	.sidenav .closebtn  {
	  position: absolute;
	  top: 0;
	  right: 25px;
	  font-size: 36px;
	}
	div#loginInfo{
		width:100%;
		margin:50px 0px 100px 0px;
		color: #f1f1f1;
	}
	@media screen and (max-height: 450px) {
	  .sidenav {padding-top: 15px;}
	  .sidenav a {font-size: 18px;}
	}
	
</style>


<script>
function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}

</script>
   
<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="transform: scaleX(-1);" src="<%= ctxPath%>/resources/images/close.png" style="width:100%;"></a>
  <div id="loginInfo">
  	<c:if test="${empty sessionScope.loginuser}"><a href="<%=ctxPath%>/login.sam">로그인을 해주세요</a></c:if>
  </div>
  <a href="#">내 수업 목록</a>
  <a href="#">공지사항</a>
  <a href="#">Q&A</a>
  <a href="#">게시판</a>
  <a href="#">열람실좌석예약</a>
  
</div>

<div style="width: 40px; font-size: 20pt; font-weight: bold; cursor:pointer;">
	<span style="text-align: right; cursor:pointer; margin:0px 0px 50px 30px;" onclick="openNav()"><img src="<%= ctxPath%>/resources/images/addmenu.png" style="width:100%;"></span>
</div>

<div id="calendar" align="center">

</div>


<div id="logincheck"  >
	<c:if test="${empty sessionScope.loginuser}">
	<span style="font-size: 20pt; font-weight: bold;">${sessionScope.loginuser.userid}로그인을 해주세요.</span>
	</c:if>
	
	<c:if test="${not empty sessionScope.loginuser}">
	<span style="font-size: 20pt; font-weight: bold;">내 강의</span>
	</c:if>
	
</div>

<div id="mainBoard" align="center">
		<h2 style="padding-top:50px; font-weight:bold;">게시판</h2>
		<h3>실시간 인기글</h3>
		<br><br>
	    <table>
	        <thead>
	           <tr id="menu" align="center">	 
	              <th>No.</th>
	              <th>분류</th>
	              <th>제목</th>
	              <th>작성자</th>
	              <th>추천수</th>
	           </tr>
	        </thead>
	    </table>

</div>


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
        
     table#MainBoard{
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
	
	table#MainBoard > thead > tr > td,
   table#MainBoard > tbody > tr > td {
   		height: 60px;
   		text-align: center;
   		border-bottom:  solid 1px #F5F5F5;
   }
   th#subject{
   		width: 600px;
   }
   th#category{
   		width: 195px;
   }
    tr#tr_MainBoard:hover{
     	background-color:#F5F5F5; 
     	cursor: pointer;
     }
</style>


<script>
	$(document).ready(function(){
		goViewBoard(1);
		$("tr#tr_MainBoard").click(function(){
		var seq = $(this).children("seq").text();
	
		location.href ="<%= request.getContextPath()%>/baord/view.sam?seq="+seq+"&goBackURL=${requestScope.goBackURL}";
		});// end of click

	
	});// end of $(document).ready(function(){})--------------
	
	// === #127. Ajax로 불러온 댓글내용을 페이징처리하기 === //
	function goViewBoard(currentShowPageNo) {
		
		$.ajax({
			url:"<%= ctxPath%>/MainBoardList.sam",
			data:{"currentShowPageNo":currentShowPageNo},
			dataType:"json",
			success:function(json){ 
				// []  또는 
				// [{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열네번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열세번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열두번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열한번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열번째 댓글입니다."}] 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr id='tr_MainBoard'>";
						html += "<td class='comment'>"+item.seq+"</td>";
						
						var categoryno = item.categoryno;
						switch (categoryno) {
						case "1":
							html += "<td>자유게시판</td>";
							break;
						case "2":
							html += "<td>중고게시판</td>";
							break;
						case "3":
							html += "<td>동아리&공모전 모집</td>";
							break;
						}
						
						html += "<td class='comment'>"+ item.subject +"</td>";
						
						if(item.commentCount>0){
							html+="<span>"+ item.commentCount+"</span>"
						}
						else if(item.commentCount == 0){
							html+="<span>"+ item.subject+"</span>"
						}
						if(item.namecheck == 0){
						html += "<td class='comment'>"+ item.name +"</td>";
						}
						if(item.namecheck == 1){
							html += "<td class='comment'>익명</td>";
						}
						
						html += "<td class='comment'>"+ item.good +"</td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='5' class='comment'>인기 게시글이 없습니다.</td>";
					html += "</tr>";
				}
				
				$("tbody#boardDisplay").html(html);
				
				// 페이지바 함수 호출
				makeboardPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	
	
	// ==== 댓글내용 페이지바  Ajax로 만들기 ==== // 
	function makeboardPageBar(currentShowPageNo) {
	
		<%-- 원글에 대한 댓글의 totalPage 수를 알아오려고 한다. --%> 
		$.ajax({
			url:"<%= ctxPath%>/getboardTotalPage.sam",
			data:{"sizePerPage":"3"},
			type:"get",
			dataType:"json",
			success:function(json) {
	
				if(json.totalPage > 0) {
					// 댓글이 있는 경우 
					
					var totalPage = json.totalPage;
					
					var pageBarHTML = "<ul style='list-style: none;'>";
					
					var blockSize = 3;
		
					var loop = 1;
				    
				    if(typeof currentShowPageNo == "string") {
				    	currentShowPageNo = Number(currentShowPageNo);
				    }
				    
				    // *** !! 다음은 currentShowPageNo 를 얻어와서 pageNo 를 구하는 공식이다. !! *** //
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;
					// === [맨처음][이전] 만들기 === 
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewBoard(\"1\")'>[맨처음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewBoard(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					}
				
					while( !(loop > blockSize || pageNo > totalPage) ) {
					
						if(pageNo == currentShowPageNo) {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
						}
						else {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewBoard(\""+pageNo+"\")'>"+pageNo+"</a></li>";
						}
						
						loop++;
						pageNo++;
					}// end of while------------------------
				
				
				// === [다음][마지막] 만들기 === 
					if(pageNo <= totalPage) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewBoard(\""+pageNo+"\")'>[다음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewBoard(\""+totalPage+"\")'>[마지막]</a></li>";
					}
					
					pageBarHTML += "</ul>";
				    
					$("div#pageBar").html(pageBarHTML);
				}
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function makeCommentPageBar(currentShowPageNo) {}-----------------
	


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
  		<c:if test="${sessionScope.loginuser.identity == 0}">
			<a>${sessionScope.loginuser.name} 학생</a>
	</c:if>
		<c:if test="${sessionScope.loginuser.identity == 1}">
			<a>${sessionScope.loginuser.name} 교수</a>
	</c:if>
	${id}
  </div>
  <a href="#">내 수업 목록</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=4">공지사항</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=5">Q&A</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=1">게시판</a>
  <a href="<%=ctxPath %>/reading/index.sam">열람실좌석예약</a>
  
</div>

<div style="width: 40px; font-size: 20pt; font-weight: bold; cursor:pointer;">
	<span style="text-align: right; cursor:pointer; margin:0px 0px 50px 30px;" onclick="openNav()"><img src="<%= ctxPath%>/resources/images/addmenu.png" style="width:100%;"></span>
</div>

<div id="calendar" align="center">

</div>


<div id="logincheck"  >
	<c:if test="${empty sessionScope.loginuser}">
	<span style="display:inline-block; padding:250px 0px 0px 150px;  font-size: 20pt; font-weight: bold;">${sessionScope.loginuser.userid}로그인을 해주세요.</span>
	</c:if>
	
	
	<c:if test="${sessionScope.loginuser.identity == 0}">
		<div style="font-size: 25pt; font-weight: bold; margin-top:50px; margin-bottom: 50px;">
		내 강의
		</div>
		<c:forEach var="subjectvo" items="${requestScope.MainsubjectList}" varStatus="status"> 
			<span style="font-size: 20pt; font-weight: bold; margin-left: 50px; margin-bottom: 30px;"><a href="#">${subjectvo.subname}</a></span>
			<br>
			<span style="font-size: 15pt; font-weight: bold; margin-left: 200px;">${subjectvo.day}</span>
		</c:forEach>
	</c:if>
	<c:if test="${sessionScope.loginuser.identity == 1}">
			<div style="font-size: 25pt; font-weight: bold; margin-top:50px; margin-bottom: 50px;">
				${sessionScope.loginuser.name} 교수님 강의 목록
				</div>
				<c:forEach var="subjectvo" items="${requestScope.MainsubjectList}" varStatus="status"> 
			<span style="font-size: 20pt; font-weight: bold; margin-left: 50px; margin-bottom: 30px;"><a href="#">${subjectvo.subname}</a></span>
			<br>
			<span style="font-size: 15pt; font-weight: bold; margin-left: 200px;">${subjectvo.day}</span>
		</c:forEach>
	</c:if>
	
</div>

<div id="mainBoard" align="center">
		<h2 style="padding-top:50px; font-weight:bold;">게시판</h2>
		<h3>실시간 인기글</h3>
		<br><br>
	    <table id = "MainBoard">
	        <thead>
	       
	           <tr id="menu" align="center">	 
	              <th>No.</th>
	              <th id = "category">분류</th>
	              <th id="subject">제목</th>
	              <th>작성자</th>
	              <th>추천수</th>
	           </tr>
	        </thead>
	        <tbody id="boardDisplay" >
	     
	        </tbody>
	    </table>
	    <div id="pageBar" style="margin-top: 30px;"></div>

</div>


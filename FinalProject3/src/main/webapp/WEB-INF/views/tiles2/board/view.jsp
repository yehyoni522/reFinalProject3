<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
  
#table, #table2 {
	border-collapse: collapse;
	border-top: 1px solid #ccc;
    width: 1024px;
}

#table th, #table td{
	padding: 5px;
	border-bottom: 1px solid #ccc;
	
}
#table td{width: 880px;}


.move {cursor: pointer; color:navy;}
.moveColor {color: #660029; font-weight: bold;}

a {text-decoration: none !important;}

td.comment {text-align: center; border}
.boarda{
	color:black;
} 
.styhr{
	border: 0;
	height: 2px;
	background-color: #ccc;
	margin: 5px 0 5px 0;
}	
#contentfooter{
	position: relative;
	left: 90%;
}
#contnentsubj{
	margin: 10px;
	font-size: 12pt;
}
#contentinfo{
	margin: 10px;
}
#boardcontent{
	width:80%; 
	height: 100px; 
	border: 0px solid blue; 
	padding: 2px; 
	margin: 15px 5px 5px 10px;
}
#boardcommet{
	margint: 10px;
}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
	//	goReadComment();  // 페이징처리 안한 댓글 읽어오기
		goViewComment(1); // 페이징처리 한 댓글 읽어오기 
		
		$("span.move").hover(function(){
			                    $(this).addClass("moveColor");
			                }
	                        ,function(){
	                        	$(this).removeClass("moveColor");
	                        });
		
	});// end of $(document).ready(function(){})------------------

	
	// === 댓글쓰기 === //
	function goAddWrite() {
		
		var contentVal = $("input#commentContent").val().trim();
		if(contentVal == "") {
			alert("댓글 내용을 입력하세요!!");
			return; // 종료
		}
		
		var form_data = $("form[name=addWriteFrm]").serialize();
		
		$.ajax({
			url:"<%= ctxPath%>/board/addComment.sam",
			data:form_data,
			type:"post",
			dataType:"json",
			success:function(json){ 
			   var n = json.n;
			   
			   if(n == 0) {
				   alert(json.name+"님의 포인트는 300점을 초과할 수 없으므로 댓글쓰기가 불가합니다.");
			   }
			   else {
				   goViewComment(1); // 페이징처리 한 댓글 읽어오기 
			   }
			   
			   $("input#commentContent").val("");
			   
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goAddWrite(){}--------------------------
	
	
	// === 페이징처리 안한 댓글 읽어오기 === //
	function goReadComment() {
		
		$.ajax({
			url:"<%= ctxPath%>/board/readComment.sam",
			data:{"parentSeq":"${requestScope.boardvo.seq}"},
			dataType:"json",
			success:function(json){ 
				// []  또는 
				// [{"content":"댓글내용물", "name":"작성자명", "regDate":"작성일자"},{},{}] 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr>";
						html += "<td class='comment'>"+(index+1)+"</td>";
						html += "<td>"+ item.content +"</td>";
						html += "<td class='comment'>"+ item.name +"</td>";
						html += "<td class='comment'>"+ item.regDate +"</td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='4' class='comment'>댓글이 업습니다</td>";
					html += "</tr>";
				}
				
				$("tbody#commentDisplay").html(html);
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goReadComment() {}----------------------
	
	
	// === #127. Ajax로 불러온 댓글내용을 페이징처리하기 === //
	function goViewComment(currentShowPageNo) {
		
		$.ajax({
			url:"<%= ctxPath%>/board/commentList.sam",
			data:{"parentSeq":"${requestScope.boardvo.seq}",
				  "currentShowPageNo":currentShowPageNo},
			dataType:"json",
			success:function(json){ 
				// []  또는 
				// [{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열네번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열세번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열두번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열한번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열번째 댓글입니다."}] 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr>";
						html += "<td class='comment'>"+(index+1)+"</td>";
						html += "<td>"+ item.content +"</td>";
						html += "<td class='comment'>"+ item.name +"</td>";
						html += "<td class='comment'>"+ item.regDate +"</td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='4' class='comment'>댓글이 업습니다</td>";
					html += "</tr>";
				}
				
				$("tbody#commentDisplay").html(html);
				
				// 페이지바 함수 호출
				makeCommentPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	
	// ==== 댓글내용 페이지바  Ajax로 만들기 ==== // 
	function makeCommentPageBar(currentShowPageNo) {
	
		/* 원글에 대한 댓글의 totalPage 수를 알아오려고 한다.  */
		$.ajax({
			url:"<%= ctxPath%>/board/getCommentTotalPage.sam",
			data:{"parentSeq":"${requestScope.boardvo.seq}",
				  "sizePerPage":"5"},
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
				    
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;								    
				
				// === [맨처음][이전] 만들기 === 
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewComment(\"1\")'>[맨처음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					}
				
					while( !(loop > blockSize || pageNo > totalPage) ) {
					
						if(pageNo == currentShowPageNo) {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
						}
						else {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>"+pageNo+"</a></li>";
						}
						
						loop++;
						pageNo++;
					}// end of while------------------------
				
				
				// === [다음][마지막] 만들기 === 
					if(pageNo <= totalPage) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>[다음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewComment(\""+totalPage+"\")'>[마지막]</a></li>";
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
	

	
	
</script>
<c:if test="${categoryno == 1 || categoryno == 2 || categoryno == 3}"> 	
<hr>
	<div id="boardmenu" style="padding-left: 25%;">
		<ul>
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=1">자유게시판</a><span style="border-right: 2px black solid; margin: 0 80px 0 80px;"></span></li>
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=2">중고거래</a><span style="border-right: 2px black solid; margin: 0 80px 0 80px;"></span></li>	
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=3">동아리&공모전 모집</a></li>	
		</ul>	
	</div>	
<hr>
</c:if> 

<div style="padding-left: 10%; padding-right:10%;">
	<h2 style="margin-bottom: 30px;">
		<c:if test="${categoryno == 1}">자유게시판</c:if>
		<c:if test="${categoryno == 2}">중고거래</c:if>
		<c:if test="${categoryno == 3}">동아리&공모전 모집</c:if> 
		<c:if test="${categoryno == 4}">공지사항</c:if> 
		<c:if test="${categoryno == 5}">Q&A</c:if> 
	</h2>
	<hr class="styhr">
	<c:if test="${not empty requestScope.boardvo}">
		<div id="viewcontent">
			<div id="contnentsubj">
				${requestScope.boardvo.subject}
			</div>
			<hr class="styhr">
			<div id="contentinfo">			
				${requestScope.boardvo.name} &nbsp; ${requestScope.boardvo.regDate}
			</div>
			<br>
			<div id="boardcontent" >
				<p style="word-break: break-all;">${requestScope.boardvo.content}</p>
			</div>	
			<div id="contentfooter">
				<img src="<%=ctxPath%>/resources/images/good.PNG" style="width:45px; height:43px;">${requestScope.boardvo.readCount}
				<img src="<%=ctxPath%>/resources/images/comment.PNG" style="width:44px; height:38px;">${requestScope.boardvo.commentCount}
			</div>	
			<hr class="styhr">
		</div>		
		<br>
		
		<div id="boardcommet">	
			<%-- 댓글쓰기 폼 추가--%> 
			<c:if test="${categoryno != 4}">
			    <c:if test="${not empty sessionScope.loginuser}">
					<form name="addWriteFrm" style="margin-top: 20px;">
						<input type="hidden" name="fk_userid" value="${sessionScope.loginuser.userid}" />
						성명 : <input type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />  
						&nbsp;&nbsp;
						댓글내용 : <input id="commentContent" type="text" name="content" class="long" /> 
						
						댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호)
						<input type="hidden" name="parentSeq" value="${requestScope.boardvo.seq}" /> 
						
						<button id="btnComment" type="button" onclick="goAddWrite()">확인</button> 
						<button type="reset">취소</button> 
					</form>
			    </c:if>		   
			    		    
			    <!-- 댓글 내용 보여주기 -->
				<table id="table2" >
					<thead>
					<tr>
					    <th style="width: 10%; text-align: center;">번호</th>
						<th style="width: 60%; text-align: center;">내용</th>
						<th style="width: 10%; text-align: center;">작성자</th>
						<th style="text-align: center;">작성일자</th>
					</tr>
					</thead>
					<tbody id="commentDisplay"></tbody>
				</table>
			</c:if> 
		</div>	
   		<!-- 댓글 페이지바-->
    	<div id="pageBar" style="border:solid 0px gray; width: 90%; margin: 10px auto; text-align: center;"></div> 
	 
	 	<c:set var="gobackURL2" value='${fn:replace(requestScope.gobackURL, "&", " ") }' />
	 	
	 	<div style="margin-bottom: 1%;">이전글제목&nbsp;&nbsp;<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.previousseq}&gobackURL=${gobackURL2}'">${requestScope.boardvo.previoussubject}</span></div>
		<div style="margin-bottom: 1%;">다음글제목&nbsp;&nbsp;<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.nextseq}&gobackURL=${gobackURL2}'">${requestScope.boardvo.nextsubject}</span></div>
		
	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/list.sam'">전체목록보기</button>
	<button type="button" onclick="javascript:location.href='${requestScope.gobackURL}'">검색된결과목록보기</button>
	
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/edit.sam?seq=${requestScope.boardvo.seq}'">수정</button>
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/del.sam?seq=${requestScope.boardvo.seq}'">삭제</button>


 	
</div>





    
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table, #table2 {border-collapse: collapse;
	 		         width: 1024px;
	 		        }
	#table th, #table td{padding: 5px;}
	#table th{width: 120px; background-color: #DDDDDD;}
	#table td{width: 880px;}
	.long {width: 470px;}
	.short {width: 120px;}
	
	.move {cursor: pointer; color:navy;}
	.moveColor {color: #660029; font-weight: bold;}
	
	a {text-decoration: none !important;}
	
	td.comment {text-align: center;}
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
			success:function(json){ // 정상이라면  {"n":1, "name":"서영학"}  오류가 발생하면  {"n":0, "name":"서영학"}
			   var n = json.n;
			   
			   if(n == 0) {
				   alert(json.name+"님의 포인트는 300점을 초과할 수 없으므로 댓글쓰기가 불가합니다.");
			   }
			   else {
				// goReadComment();  // 페이징처리 안한 댓글 읽어오기
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
	
		<%-- 원글에 대한 댓글의 totalPage 수를 알아오려고 한다. --%> 
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

<div style="padding-left: 10%;">
	<h1>글내용보기</h1>

	<c:if test="${not empty requestScope.boardvo}">
		<table id="table">
			<tr>
				<th>글번호</th>
				<td>${requestScope.boardvo.seq}</td>
			</tr>
			<tr>
				<th>성명</th>
				<td>${requestScope.boardvo.name}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${requestScope.boardvo.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
				    <p style="word-break: break-all;">${requestScope.boardvo.content}</p>
				</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${requestScope.boardvo.readCount}</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>${requestScope.boardvo.regDate}</td>
			</tr>
		</table>
		
		<br>
		
		<div style="margin-bottom: 1%;">이전글제목&nbsp;&nbsp;<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.previousseq}'">${requestScope.boardvo.previoussubject}</span></div>
		<div style="margin-bottom: 1%;">다음글제목&nbsp;&nbsp;<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.nextseq}'">${requestScope.boardvo.nextsubject}</span></div>

	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/list.sam'">전체목록보기</button>
	<button type="button" onclick="javascript:location.href='${requestScope.gobackURL}'">검색된결과목록보기</button>
	
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/edit.sam?seq=${requestScope.boardvo.seq}'">수정</button>
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/board/del.sam?seq=${requestScope.boardvo.seq}'">삭제</button>


    <%-- === #83. 댓글쓰기 폼 추가 === --%>
    <c:if test="${not empty sessionScope.loginuser}">
    	<h3 style="margin-top: 50px;">댓글쓰기 및 보기</h3>
		<form name="addWriteFrm" style="margin-top: 20px;">
			      <input type="hidden" name="fk_userid" value="${sessionScope.loginuser.userid}" />
			성명 : <input type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />  
			&nbsp;&nbsp;
			댓글내용 : <input id="commentContent" type="text" name="content" class="long" /> 
			
			<%-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) --%>
			<input type="hidden" name="parentSeq" value="${requestScope.boardvo.seq}" /> 
			
			<button id="btnComment" type="button" onclick="goAddWrite()">확인</button> 
			<button type="reset">취소</button> 
		</form>
    </c:if>
    
    <c:if test="${empty sessionScope.loginuser}">
    	<h3 style="margin-top: 50px;">댓글보기</h3>
    </c:if>	
    
    <!-- ===== #94. 댓글 내용 보여주기 ===== -->
	<table id="table2" style="margin-top: 2%; margin-bottom: 3%;">
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

    <%-- ==== #136. 댓글 페이지바 ==== --%>
    <div id="pageBar" style="border:solid 0px gray; width: 90%; margin: 10px auto; text-align: center;"></div> 
     
</div>





    
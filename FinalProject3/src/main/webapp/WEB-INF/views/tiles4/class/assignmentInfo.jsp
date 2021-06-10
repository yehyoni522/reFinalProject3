<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

.move{
	display:inline-block; 
	font-weight:bold;
	margin: 15px 30px;
	
}

.move:hover{
	text-decoration: underline;
	cursor: pointer;
}

.headerName{
	text-align:center;
	font-weight: bolder;
}

.headerCategoty{
	display: inline-block; 
	padding-bottom:5px; 
	border-bottom: 1px gray solid; 
	font-size: 13pt; 
	font-weight: bold;
}

th{text-align:center;}   

td{
	text-align:left;
	padding:0 15px;
}

th{
	background-color: #fafafa;
	
}

tr{
	border-top: solid 0.5px #bfbfbf;
	border-bottom: solid 0.5px #bfbfbf;
	height:40px;
}

td.comment {text-align: center;}

div#btn-board{
	align-items: center; 
	margin-top: 10px;
	margin-bottom: 30px;
	display:inline-block;
	float:right;
}
	
.btn-board{
 	width:100px;
	border:0.5px solid #bfbfbf;
 	font-weight: bold;
 	margin-left:10px;	 	
 	margin-right:5px;
 	padding:5px;
 	border-radius: 5px;
 	height: 30px;
}

.small {
	width:40px;
	height: 26px;
}
 .long {width: 80%;}

</style>

<script type="text/javascript">

	$(document).ready(function(){
		var perno = ${sessionScope.loginuser.perno};
		
		var loginuser = ${sessionScope.loginuser.identity};

		if(loginuser=='1'||loginuser=='2'){ 		// 교수가 로그인 했을 경우
			goViewSubject(1);
			$("#student").hide();			
		}
 	
		else if(loginuser=='0'){	// 학생이 로그인 했을 경우
			studentSubmit(perno);
			$("#student").hide();
			$("#professor").hide();
		}
			
		else {					// 로그인 안한 경우
			$("#student").hide();
			$("#professor").hide();
		}  
 
		
		// 과제글 삭제버튼
		$("button#assgnDelete").click(function(){
			var bool = confirm("삭제하시겠습니까?");
					
			if(bool){
				var frm = document.assgnDelFrm;
				
				frm.method = "post";
		   	    frm.action = "<%= ctxPath%>/class/assignmentDeleteEnd.sam";
		   	    frm.submit();
			}
		});
		
		

		
		
		
		
	});
	
	
	// 학생이 과제 제출을 했는지 안했는지 알아오는 함수
	function studentSubmit(perno){
		
		$.ajax({
			url:"<%= ctxPath%>/class/studentSubmit.sam",
			data:{"perno":perno
				, "assgnno":"${requestScope.assignmentVO.assgnno}"},
			dataType:"json",
			success:function(json){ 
			   var n = json.n;
		
			   if(n==1){	// 제출 했음
				   $("#submit").hide();
				   goViewMySubject(perno);
			   }
			   else{		// 제출 안함
				   $("#student").show();
				   goViewMySubject(perno);
			   }
		
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}			
		});
	}
	
	
	
	
	// === 과제제출 댓글 쓰기 === //
	function goAddSubmit(){

		var perno = ${sessionScope.loginuser.perno};
		var contentVal = $("input#submitContent").val().trim();

	 	if(contentVal == "") {
			alert("댓글 내용을 입력하세요!!");
			return; // 종료
		}
	 	
	 	var bool=confirm("과제 제출 후엔 수정이나 삭제가 불가합니다.\n그래도 과제 제출을 완료하시겠습니까?");
		if(bool){
			if($("input#attach").val() == "") {
			//	alert("첨부파일 없음");
				goAddSubmit_noAttach();
				
			}
			else {
			//	alert("첨부파일 있음");
				goAddSubmit_withAttach();
			}
		}		
 		else{
			alert("과제 제출을 취소하셨습니다.");
		}
	}
 		
	 	
		function goAddSubmit_noAttach() { 		 

			var perno = ${sessionScope.loginuser.perno};
			
		$.ajax({
			url:"<%= ctxPath%>/class/addSubmit.sam",
			data:{ "fk_perno": perno
				, "fk_subno": $("#fk_subno").val()
				, "content": $("input#submitContent").val()
				, "fk_assgnno": $("[name=fk_assgnno]").val()
				},				
			type:"post",
			dataType:"json",
			success:function(json){ 
			   var n = json.n;
			   			 		    
			   if(n==1){
				   $("#student").hide();
				   goViewMySubject(perno);
			   }
		
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		}); 
				
	}	
	

		// 파일첨부가 있는 댓글 쓰기
	function goAddSubmit_withAttach() {
	 		
		var form_data = $("form[name=submitFrm]").serialize();
	
		$("form[name=submitFrm]").ajaxForm({
			url:"<%= ctxPath%>/class/addSubmit_withAttach.sam",
			data:form_data,
			type:"post",
			enctype:"multipart/form-data",
			dataType:"json",
			success:function(json){ 
			   var n = json.n;

			   if(n==1){
				   $("#student").hide();
				   goViewMySubject(perno);
			   }
			   else {
				   alert("실패");
			   }
		
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		}); 
		
		$("form[name=submitFrm]").submit();
				
	}	
		
	// 학생이 내 과제만 볼 수 있도록 하는 리스트
	function goViewMySubject(perno){
		
		$.ajax({
			url:"<%= ctxPath%>/class/mysubmitList.sam",
			data:{"fk_assgnno":"${requestScope.assignmentVO.assgnno}",	 
				  "perno":perno},
			dataType:"json",
			success:function(json){ 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr>";
						html += "<td class='comment'>"+(index+1)+"</td>";
						html += "<td>"+ item.content +"</td>";
						if(item.orgFilename != " ") {	
							html += "<td><a href='/finalproject3/class/submitdownload.sam?submitno="+item.submitno+"'>"+item.orgFilename+"</a>("+item.fileSize +")</td>";
						}
						else{
							html += "<td></td>";
						}
						html += "<td class='comment'>"+ item.submitName +"("+item.fk_perno+")</td>";
						html += "<td class='comment'>"+ item.submitDate +"</td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='5' class='comment'>제출한 과제가 없습니다</td>";
					html += "</tr>";
				}
				
				$("tbody#submitDisplay").html(html);
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}

	
	
	// === 교수가 모든 과제댓글 볼 수 있도록 댓글내용을 페이징처리하기 === //
	function goViewSubject(currentShowPageNo) {

		$.ajax({
			url:"<%= ctxPath%>/class/submitList.sam",
			data:{"fk_assgnno":"${requestScope.assignmentVO.assgnno}",
				  "currentShowPageNo":currentShowPageNo},
			dataType:"json",
			success:function(json){ 			 			 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr>";
						html += "<td class='comment'>"+(index+1)+"</td>";
						html += "<td>"+ item.content +"</td>";
						
						if(item.orgFilename != " ") {	
							html += "<td><a href='/finalproject3/class/submitdownload.sam?submitno="+item.submitno+"'>"+item.orgFilename+"</a>("+item.fileSize +")</td>";
						}
						else{
							html += "<td></td>";
						}
						html += "<td class='comment'>"+ item.submitName +"("+item.fk_perno+")</td>";
						html += "<td class='comment'>"+ item.submitDate +"</td>";
						html += "<td class='comment'><input type='hidden' value='"+item.submitno+"'/><input type='text' style='width:40px;' value='"+item.score+"'/><input type='button' class='btn-board small' onclick='goScore(this)' value='변경'/></td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='5' class='comment'>제출된 과제가 없습니다</td>";
					html += "</tr>";
				}
				
				$("tbody#submitDisplay").html(html);
				
				// 페이지바 함수 호출
				makeSubmitPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	// ==== 댓글내용 페이지바  Ajax로 만들기 ==== // 
	function makeSubmitPageBar(currentShowPageNo) {
	
		<%-- 원글에 대한 댓글의 totalPage 수를 알아오려고 한다. --%> 
		$.ajax({
			url:"<%= ctxPath%>/class/getSubmitTotalPage.sam",
			data:{"fk_assgnno":"${requestScope.assignmentVO.assgnno}",
				  "sizePerPage":"5"},
			type:"get",
			dataType:"json",
			success:function(json) {
				// {"totalPage":5} 또는 
				// {"totalPage":1} 또는
				// {"totalPage":0}
				
				if(json.totalPage > 0) {
					// 댓글이 있는 경우 
					
					var totalPage = json.totalPage;
					
					var pageBarHTML = "<ul style='list-style: none;'>";
					
					var blockSize = 3;
					// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.
					
					var loop = 1;
					/*
				    	loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
				    */
				    
				    if(typeof currentShowPageNo == "string") {
				    	currentShowPageNo = Number(currentShowPageNo);
				    }
				    
				    // *** !! 다음은 currentShowPageNo 를 얻어와서 pageNo 를 구하는 공식이다. !! *** //
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;
					
				
				// === [맨처음][이전] 만들기 === 
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewSubject(\"1\")'>[맨처음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewSubject(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					}
				
					while( !(loop > blockSize || pageNo > totalPage) ) {
					
						if(pageNo == currentShowPageNo) {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; font-weight:bold; color:red; padding:2px 4px;'>"+pageNo+"</li>";
						}
						else {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewSubject(\""+pageNo+"\")'>"+pageNo+"</a></li>";
						}
						
						loop++;
						pageNo++;
					}// end of while------------------------
				
				
				// === [다음][마지막] 만들기 === 
					if(pageNo <= totalPage) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewSubject(\""+pageNo+"\")'>[다음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewSubject(\""+totalPage+"\")'>[마지막]</a></li>";
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
	
	
	// 점수변경 버튼 눌렀을 때
	function goScore(e){
		var score = $(e).prev().val();
		var submitno = $(e).prev().prev().val();
		
		$.ajax({
			url:"<%= ctxPath%>/class/changeScore.sam",
			data:{"score":score,
				  "submitno":submitno},
			type:"get",
			dataType:"json",
			success:function(json) {
				alert("변경성공");
				
				
			},error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			 	}
		});
		
	}

	
	   
</script>  

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;과제게시판
	</div>
	
	<h1 class="headerName">컴퓨터 네트워크</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 과제 상세</h3>

	<hr>
	<c:if test="${empty requestScope.assignmentVO}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
	<c:choose>
		<c:when test="${(sessionScope.loginuser.identity == '0')||(sessionScope.loginuser.identity == '1')||(sessionScope.loginuser.identity == '2')}">
			<c:if test="${not empty requestScope.assignmentVO}">
				<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
					<tr>
					   <th style="width:200px;">제목</th>
					   <td style="font-weight: bolder;">
					   		${requestScope.assignmentVO.subject}
					   </td>
					</tr>
					<tr>
					   <th>게시일시</th>
					   <td>
					     	${requestScope.assignmentVO.regDate}
					   </td>
					</tr>
					<tr>
					   <th>마감일시</th>
					   <!-- 마감일 null(미정)일 경우 비공개 -->
						<c:choose>
							<c:when test="${requestScope.assignmentVO.deadline eq null}">
								<td>비공개</td>
							</c:when>
							<c:otherwise>
								<td>${requestScope.assignmentVO.deadline}</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>			  
					   <td colspan="2">
					      	<p style="word-break: break-all; padding:15px;">
					      		${requestScope.assignmentVO.content}
					      	</p>
					   </td>
					</tr>
					<%-- === #150. 파일첨부 타입 추가하기 === --%>
					<tr>
						<th>첨부파일</th>
						<td>
							<c:if test="${requestScope.assignmentVO.fileSize != null}">
								<a href="<%= ctxPath%>/class/assgndownload.sam?assgnno=${requestScope.assignmentVO.assgnno}">
									${requestScope.assignmentVO.orgFilename}
								</a>
									<span style="font-size: 9pt;">(<fmt:formatNumber value="${requestScope.assignmentVO.fileSize}" pattern="#,###" />bytes)</span>						
							</c:if>
							<c:if test="${requestScope.assignmentVO.fileSize == null}">								
							</c:if>
						</td>
					</tr>
					
			</table>
		<%--	<div style="display:inline-block; margin-left:250px;">
				<c:choose>
					<c:when test="${ not empty reqeustScope.assignmentVO.previousseq}">
						<div>
							<i class="fas fa-chevron-left"></i>&nbsp;&nbsp;					
								이전 ...
						</div>
					</c:when>
					<c:otherwise>
						<div class="move">
							<i class="fas fa-chevron-left"></i>&nbsp;&nbsp;
							<span onclick="javascript:location.href='/class/assignmentView.sam?assgnno=${reqeustScope.assignmentVO.previousseq}'">
								이전 ... ${requestScope.assignmentVO.previoussubject}
							</span>
						</div>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${ not empty reqeustScope.assignmentVO.nextseq}">
						<div>
							 ... 다음&nbsp;&nbsp;
							<i class="fas fa-chevron-right"></i>
						</div>
					</c:when>
					<c:otherwise>
						<div class="move">
						<span onclick="javascript:location.href='/class/assignmentView.sam?assgnno=${reqeustScope.assignmentVO.nextseq}'">
							${requestScope.assignmentVO.nextsubject}
						</span> ... 다음&nbsp;&nbsp;
						<i class="fas fa-chevron-right"></i>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		 --%>
			</c:if>
	</c:when>
	</c:choose>
<%-- 
	<c:choose>
		<c:when test="${(sessionScope.loginuser.identity eq '1')||(sessionScope.loginuser.identity eq '2')}"> --%>
			<div id="btn-board">
				<span id="professor">
					<button type="button" class="btn-board" id="assgnEdit" onclick="javascript:location.href='<%= ctxPath%>/class/assignmentEdit.sam?assgnno=${requestScope.assignmentVO.assgnno}'">수정</button>
					<button type="button" class="btn-board" id="assgnDelete">삭제</button>
				</span>
					<button type="button"class="btn-board" onclick="javascript:location.href='<%= ctxPath%>/class/assignmentBoard.sam'">목록</button>		
			</div>
<%-- 		</c:when>
	</c:choose> --%>
	
    <br><br>
    
    
    
    

	<h3 style="text-align: left; font-weight: bold;">| 과제 제출</h3>
		<hr>
		<%--=== 과제 제출 댓글쓰기 폼  --%>
		
			<form name="submitFrm">
			
				<%--------------------------=== 과목번호 fk_subno 넣어주는 곳  -------------------------------------%>
				<input type="hidden" name="fk_subno" id="fk_subno" value="1000"/>
				<%-----------------------------=== 과목번호 fk_subno 넣어주는 곳  ------------------------------------%>
				
			<c:choose>
			<c:when test="${sessionScope.loginuser.identity eq '0'}">
				<div id="student">
				<table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
			       <tr>
					   <th style="width:200px;">내용</th>
					   <td style="text-align: center;">
					     <input style="width:100%; height: 100px;" name="content" id="submitContent"/>
					   </td>
					</tr>
					<tr>
			            <th>파일첨부</th>
			            <td>
			               <input type="file" name="attach" id="attach" />
			            </td>
			         </tr>
				</table>
				<div id="btn-board">
					<button type="button" class="btn-board" id="submit" onclick="goAddSubmit()">제출</button>			
					<!-- <button type="button" class="btn-board" id="submitDelete">삭제</button>	 -->	
				</div>
				</div>
			</c:when>
			</c:choose>
			
			
		    <!-- ===== #94. 댓글 내용 보여주기 ===== -->
		<table id="table2" style="margin-top: 2%; margin-bottom: 3%; width:100%;">
			<thead>
			<tr>
			    <th style="width: 7%; text-align: center;">번호</th>
				<th style="width: 25%; text-align: center;">내용</th>
				<th style="width: 20%; text-align: center;">첨부파일(bytes)</th>
				<th style="width: 15%; text-align: center;">이름(학번)</th>
				<th style="width: 14%; text-align: center;">제출일시</th>
				
				<c:if test="${sessionScope.loginuser.identity eq '1'}">
					<th style="width: 15%; text-align: center;">점수</th>
				</c:if>
			</tr>
			</thead>
			<tbody id="submitDisplay"></tbody>
		</table>
	<!-- 	<div id="btn-board">
			<div id="student"><button type="button" class="btn-board" id="submitEdit" >수정</button></div>
		</div>	 -->
		
		<%-- ==== #136. 댓글 페이지바 ==== --%>
	    <div id="pageBar" style="border:solid 0px gray; width: 90%; margin: 10px auto; text-align: center;"></div> 
	     
			<input type="hidden" name="fk_assgnno" value="${requestScope.assignmentVO.assgnno}"/>
			<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}"/>
		</form>

 
 
 	<form name="assgnDelFrm">	
		<input type="hidden" name="assgnno" value="${requestScope.assignmentVO.assgnno}"/>
	</form>
 
 
 
</div>




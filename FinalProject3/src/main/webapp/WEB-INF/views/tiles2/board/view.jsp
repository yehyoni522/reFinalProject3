<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
  
#table2 {
	width: 100%;
}
.putcomment{
	border-bottom: 1px solid #ccc;
	/* margin: 10px 0 10px 0; */
}

.move {cursor: pointer;}
.moveColor {color: blue; font-weight: bold; }
a {
	text-decoration: none !important;
	color:black;
}
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
	left: 88%;
	width: 180px;
}
#contnentsubj{
	margin: 10px;
	font-size: 12pt;
}
#contentinfo{
	margin-left: 10px;
	margin-top: 10px;
}
#boardcontent{
	width:80%; 
	/* height: 500px;  */
	word-break: break-all;
	border: 0px solid blue; 
	padding: 2px; 
	margin: 15px 5px 5px 10px;
}
#comname{
	font-weight: bold;
	padding-top: 5px;
}
#comdate{
	font-size: 8pt;
	margin: 5px 0 5px 0;
}
#boardcomment{
	margin-bottom: 10px;
}
#commentContent{
	width: 96%;
	height: 34px;	
}
#comwriteimg{
	width:39px; 
	height:38px; 
	cursor:pointer;
	position:relative;
	top: -2.5px;	
}
.viewbtns{
	background-color: white;
	border: solid #ccc 1px;
	height: 30px;
}	
#commentdel, #commentedit{
	margin-left: 5px;
}
#commentfunc{
	border: 0px #ccc solid;
	font-size: 10pt;
	cursor:pointer;	
	margin-left: 10px;
}
#fileDown{
	 text-align: right;
}
#goodbtn{
	cursor:pointer;
}
#whoWrite{
	margin: 0 5px 0px 5px;
	padding: 0.5px ;
	border: 2px green solid;
	border-radius: 20%;	
}
.combtn{
	background-color: white;
	border-radius: 20%;	
	border: 1px solid #ccc;
	padding: 2px;
	position:relative;
	left: 84%;
}
.combtn2{
	background-color: white;
	border-radius: 20%;	
	border: 1px solid #ccc;
	padding: 2px;
	position:relative;
}
#comcontreply{
	margin-top: 5px;
}
#noSpan{
	border: 0px green solid;
	margin: 0 35px 0px 5px;
	padding: 0.5px ;
}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#comEditFrm").hide();
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
			   
				goViewComment(1); // 페이징처리 한 댓글 읽어오기 
			   
			   $("input#commentContent").val("");
			   
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goAddWrite(){}--------------------------	
	
	// Ajax로 불러온 댓글내용을 페이징처리하기
	function goViewComment(currentShowPageNo) {
		
		$.ajax({
			url:"<%= ctxPath%>/board/commentList.sam",
			data:{"fk_seq":"${requestScope.boardvo.seq}",
				  "currentShowPageNo":currentShowPageNo},
			dataType:"json",
			success:function(json){ 
				
				var html = "";
				
				if(json.length > 0) {
							
					$.each(json, function(index, item){		
						
						var content = '"'+item.content+'"';						
						
						<!-- 답변 댓글이 아닌 원 댓글인 경우 -->
						if(item.co_depthno == 0){
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<div class='putcomment' style='background-color:#e6f2ff;' >";
							}
							else{
								html += "<div class='putcomment'>";
							}
							html += "<input type='hidden' value='"+item.comseq+"'/>"
							
							if( (item.fk_perno == ${boardvo.fk_perno}) && ${boardvo.namecheck == 0}){
								html += "<div id='comname'>&nbsp;"+ item.name+"<span id='whoWrite'>작성자</span>";						
							}
							else if(item.identity == 2){
								html += "<div id='comname'>&nbsp;"+ item.name+"<span id='whoWrite'>관리자</span>";
							}
							else{
									html += "<div id='comname'>&nbsp;"+ item.name+"<span id='noSpan'>&nbsp;</span>";
							}							
							
							html += "<span id='commentfunc'>";
							html += "<c:if test='${sessionScope.loginuser.perno ne null}'>";
																
						
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<span id='commentreply' ><button class='combtn' type='button' onclick='commentreply("+item.comseq+")'>답글</button></span>";
								html += "<span id='commentedit'><button class='combtn' type='button' onclick='commentedit("+item.comseq+","+content+")'>수정</button></span>";
								html += "<span id='commentdel'><button class='combtn' type='button' onclick='commentdel("+item.comseq+")'>삭제</button></span>";
							}
							else{
								html += "<span id='commentreply' ><button class='combtn' type='button' onclick='commentreply("+item.comseq+")'>답글</button></span>";
							}
							
							html += "</span></c:if></div>";	
							
							
							html += "<div id='comcont"+item.comseq+"'>&nbsp;"+item.content+"</div>";
							
							html += "<div id='comEditFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='comcontEdit' row='10' style='width: 90%; height:80px;'>"+item.content+"</textarea><br>";
							html += "<button id='comEditEnd' style='height:50px; width:80px;' onclick='comEditEnd("+item.comseq+")'>수정 완료</button>"
							html += "</div>"						
								
							html += "<div id='comdate'>&nbsp;"+item.reregDate+"</div>";
							
							html += "</div>";
							
							html += "<div id='comreplyFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='commentreply"+item.comseq+"' row='10' style='width: 90%; height:80px;'></textarea><br>";
							html += "<button id='comreplyEnd' style='height:50px; width:70px;' onclick='comreplyEnd("+item.comseq+","+item.co_groupno+","+item.co_depthno+","+item.fk_seq+")'>등록</button>"
							html += "</div>"
							
							html += "<div id='coeditInput'></div>"	 
						} // 원댓글인 경우
						
						<!-- 답변 댓글인 경우 -->
						if(item.co_depthno > 0){
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<div class='putcomment' style='background-color:#e6f2ff;' >";
							}
							else{
								html += "<div class='putcomment'>";
							}
												
							html += "<input type='hidden' value='"+item.comseq+"'/>"
							
							if(item.fk_perno == ${boardvo.fk_perno}){
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>┗Re&nbsp;</span>&nbsp;"+ item.name+"<span id='whoWrite'>작성자</span>";						
							}
							else if(item.identity == 2){
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>┗Re&nbsp;</span>&nbsp;"+ item.name+"<span id='whoWrite'>관리자</span>";
							}
							else{
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>┗Re&nbsp;</span>&nbsp;"+ item.name+"<span id='noSpan'>&nbsp;</span>";
							}
							
							
							html += "<span id='commentfunc'>";
							html += "<c:if test='${sessionScope.loginuser.perno ne null}'>";
																
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<span id='commentreply' ><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentreply("+item.comseq+")' >답글</button></span>";
								html += "<span id='commentedit'><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentedit("+item.comseq+","+content+" )'>수정</button></span>";
								html += "<span id='commentdel'><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentdel("+item.comseq+")' >삭제</button></span>";
							}
							else{
								html += "<span id='commentreply' ><button class='combtn2' type='button' onclick='commentreply("+item.comseq+")' style='left: "+(81-item.co_depthno)+";'>답글</button></span>";
							}
							
							html += "</span></c:if></div>";	
							
							
							html += "<div style='padding-left: "+(item.co_depthno * 14)+"px;' id='comcont"+item.comseq+"'>&nbsp;"+item.content+"</div>";
							
							html += "<div id='comEditFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='comcontEdit' row='10' style='width: 90%; height:80px;'>"+item.content+"</textarea><br>";
							html += "<button id='comEditEnd' style='height:50px; width:80px;' onclick='comEditEnd("+item.comseq+")'>수정 완료</button>"
							html += "</div>"						
								
							html += "<div style='padding-left: "+(item.co_depthno * 14)+"px;' id='comdate'>&nbsp;"+item.reregDate+"</div>";
							
							html += "</div>";
							
							html += "<div id='comreplyFrm"+item.comseq+"' style='display:none;'>"
							html += "<label>익명</label> <input type='checkbox' name='namecheck' id='namecheck' value='1'/><br>";
							html += "<textarea id='commentreply"+item.comseq+"' row='10' style='width: 90%; height:80px;'></textarea><br>";
							html += "<button id='comreplyEnd' style='height:50px; width:70px;' onclick='comreplyEnd("+item.comseq+","+item.co_groupno+","+item.co_depthno+","+item.fk_seq+")'>등록</button>"
							html += "</div>"
							
							html += "<div id='coeditInput'></div>"	
						} // end of 답변글인 경우
					
					});
				}
				else {
					html += "<div style='text-align:center;'>댓글이 없습니다</div>";
				}
				
				$("div#commentDisplay").html(html);
				
				// 페이지바 함수 호출
				makeCommentPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	
	// 댓글내용 페이지바  Ajax로 만들기
	function makeCommentPageBar(currentShowPageNo) {
	
		$.ajax({
			url:"<%= ctxPath%>/board/getCommentTotalPage.sam",
			data:{"fk_seq":"${requestScope.boardvo.seq}",
				  "sizePerPage":"5"},
			type:"get",
			dataType:"json",
			success:function(json) {				
				if(json.totalPage > 0) {
					// 댓글이 있는 경우 
					
					var totalPage = json.totalPage;					
					var pageBarHTML = "<ul style='list-style: none;'>";					
					var blockSize = 5;					
					var loop = 1;
				    
				    if(typeof currentShowPageNo == "string") {
				    	currentShowPageNo = Number(currentShowPageNo);
				    }
				    
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;								    
						
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					}
				
					while( !(loop > blockSize || pageNo > totalPage) ) {
					
						if(pageNo == currentShowPageNo) {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
						}
						else {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>"+pageNo+"</a></li>";
						}
						
						loop++;
						pageNo++;
					}// end of while------------------------
								
					if(pageNo <= totalPage) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>[다음]</a></li>";
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
	
	// 글 삭제하기
	function removeCheck() {

		if (confirm("정말 삭제하시겠습니까??") == true){    //확인
			
		 var frm = document.delFrm;
		   	frm.method = "POST";
		   	frm.action = "<%= ctxPath%>/board/del.sam";
		   	frm.submit();
		   	
		}else{   //취소	
			return false;	
		}

	} // end of function removeCheck() {} 글삭제하기
	
	// 댓글 답댓글 달기
	function commentreply(comseq){
		$("div#comreplyFrm"+comseq).show();	
	} // end of function commentreply(){} 댓글 답댓글달기
	
	// 댓글 답댓글 등록 버튼 클릭
	function comreplyEnd(comseq, co_groupno, co_depthno, fk_seq){
		
		var content = $("textarea#commentreply"+comseq).val().trim();
		
		// alert("결과내용 : "+comreplyVal+"co_groupno"+co_groupno);
		
		if(content == "") {
           alert("댓글내용을 입력하세요!!");
           return;
        } 
			 
		$.ajax({
			   url:"<%= ctxPath%>/board/addComment.sam",
			   type:"post",
			   data:{"fk_comseq":comseq,
				   	 "co_groupno":co_groupno,
				   	 "co_depthno":co_depthno,
				   	 "fk_seq":fk_seq,
				   	 "fk_perno":"${sessionScope.loginuser.perno}",
				     content},
			   dataType:"json",
			   success:function(json){
				   goViewComment(1);		   
			   },
			   error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			   }
		   });	   	    
		
	} // end of function comreplyEnd(comseq){}
	
	// 댓글 수정 버튼 클릭
	function commentedit(comseq, content){
		// alert("댓글 수정하기 클릭, 댓글번호와 내용: "+comseq+content);
		
		$("div#comcont"+comseq).hide();
		$("div#comEditFrm"+comseq).show();					
		
	} // end of function commentedit(){} 댓글 수정하기
	
	
	// 댓글 수정완료 버튼 클릭
	function comEditEnd(comseq){
		
		var comEditVal = $("textarea#comcontEdit").val().trim();
        				
		if(comEditVal == "") {
           alert("댓글내용을 입력하세요!!");
           return;
        } 
		
		 // alert("수정댓글내용 : "+comEditVal);
		 
		$.ajax({
			   url:"<%= ctxPath%>/board/comEditEnd.sam",
			   type:"post",
			   data:{"comseq":comseq,
				     comEditVal},
			   dataType:"json",
			   success:function(json){
				   goViewComment(1);		   
			   },
			   error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			   }
		   });	   		       
	}

	
	// 댓글삭제하기
	function commentdel(comseq){

		  //alert(${requestScope.boardvo.seq});
		
		if (confirm("정말 삭제하시겠습니까??") == true){    //확인
					  
		 $.ajax({
			   url:"<%= ctxPath%>/board/commentdel.sam",
			   type:"get",
			   data:{"comseq":comseq,
				     "fk_seq":"${requestScope.boardvo.seq}"},
			   dataType:"json",
			   success:function(json){
				   
				   goViewComment(1);		   
			   },
			   error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			   }
		   });
		
		}else{   //취소	
			return false;	
		}
	} // end of function commentdel(){} 댓글 삭제하기
	
 	function goGoodAdd(seq){
		
		alert("좋아요 클릭");
		if( ${empty sessionScope.loginuser} ) {
			   alert("좋아요 하시려면 먼저 로그인 하셔야 합니다.");
			   return; // 종료 
		   }
		   
		$.ajax({
	   		url:"<%= ctxPath%>/board/goGoodAdd.sam",
		   	type:"get",
		   	data:{"seq":seq},
		   	dataType:"json",
		   	success:function(json){
				goLikeCount(); // 좋아요 수 보여주기
		   	},
		   	error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		   	}
	   	});	    
	} // end of function goGoodAdd(seq){}
	
	
	function goLikeCount(){
		
		$.ajax({
	   		url:"<%= ctxPath%>/board/likeCount.sam",
		   	data:{"seq":"${boardvo.seq}"},
		   	dataType:"json",
		   	success:function(json){
			 	$("span#goodCount").html(json.likecnt);
		   	},
		   	error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		   	}
	   	});
	} 
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
		<div id="viewcontent" >
			<div id="contnentsubj">
				${requestScope.boardvo.subject}
			</div>
			<hr class="styhr">
			<div id="contentinfo">			
				${requestScope.boardvo.name} &nbsp; ${requestScope.boardvo.regDate}
			</div>
			<div id="fileDown"> 
				<c:if test="${requestScope.boardvo.orgFilename != null}"> 
				<a href="<%=ctxPath%>/board/download.sam?seq=${requestScope.boardvo.seq}&categoryno=${categoryno}"> ${requestScope.boardvo.orgFilename} 
			    <span>(<fmt:formatNumber value="${requestScope.boardvo.fileSize}" pattern="#,###"/>&nbsp;byte)</span></a>
				</c:if>
			</div>

			<br>
			<div id="boardcontent">
				<p>${requestScope.boardvo.content}</p>
			</div>	
			<div id="contentfooter">
				<img src="<%=ctxPath%>/resources/images/good.PNG"  id="goodbtn" style="width:45px; height:43px;" onclick="goGoodAdd(${boardvo.seq})">
					<span id ="goodCount" style="color:red; font-weight:bold;">${requestScope.boardvo.good}</span>
				<img src="<%=ctxPath%>/resources/images/comment.PNG" style="width:44px; height:38px;">
					<span style="color:blue; font-weight:bold;">${requestScope.boardvo.commentCount}</span>
			</div>	
			<hr class="styhr">
		</div>		
		<br>
		
		<!-- 공지사항 게시판에서는 댓글사용 안함 -->
		<c:if test="${categoryno != 4}">	
			<div id="boardcomment">										      			    		    
			    <!-- 댓글 내용 보여주기 -->			   
				<div id="table2" >
					<div id="commentDisplay"></div>
				</div>
			
				<!-- 댓글 페이지바-->
    			<div id="pageBar" style="border:solid 0px gray; width: 90%; margin: 10px auto; text-align: center;"></div> 
	 
				<%-- 댓글쓰기 --%> 
				<c:if test="${not empty sessionScope.loginuser}">
					<form name="addWriteFrm" style="margin-top: 20px;">
						<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}" />  
						<h3>댓글작성</h3>
						<input id="commentContent" type="text" name="content" class="long" /> 						
						<input type="hidden" name="fk_seq" value="${requestScope.boardvo.seq}" /> 
						<img src="<%=ctxPath%>/resources/images/commentWrite.PNG" id= "comwriteimg" onclick="goAddWrite()">
					</form>
			    </c:if>		 			
			</div>	
   		</c:if> 
   		
	 	<c:set var="gobackURL2" value='${fn:replace(requestScope.gobackURL, "&", " ") }' />
	 	<c:if test="${requestScope.boardvo.previoussubject ne null}">
	 		<div style="margin-bottom: 1%;">
	 			<span style="font-weight:bold;">이전글제목&nbsp;&nbsp;</span>
	 			<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.previousseq}&categoryno=${categoryno}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${gobackURL2}'">${requestScope.boardvo.previoussubject}</span>
	 		</div>
	 	</c:if>
	 	<c:if test="${requestScope.boardvo.nextsubject ne null}">	
			<div style="margin-bottom: 1%;">
				<span style="font-weight:bold;">다음글제목&nbsp;&nbsp;</span>
				<span class="move" onclick="javascript:location.href='/board/view.sam?seq=${requestScope.boardvo.nextseq}&categoryno=${categoryno}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${gobackURL2}'">${requestScope.boardvo.nextsubject}</span>
			</div>
		</c:if>
	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
	<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/list.sam?categoryno=${categoryno}'">전체목록보기</button>
	
	<c:if test="${categoryno == 1}"> <!-- 자유게시판에서만 답글기능 사용  -->
		<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/add.sam?categoryno=${boardvo.categoryno}&fk_seq=${requestScope.boardvo.seq}&groupno=${requestScope.boardvo.groupno}&depthno=${requestScope.boardvo.depthno}'">답글</button>
	</c:if>
	
	<c:if test="${sessionScope.loginuser.perno == boardvo.fk_perno}"> <!-- 보고있는 글이 내글일 경우  -->
		<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/edit.sam?seq=${requestScope.boardvo.seq}'">수정</button>
		<button type="button" class="viewbtns" onclick="removeCheck()">삭제</button>
	</c:if>
 	<%-- <br><span>${requestScope.gobackURL}</span> --%>
</div>

<form name="delFrm"> 
	<input type="hidden" name="categoryno" value="${categoryno}" />
    <input type="hidden" name="seq" value="${requestScope.boardvo.seq}" />          
</form>

    
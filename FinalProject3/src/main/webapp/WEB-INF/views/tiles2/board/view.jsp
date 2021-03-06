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
	left: 82%;
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
		goViewComment(1); // ??????????????? ??? ?????? ???????????? 

		
		$("span.move").hover(function(){
			                    $(this).addClass("moveColor");
			                }
	                        ,function(){
	                        	$(this).removeClass("moveColor");
	                        });
		
	});// end of $(document).ready(function(){})------------------

	
	// === ???????????? === //
	function goAddWrite() {
		
		var contentVal = $("input#commentContent").val().trim();
		if(contentVal == "") {
			alert("?????? ????????? ???????????????!!");
			return; // ??????
		}
		
		var form_data = $("form[name=addWriteFrm]").serialize();
		
		$.ajax({
			url:"<%= ctxPath%>/board/addComment.sam",
			data:form_data,
			type:"post",
			dataType:"json",
			success:function(json){ 
		   		var n = json.n;
			   
				goViewComment(1); // ??????????????? ??? ?????? ???????????? 
			   
			   $("input#commentContent").val("");
			   
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goAddWrite(){}--------------------------	
	
	// Ajax??? ????????? ??????????????? ?????????????????????
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
						
						<!-- ?????? ????????? ?????? ??? ????????? ?????? -->
						if(item.co_depthno == 0){
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<div class='putcomment' style='background-color:#e6f2ff;' >";
							}
							else{
								html += "<div class='putcomment'>";
							}
							html += "<input type='hidden' value='"+item.comseq+"'/>"
							
							if( (item.fk_perno == ${boardvo.fk_perno}) && ${boardvo.namecheck == 0}){
								html += "<div id='comname'>&nbsp;"+ item.name+"<span id='whoWrite'>?????????</span>";						
							}
							else if(item.identity == 2){
								html += "<div id='comname'>&nbsp;"+ item.name+"<span id='whoWrite'>?????????</span>";
							}
							else{
									html += "<div id='comname'>&nbsp;"+ item.name+"<span id='noSpan'>&nbsp;</span>";
							}							
							
							html += "<span id='commentfunc'>";
							html += "<c:if test='${sessionScope.loginuser.perno ne null}'>";
																
						
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<span id='commentreply' ><button class='combtn' type='button' onclick='commentreply("+item.comseq+")'>??????</button></span>";
								html += "<span id='commentedit'><button class='combtn' type='button' onclick='commentedit("+item.comseq+","+content+")'>??????</button></span>";
								html += "<span id='commentdel'><button class='combtn' type='button' onclick='commentdel("+item.comseq+")'>??????</button></span>";
							}
							else{
								html += "<span id='commentreply' ><button class='combtn' type='button' onclick='commentreply("+item.comseq+")'>??????</button></span>";
							}
							
							html += "</span></c:if></div>";	
							
							
							html += "<div id='comcont"+item.comseq+"'>&nbsp;"+item.content+"</div>";
							
							html += "<div id='comEditFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='comcontEdit' row='10' style='width: 90%; height:80px;'>"+item.content+"</textarea><br>";
							html += "<button id='comEditEnd' style='height:50px; width:80px;' onclick='comEditEnd("+item.comseq+")'>?????? ??????</button>"
							html += "</div>"						
								
							html += "<div id='comdate'>&nbsp;"+item.reregDate+"</div>";
							
							html += "</div>";
							
							html += "<div id='comreplyFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='commentreply"+item.comseq+"' row='10' style='width: 90%; height:80px;'></textarea><br>";
							html += "<button id='comreplyEnd' style='height:50px; width:70px;' onclick='comreplyEnd("+item.comseq+","+item.co_groupno+","+item.co_depthno+","+item.fk_seq+")'>??????</button>"
							html += "</div>"
							
							html += "<div id='coeditInput'></div>"	 
						} // ???????????? ??????
						
						<!-- ?????? ????????? ?????? -->
						if(item.co_depthno > 0){
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<div class='putcomment' style='background-color:#e6f2ff;' >";
							}
							else{
								html += "<div class='putcomment'>";
							}
												
							html += "<input type='hidden' value='"+item.comseq+"'/>"
							
							if(item.fk_perno == ${boardvo.fk_perno}){
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>???Re&nbsp;</span>&nbsp;"+ item.name+"<span id='whoWrite'>?????????</span>";						
							}
							else if(item.identity == 2){
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>???Re&nbsp;</span>&nbsp;"+ item.name+"<span id='whoWrite'>?????????</span>";
							}
							else{
								html += "<div id='comname'><span style='color: red; font-style: italic; padding-left: "+(item.co_depthno * 8)+"px;'>???Re&nbsp;</span>&nbsp;"+ item.name+"<span id='noSpan'>&nbsp;</span>";
							}
							
							
							html += "<span id='commentfunc'>";
							html += "<c:if test='${sessionScope.loginuser.perno ne null}'>";
																
							if(${sessionScope.loginuser.perno} == item.fk_perno){
								html += "<span id='commentreply' ><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentreply("+item.comseq+")' >??????</button></span>";
								html += "<span id='commentedit'><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentedit("+item.comseq+","+content+" )'>??????</button></span>";
								html += "<span id='commentdel'><button class='combtn2' type='button' style='left: "+(81-item.co_depthno)+";' onclick='commentdel("+item.comseq+")' >??????</button></span>";
							}
							else{
								html += "<span id='commentreply' ><button class='combtn2' type='button' onclick='commentreply("+item.comseq+")' style='left: "+(81-item.co_depthno)+";'>??????</button></span>";
							}
							
							html += "</span></c:if></div>";	
							
							
							html += "<div style='padding-left: "+(item.co_depthno * 14)+"px;' id='comcont"+item.comseq+"'>&nbsp;"+item.content+"</div>";
							
							html += "<div id='comEditFrm"+item.comseq+"' style='display:none;'>"
							html += "<textarea id='comcontEdit' row='10' style='width: 90%; height:80px;'>"+item.content+"</textarea><br>";
							html += "<button id='comEditEnd' style='height:50px; width:80px;' onclick='comEditEnd("+item.comseq+")'>?????? ??????</button>"
							html += "</div>"						
								
							html += "<div style='padding-left: "+(item.co_depthno * 14)+"px;' id='comdate'>&nbsp;"+item.reregDate+"</div>";
							
							html += "</div>";
							
							html += "<div id='comreplyFrm"+item.comseq+"' style='display:none;'>"
							html += "<label>??????</label> <input type='checkbox' name='namecheck' id='namecheck' value='1'/><br>";
							html += "<textarea id='commentreply"+item.comseq+"' row='10' style='width: 90%; height:80px;'></textarea><br>";
							html += "<button id='comreplyEnd' style='height:50px; width:70px;' onclick='comreplyEnd("+item.comseq+","+item.co_groupno+","+item.co_depthno+","+item.fk_seq+")'>??????</button>"
							html += "</div>"
							
							html += "<div id='coeditInput'></div>"	
						} // end of ???????????? ??????
					
					});
				}
				else {
					html += "<div style='text-align:center;'>????????? ????????????</div>";
				}
				
				$("div#commentDisplay").html(html);
				
				// ???????????? ?????? ??????
				makeCommentPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	
	// ???????????? ????????????  Ajax??? ?????????
	function makeCommentPageBar(currentShowPageNo) {
	
		$.ajax({
			url:"<%= ctxPath%>/board/getCommentTotalPage.sam",
			data:{"fk_seq":"${requestScope.boardvo.seq}",
				  "sizePerPage":"5"},
			type:"get",
			dataType:"json",
			success:function(json) {				
				if(json.totalPage > 0) {
					// ????????? ?????? ?????? 
					
					var totalPage = json.totalPage;					
					var pageBarHTML = "<ul style='list-style: none;'>";					
					var blockSize = 5;					
					var loop = 1;
				    
				    if(typeof currentShowPageNo == "string") {
				    	currentShowPageNo = Number(currentShowPageNo);
				    }
				    
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;								    
						
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+(pageNo-1)+"\")'>[??????]</a></li>";
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
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>[??????]</a></li>";
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
	
	// ??? ????????????
	function removeCheck() {

		if (confirm("?????? ??????????????????????????") == true){    //??????
			
		 var frm = document.delFrm;
		   	frm.method = "POST";
		   	frm.action = "<%= ctxPath%>/board/del.sam";
		   	frm.submit();
		   	
		}else{   //??????	
			return false;	
		}

	} // end of function removeCheck() {} ???????????????
	
	// ?????? ????????? ??????
	function commentreply(comseq){
		$("div#comreplyFrm"+comseq).show();	
	} // end of function commentreply(){} ?????? ???????????????
	
	// ?????? ????????? ?????? ?????? ??????
	function comreplyEnd(comseq, co_groupno, co_depthno, fk_seq){
		
		var content = $("textarea#commentreply"+comseq).val().trim();
		
		// alert("???????????? : "+comreplyVal+"co_groupno"+co_groupno);
		
		if(content == "") {
           alert("??????????????? ???????????????!!");
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
	
	// ?????? ?????? ?????? ??????
	function commentedit(comseq, content){
		// alert("?????? ???????????? ??????, ??????????????? ??????: "+comseq+content);
		
		$("div#comcont"+comseq).hide();
		$("div#comEditFrm"+comseq).show();					
		
	} // end of function commentedit(){} ?????? ????????????
	
	
	// ?????? ???????????? ?????? ??????
	function comEditEnd(comseq){
		
		var comEditVal = $("textarea#comcontEdit").val().trim();
       
		
		if(comEditVal == "") {
           alert("??????????????? ???????????????!!");
           return;
        } 
		
		 // alert("?????????????????? : "+comEditVal);
		 
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

	
	// ??????????????????
	function commentdel(comseq){

		  //alert(${requestScope.boardvo.seq});
		
		if (confirm("?????? ??????????????????????????") == true){    //??????
					  
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
		
		}else{   //??????	
			return false;	
		}
	} // end of function commentdel(){} ?????? ????????????
	
 	function goGoodAdd(seq){
		
		alert("????????? ??????");
		if( ${empty sessionScope.loginuser} ) {
			   alert("????????? ???????????? ?????? ????????? ????????? ?????????.");
			   return; // ?????? 
		   }
		   
		$.ajax({
	   		url:"<%= ctxPath%>/board/goGoodAdd.sam",
		   	type:"get",
		   	data:{"seq":seq},
		   	dataType:"json",
		   	success:function(json){
				goLikeCount(); // ????????? ??? ????????????
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

	<div id="boardmenu" style="padding-left: 25%;">
		<ul>
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=1">???????????????</a><span style="border-right: 2px black solid; margin: 0 80px 0 80px;"></span></li>
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=2">????????????</a><span style="border-right: 2px black solid; margin: 0 80px 0 80px;"></span></li>	
			<li style='display:inline-block; font-size: 20pt;'><a class="boarda" href="<%=ctxPath%>/board/list.sam?categoryno=3">?????????&????????? ??????</a></li>	
		</ul>	
	</div>	

</c:if> 

<div style="padding-left: 10%; padding-right:10%;">
	<h2 style="margin-bottom: 30px;">
		<c:if test="${categoryno == 1}">???????????????</c:if>
		<c:if test="${categoryno == 2}">????????????</c:if>
		<c:if test="${categoryno == 3}">?????????&????????? ??????</c:if> 
		<c:if test="${categoryno == 4}">????????????</c:if> 
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
				<c:if test="${boardvo.namecheck == 1}">
		       		?????? &nbsp; ${requestScope.boardvo.regDate}
		       </c:if>
		       <c:if test="${boardvo.namecheck != 1}">
		       		${requestScope.boardvo.name} &nbsp; ${requestScope.boardvo.regDate}
		       </c:if>
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
		
		<!-- ???????????? ?????????????????? ???????????? ?????? -->
		<c:if test="${categoryno != 4}">	
			<div id="boardcomment">										      			    		    
			    <!-- ?????? ?????? ???????????? -->			   
				<div id="table2" >
					<div id="commentDisplay"></div>
				</div>
			
				<!-- ?????? ????????????-->
    			<div id="pageBar" style="border:solid 0px gray; width: 90%; margin: 10px auto; text-align: center;"></div> 
	 
				<%-- ???????????? --%> 
				<c:if test="${not empty sessionScope.loginuser}">
					<form name="addWriteFrm" style="margin-top: 20px;">
						<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}" />  
						<h3>????????????</h3>
						<input type="hidden" name="fk_seq" value="${requestScope.boardvo.seq}" /> 
						<input hidden="hidden" />
						<input id="commentContent" type="text" name="content" class="long" autocomplete=???off???/> 						
						<img src="<%=ctxPath%>/resources/images/commentWrite.PNG" id= "comwriteimg" onclick="goAddWrite()">
					</form>
			    </c:if>		 			
			</div>	
   		</c:if> 
   		
	 	<c:set var="gobackURL2" value='${fn:replace(requestScope.gobackURL, "&", " ") }' />
	 	<c:if test="${requestScope.boardvo.previoussubject ne null}">
	 		<div style="margin-bottom: 1%;">
	 			<span style="font-weight:bold;">???????????????&nbsp;&nbsp;</span>
	 			<span class="move" onclick="javascript:location.href='<%=ctxPath%>/board/view.sam?seq=${requestScope.boardvo.previousseq}&categoryno=${categoryno}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}'">${requestScope.boardvo.previoussubject}</span>
	 		</div>
	 	</c:if>
	 	<c:if test="${requestScope.boardvo.nextsubject ne null}">	
			<div style="margin-bottom: 1%;">
				<span style="font-weight:bold;">???????????????&nbsp;&nbsp;</span>
				<span class="move" onclick="javascript:location.href='<%=ctxPath%>/board/view.sam?seq=${requestScope.boardvo.nextseq}&categoryno=${categoryno}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}'">${requestScope.boardvo.nextsubject}</span>
			</div>
		</c:if>
	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">???????????? ????????????</div>
	</c:if>
	
	<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/list.sam?categoryno=${categoryno}'">??????????????????</button>
	
	<c:if test="${categoryno == 1}"> <!-- ???????????????????????? ???????????? ??????  -->
		<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/add.sam?categoryno=${boardvo.categoryno}&fk_seq=${requestScope.boardvo.seq}&groupno=${requestScope.boardvo.groupno}&depthno=${requestScope.boardvo.depthno}'">??????</button>
	</c:if>
	
	<c:if test="${sessionScope.loginuser.perno == boardvo.fk_perno}"> <!-- ???????????? ?????? ????????? ??????  -->
		<button type="button" class="viewbtns" onclick="javascript:location.href='<%= ctxPath%>/board/edit.sam?seq=${requestScope.boardvo.seq}'">??????</button>
		<button type="button" class="viewbtns" onclick="removeCheck()">??????</button>
	</c:if>
 	<%-- <br><span>${requestScope.gobackURL}</span> --%>
</div>

<form name="delFrm"> 
	<input type="hidden" name="categoryno" value="${categoryno}" />
    <input type="hidden" name="seq" value="${requestScope.boardvo.seq}" />          
</form>

    
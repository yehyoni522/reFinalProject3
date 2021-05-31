<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

     
    .subjectStyle {font-weight: bold;
                   cursor: pointer;} 
    #table th, #table td {padding: 5px;}       
	table{
		width:100%;
	
		border-bottom: solid 1.5px #b3b3b3;;
	}
	
	th{
		background-color: #f2f2f2;
	}
	
	tr{
		border-top: solid 0.5px #bfbfbf;
		border-bottom: solid 0.5px #bfbfbf;
		height:40px;
	}
	
	tr.list:hover{
		background-color: #fafafa;
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
	
	.select{
	 	padding:5px;
	 	border-radius: 5px;
	 	height: 30px;
		border:0.5px solid #a6a6a6;	 	
	}
	
	.search{
		width:320px;
		border:0.5px solid #a6a6a6;
	 	padding:5px;
	 	border-radius: 5px;
	 	height: 30px;	 	
	}
	
	.comment{
		display: inline-block;
		border:0.5px solid #a6a6a6;	
		border-radius: 7px;
		width: 20px;
		text-align: center;
		color:red;
		font-weight: bold;
		font-size: 8pt;
	}
	
	
	
</style>

<script type="text/javascript">

   $(document).ready(function(){

		$("span.subject").bind("mouseover",function(event){
			var $target=$(event.target);
			$target.addClass("subjectStyle");
		});
	   
		$("span.subject").bind("mouseout",function(event){
			var $target=$(event.target);
			$target.removeClass("subjectStyle");			
		});
<%--
		// 이동버튼 클릭했을 때
		$(document).on("click","[name=move]", function(){
			var boardNo=$("select#selectBoard").val();
			alert(boardNo);
			var boardname="";
			
			if(boardNo==1) {
				boardname="자유게시판";
			}
			if(boardNo==2) {
				boardname="중고거래";
			}
			if(boardNo==3) {
				boardname="동아리/공모전";
			}
			alert(boardname);
		//	alert("선택하신 글을 "+boardname+"(으)로 이동시키겠습니까?");
		});
	--%>	
		// 삭제버튼 클릭했을 때
		$(document).on("click","[name=delete]", function(){					
			alert("선택하신 글을 삭제하시겠습니까?");
		});
		
		$("input#searchWord").bind("keydown", function(event){
	          if(event.keyCode == 13){
	             goSearch();
	          }
	    });
		
	
		
		
		
		<%-- === #107. 검색어 입력시 자동글 완성하기 2 === --%>
	       $("div#displayList").hide();
	       
	       $("input#searchWord").keyup(function(){
	          
	          var wordLength = $(this).val().trim().length;
	          // 검색어의 길이를 알아온다.
	          
	          if(wordLength == 0 ) {
	             $("div#displayList").hide();
	             // 검색어가 공백이거나 검색어 입력후 백스페이스키를 눌러서 검색어를 모두 지우면 검색된 내용이 안 나오도록 해야 한다. 
	          }
	          else {
	             $.ajax({
	                url:"<%= ctxPath%>/admin/wordSearchShow.sam",
	                type:"get",
	                data:{"searchType":$("select#searchType").val()
	                    ,"searchWord":$("input#searchWord").val()
	                    ,"viewBoard":$("select#viewBoard").val()},
	                dataType:"json",
	                success:function(json){ // [] 또는  [{"word":"글쓰기 첫번째 java 연습입니다"},{"word":"글쓰기 두번째 JaVa 연습입니다"}] 
	                   <%-- === 검색어 입력시 자동글 완성하기 === --%> 
	                   if(json.length > 0) {
	                      // 검색된 데이터가 있는 경우임.
	                      
	                      var html = "";
	                      
	                      $.each(json, function(index, item){
	                         var word = item.word;
	                         // word ==> "글쓰기 첫번째 java 연습입니다"
	                         // word ==> "글쓰기 두번째 JaVa 연습입니다"
	                         
	                         var index = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase());
	                         // word ==> "글쓰기 첫번째 java 연습입니다"
	                         // word ==> "글쓰기 두번째 java 연습입니다"
	                         // 만약에 검색어가 jAva 이라면 index 는 8 이 된다.
	                         
	                         var len = $("input#searchWord").val().length;
	                         // 검색어의 길이 len = 4
	                         
	                         word = word.substr(0,index) + "<span style='color:blue;'>"+word.substr(index,len)+"</span>" + word.substr(index+len);
	                         
	                         html += "<span style='cursor:pointer;' class='word' >"+word+"</span><br>";
	                      });
	                      
	                      $("div#displayList").html(html);
	                      $("div#displayList").show();
	                   }
	                },
	                error: function(request, status, error){
	                   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	                 }
	             });
	          }
	          
	       });

	       <%-- 끝 === 검색어 입력시 자동글 완성하기  === --%>
	       
	       <%-- === 검색어 입력시 자동글 완성하기  === --%> 
	       $(document).on("click","span.word", function(){
	          $("input#searchWord").val($(this).text()); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
	          $("div#displayList").hide();
	          goSearch();
	       });
	        	       
	       //검색시 검색조건 및 검색어 값 유지시키기
	       if( ${not empty requestScope.paraMap} ){    	   
		    		$("select#viewBoard").val("${requestScope.paraMap.viewBoard}");
					$("select#searchType").val("${requestScope.paraMap.searchType}");
	          		$("input#searchWord").val("${requestScope.paraMap.searchWord}");	    	   
	       }

	       
	    // 전체 선택   
	   	$("[name=checkAll]").click(function(){
		    allCheck(this);
		    //모두동의하기 체크박스 클릭시
		});
		
	    // 개별 선택
		$("[name=checkOne]").each(function(){
		    $(this).click(function(){
		        oneCheck($(this));
		    });
		});
	       
   });// end of $(document).ready(function(){}---------------------------------------

		   
		   
// == 게시글 체크박스 함수 시작 == //

   function allCheck(obj) {
       $("[name=checkOne]").prop("checked",$(obj).prop("checked")); 
		
   }// 모두 체크하기

   function oneCheck(a){
   	var allChkBox = $("[name=checkAll]");
   	var chkBoxName = $(a).attr("name");
   	
   	if( $(a).prop("checked") ){
   		    checkBoxLength = $("[name="+ chkBoxName +"]").length;
   		    //전체체크박스 수(모두동의하기 체크박스 제외)
   		    checkedLength = $("[name="+ chkBoxName +"]:checked").length;
   		    //체크된 체크박스 수 
   		    if( checkBoxLength == checkedLength ) {
   		        allChkBox.prop("checked", true);
   		        //전체체크박스수 == 체크된 체크박스 수 같다면 모두체크
   		
   		    } else {
   		        allChkBox.prop("checked", false);
   		        
   		    }
   	}
   	else{
   	   allChkBox.prop("checked", false);
   	}
   }

   //== 게시글 체크박스 함수 끝 == //

	function goView(seq){
		   
		   location.href="<%=ctxPath%>/view.action?seq="+seq;
		   
	} // end of function goView(seq)-----------------------------
	
	function goSearch(){
		   
		   var frm = document.searchFrm;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/admin/boardlist.sam";
		   frm.submit();
		   
	}// end of function goSearch()------------------------------------
	
	
	function goPage(){
		var frm = document.selectPage;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/admin/boardlist.sam";
		   frm.submit();
	}
	<%--
	function goBoard(){
		var frm = document.viewBoard;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/admin/boardlist.sam";
		   frm.submit();
		   console.log($("#viewBoard option:selected").val());
	} 
	   --%>
	   
	function goMove(){
		
		var checkCnt = $("input:checkbox[name=checkOne]:checked").length;
	    
		if(checkCnt < 1) {
	    	alert("이동시킬 게시글을 선택하세요.");
	    	return; 
	    }	
		
		var categoryno=$("select#selectBoard").val();
		var categoryname="";
		
		if(categoryno==0){				
			alert("게시판을 선택해주세요");
			return;
		}
		if(categoryno==1) {
			categoryname="자유게시판";
		}
		if(categoryno==2) {
			categoryname="중고거래";
		}
		if(categoryno==3) {
			categoryname="동아리/공모전";
		}
		
		var bool = confirm("선택하신 글을 "+categoryname+"(으)로 이동시키겠습니까?");
		
		var allCnt = $("input:checkbox[name=checkOne]").length;
		var boardSeqArr = new Array();
		
		for(var i=0; i<allCnt; i++) {
			
			if( $("input:checkbox[name=checkOne]").eq(i).is(":checked") ) {
				boardSeqArr.push( $("input:checkbox[name=checkOne]").eq(i).val());
			}
		}
		console.log(boardSeqArr);
	
		if(bool){
			$.ajax({
				url:"<%= ctxPath%>/admin/boardMove.sam",
				type: "post",
				data: {"boardSeqArr" : boardSeqArr
					, "categoryno":categoryno},					  
				dataType: "json",
				success:function(json){
						if(json.n==1){
							location.href="<%=ctxPath%>/admin/boardlist.sam";
						}
				},
				 error: function(request, status, error){
			            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			     }  
			});
			
		}
		else{
			return;
		}

	}	  
	   
	   
</script>   

<div style="padding-left: 3%;padding-right: 3%;">
   <h2 style="margin-bottom: 30px;">| 게시글 관리</h2>
   <div style="text-align:right; vertical-align:middle; margin:5px;  ">
	   <%-- === #101. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --%>
		<form name="searchFrm" style="margin-top: 20px;">
			<select name="viewBoard" id="viewBoard" class="select" onChange="goBoard()">
			      <option value="0">게시판 전체</option>
			      <option value="1">자유게시판</option>
			      <option value="2">중고거래</option>
			      <option value="3">동아리/공모전</option>
			</select>
	      <select name="searchType" id="searchType"  class="select">	
	        <!--  <option value="total">제목+내용</option> -->
	         <option value="subject">제목</option>
	         <option value="content">내용</option>
	         <option value="name">글쓴이</option>
	      </select>
	      <input type="text" name="searchWord" class="search" id="searchWord" size="15" autocomplete="off" /> 
	      <button type="button" onclick="goSearch()" class=" btn-board">검색</button>
	   </form>
	   <div style="height:70px; " >
		   <%-- === 검색어 입력시 자동글 완성하기  === --%>
			<div id="displayList" style="border:solid 1px gray; border-top:0px; width:320px; height:70px; margin-right:118px; 
					overflow:auto; float:right; padding:5px; text-align: left;  border-radius: 5px;  box-shadow: 0.5px 0.5px 0.5px 0.5px gray;">	
			</div>
		</div>
   </div>
   
 
   <table id="table" style="width:100%; ">
   	  <tr style="border-top: none; border-bottom: none;">
   		<td colspan="3" style="text-align: left; font-size: 13pt; font-weight: bolder;">
   			게시글 <span style="color:#53c68c;">${requestScope.totalCount}</span></td>
   		<td colspan="5" style="text-align: right;">
	      	<form name="selectPage">
		    <select name="page" id="page" style="width:100px; margin-right:5px;" class="select" onChange="goPage()">
		      <option>게시글 수</option>
		      <option value="5">5개씩</option>
		      <option value="15">15개씩</option>
		      <option value="30">30개씩</option>
		    </select>
		    </form>
		</td>
   	  </tr>
      <tr style="border-top:solid 1.5px #b3b3b3;">
         <td style="text-align: center;">
         	<input type="checkbox"  name="checkAll" />
		 </td>
		 <td></td>
         <td style="text-align: center;">
 <!--        <form name="viewBoard">
			    <select name="selectBoard" id="selectBoard" class="select" onChange="goBoard()">
			      <option selected="selected">게시판 전체</option>
			      <option value="1">자유게시판</option>
			      <option value="2">중고거래</option>
			      <option value="3">동아리/공모전</option>
			    </select>
		 </form>
		  --> 
         </td>
         
         <td colspan="5" style="text-align:right; vertical-align: middle;">
         	<form name="moveCategory">
         	<span style="font-weight: bold; font-size: 13pt;">선택한 글</span>
			    <select name="selectBoard" id="selectBoard" class="select">
			      <option selected="selected" value="0">게시판 선택</option>
			      <option value="1">자유게시판</option>			     
			      <option value="2">중고거래</option>
			      <option value="3">동아리/공모전</option>
			    </select>
			<input type="button" value="이동" class=" btn-board" name="move" onclick="goMove()"/>
			<input type="button" value="삭제" class=" btn-board" name="delete" onclick="goDelete()"/>
			</form>
		</td>
				
      </tr>
      <tr>
      	 <th style="width: 3%;  text-align: center;"></th>
      	 <th style="width: 5%;  text-align: center;">No.</th>
         <th style="width: 10%;  text-align: center;">게시판 명</th>
         <th style="width: 35%;  text-align: center;">제목</th>
         <th style="width: 15%;">글쓴이</th>
         <th style="width: 20%;  text-align: center;">작성일</th>
         <th style="width: 5%;  text-align: center;">추천수</th>
         <th style="width: 5%;  text-align: center;">조회수</th>
      
      </tr>
      
      <c:forEach var="boardvo" items="${requestScope.boardList}" varStatus="status"> 
         <tr class="list">  	
         	<td style="text-align: center;">
         		<input type="checkbox" name="checkOne" value="${boardvo.seq}" />
		 	</td>
         	<td align="center">${boardvo.seq}</td>
             <td align="center">
             	<c:if test="${boardvo.categoryno==1}">
             		자유게시판
             	</c:if>
             	<c:if test="${boardvo.categoryno==2}">
             		중고거래
             	</c:if>
             	<c:if test="${boardvo.categoryno==3}">
             		동아리 / 공모전
             	</c:if>
             </td>
             <td align="left">
	             <%-- === 댓글쓰기가 있는 게시판 === --%>
	             <c:if test="${boardvo.commentCount>0}">
	             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject} <span class="comment">${boardvo.commentCount}</span></span> 
	           	</c:if>
	           	<c:if test="${boardvo.commentCount==0}">
             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span>
             	</c:if>
             </td>
            <td>${boardvo.name}</td>
            <td align="center">${boardvo.regDate}</td>
            <td align="center">${boardvo.good}</td>
             <td align="center">${boardvo.readCount}</td>
             
         </tr>
      </c:forEach>
   </table>   

       <%-- === #122. 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>
   
   
   
</div>



    
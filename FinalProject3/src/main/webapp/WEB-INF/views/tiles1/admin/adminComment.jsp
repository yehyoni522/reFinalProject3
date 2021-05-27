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
		border-top: solid 1px gray;
		border-bottom: solid 1px gray;
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
		border:0.5px solid #a6a6a6;
	 	padding:5px;
	 	border-radius: 5px;
	 	height: 30px;	 	
	}
	
	.btn-
	
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

	
		
		$(document).on("click","[name=delete]", function(){					
			alert("선택하신 댓글을 삭제하시겠습니까?");
		});

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



function goView(seq){
	   
	   location.href="<%=ctxPath%>/view.action?seq="+seq;
	   
} // end of function goView(seq)-----------------------------

function goSearch(){
	   
	   var frm = document.searchFrm;
	   frm.method = "get";
	   frm.action = "<%=ctxPath%>/list.action";
	   frm.submit();
	   
}// end of function goSearch()------------------------------------

//== 댓글 체크박스 함수 시작 == //

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

//== 댓글 체크박스 함수 끝 == //
</script>   

<div style="padding-left: 3%;">
   <h2 style="margin-bottom: 30px;">| 댓글 관리</h2>
   <div style="text-align:right; vertical-align:middle;  margin:5px; margin-bottom:30px; ">
	   <%-- === #101. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --%>
		<form name="searchFrm" style="margin-top: 20px;">
	      <select name="searchType" id="searchType"  class="select">	   
	         <option value="subject">제목</option>
	         <option value="content">내용</option>
	         <option value="name">글쓴이</option>
	      </select>
	      <input type="text" name="searchWord" class="search" id="searchWord" size="15" autocomplete="off" /> 
	      <button type="button" onclick="goSearch()" class=" btn-board">검색</button>
	   </form>
	
   </div>
   
	
   <table id="table" style="width:100%;">
   	  <tr style="border-top: solid 1.5px #b3b3b3; border-bottom: none;">
   		<td colspan="3" style="text-align: left; font-size: 13pt; font-weight: bolder;">
   			댓글 <span style="color:#53c68c;">개수</span></td>
   		<td colspan="5" style="text-align: right;">
	      	<label for="page"></label>
		    <select name="page" id="page" style="width:100px;margin-right:5px;" class="select">
		      <option selected="selected">15개씩</option>
		      <option>30개씩</option>
		    </select>
		</td>
   	  </tr>
      <tr>
         <td style="text-align: center;">
         	<input type="checkbox"  name="checkAll" />
		 </td>
		 <td></td>
         <td style="text-align: center;">
         	<label for="viewBoard"></label>
			    <select name="viewBoard" id="viewBoard" class="select">
			      <option selected="selected">게시판 전체</option>
			      <option value="1">자유게시판</option>
			      <option value="2">중고거래</option>
			      <option value="3">동아리/공모전</option>
			    </select>
         </td>
         <td colspan="5" style="text-align:right; vertical-align: middle;">
         	<span style="font-weight: bold; font-size: 13pt;">선택한 글</span>
         	<label for="selectBoard"></label>
			    <select name="selectBoard" id="selectBoard" class="select">
			      <option selected="selected" value="1">자유게시판</option>			     
			      <option value="2">중고거래</option>
			      <option value="3">동아리/공모전</option>
			    </select>
			<input type="button" value="이동" class=" btn-board" name="move"/>
			<input type="button" value="삭제" class=" btn-board" name="delete"/>
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
         		<input type="checkbox" name="checkOne" />
		 	</td>
         	<td align="center">${boardvo.seq}</td>
             <td align="center">
             	<c:if test="${boardvo.categoryno==1}">
             		자유게시판
             	</c:if>
             	<c:if test="${boardvo.categoryno==2}">
             		중고게시판
             	</c:if>
             	<c:if test="${boardvo.categoryno==3}">
             		동아리 / 공모전
             	</c:if>
             </td>
             <td align="left">
	             <%-- === 댓글쓰기가 있는 게시판 === --%>
	             <c:if test="${boardvo.commentCount>0}">
	             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject} <span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span> </span>
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
   
	
   
   
   
   
</div>



    
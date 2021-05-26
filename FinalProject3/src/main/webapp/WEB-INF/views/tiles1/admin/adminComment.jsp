<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

     
    .subjectStyle {font-weight: bold;
                   color: navy;
                   cursor: pointer;} 
    #table th, #table td {padding: 5px;}       
	table{
		width:100%;
		border-top: solid 1px gray;
		border-bottom: solid 1px gray;
	}
	
	th{
		background-color: #e6e6e6;
	}
	
	tr{
		border-top: solid 1px gray;
		border-bottom: solid 1px gray;
	}
	
	input#btn-board{
	 	width:100px;
	 	border:none;
	 	font-weight: bold;
	 	margin:15px;
	 	padding:10px;
	 	border-radius: 10px;
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

		$("[name=delete]").click(function(){
			alert("선택하신 댓글을 삭제하시겠습니까?");
		});
		
   });// end of $(document).ready(function(){})------------------

   
   function goView(seq){
	   
	   location.href="<%=ctxPath%>/view.action?seq="+seq;
	   
   } // end of function goView(seq)-----------------------------
   
</script>   

<div style="padding-left: 3%;">
   <h2 style="margin-bottom: 30px;">| 게시글 관리</h2>
   <div style="text-align:right; margin:5px;">
	   <%-- === #101. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --%>
		<form name="searchFrm" style="margin-top: 20px;">
	      <select name="searchType" id="searchType" style="height: 26px;">
	         <option value="subject">제목+내용</option>
	         <option value="name">제목</option>
	         <option value="name">내용</option>
	         <option value="name">글쓴이</option>
	      </select>
	      <input type="text" name="searchWord" id="searchWord" size="15" autocomplete="off" /> 
	      <button type="button" onclick="goSearch()">검색</button>
	   </form>
   </div>
   
	
   <table id="table" style="width:100%;">
   	  <tr>
   		<td colspan="3" style="text-align: left; font-size: 13pt; font-weight: bold">댓글 <span style="color:#53c68c;">댓글갯수</span></td>
   		<td colspan="4" style="text-align: right;">
	      	<label for="speed"></label>
		    <select name="speed" id="speed" style="width:100px;">
		      <option selected="selected">15개씩</option>
		      <option>30개씩</option>
		    </select>
		</td>
   	  </tr>
      <tr>
         <td style="width: 5%;  text-align: center;">
         	<input type="checkbox"  class="" name="" />
		 </td>
         <td style="width: 10%; text-align: center;">
         	<label for="speed"></label>
			    <select name="selectBoard" id="selectBoard">
			      <option selected="selected">게시판 전체</option>
			      <option>자유게시판</option>
			      <option>중고거래</option>
			      <option>동아리/공모전</option>
			    </select>
         </td>
         <td colspan="5" style="text-align:right;"><span style="font-weight: bold; font-size: 10pt;">선택한 댓글</span>
			<input type="button" value="삭제" class="btn btn-board" name="delete"/>
		</td>
      </tr>
      <tr>
      	 <th style="width: 5%;  text-align: center;"></th>
         <th style="width: 10%;  text-align: center;">게시판 명</th>
         <th style="width: 40%;  text-align: center;">제목</th>
         <th style="width: 10%; text-align: center;">글쓴이</th>
         <th style="width: 10%;  text-align: center;">조회수</th>
         <th style="width: 10%;  text-align: center;">댓글수</th>
         <th style="width: 15%;  text-align: center;">작성일</th>
      </tr>
      
      <c:forEach var="boardvo" items="${requestScope.boardList}" varStatus="status"> 
         <tr>   
             <td align="center">${boardvo.seq}</td>
             <td align="left">
             <%-- === 댓글쓰기가 없는 게시판 ===             
             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span> --%>
             <%-- === 댓글쓰기가 없는 게시판 === --%>
             <c:if test="${boardvo.commentCount>0}">
             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject} <span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span> </span>
           	</c:if>
           	<c:if test="${boardvo.commentCount==0}">
             	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span>
             	</c:if>
             </td>
             <td align="center">${boardvo.name}</td>
             <td align="center">${boardvo.regDate}</td>
             <td align="center">${boardvo.readCount}</td>
         </tr>
      </c:forEach>
   </table>   
   
	
   
   
   
   
</div>



    
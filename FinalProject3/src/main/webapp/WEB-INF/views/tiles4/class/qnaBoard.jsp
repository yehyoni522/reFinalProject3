<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">


.subjectStyle {
	font-weight: bolder;
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

th, td {text-align:center;}   

th{
	background-color: #f2f2f2;
	
}

tr{
	border-top: solid 0.5px #bfbfbf;
	border-bottom: solid 0.5px #bfbfbf;
	height:40px;
}

div#btn-board{
	margin-top: 10px;
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

	
	tr.list:hover{
		background-color: #fafafa;
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
		
		$("span.subject").click(function(){		
			var qnano = $(this).parent().prev().text();
			var fk_perno = $(this).prev().val();
			
			var frm = document.goViewFrm;
			frm.qnano.value = qnano;
			frm.fk_perno.value = fk_perno;
			
			frm.method = "get";
	   	    frm.action = "<%= ctxPath%>/class/qnaView.sam";
	   	    frm.submit();
		});
	        	       
	       //검색시 검색조건 및 검색어 값 유지시키기
	       if( ${not empty requestScope.paraMap} ) {
					$("select#searchType").val("${requestScope.paraMap.searchType}");
	          		$("input#searchWord").val("${requestScope.paraMap.searchWord}");	   	    	   
	    	}
		
	       
	});// end of function goView(assgnno) {}-----------------------
	   	
	function goSearch(){
		   
		   var frm = document.searchFrm;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/class/qnaBoard.sam";
		   frm.submit();
		   
	}// end of function goSearch()------------------------------------
	
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;질문게시판
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 질문게시판</h3>
	<br>

	<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
		<tr style="border-bottom: 1px #e6e6e6 solid;">
			<th style="width: 10%;">No.</th>
			<th style="width: 40%;">제목</th>
			<th style="width: 15%;">답변여부</th>
			<th style="width: 15%;">글쓴이(학번)</th>
			<th style="width: 20%;">작성일시</th>			
		</tr>
		
		<c:if test="${empty requestScope.qnaList}">
			<tr>
				<td colspan="100%" style="text-align: center;">질문 게시글이 없습니다.</td>
			</tr> 
		</c:if>
		
		<c:forEach var="qnaVO" items="${requestScope.qnaList}" varStatus="status"> 
			<tr class="list">
				<td>${qnaVO.qnano}</td>
				<td style="text-align:left;">
					<input type="hidden" value="${qnaVO.fk_perno}"/>
				
					<c:if test="${qnaVO.depthno == 0}">			           
						<span class="subject">${qnaVO.subject}</span>
					</c:if>
					<c:if test="${qnaVO.depthno > 0}">
						<span style="color:red; font-style: italic; padding-left: ${qnaVO.depthno * 20}px;">ㄴRe :&nbsp;</span>
						<input type="hidden" value="${qnaVO.fk_perno}"/><span class="subject">${qnaVO.subject}</span>
					</c:if>		 					
				</td>	
				<td>
					<c:if test="${ qnaVO.depthno eq 0 and (qnaVO.answer eq 0) }"><span style="color:red; font-weight: bold;">미답변</span></c:if>
					<c:if test="${ (qnaVO.depthno eq 0) and (qnaVO.answer ne 0) }"><span style="color:blue; font-weight: bold;">답변완료</span></c:if>
					<c:if test="${qnaVO.depthno != 0}"></c:if>
				</td>
				<c:if test="${qnaVO.depthno == 0}">
					<td>${qnaVO.name}(${qnaVO.fk_perno})</td>
				</c:if>
				<c:if test="${qnaVO.depthno > 0}">
					<td>${qnaVO.name} 교수님</td>
				</c:if>
				<td>${qnaVO.regDate}</td>
			</tr>
		</c:forEach>	
		
	</table>
	
	<c:if test="${sessionScope.loginuser.identity eq 0 }">
	<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/class/qnaAdd.sam'"/>
	</div>
    </c:if>
    <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

   <div style="text-align:right; vertical-align:middle; margin:5px;  ">
   <%-- === #101. 글검색 폼 추가하기 : 글제목, 글내용으로 검색을 하도록 한다. === --%>
	<form name="searchFrm" style="margin-top: 20px;">		
      <select name="searchType" id="searchType"  class="select">	
         <option value="name">글쓴이(이름)</option>
         <option value="perno">글쓴이(학번)</option>
         <option value="subject">제목</option>
      </select>
      <input type="text" name="searchWord" class="search" id="searchWord" size="15" autocomplete="off" /> 
      <button type="button" onclick="goSearch()" class="btn-board">검색</button>
   </form>
  </div>
	<form name="goViewFrm">	
		<input type="hidden" name="qnano" />
		<input type="hidden" name="fk_perno" />
	</form>



</div>




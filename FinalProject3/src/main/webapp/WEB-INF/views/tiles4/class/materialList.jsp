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
		border:solid 0px gray; 
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
			var mtrno = $(this).parent().prev().text();
			var frm = document.goViewFrm;
			frm.mtrno.value = mtrno;
			
			frm.method = "get";
	   	    frm.action = "<%= ctxPath%>/class/materialView.sam";
	   	    frm.submit();
		});
		
		
		
		
		<%-- === 검색어 입력시 자동글 완성하기 2 === --%>
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
	                url:"<%= ctxPath%>/class/materialWordSearchShow.sam",
	                type:"get",
	                data:{"searchType":$("select#searchType").val()
	                    ,"searchWord":$("input#searchWord").val()},
	                dataType:"json",
	                success:function(json){  
	                   <%-- === 검색어 입력시 자동글 완성하기 === --%> 
	                   if(json.length > 0) {
	                      // 검색된 데이터가 있는 경우임.
	                      
	                      var html = "";
	                      
	                      $.each(json, function(index, item){
	                         var word = item.word;
	                         
	                         var index = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase());
	                         
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
	       if( ${not empty requestScope.paraMap} ) {
					$("select#searchType").val("${requestScope.paraMap.searchType}");
	          		$("input#searchWord").val("${requestScope.paraMap.searchWord}");	   	    	   
	    	}
		
		
		
		
	});// end of function goView(assgnno) {}-----------------------
	   	
	   
	function goSearch(){
		   
		   var frm = document.searchFrm;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/class/materialList.sam";
		   frm.submit();
		   
	}// end of function goSearch()------------------------------------
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;수업자료
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 수업자료 게시판</h3>
	<br>

	<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
		<tr style="border-bottom: 1px #e6e6e6 solid;">
			<th style="width: 10%;">No.</th>
			<th style="width: 50%;">제목</th>		 					
			<th style="width: 30%;">작성일</th>	
			<th style="width: 10%;">조회수</th> 		
		</tr>
		
		<c:if test="${empty requestScope.mtrList}">
			<tr>
				<td colspan="100%" style="text-align: center;">수업 자료가 없습니다.</td>
			</tr> 
		</c:if>
		
		<c:forEach var="mtrVO" items="${requestScope.mtrList}" varStatus="status"> 
		
			<tr class="list">
				<td>${mtrVO.mtrno}</td>
				<td style="text-align:left;">
					<%-- 첨부파일이 없는 경우 시작 --%>
						<c:if test="${empty mtrVO.fileName}">
							<span class="subject">${mtrVO.subject}</span>
						</c:if>
					<%-- 첨부파일이 없는 경우 끝 --%>
					
					<%-- 첨부파일이 있는 경우 시작 --%>
						<c:if test="${not empty mtrVO.fileName}">
							<span class="subject">${mtrVO.subject}</span>&nbsp;<img src="<%=ctxPath %>/resources/images/disk.gif"/>
						</c:if>
					<%-- 첨부파일이 있는 경우 끝 --%>
				</td>				
				<td>${mtrVO.regDate}</td>		
				<td>${mtrVO.readCount}
			</tr>
		</c:forEach>	
	</table>
	
	
	

  
  
	<c:if test="${sessionScope.loginuser.identity eq '1'}">
		<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/class/materialAdd.sam'"/>
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
         <option value="subject">제목</option>
         <option value="content">내용</option>
      </select>
      <input type="text" name="searchWord" class="search" id="searchWord" size="15" autocomplete="off" /> 
      <button type="button" onclick="goSearch()" class="btn-board">검색</button>
   </form>
   <div style="height:70px; " >
	   <%-- === 검색어 입력시 자동글 완성하기  === --%>
		<div id="displayList" style="border:solid 1px gray; border-top:0px; width:320px; height:70px; margin-right:118px; 
				overflow:auto; float:right; padding:5px; text-align: left;  border-radius: 5px;  box-shadow: 0.5px 0.5px 0.5px 0.5px gray;">	
		</div>
	</div>
  </div>

</div>


	<form name="goViewFrm">	
		<input type="hidden" name="mtrno" />
		<input type="hidden" name="gobackURL"  value="${requestScope.gobackURL}" />
	</form>



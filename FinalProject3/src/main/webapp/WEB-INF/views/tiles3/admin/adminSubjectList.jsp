<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

div#adminhome {
	min-height: 700px;
	padding-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
}

div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	min-height: 600px;
}
    
div#admincontent {
	float: left;
	padding: 0 50px 0 30px;
	width: 80%;
}         
.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 
}

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
	   
	  	$("select#page").val("${requestScope.page}");

		$("span.subject").bind("mouseover",function(event){
			var $target=$(event.target);
			$target.addClass("subjectStyle");
		});
	   
		$("span.subject").bind("mouseout",function(event){
			var $target=$(event.target);
			$target.removeClass("subjectStyle");			
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
	                url:"<%= ctxPath%>/admin/subjectwordSearchShow.sam",
	                type:"get",
	                data:{"searchType":$("select#searchType").val()
	                    ,"searchWord":$("input#searchWord").val()},
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
	        	       
	       if(${not empty requestScope.paraMap}){
	      	 $("select#searchType").val("${requestScope.paraMap.searchType}");
	      	 $("input#searchWord").val("${requestScope.paraMap.searchWord}");
	      	 
	       }
		
	       ///// === Excel 파일로 다운받기 시작 === /////
		   $("button#btnExcel").click(function(){
			    location.href ="<%= request.getContextPath()%>/admin/downloadExcelFile.sam";   		   
		   });
		   ///// === Excel 파일로 다운받기 끝 === /////
		    
	       
	       
	       
});// end of $(document).ready(function(){}---------------------------------------

		
		
	function goSearch(){
		   
		   var frm = document.searchFrm;
		   frm.method = "get";
		   frm.action = "<%=ctxPath%>/admin/adminSubjectList.sam";
		   frm.submit();
		   
	}// end of function goSearch()------------------------------------
	
	
	
</script>   

<div id="adminhome">
	
	<div id="admincontent">
	
		
	<div style="padding-left: 3%;padding-right: 3%;">
		<div class="admsubtitle">수업목록</div>
	  
	  <div style="text-align:right; vertical-align:middle; margin:5px;  ">
	   <%-- === #101. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --%>
		<form name="searchFrm" style="margin-top: 20px;">
			<select name="searchType" id="searchType" style="height: 26px;">
			      <option value="content">학과</option>
			      <option value="name">교수명</option>
			      <option value="subname">수업명</option>
			</select>
	      <input type="text" name="searchWord" class="search" id="searchWord" size="15" autocomplete="off" /> 
	      <button type="button" onclick="goSearch()" class=" btn-board">검색</button>
	  
	   </form>
		   <%-- === 검색어 입력시 자동글 완성하기  === --%>
			<div id="displayList" style="border:solid 1px gray; border-top:0px; width:320px; height:50px; margin-right:118px;
					overflow:auto; float:right; padding:5px; text-align: left;  border-radius: 5px;  box-shadow: 0.5px 0.5px 0.5px 0.5px gray;">	
		</div>

   </div>
	   
	   <table id="table" style="width:100%;margin-top: 50px; ">
	 		   <tr style="border: none;" align="right" ><td colspan="7" align="right">	
		      <button type="button" id="btnExcel" class=" btn-board">excel 다운로드</button>
		</td></tr>
	      <tr>
	      
	       <th style="width: 3%;  text-align: center;">학기</th>
	      	 <th style="width: 7%;  text-align: center;">학과</th>
	         <th style="width: 7%;  text-align: center;">교번</th>
	         <th style="width: 7%;  text-align: center;">교수명</th>
	         <th style="width: 15%; text-align: center;">수업명</th>
	         <th style="width: 15%;  text-align: center;">시간표</th>
	         <th style="width: 3%;  text-align: center;">학점</th>
	        
	      
	      </tr>
	   
	      <c:forEach var="subList" items="${requestScope.adminsubjectList}" varStatus="status"> 
	         <tr class="list">  
	         <c:if test="${subList.semeter==0}"><td align="center">21-1</td></c:if>
	         	  <c:if test="${subList.semeter==1}"><td align="center">20-2</td></c:if>
	             <td align="center">${subList.content}</td>	
	            <td align="center">${subList.fk_perno}</td>
	            <td align="center">${subList.name}</td>
	            <td align="center">${subList.subname}</td>
	             <td align="center">${subList.day} ${subList.time}</td>
	             <td align="center">${subList.credit}</td>
	         </tr>
	      </c:forEach>
	   </table>   
	
	          <%-- === #122. 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>
   
	</div>
</div>
</div>


    
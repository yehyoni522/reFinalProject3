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

</style>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
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
	   	    frm.action = "<%= ctxPath%>/class/noticeView.sam";
	   	    frm.submit();
		});
		
		$("div#displayList").hide();
	      
      	$("input#searchWord").keyup(function(){
         
       		var wordLength = $(this).val().trim().length;
         	// 검색어의 길이를 알아온다.
         
         	if(wordLength == 0) {
            	$("div#displayList").hide();
         	}
         	else {
            	$.ajax({
               		url:"<%= ctxPath%>/lesson/wordSearchShow.sam",
           		 	type:"get",
               		data:{"searchType":$("select#searchType").val()
                   		 ,"searchWord":$("input#searchWord").val()
                   		 ,"categoryno":$("input#catnoSearch").val()},
               		dataType:"json",
               		success:function(json){ 
                  		if(json.length > 0) {
                     
                     		var html = "";
                     
                     		$.each(json, function(index, item){
                        		var word = item.word;
		                        
		                        var index = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase());
		                        
		                        var len = $("input#searchWord").val().length;
		                        
		                        word = word.substr(0,index) + "<span style='color:blue;'>"+word.substr(index,len)+"</span>" + word.substr(index+len);
		                        
		                        html += "<span style='cursor:pointer;' class='word'>"+word+"</span><br>";
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
         	
	});// end of $("input#searchWord").keyup(function(){}
	
});	// end of $(document).ready(function(){}
	   	
	
	function gonoticeView(seq){
		
		var frm = document.goViewFrm;
		frm.seq.value=seq;
		frm.searchType.value = "${requestScope.paraMap.searchType}";
	    frm.searchWord.value = "${requestScope.paraMap.searchWord}"; 
		frm.method="get";
		frm.action="<%= ctxPath%>/lesson/noticeView.sam";
		frm.submit();
	} // end of function noticeView(seq){}
	   
	// 검색하기
	function goSearch(){
		var frm = document.searchFrm;
		frm.method="get";
		frm.action="<%=ctxPath%>/lesson/notice.sam";
		frm.submit();
		
	} // end of function goSearch(){}
	
</script>   

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;공지사항
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 공지사항</h3>
	<br>

	<table style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
		<tr style="border-bottom: 1px #e6e6e6 solid;">
			<th style="width: 10%;">No.</th>
			<th style="width: 40%;">제목</th>
			<th style="width: 20%;">작성일</th>			
		</tr>			
		<c:forEach var="lenotivo" items="${requestScope.lenotivo}" varStatus="status"> 
			<tr class="list">
				<td>${lenotivo.seq}</td>
				<td style="text-align:center;">			           
					<span class="subject" onclick="gonoticeView('${lenotivo.seq}')">${lenotivo.subject}</span>					
				</td>	
				<td>${lenotivo.regDate}</td>
			</tr>
		</c:forEach>	
		
	</table>
	
	<c:if test="${sessionScope.loginuser.identity eq 1 }">
	<div id="btn-board">
			<input type="button" value="글쓰기" class="btn-board" name="write" onclick="location.href='<%=ctxPath%>/lesson/noticeAdd.sam'"/>
	</div>
    </c:if>
    
    <%-- 검색창(글쓴이,글제목) --%>
	<div id="bottomop" style="height: 30px;"> 
		<form name="searchFrm" style="margin-top: 20px; ">
			<input type="hidden" name="categoryno" id="catnoSearch" value="${categoryno}"/>
	   		<select name="searchType" id="searchType" style="height: 26px;">
	      		<option value="subject">글제목</option>
	       		<option value="name">글쓴이</option>
	     	</select>
	    	<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
	   	 	<button type="button" onclick="goSearch()">검색</button>
		</form>
	
		<%-- 검색어 입력시 자동글 완성하기 1--%>
		<div id="displayList"></div>		
	</div>	
	
    <%-- === 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border:solid 0px gray; margin:20px auto;">
		${requestScope.pageBar}
	</div>

	<form name="goViewFrm">
		<input type="hidden" name="seq" />
		<input type="hidden" name="gobackURL" value="${requestScope.gobackURL}" />
		<input type="hidden" name="searchType" />
	    <input type="hidden" name="searchWord" />
	</form>

</div>




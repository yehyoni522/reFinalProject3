<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">
	#addFrm{
		padding-left: 10%;
	}
	input, textarea {
		 border: solid gray 1px; 
	}
  	.long {
  		width: 95%; height: 40px;
  		border: none;
  		border-bottom: 1px solid gray;
  	}
  	.short {width: 120px;}
	.mainline{
		background-color: #e6e6e6;
		height: 3px;
		width: 95%;
	}
	button {
		background-color: white;
		border: solid #e6e6e6 1px;
		width: 54px;
		height: 34px;		
	}
	#btnWrite{
		margin-right: 5px;
	}
	#sub{
		margin-bottom: 1%;
	}
	#userinfo{
		margin-bottom: 1%;
	}
	#noname{
		margin-left: 10px;	
	}
</style>

<script type="text/javascript" src="<%=ctxPath%>/resources/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
   $(document).ready(function(){
      
      // 스마트에디터 구현
      
      var obj = [];
      
      ngn.husky.EZCreator.createInIFrame({
    	  oAppRef: obj,
    	  elPlaceHolder: "content",
    	  sSkinURI:"<%=ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
    	  htParama:{
    		  bUseToolbar:true,
    		  bUseVerticalResizer:true,
    		  bUseModeChanger:true
    	  }
      });
	      
      // 쓰기버튼
      $("button#btnWrite").click(function(){
      
         <%-- === 스마트 에디터 구현 시작 === --%>
          
         <%-- === 스마트 에디터 구현 끝 === --%>
         
          // 글제목 유효성 검사
         var subjectVal = $("input#subject").val().trim();
         if(subjectVal == "") {
            alert("글제목을 입력하세요!!");
            return;
         }
         
         // 글내용 유효성 검사(스마트에디터 사용 안 할시)
         <%--
         var contentVal = $("textarea#content").val().trim();
         if(contentVal == "") {
            alert("글내용을 입력하세요!!");
            return;
         }
         --%>
         
         <%-- === 스마트에디터 구현 시작 === --%>
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거
         var contentval = $("content").val();      
           
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
         // 글내용 유효성 검사 
         if(contentval == ""|| contentval == "<p>&nbsp;<p>"){
        	 alert("글내용을 입력하세요!!");
        	 return;
         }  
         
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
         contentval = $("#content").val().replace(/<p><br><\/p>/gi, "<br>");
         
         contentval = $("#content").val().replace(/<\/p><p>/gi, "<br>");
         contentval = $("#content").val().replace(/(<\/p><br>|<p><br>)/gi, "<br><br>");
         contentval = $("#content").val().replace(/(<p>|<\/p>/gi, "");
         
         $("#content").val(contentval); 
       <%-- === 스마트에디터 구현 끝 === --%>
         
         // 글암호 유효성 검사
         var pwVal = $("input#pw").val().trim();
         if(pwVal == "") {
            alert("글암호를 입력하세요!!");
            return;
         }
         
         // 폼(form) 을 전송(submit)
         var frm = document.addFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath%>/addEnd.action";
         frm.submit();   
      });
           

   });// end of $(document).ready(function(){})----------------
</script>

<div id="addFrm">
	<h1>
 		<c:if test="${requestScope.boardList.categoryno == 1}">자유게시판</c:if>
 		<c:if test="${requestScope.boardList.categoryno == 2}">중고거래</c:if>
 		<c:if test="${requestScope.boardList.categoryno == 2}">동아리&공모전 모집</c:if>
	</h1>
	<h1>자유게시판</h1>
<form> 
 
<hr class="mainline" align="left" >
	<div id="table">
  	 	<div id="sub">                   
			<input type="text" name="subject" id="subject" class="long" placeholder="  제목을 입력하세요."/>  
        </div>
		<div id="userinfo">
	    	작성자 : 컴퓨터공학과&nbsp;채연하&nbsp;12423040
	    	<span id="noname"> <label>익명</label> <input type="checkbox"/> </span>       
	   	</div> 
        <%-- === #150. 파일첨부 타입 추가하기 === --%>
		<div id="con">		
		  <textarea rows="10" cols="100" style="width: 95%; height: 612px;" name="content" id="content" placeholder="  내용을 입력하세요."></textarea>       
		</div>
   
         
       

      </div>
      <hr class="mainline" align="left" >
      <div style="margin-top: 20px;">
         <button type="button" id="btnWrite">등록</button>
         <button type="button" onclick="javascript:history.back()">취소</button>
      </div>
         
   </form>
   
</div>    

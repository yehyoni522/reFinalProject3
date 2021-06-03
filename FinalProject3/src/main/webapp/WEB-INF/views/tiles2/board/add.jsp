<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">

	input, textarea {border: solid gray 1px;}  
	.long {width: 470px;}
	#content{
		width: 95%;
		height: 612px;
	}
	#subject{
		width: 95%;
		margin-top:10px;
		margin-bottom: 10px;
	}
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
		margin-right: 4px;
	}
	
</style>

<script type="text/javascript">
   $(document).ready(function(){
      
      <%-- === #167. 스마트 에디터 구현 시작 === --%>
       
       <%-- === 스마트 에디터 구현 끝 === --%>
      
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
         
         var contentVal = $("textarea#content").val().trim();
         if(contentVal == "") {
            alert("글내용을 입력하세요!!");
            return;
         }
         
         
         <%-- === 스마트에디터 구현 시작 === --%>
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거
          
              
           
           
           // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
           // 글내용 유효성 검사 
           
           
           // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
           
       
          
        
        
       <%-- === 스마트에디터 구현 끝 === --%>         
         
         // 폼(form) 을 전송(submit)
         var frm = document.addFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath%>/board/addEnd.sam";
         frm.submit();   
      });
           
   });// end of $(document).ready(function(){})----------------
   

</script>

<div style="padding-left: 10%; padding-right:10%;">
	<h1>
 		<c:if test="${requestScope.categoryno == 1}">자유게시판</c:if>
 		<c:if test="${requestScope.categoryno == 2}">중고거래</c:if>
 		<c:if test="${requestScope.categoryno == 2}">동아리&공모전 모집</c:if>
	</h1>
	<hr class="mainline" align="left" >
	
	<form name="addFrm"> 
 		<input type="hidden" name="categoryno" value="${categoryno}" />                   
		<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}"/>
		<input type="hidden" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />
		
		작성자 : ${sessionScope.loginuser.name}&nbsp;${sessionScope.loginuser.perno}
		<label>익명</label> <input type="checkbox" name="namecheck" id="namecheck" value="1"/>    
		
		<div>
			<input type="text" name="subject" id="subject" placeholder="  제목을 입력하세요."/>       
		</div>
		 현위치 스마트에디터 추가예정 
        <textarea rows="10" cols="100" name="content" id="content" placeholder="  내용을 입력하세요."></textarea>       
           
     
	<%-- === #150. 파일첨부 타입 추가하기 === --%>
   	<hr class="mainline" align="left" >   
    <div style="margin: 20px;">
    	<button type="button" id="btnWrite">등록</button>
        <button type="button" onclick="javascript:history.back()">취소</button>
   	</div>
         
</form>
   
</div>    
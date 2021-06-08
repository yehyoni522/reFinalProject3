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
	#btnFileAdd{
		cursor: pointer;
		width:25px; 
		height:25px;
		margin-left: 5px;
	}
	#fileAddin{
		margin-top: 5px;
	}
</style>

<script type="text/javascript">
   $(document).ready(function(){
      
	   // 파일첨부 input 숨기기
	   $("input#fileAddin").hide();
	   
	   // 파일이미지 클릭하면 파일첨부 추가
	   $("img#btnFileAdd").click(function(){
		   $("input#fileAddin").show();   		   		   
	   });
	   
       <%-- === #167. 스마트 에디터 구현 시작 === --%>
	   //전역변수
       var obj = [];
       
       //스마트에디터 프레임생성
       nhn.husky.EZCreator.createInIFrame({
           oAppRef: obj,
           elPlaceHolder: "content",
           sSkinURI: "<%= request.getContextPath() %>/resources/smarteditor/SmartEditor2Skin.html",
           htParams : {
               // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
               bUseToolbar : true,            
               // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
               bUseVerticalResizer : true,    
               // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
               bUseModeChanger : true,
           }
       });
	   <%-- === 스마트 에디터 구현 끝 === --%>
      
      // 쓰기버튼
      $("button#btnWrite").click(function(){
      
         <%-- === 스마트 에디터 구현 시작 === --%>
         //id가 content인 textarea에 에디터에서 대입
         obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
         <%-- === 스마트 에디터 구현 끝 === --%>
         
          // 글제목 유효성 검사
         var subjectVal = $("input#subject").val().trim();
         if(subjectVal == "") {
            alert("글제목을 입력하세요!!");
            return;
         }
         
         // 글내용 유효성 검사(스마트에디터 사용 안 할시)
         
        <%--  var contentVal = $("textarea#content").val().trim();
         if(contentVal == "") {
            alert("글내용을 입력하세요!!");
            return;
         } --%>
         
         
         <%-- === 스마트에디터 구현 시작 === --%>
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거
         var contentval = $("textarea#content").val(); 
         
         // === 확인용 ===
         // alert(contentval); // content에 내용을 아무것도 입력치 않고 쓰기할 경우 알아보는것.
         // "<p>&nbsp;</p>" 이라고 나온다.
           
           
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
         // 글내용 유효성 검사 
         if(contentval == "" || contentval == "<p>&nbsp;</p>") {
              alert("글내용을 입력하세요!!");
              return;
         }  
           
         // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
         contentval = $("textarea#content").val().replace(/<p><br><\/p>/gi, "<br>"); //<p><br></p> -> <br>로 변환
         /*    
       	  	대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
            ==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
                          그리고 뒤의 gi는 다음을 의미합니다.

              g : 전체 모든 문자열을 변경 global
              i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
         */    
         contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
         contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
         contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
       
         $("textarea#content").val(contentval);  
         
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
	
	<form name="addFrm" enctype="multipart/form-data"> 
 		<input type="hidden" name="categoryno" value="${categoryno}" />                   
		<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}"/>
		<input type="hidden" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />
		
		작성자 : ${sessionScope.loginuser.name}&nbsp;${sessionScope.loginuser.perno}
		<label>익명</label> <input type="checkbox" name="namecheck" id="namecheck" value="1"/>
		<br>      
		  
		<%-- 파일첨부--%>
		<div><img id="btnFileAdd"src="<%=ctxPath %>/resources/images/fileAdd.PNG" /> 파일 첨부하기 <input type="file" id="fileAddin" name="attach" /> </div> 
		
		<div>
			<input type="text" name="subject" id="subject" placeholder="  제목을 입력하세요."/>       
		</div>
		
        <textarea rows="10" cols="100" name="content" id="content" placeholder="  내용을 입력하세요."></textarea>       
        
        <%-- 답변글쓰기가 추가 --%>
      	<input type="hidden" name="fk_seq" value="${requestScope.fk_seq}"/>
      	<input type="hidden" name="groupno" value="${requestScope.groupno}"/>
      	<input type="hidden" name="depthno" value="${requestScope.depthno}"/>  
     
	
	   	<hr class="mainline" align="left" >   
	    <div style="margin: 20px;">
	    	<button type="button" id="btnWrite">등록</button>
	        <button type="button" onclick="javascript:history.back()">취소</button>
	   	</div>         
	</form>
   
</div>    
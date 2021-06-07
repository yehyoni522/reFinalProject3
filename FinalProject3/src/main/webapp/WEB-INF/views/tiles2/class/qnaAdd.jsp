<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">

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


 .long {width: 80%;}

</style>

<script type="text/javascript">
   
   $(document).ready(function(){
	   
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
         
          // 글제목 유효성 검사
         var subjectVal = $("input#subject").val().trim();
         if(subjectVal == "") {
            alert("글제목을 입력하세요!!");
            return;
         }
         
         <%-- === 스마트 에디터 구현 시작 === --%>
        	//id가 content인 textarea에 에디터에서 대입
          obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
          <%-- === 스마트 에디터 구현 끝 === --%>
          
        
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
         
         
         var bool = confirm("답변이 달린 후에는 질문글을 수정 및 삭제하실 수 없습니다.\n질문 글쓰기를 완료하시겠습니까?");
         
        if(bool){
	         // 폼(form) 을 전송(submit)
	         var frm = document.addFrm;
	         frm.method = "POST"; // 파일 첨부는 무조건 포스트!!!!!
	         frm.action = "<%= ctxPath%>/class/qnaAddEnd.sam";
	         frm.submit();   
        }
        else{
			alert("과제 제출을 취소하셨습니다.");
		}
        
      });
           
   });// end of $(document).ready(function(){})----------------
   
</script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;질문게시판
	</div>

	<h1 class="headerName">${requestScope.subject}</h1>
	<br>
	<h3 style="text-align: left; font-weight: bold;">| 질문게시판</h3>
	<br>
	
 <form name="addFrm">
      <table id="table" style="width:100%; border-top: 1.5px #b3b3b3 solid; border-bottom: 1.5px #b3b3b3 solid;">
         <tr>
            <th style="width:200px;">제목</th>
            <td>
               <input type="text" name="subject" id="subject" class="long" />       
            </td>
         </tr>
         
         <tr>
            <th>성명(학번)</th>
            <td>
            	${sessionScope.loginuser.name}(${sessionScope.loginuser.perno})
                <input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}" />
                <input type="hidden" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />       
            </td>
         </tr>
         
         <tr>
            <th>내용</th>
            <td>
               <textarea rows="10" cols="100" style="width: 80%; height: 612px;" name="content" id="content"></textarea>       
            </td>
         </tr>                
      </table>
      
      <%-- === #143. 답변글쓰기가 추가된 경우 === --%>
      <input type="hidden" name="fk_qnano" value="${requestScope.fk_qnano}" />
      <input type="hidden" name="groupno" value="${requestScope.groupno}" />
      <input type="hidden" name="depthno" value="${requestScope.depthno}" />
      
		<div id="btn-board">
			<button type="button" class="btn-board" id="btnWrite">완료</button>
		<button type="button"class="btn-board" onclick="javascript:history.back()">취소</button>
	</div>
   
   </form>
   
</div>    
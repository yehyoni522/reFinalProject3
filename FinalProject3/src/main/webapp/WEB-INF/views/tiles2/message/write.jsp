<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>

<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<title> 쪽지함</title>
<style type="text/css">
body{
   font-family: 'Noto Sans KR', sans-serif;
}


div#msgSide{
	/* border: solid 1px blue;  */
	display:inline-block ; 
	width: 20%; 
	margin-right: 20px;
	height: 1000px;
	float: left;
	border-right: solid 1px #E5E5E5;
}
span#title{
	font-size:20px;
	color: #3498DB;
	margin-bottom: 10px;
	font-weight: bold;
}

.button {
	
	margin-top:40px;
	margin-left: 160px;
    width:100px;
    background-color:#2ECC71;
    border: none;
    color:#fff;
    padding: 10px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    cursor: pointer;
    font-weight: bold;
    border-radius: 10px;
	transition:0.3s;
	transform: translate(-50%,-50%);
	box-shadow: 2px 2px 2px 2px #27AF61;

}
.button:hover {
    background-color: #27AF61;
	cursor: pointer;
	color: #fff;
	box-shadow: 0 2px 4px  #2ECC71;
}
.button:focus {
	outline:0;
}

div.msgBox{
	
	/* border: solid 1px red;  */
	height: 35px;
	text-align: center;
	font-weight: bold;
	font-size: 15px;
	padding-top: 7px;
	cursor: pointer;
	


}
div.msgBox:hover{
	 opacity:0.7;
}

div#msgNew{
	 border-radius: 50%;
	 background-color: red;
	 color: white;
	 width: 25px;
	 height: 25px;
	 display: inline-block;
	 margin-left: 20px;
	 float:right;
	 font-size: 15px;
	 text-align: center;
	 padding-top:2px;
}
button.del{
	width:50px;
	height: 30px;
	color: #f8585b;
	background: #fff;
	font-size: 10pt;
	border: solid 1px #f8585b;
	border-radius: 20px;
	transition:0.3s;
	transform: translate(-50%,-50%);
	margin-top:20px;
	
}
button.del:focus {
	outline:0;
}
button.del:hover{
	background: #f8585b;
	cursor: pointer;
	color: #fff;
	box-shadow: 0 2px 4px #f8585b;
}

button.re{
	width:50px;
	height: 30px;
	color: #2ECC71;
	background: #fff;
	font-size: 10pt;
	border: solid 1px #2ECC71;
	border-radius: 20px;
	transition:0.3s;
	transform: translate(-50%,-50%);
	margin-top:20px;
	margin-left: 270px;
}
button.re:focus {
	outline:0;
}
button.re:hover{
	background: #2ECC71;
	cursor: pointer;
	color: #fff;
	box-shadow: 0 2px 4px #2ECC71;
}



.green_window {
	display: inline-block;
	width: 200px;
	border: 3px solid #2ECC71;
	border-radius: 20px;
}
.input_text {
	width: calc( 100% - 40px );
	margin: 6px 7px;
	border: 0;
	font-weight: bold;
	font-size: 12px;
	outline: none;
	border-radius: 20px;
}
.sch_smit {
	width: 40px; height: 30px;
	margin-top: 5px; border: 0;
	vertical-align: top;
	background: #2ECC71;
	color: white;
	border-radius: 20px;
	cursor: pointer;
	font-size: 12px;
}
.sch_smit:hover {
	background: #27AF61;
}
div#add{
	border: solid 1px gray;
	 width: 408px; height: 40px;
	 font-size: 13px;
	 overflow: auto;
	 margin: 0;
	 color:gray;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">

	

	$(document).ready(function(){
		$("div#inbox").bind("click", function(){
			goInbox();
		});
		$("div#outbox").bind("click", function(){
			goOutbox();
		});
	
		$('textarea.DOC_TEXT').keyup(function (e){
		    var content = $(this).val();
		    $('span#counter').html(content.length);    //글자수 실시간 카운팅

		    if (content.length > 1000){
		        alert("최대 1000자까지 입력 가능합니다.");
		        $(this).val(content.substring(0, 1000));
		        $('span#counter').html(1000);
		    }
		});
		
		$("input:checkbox[name=forme]").click(function(){
			// 내게쓰기 체크되어있다면
			var loginuser_id = $("input#loginuser_id").val();
			var loginuser_name = $("input#loginuser_name").val();
			
			
			 var bool = $(this).prop("checked");
				
				if (bool) { // 내게쓰기 체크박스에 체크했을 때
					getval = loginuser_name +"("+loginuser_id+")";
					
					 $("div#add").html(getval);
					 $("input[name=receiver_es]").val(loginuser_id);
					 $("input[name=name_es]").val(loginuser_name);
					 
					
				}
				else {	//  내게쓰기 체크박스에 체크 해제했을 때
					$("div#add").html("");
					$("input[name=receiver_es]").val("");
					$("input[name=name_es]").val("");
					
				}
		});
		
		
		// 답장하기 때문에 값이 들어왔을때 
		var loginuser_id = $("input#loginuser_id").val();
		var loginuser_name = $("input#loginuser_name").val();
		
		if(loginuser_id !=null && loginuser_name !=null){
			getval = loginuser_name +"("+loginuser_id+")";
			
			 $("div#add").html(getval);
			 $("input[name=receiver_es]").val(loginuser_id);
			 $("input[name=name_es]").val(loginuser_name);
		}
		
		
	});
	

	function goWrite(){
		location.href="<%= ctxPath%>/message/write.sam";
	}
	function goInbox(){
		location.href="<%= ctxPath%>/message/inbox.sam";
	}
	function goOutbox(){
		location.href="<%= ctxPath%>/message/outbox.sam";
	}
	
	function goSearch() {
		$("input:checkbox[name=forme]").prop("checked", false);
	    var lo_form = document.writeFrm;
	    lo_form.target = "idFindiframe";
	    lo_form.action = "<%=ctxPath%>/message/userFind.sam";
	    lo_form.submit();
	 }
	
	function sumitSendForm() {
		
		 var getval = $('#iframe').contents().find('#someID').val();
		 var pernoval = $('#iframe').contents().find('#someID_2').val();
		 var nameval = $('#iframe').contents().find('#someID_3').val();
		  //alert(getval);
		 $("div#add").html(getval);
		 $("input.input_text").val("");
		 $("input[name=receiver_es]").val(pernoval);
		 $("input[name=name_es]").val(nameval);
	}
	
	// 전송하기
	function goSubmit() {
		
		var addHtml = $("div#add").html();
		
		if(addHtml == null) {
			alert("받는사람의 정보가 없습니다!");
			return; // 종료
		}
		
		var textarea = $("textarea.DOC_TEXT").val();
		
		if(textarea == null) {
			alert("내용을 입력하셔야 합니다!");
			return; // 종료
		}
		var frm = document.writeFrm2;
		
		frm.action = "<%=ctxPath%>/message/writeEnd.sam";
		frm.method = "POST";
		frm.submit();

		
	}// end of function submit()---------------------------------
	
	

</script>

<body>

<div class="msgHead">
	<div><div style="display:inline-block; width:10px; height: 30px; background-color: #3498DB;"></div>&nbsp;&nbsp;<span id="title">쪽지함</span></div>
	<hr style="border: solid 1px #E5E5E5;">
</div>

<div id="msgSide" >
	<div class="row">
		<div class="col-md-12" >
			<button class="button" onclick="goWrite()">쪽지보내기</button>
			<div class="msgBox" id="inbox" style=" margin-top: 30px; padding-right: 55px;">받은쪽지함<div id="msgNew">${requestScope.nonReadCount}</div></div>
			<div class="msgBox" id="outbox" style="padding-right: 100px; border-top: solid 1px #ccc;" >보낸쪽지함</div>
		</div>
	</div>	
</div>

<div class="msgContents" style="width: 70%; display:inline-block ; margin-left:20px; ">

 	
    	<form name="writeFrm" style="margin-top: 20px;" >
			<input name="forme" type="checkbox"><label for="forme" style="font-size:12px;">내게쓰기</label>&nbsp;&nbsp;
			<input type="hidden" id="loginuser_id" value="${requestScope.loginuser_id}">
			<input type="hidden" id="loginuser_name" value="${requestScope.loginuser_name}">
			<span class='green_window'>
				<input type='text' class='input_text' placeholder="학번/교번을 입력하세요" name="receiver"/>
			</span>
			<a style="cursor: pointer;" data-toggle="modal" data-target="#userfind" data-dismiss="modal"><button type='submit' class='sch_smit' onclick="goSearch();">찾기</button></a>
		</form>
		<form name="writeFrm2" >
				<input type="hidden" name="receiver_es" />
				<input type="hidden" name="name_es" />
				<div class="addBox" id="receiverID" style="font-size: 13px; font-weight: bold; color: #205890;">받는사람</div>
	    		<div class="addBox" id="add"> </div>
			<br>
				<div class="addBox" id="receiverID" style="font-size: 13px; font-weight: bold; color: #205890;">내용 <span style="color:#aaa;">(<span id="counter">0</span> / 1000자)</span></div>
				<textarea rows="15" cols="50" style="margin: 0px; width: 408px; height: 215px;" class="DOC_TEXT" name="DOC_TEXT"></textarea>
			<br>
			<a>임시저장</a>
			<button class="re" id="btnComment" type="button" onclick="goSubmit();">전송</button> 
			<button class="del" type="reset">취소</button> 
		</form>
  

</div>

<%-- ****** 학번/교수번호 찾기 Modal 시작****** --%>
  <div class="modal fade" id="userfind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
	      
	        <div class="modal-header">
		          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>  
		          <br>
		          <div align="center" style="font-weight: bold; font-size: 18pt;">쪽지주소검색</div>
		          <div align="center" >학번 혹은 교수번호가 쪽지 주소입니다.</div>
	        </div>
	        
	        <div class="modal-body" style="height: 250px; width: 100%;">        
		          <div id="idFind" >
		          	<iframe name="idFindiframe" id="iframe" style="border: none; width: 100%; height: 230px; " src="<%=ctxPath%>/message/userFind.sam">
		          	</iframe>
		          </div>	          
	        </div>
	         <div class="modal-footer">
	          	<button type="button" onclick="sumitSendForm()" class="btn btn-default myclose" data-dismiss="modal" style="margin-right: 180px;">선택하기</button>
	          	<button type="button" class="btn btn-default myclose" data-dismiss="modal">close</button>
	         </div>
        
      </div>
      
    </div>
  </div>
<%-- ****** 학번/교수번호 찾기 Modal 끝****** --%>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

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
	padding: 0 50px 0 50px;
	width: 80%;
}    
.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	margin-bottom: 30px;
 
}
.button {
	
	margin-top:40px;
	margin-left: 110px;
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
}
.button:hover {
    background-color: #27AF61;
	cursor: pointer;
	color: #fff;
	box-shadow: 0 2px 4px  #27AF61;
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
thead{
	color: black;
	background-color: #F9F9F9;
	
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
	margin-left: 30px;
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
/* /////////////////// */
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
	function outDelOne(outboxSeq){
		 var bool = confirm("해당 쪽지를 삭제하시겠습니까?");
		 if(bool){
			 location.href="<%= ctxPath%>/message/outDelOne.sam?outboxSeq="+outboxSeq;
		 }
	}
</script>

<div id="adminhome">

<div id=adminside  >
	<div class="row">
		<div class="col-md-12" >
			<button class="button" onclick="goWrite()">쪽지보내기</button>
			<div class="msgBox" id="inbox" style="  margin-top: 30px; padding-right: 55px;">받은쪽지함<div id="msgNew">${requestScope.nonReadCount}</div></div>
			<div class="msgBox" id="outbox" style="background-color: #2ECC71; padding-right: 100px;color: white;"  >보낸쪽지함</div>
		</div>
	</div>	
</div>

<div id="admincontent">
<div class="admsubtitle">
			<span >보낸쪽지함</span>
		</div>

		<button class="del" type="button" onclick="outDelOne(${requestScope.outboxvo.outboxSeq})">삭제</button>
		

	<c:if test="${not empty requestScope.outboxvo}">
		<div>받은사람 : <span>${requestScope.outboxvo.outboxName}(${requestScope.outboxvo.fk_perno})</span></div>
	 	<div>보낸시간 : <span>${requestScope.outboxvo.senDate}</span></div>
	 	<hr>
	 	<div>${requestScope.outboxvo.subject}</div>
	</c:if>
	
	<c:if test="${empty requestScope.outboxvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>

</div>

</div>

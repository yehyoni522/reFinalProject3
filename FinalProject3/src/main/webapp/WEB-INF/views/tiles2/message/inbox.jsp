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
	margin-left: 120px;
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
		location.href="<%= ctxPath%>/write.sam";
	}
	function goInbox(){
		location.href="<%= ctxPath%>/inbox.sam";
	}
	function goOutbox(){
		location.href="<%= ctxPath%>/outbox.sam";
	}
	function goView(seq){
		location.href="<%= ctxPath%>/inView.sam?inboxSeq="+inboxSeq;	
	}
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
			<div class="msgBox" id="inbox" style=" background-color: #2ECC71;  margin-top: 30px; color: white; padding-right: 55px;">받은쪽지함<div id="msgNew">2</div></div>
			<div class="msgBox" id="outbox" style="padding-right: 100px;" >보낸쪽지함</div>
		</div>
	</div>	
</div>

<div class="msgContents" style="width: 70%; display:inline-block ; ">

		<button class="del" type="button" onclick="javascript:location.href='<%= ctxPath%>/del.action?seq=${requestScope.boardvo.seq}'">삭제</button>
		 <button class="re" >답장</button>

	<div style="margin-left: 30px; display: inline-block; float: right;">
	
	<a style="font-size:10px;">안읽은쪽지삭제</a>&nbsp;&nbsp;<span style="color: #2ECC71; font-weight: bold;">5</span>/<span>500</span>
		<select id="msgSearch" name="msgSearch">
			<option value="name">이름</option>
			<option value="contents">내용</option>
		</select>
		<span class='green_window'>
			<input type='text' class='input_text' />
		</span>
			<button type='submit' class='sch_smit' onclick="goSearch();">검색</button>
	</div>

  <table class="table table-hover">
    <thead>
      <tr>
        <th><input type="checkbox" id = "check" name="checkall"/><label for="check"></label></th>
        <th>보낸사람</th>
        <th>내용</th>
        <th>날짜</th>
        <th>읽음표시</th>
      </tr>
    </thead>
    
    <c:forEach var="inboxvo" items="${requestScope.inboxList}" varStatus="status">     
    
    <tbody>
      <tr>
        <td><input type="checkbox" /></td>
        <td>${inboxvo.fk_name}</td>
        <td><span onclick="goView(${inboxvo.inboxSeq})">${inboxvo.subject}</span></td>
        <td>${inboxvo.reDate}</td>
        <c:if test="${inboxvo.readState == 0}">
        	<td style="color:red; font-weight: bold;">new</td>
        </c:if>
        <c:if test="${inboxvo.readState == 1}">
        	<td>읽음</td>
        </c:if>
      </tr>

    </tbody>
    
    </c:forEach>
    
  </table>

</div>


</body>
</html>
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
 .subjectStyle {font-weight: bold;
                   text-decoration:underline;
                   cursor: pointer;} 
.readstate{
	font-weight: bold;
    color: red;
   
}
tr#tr_1:hover{
	cursor: pointer;
}
a:visited {
  background-color : black;
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
		
		$("span.subject").bind("mouseover", function(event){
			var $target = $(event.target);
			$target.addClass("subjectStyle");
		});
		
		$("span.subject").bind("mouseout", function(event){
			var $target = $(event.target);
			$target.removeClass("subjectStyle");
		});
		
		$("input#searchWord").bind("keydown", function(event){
			if(event.keyCode == 13){
				goSearch();
			}
		});
		
		
	  if( ${not empty requestScope.paraMap}){
    	  $("select#searchType").val("${requestScope.paraMap.searchType}");
    	  $("input#searchWord").val("${requestScope.paraMap.searchWord}");
       }
	  
	 
		//== 체크박스 전체선택/전체해제 == 시작 //
		$("input:checkbox[name=checkall]").click(function(){
			var bool = $(this).prop("checked");
			// 체크되어있으면 true, 해제되어있으면 false
			
			$("input:checkbox[name=check]").prop("checked", bool);
		});
		// == 상품의 체크박스 클릭시 == //
		$("input:checkbox[name=check]").click(function(){
			var bool = $(this).prop("checked");
			
			if (bool) { // 현재 상품의 체크박스에 체크했을 때
				var bFlag = false;
				
				$("input:checkbox[name=check]").each(function(index, item){ // 다른 모든 상품의 체크박스 상태 확인
					var bChecked = $(item).prop("checked");
					if (!bChecked) {	// 체크표시가 안되어있는 상품일 경우 반복문 종료
						bFlag = true;
						return false;
					}
				});
				
				if(!bFlag) {	// 모든 체크박스가 체크되어있을 경우
					$("input:checkbox[name=checkall]").prop("checked", true);	
					
				}
			}
			else {	// 현재 상품의 체크박스에 체크 해제했을 때
				$("input:checkbox[name=checkall]").prop("checked", false);
				
			}
			
		});
		//== 체크박스 전체선택/전체해제 == 끝// 
	  
	  
	});
	
	// === 장바구니에서 특정 제품을 비우기 === //  
	function goInDel() {
		
		var cnt = $("input[name='check']:checked").length;
        var seqArr = new Array();
        $("input[name='check']:checked").each(function() {
        	seqArr.push($(this).val());
        });
        if(cnt == 0){
            alert("삭제하실 쪽지를 선택하세요.");
        }
        else{
        	     	
        	$.ajax({
                type: "post",
                url: "<%=ctxPath%>/message/goInDel.sam",
                data: {seqArr: seqArr},
                dataType:"json",
                success: function(json){
                    if(json.n != 1) {
                        alert("삭제 오류");
                    }
                    else{
                        alert("선택한 쪽지의 삭제를  성공했습니다.");
                        javascript:history.go(0);
                    }
                },
                error: function(request, status, error){
    				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    		 	}
        	});
        }
		
	}// end of function goInDel()---------------------------
	
	 function goSearch(){
	      
	      var frm = document.searchFrm;
	      frm.method = "get";
	      frm.action = "<%=ctxPath%>/message/inbox.sam";
	      frm.submit();
	      
	}// end of function goSearch()----------------------------------
	
	function goNonRead(){
		
		var frm = document.readState;
	      frm.method = "get";
	      frm.action = "<%=ctxPath%>/message/inbox.sam";
	      frm.submit();
	      
	      var x = document.getElementById("readstate");
			x.style.fontWeight = "bold";
		    x.style.color = "red"; 
    	
		
	}// end of function gatheringNonRead()----------------------------------
	function goWrite(){
		location.href="<%= ctxPath%>/message/write.sam";
	}
	function goInbox(){
		location.href="<%= ctxPath%>/message/inbox.sam";
	}
	function goOutbox(){
		location.href="<%= ctxPath%>/message/outbox.sam";
	}
	function goView(inboxSeq){
		location.href="<%= ctxPath%>/message/inView.sam?inboxSeq="+inboxSeq;	
	}
	function goreRead(){
		location.href="<%= ctxPath%>/message/inbox.sam";
	}
	
	function gorResponse(){

		var cnt = $("input[name='check']:checked").length;
        
        if(cnt > 1){
            alert("답장은 한개의 쪽지만 선택이 가능합니다!");
            return;
        }
        else{
        	 var name = $("input[name=name]").val();
        	 var fk_userid = $("input[name=fk_userid]").val();
        	location.href="<%= ctxPath%>/message/write.sam?fk_userid="+fk_userid+"&name="+name;
        	
        }
		
	}
</script>

<div class="msgHead">
	<div><div style="display:inline-block; width:10px; height: 30px; background-color: #3498DB;"></div>&nbsp;&nbsp;<span id="title">쪽지함</span></div>
	<hr style="border: solid 1px #E5E5E5;">
</div>

<div id="msgSide" >
	<div class="row">
		<div class="col-md-12" >
			<button class="button" onclick="goWrite()">쪽지보내기</button>
			<div class="msgBox" id="inbox" style=" background-color: #2ECC71;  margin-top: 30px; color: white; padding-right: 55px;">받은쪽지함<div id="msgNew">${requestScope.nonReadCount}</div></div>
			<div class="msgBox" id="outbox" style="padding-right: 100px;" >보낸쪽지함</div>
		</div>
	</div>	
</div>

<div class="msgContents" style="width: 70%; display:inline-block; ">

		<button class="del" type="button" onclick="goInDel()">삭제</button>
		 <button class="re" onclick="gorResponse()">답장</button>
		 

	<div style="margin-left: 100px; display: inline-block; float: right;">
	<form name="readState">
		<input type='hidden'  name="readState" value="0" />
	</form>
	<c:if test="${not empty requestScope.nonReadCount}">
		<a id="readstate" style="font-size:13px;  cursor: pointer;" onclick="goNonRead()">안읽은쪽지보기</a>&nbsp;&nbsp;<span style="color: #2ECC71; font-weight: bold;">${requestScope.nonReadCount}</span>/<span>${requestScope.totalCount}</span>
	</c:if>
	<c:if test="${empty requestScope.nonReadCount}">
		<a id="readstate" style="font-size:13px;  cursor: pointer; background-color: red; color: white;" onclick="goreRead()">안읽은쪽지보기</a>&nbsp;&nbsp;<span style="color: #2ECC71; font-weight: bold;">${requestScope.nonReadCount}</span>/<span>${requestScope.totalCount}</span>
	</c:if>
	<form name="searchFrm">
		<select id="searchType" name="searchType">
			<option value="subject">내용</option>
			<option value="inboxname">이름</option>
		</select>
		<span class='green_window'>
			<input type='text'  name="searchWord" id="searchWord"  class='input_text' />
		</span>
			<button type='button' class='sch_smit' onclick="goSearch();">검색</button>
	</form>
	
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
    
    <c:if test="${empty requestScope.inboxList}">
    	<tbody>
	      <tr id="tr_1" style="color: #055AC1;">
	        <td colspan="5" style="text-align: center;">받은 쪽지가 존재하지 않습니다.</td>
	      </tr>
	    </tbody>
    </c:if>
    
    <c:forEach var="inboxvo" items="${requestScope.inboxList}" varStatus="status">     
    
    <c:if test="${inboxvo.readState == 0}">
    <tbody>
      <tr  id="tr_1" style="color: #055AC1;">
        <td><input type="checkbox" name="check" value="${inboxvo.inboxSeq}"/></td>
        <td onclick="goView(${inboxvo.inboxSeq}" >${inboxvo.inboxName}
        <input type="hidden" name="name" value="${inboxvo.inboxName}" />
        <input type="hidden" name="fk_userid" value="${inboxvo.fk_perno}" />
        </td>
        <td onclick="goView(${inboxvo.inboxSeq})"><span class="subject" >${inboxvo.subject}</span></td>
        <td onclick="goView(${inboxvo.inboxSeq})">${inboxvo.reDate}</td>
        <td onclick="goView(${inboxvo.inboxSeq})" style="color:red; font-weight: bold;">new</td>
      </tr>
    </tbody>
    </c:if>
    
    <c:if test="${inboxvo.readState == 1}">
    <tbody>
      <tr  id="tr_1" style="color: gray;">
        <td><input type="checkbox" name="check" value="${inboxvo.inboxSeq}"/></td>
        <td onclick="goView(${inboxvo.inboxSeq})">${inboxvo.inboxName}
        <input type="hidden" name="name" value="${inboxvo.inboxName}" />
        <input type="hidden" name="fk_userid" value="${inboxvo.fk_perno}" />
        </td>
        <td onclick="goView(${inboxvo.inboxSeq})"><span class="subject" >${inboxvo.subject}</span></td>
        <td onclick="goView(${inboxvo.inboxSeq})">${inboxvo.reDate}</td>
        <td onclick="goView(${inboxvo.inboxSeq})">읽음</td>
      </tr>
    </tbody>
    </c:if>
    
    </c:forEach>
    
  </table>
  
  <div align="center" style="width: 70%; margin: 20px auto;">
     	${requestScope.pageBar}
   </div>
</div>




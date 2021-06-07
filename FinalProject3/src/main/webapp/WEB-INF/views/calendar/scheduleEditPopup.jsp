<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html> <html> <head> <meta charset="UTF-8"> 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

 <% String ctxPath = request.getContextPath(); %>  
<title>일정 추가</title>
 <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> 
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>  
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<link href="resources/custom/css/schedule.css" rel="stylesheet" />
<script src="resources/custom/js/schedule.js" type="text/javascript"></script> 
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<style>
.group-head{
	margin-bottom: 30px;
}
.subject{
	width: 355px;
}
input{
	height: 30px;
}
.group-body{

	margin-left: 20px;
}
#div_btnOk{
	margin-top: 30px;
	margin-right: 30px;

}
button#End{
		background-color: #f2f2f2;
		color: black;
		border: none;
		cursor: pointer;
		height:50px;
		width:120px;
	}
button#ok {
		background-color: #666;
		color: white;
		border: none;
		cursor: pointer;
		height:50px;
		width:120px;
		margin-right: 10px;
}


</style>
<script type="text/javascript">
$(document).ready(function(){
	backgroundCh = function() {
	    var sel = document.getElementById('color');
	    sel.style.backgroundColor = sel.value;
	};
});

 $(function() { 
		$.datepicker.setDefaults({
			
			dateFormat : 'yy-mm-dd', 
			showOtherMonths : true, 
			showMonthAfterYear : true, 
			changeYear : true, 
			changeMonth : true, 
			yearSuffix: "년",
			monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], 
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], 
			dayNamesMin: ['일','월','화','수','목','금','토'], 
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] });
		
		$("#startDate").datepicker(); 
		$("#endDate").datepicker(); 
		$("#startDate").datepicker('setDate', '${startDate}'); 
		$("#endDate").datepicker('setDate', '${endDate}'); 
	});
 
function click_ok(){

	 var calsubject = $("input#calsubject").val().trim();
	var memo = $("textarea#memo").val().trim();
	
	if(!$('#color> option:selected').val()) {
	    alert("색상을 선택해주세요");
	    return;
	}
	
	if(calsubject == "") {
		alert("제목을 입력하세요!!");
		$("input#lcalsubject").focus();
		return; 
	}
	if(memo == "") {
		alert("메모를 입력하세요!!");
		$("input#memo").focus();
		return;  
	}
 	var frm = document.scheduleData;
	frm.method = "POST";
	frm.action = "<%= ctxPath%>/scheduleEditEnd.sam";
	frm.submit();
}

</script>
</head> 
<body> 
	<div class = "group" id = "popupGroup"> 
		<div class = "group-head"> 
			<h1 style="	margin-left: 5px;"> 일정 수정 </h1>
			<hr>
		</div> 
		
		<div class = "group-body"> 
				 	
			
				<form name = "scheduleData">
					<div class = "domain"> 
				 		<h3> 색상 </h3> 
				 	</div> 
					<div>
						<select name="color" id="color" class="color" style="height: 26px;"  onchange="backgroundCh();">
								<option value="">색상을 선택해주세요</option>
								<c:choose>
								
								<c:when test="${sessionScope.loginuser.identity == 2}">
									<option value="#FFCD42"style=" background-color:#FFCD42;"></option>
								</c:when>
								<c:otherwise>
								<option value="#9775FA"style=" background-color:#9775FA;"></option>
								<option value="#F06595"style=" background-color:#F06595;"></option>
								<option value="#919191"style=" background-color:#919191;"></option>
								<option value="#46E086"style=" background-color:#46E086;"></option>
								<option value="#3788D8" style=" background-color:#3788D8;"></option>
								</c:otherwise>
								</c:choose>
						</select>
					</div>
				
					<div class = "domain"> 
				 		<h3> 제목 </h3> 
				 	</div> 
				 	<div class = "top"> 
				 		<input class = "subject" id = "calsubject" type = "text" name = "calsubject" value="${title}" placeholder="제목을 입력해주세요"> 
				 	</div> 
				 	
				 	<div class = "domain"> 
				 		<h3> 기간</h3> 
				 	</div> 
				 	
				 	<div class = "domain"> 
				 		<input class = "date" id = "startDate" type = "text" name = "startDate" id = "startDate" value="2020-01-01">
						~
				 		<input class = "date" id = "endDate" type = "text" name = "endDate" id = "endDate" value="${endDate}"> 
				 	 </div> 
				 	 
				 	 <div class = "domain" >
				 	  	<h3> 메모 </h3> 
				 	 </div> 
				 	  
				 	 <div class = "domain"> 
				 	  <textarea  class = "memo" id = "memo" name = "memo" rows = "5" cols = "51" placeholder="100글자까지 입력 가능합니다" >${memo}</textarea> 
				 	  </div>
				 	      <input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}"/>
				 	         <input type="hidden" name="schno" value="${schno}"/>
				 </form>
				 <div id="div_btnOk" align="center">
			   		<button type="button" id="ok" onclick="click_ok();">수정</button>
		   		  <button type="button" id="End" onClick='window.close()'>닫기</button>
		      </div>
		   
		 </div> 
	 </div> 
</body> 
</html>

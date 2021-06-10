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
	   	    frm.action = "<%= ctxPath%>/class/qnaView.sam";
	   	    frm.submit();
		});
		
		
	});// end of function goView(assgnno) {}-----------------------
	   	
	   
	
</script>   
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<div class="container">

	<div class="headerCategoty">
	<i class="fas fa-university "></i>
	&nbsp;>&nbsp;홈
	</div>

	<h1 class="headerName">${sessionScope.subject}</h1>
	<br>
	<br>



	<form name="goViewFrm">	
		<input type="hidden" name="qnano" />
		<input type="hidden" name="fk_perno" />
	</form>



</div>




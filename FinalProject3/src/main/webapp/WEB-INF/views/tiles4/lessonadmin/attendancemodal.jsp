<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>   

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">


	span.signmodal {
		border: 1px red solid;
		font-size: 17pt;
	}

</style>

<script type="text/javascript">
	
	
	function signgo(){
		
		$.ajax({
			url:"<%=ctxPath%>/lesson/attendancesign.sam",
			type:"post",
			data:{"fk_subno":"${requestScope.fk_subno}",
				  "fk_perno":"${requestScope.fk_perno}"},
			dataType:"json",
		   	success:function(json) {
		   		
		   		var jrandomsign = json.randomsign;
		   				   		
		   		$("span.signmodal").text(jrandomsign);
		   		
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});	
		
	} // end of function signgo(){}
	
</script>

<html style="border: 0px solid;">
<body style="border: 0px solid;">

<div align="center" style="padding-top: 55px;">
	<span class="signmodal" style="border: 0px solid;"><button type="button" onclick="signgo()">클릭</button></span>
</div>    

</body>
</html>
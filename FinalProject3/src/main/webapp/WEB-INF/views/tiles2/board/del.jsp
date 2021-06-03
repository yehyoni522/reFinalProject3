<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">

   table, th, td, input, textarea {border: solid gray 1px;}
   
   #table {border-collapse: collapse;
          width: 900px;
          }
   #table th, #table td{padding: 5px;}
   #table th{width: 120px; background-color: #DDDDDD;}
   #table td{width: 860px;}
   .long {width: 470px;}
   .short {width: 120px;}

</style>

<script type="text/javascript">
$(document).ready(function(){
	
 	removeCheck();   
 	
});// end of $(document).ready(function(){})----------------
   
function removeCheck() {

	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
	
 		var frm = document.delFrm;
	   	frm.method = "POST";
	   	frm.action = "<%= ctxPath%>/board/del.sam";
	   	frm.submit();	
	}else{   //취소	
		
	    return false;	
	}

}
</script>

<%-- <span>${requestScope.gobackURL}</span><br> --%>
<form name="delFrm"> 
	<input type="hidden" name="categoryno" value="${requestScope.categoryno}" />
    <input type="hidden" name="seq" value="${requestScope.seq}" />          
</form>
 
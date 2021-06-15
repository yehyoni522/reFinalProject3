<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>    



<style type="text/css">

table.studentList {
  border-collapse: separate;
  border-spacing: 1px;
  text-align: center;
  line-height: 1.5;
  margin: 20px 10px;
  width: 85%;
  font-size: 12pt;
}
table.studentList th {
  width: 155px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  color: #575b5b;
  background-color: #f2f2f2;
  text-align: center;
}
table.studentList td {
  width: 155px;
  padding: 10px;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
  background-color: #f2f3f3
}
button#lesson {
 border-radius: 5%;
	  border: none;
	  color: white;
	  font-weight: bold;
	  margin: 10px 5px 0 5px;
	  font-size: 14pt;
	  width: 150px;	
	  background-color: #bfbfbf;
	  height: 40px;
}


</style>

<script>

	$(function(){
		
 		var sign_frm = document.takemodalFrm;
		
 		sign_frm.target = "signoutput";
 		sign_frm.action = "<%=ctxPath%>/lesson/attendancemodal.sam";
 		sign_frm.method = "post";
 		sign_frm.submit();
		
 		$("#dateList").change(function(){
 			
 			var html = "";
 			
 			if($("#dateList").text() != "날짜를 선택해주세요!"){
 			
	 	        $.ajax({
	 	        	url:"<%= ctxPath%>/lesson/studentsignList.sam",
	 	            type: "post",
	 	            data:  {"attendancedate":$('#dateList option:selected').val()
	 	            	   ,"subno":"${requestScope.subno}"},
	 	       		dataType:"json",
	 	       		success:function(json){
	
	 	       			
						
	 	       				if(json.length > 0){
	 	       					
	 	       					html = "";
	 	       					
		 	       				$.each(json, function(index, item){
		 	       				
			 	       				html += "<tr>";
			 	       				
			 	       				html += "<td>"+(index+1)+"</td>";       				
			 	       				html += "<td>"+item.perno+"</td>";
			 	       				html += "<td>"+item.name+"</td>";
			 	       				html += "<td>"+item.inputatdcdate+"</td>";       				
			 	       				
			 	       				html += "</tr>";
		 	       				
		 	       				});
	 	       				}
	 	       				else {
	 	       				html = "<tr><td colspan='4'>조회된 학생이 없습니다.</td></tr>";
	 	       				}
					
	 	       				       			
	 	       				$("tbody.in").html(html);
	 	       				
		 	       	}, error: function(request, status, error){
					      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
	 	       	 }); // end of $.ajax({})
 			}
 			else{
 				html = "<tr><td colspan='4'>조회된 학생이 없습니다.</td></tr>";
 				$("tbody.in").html(html);
 			}
 			
 		}); 
 		
	}); // END OF $(function(){})

</script>

<div align="center" style="margin-top: 200px; margin-left:480px; min-height: 600px; width: 60%;">

	<div align="center" style="font-size: 18pt; font-weight: bold;">출석</div>
	
	<div align="center">
			<button id="lesson" type="button" style="background-color: #bfbfbf;" data-toggle="modal" data-target="#sign">출석시작</button>
	</div>
	
	<div align="right" style="width: 90%; margin-top: 10px;">	
		<form name="attendancedateFrm" >
		<select id="dateList" name="dateList" style="margin-right:25px; width:110px; size: 10pt; text-align: center; ">			
			<c:forEach var="attendancevo" items="${requestScope.attendanceList}" varStatus="status">
				<option>${attendancevo.attendancedate}</option>
			</c:forEach>
		</select> 
		</form>
	</div>

	<table class="studentList" style="margin-top: 20px;">
	
	  <thead>
	  <tr>
	    <th scope="cols">No</th>
	    <th scope="cols">학번</th>
	    <th scope="cols">이름</th>  
	    <th class="123" scope="cols">출석시간</th>
	  </tr>
	  </thead>
	  
	  <tbody class="in">
	  <tr>
	  <td colspan="4">조회된 학생이 없습니다.</td>
	  </tr>
	  </tbody>
	</table>
	
  <!-- Modal 출석  신호 시작  -->
  <div class="modal fade" id="sign" role="dialog" style="margin-top: 150px;">
    <div class="modal-dialog" style="width: 450px; height: 30px;" >
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" align="center" style="border:none;">
        	<span style="font-size: 20pt; ">출석번호</span>
        </div>
        <div class="modal-body" align="center" >
          <iframe id="signoutput" name="signoutput" src="<%=ctxPath%>/lesson/attendancemodal.sam">
		  </iframe>
        </div>
        <div class="modal-footer" style="text-align: center; ">
          <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
        </div>
      </div>
      
    </div>
  </div>
  <!-- Modal 출석  신호 끝  -->	
	
  <form name="takemodalFrm">
  	<input type="hidden" name="fk_subno" value="${requestScope.subno}" />
  	<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}" />
  </form>	

</div>
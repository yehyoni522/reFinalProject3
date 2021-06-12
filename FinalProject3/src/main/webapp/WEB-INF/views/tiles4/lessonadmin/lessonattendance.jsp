<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
     
%>    

<style type="text/css">

	.colorbox {
	margin-right: 15px;
	}
	table {
    width: 70%;
    border: 1px solid #444444;
    font-family: 'Noto Sans KR', sans-serif;
  	}
	th, td {
    border: 1px solid #444444;
    width: 30px;
    height: 100px;
  	}
  	div.weeks {
  	height: 60%; font-size: 15pt; padding: 10px 0 0 10px; font-weight: bold;
  	}
  	div.date {
  	padding: 0 10px 10px 0;
  	font-size: 12pt;
  	}
	.checkon {
		background-color: #8cd98c;
	}
	.lateness {
		background-color: #ffc266;
	}
	.off {
		background-color: #ff6666;
	}
	
	button {
	  border-radius: 5%;
	  border: none;
	  color: white;
	  font-weight: bold;
	  margin: 10px 5px 0 5px;
	  font-size: 14pt;
	  width: 150px;	
	  background-color: #bfbfbf;
	}
	
</style>

<script>

	$(function(){
 		
 		// 접속한 회원의 출석 정보를 표에 넣는다.
 		$.ajax({
			url:"<%=ctxPath%>/lesson/attendancecheckList.sam",
			type:"post",
			data:{"fk_subno":"${requestScope.fk_subno}",
				  "fk_perno":"${sessionScope.loginuser.perno}"},
			dataType:"json",
		   	success:function(json) {
		   		
		   		html = "";
		   		
		   		// console.log("ㅇㅇ");
		   		
		   		$.each(json, function(index, item){
		   			
		   			// console.log("dd");
		   			
		   			html += "<div class='weeks' align='left'>"+(index+1)+"주차</div>";
		   			html += "<div class='date' align='right'>"+item.inputatdcdate+"</div>";
					
		   			
		   			$("td#weekno"+(index+1)).html(html);
		   			
		   			if(Number(item.inputatdcstatus) == 0){
		   				$("td#weekno"+(index+1)).addClass('off');
		   			}
		   			if(Number(item.inputatdcstatus) == 1){
		   				$("td#weekno"+(index+1)).addClass('lateness');
		   			}
		   			if(Number(item.inputatdcstatus) == 2){
		   				$("td#weekno"+(index+1)).addClass('checkon');
		   			}
		   			
		   		}); // end of $.each(json, function(index, item){});
		   		
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		   	
		});	// end of $.ajax({}) 
		
	}); // end of $(function(){})



</script>

<div style="margin: 160px 50px 40px 150px; font-family: 'Noto Sans KR', sans-serif;">

	<div align="center" style="font-size: 18pt; font-weight: bold;">출석</div>
	
	<div align="center"><button type="button" data-toggle="modal" data-target="#sign">출석하기</button></div>
	
	<div align="right" style="margin-bottom: 15px; margin-right: 130px;">
	<span class="colorbox">
		<span  style="background-color: #8cd98c;">&nbsp;&nbsp;&nbsp;</span>출석
	</span>
	<span class="colorbox">
		<span style="background-color: #ffc266;">&nbsp;&nbsp;&nbsp;</span>지각
	</span>
	<span class="colorbox">
		<span style="background-color: #ff6666;">&nbsp;&nbsp;&nbsp;</span>결석
	</span>
	</div>
	
	<div align="center">
		<table>
			<tbody>
				<tr id="week1">
					<td id="weekno1" style="width: 25%;">
						<div class="weeks" align="center">1주차</div>
					</td>
					<td id="weekno2" style="width: 25%;">
						<div class="weeks" align="center">2주차</div>
					</td>
					<td id="weekno3" style="width: 25%;">
						<div class="weeks" align="center">3주차</div>
					</td>
					<td id="weekno4" style="width: 25%;">
						<div class="weeks" align="center">4주차</div>
					</td>					
				</tr>
				<tr id="week2">
					<td id="weekno5" style="width: 25%;">
						<div class="weeks" align="center">5주차	</div>
					</td>
					<td id="weekno6" style="width: 25%;">
						<div class="weeks" align="center">6주차	</div>
					</td>
					<td id="weekno7" style="width: 25%;">
						<div class="weeks" align="center">7주차	</div>
					</td>
					<td id="weekno8" style="width: 25%;">
						<div class="weeks" align="center">8주차	</div>
					</td>					
				</tr>
				<tr id="week3">
					<td id="weekno9" style="width: 25%;">
						<div class="weeks" align="center">9주차	</div>
					</td>
					<td id="weekno10" style="width: 25%;">
						<div class="weeks" align="center">10주차</div>
					</td>
					<td id="weekno11" style="width: 25%;">
						<div class="weeks" align="center">11주차</div>
					</td>
					<td id="weekno12" style="width: 25%;">
						<div class="weeks" align="center">12주차</div>
					</td>					
				</tr>		
				<tr id="week4">
					<td id="weekno13" style="width: 25%;">
						<div class="weeks" align="center">13주차</div>
					</td>
					<td id="weekno14" style="width: 25%;">
						<div class="weeks" align="center">14주차</div>
					</td>
					<td id="weekno15" style="width: 25%;">
						<div class="weeks" align="center">15주차</div>
					</td>
					<td id="weekno16" style="width: 25%;">
						<div class="weeks" align="center">16주차</div>
					</td>					
				</tr>
			</tbody>
		</table>
	</div>

  <form name="takemodalFrm">
  	<input type="hidden" name="fk_subno" value="${requestScope.subno}" />
  	<input type="hidden" name="fk_perno" value="${sessionScope.loginuser.perno}" />
  </form>	

</div>


  <!-- Modal 출석  신호 시작  -->
  <div class="modal fade" id="sign" role="dialog" style="margin-top: 150px;">
    <div class="modal-dialog" style="width: 450px; height: 30px;" >
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" align="center" style="border:none;">
        	<span style="font-size: 20pt; ">출석번호</span>
        </div>
        <div class="modal-body" align="center" >
          <iframe id="signoutput" name="signoutput" src="<%=ctxPath%>/lesson/studentmodal.sam">
		  </iframe>
        </div>
        <div class="modal-footer" style="text-align: center; ">
          <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
        </div>
      </div>
      
    </div>
  </div>
  <!-- Modal 출석  신호 끝  -->

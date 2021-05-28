<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String ctxPath = request.getContextPath();
%>

<style>

.container2 {
	max-width: 800px;
	margin: 0 auto;
}

.seat {
	padding: 7px;

}



</style>
<script type="text/javascript">

	var rname = "제1열람실";
	var tno = 1;
	$(function() {
		
		var cnt = 0;
		var html = "";
		$(document).on('click','td.seat',function(){
			
			//console.log("gg" + $(this).find("input").val() );
			// 너 이자리 예약할거야? 제?열람실 좌석번호 시간대 인데?
		});
		
		$(document).on('click','a.nav-link',function(){
			rname = $(this).text();
		});
		
		$("select.timeselect").bind("change", function(){
			tno = $(this).val();
			goTimeSelectView(rname, tno)
		});
	}); // end of function() {} ------------------------
	
	
	function goTimeSelectView(rname, tno) {
		// ajax 들어갈 자리
		// select 값과 탭이름을 가지고 가서 좌석을 조회해 와야함
		html = "";
		var count = 0;
		$.ajax({
			url:"<%=ctxPath%>/reading/selectViewSeat.sam",
			type:"get",
			data:{"rname":rname, "tno":tno},
			dataType:"json",
		   	success:function(json) {
		   		$.each(json, function(index, item){
		   			count++;
		   			if(count == 1) {
		   				html += "<tr>";
		   			}
		   			
		   			if(item.dscheck == '1') {
		   				html += '<td class="seat"><img src="<%= ctxPath%>/resources/images/selectedseat.png" style="width:23px; height:23px;"><input type="hidden"  value="item.dsno"/></td>';
		   			} else {
		   				html += '<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png" id="abc" style="width:23px; height:23px; cursor:pointer;"><input type="hidden"  value='+item.dsno+'></td>'
		   			}
		   			
		   			if(count == 5) {
		   				html += "</tr>";
		   				count = 0;
		   			}
		   			
		   		});
		   		$("table#tableseat").html(html);
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}

</script>

<div class="container container2">
	<h1 align="center"  style="font-weight:bold;">열람실 예약</h1><br><br>
	
	
	
	<ul class="nav nav-tabs" style="padding-left:235px;">
	  <c:forEach var="list" items="${requestScope.rRoomList}">
         <li class="nav-item">
         	<a class="nav-link" data-toggle="tab" href="#${list.rcode}">${list.rname}</a>
		</li>
      </c:forEach>
  </ul>
	<br>
	<div class="tab-content" align="center">
		<div class="tab-pane active" id="home">
			<div class="form-group" style="width:150px;">
			  <label for="sel1">시간별 좌석보기</label>
			  <select class="form-control timeselect" id="sel1">
			  	<c:forEach var="tvo" items="${requestScope.timeList}">
			  		<option value="${tvo.tno}" >${tvo.tname}</option>
		        </c:forEach>
			  </select>
			</div>
			<table id = "tableseat"> <%-- 좌석이미지가 들어 갈 부분 --%>
			</table>
		</div>
		
	</div>
	<br><br>
	<div align="center">
	<textarea style="width:600px; height:150px;'" readonly>
	&nbsp;※이용안내※
	열람실 예약 후 실사용자는 37%밖에 되지 않습니다.
	실제 열람실 이용이 필요한 학우들이 피해를 보고있어 쌍용대학교는 보증금을 받기로 했습니다.
	
	1. 좌석 예약 시 500원 결제
	2. 열람실 이용 확인
	3. 관리자 확인 후 500원 환불
	## 열람실을 이용하지 않았을 경우 미환불
	
	보증금으로 인해 발생한 수익금은 도서관의 쾌척한 환경을 위해 사용됩니다.
	## 매월말 해당 달의 보증금 현황에 대한 보고서 도서관 1층 공지사항란에 부착.
	</textarea>
	<br><br><br>
	<input type="checkbox" id="chk1"><label for="chk1">이용약관에 동의합니다.</label><br>
	<input type="checkbox" id="chk2"><label for="chk2">쾌적한 도서관 이용에 동참할 것을 동의합니다.</label><br><br>
	
	<button type="button" class="btn btn-default btn-lg">결제하기</button>
	</div>

      
</div>
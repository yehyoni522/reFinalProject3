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

	$(function() {
		
		var cnt = 0;
		var html = "";
		$("td.seat").click(function() {
			if(cnt == 0) {
				html = '<img src="<%= ctxPath%>/resources/images/selectedseat.png"  style="width:23px; height:23px; cursor:pointer;">';
				$(this).html(html);
				cnt++;
			} else {
				alert("예약은 한 좌석만 가능합니다.");
				cnt = 0;
			}
			
		})
	});

</script>

<div class="container container2">
	<h1 align="center"  style="font-weight:bold;">열람실 예약</h1><br><br>
	
	
	
	<ul class="nav nav-tabs" style="padding-left:235px;">
	  <c:forEach var="list" items="${requestScope.RroomList}">
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
			  <select class="form-control" id="sel1">
			    <option>00:00 ~ 02:00</option>
			    <option>02:00 ~ 04:00</option>
			    <option>04:00 ~ 06:00</option>
			    <option>06:00 ~ 08:00</option>
			    <option>08:00 ~ 10:00</option>
			    <option>10:00 ~ 12:00</option>
			    <option>12:00 ~ 14:00</option>
			    <option>14:00 ~ 16:00</option>
			    <option>16:00 ~ 18:00</option>
			    <option>18:00 ~ 20:00</option>
			    <option>20:00 ~ 22:00</option>
			    <option>22:00 ~ 24:00</option>
			  </select>
			</div>
			<table>
				<tr>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
				</tr>
				<tr>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
				</tr>
				<tr>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
				</tr>
				<tr>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
					<td class="seat"><img src="<%= ctxPath%>/resources/images/seat.png"  style="width:23px; height:23px; cursor:pointer;"></td>
				</tr>
			</table>
		</div>
		<div class="tab-pane" id="ver2">
			<span>테스트2</span>
		</div>
		<div class="tab-pane" id="ver3">
			<span>테스트3</span>
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
	<input type="radio">이용약관에 동의합니다.<br>
	<input type="radio">쾌적한 도서관 이용에 동참할 것을 동의합니다.<br><br>
	
	<button type="button" class="btn btn-default btn-lg">결제하기</button>
	</div>

      
</div>
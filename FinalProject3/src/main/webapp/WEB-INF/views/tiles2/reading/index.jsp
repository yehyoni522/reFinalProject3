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
	var cnt = 0;
	var dsno = 0;
	$(function() {
		
		goTimeSelectView(rname, tno);
		
		
		var html = "";
		$(document).on('click','td.seat',function(){
			
			dsno = $(this).find("input").val();
			
			cnt++;
			
			var tagId = $(this).attr('id');
			//console.log("ggg" + tagId);
			
			if(tagId == "disabled") {
				cnt = 0;
				alert("이미 예약된 좌석은 선택하실 수 없습니다.");
				return;
				
			}
			
			if(cnt > 1) {
				alert("한 좌석 이상 예약하실 수 없습니다.");
				return;
			} else {
				//console.log("gg" + $(this).find("input").val() );
				var value = $(this).find("input").val();
				
				$(this).html('<img src="<%= ctxPath%>/resources/images/colorchangeseat.png" style="width:23px; height:23px; cursor:pointer;"><input type="hidden"  value='+value+'>');
				//console.log("gg" + $(this).find("input").val() );
				
				$(this).click(function() {
					cnt = 0;
					dsno = 0;
					$(this).html('<img src="<%= ctxPath%>/resources/images/seat.png" style="width:23px; height:23px; cursor:pointer;"><input type="hidden"  value='+value+'>');
				});
				
			}
			
			
			
		});
		
		$(document).on('click','a.nav-link',function(){
			rname = $(this).text();
			cnt = 0;
			goTimeSelectView(rname, tno);
		});
		
		$("select.timeselect").bind("change", function(){
			tno = $(this).val();
			cnt=0;
			goTimeSelectView(rname, tno);
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
		   			count++; // 한 줄에 5석씩 출력하기 위한 변수
		   			if(count == 1) {
		   				html += "<tr>";
		   			}
		   			
		   			if(item.dscheck == '1') {
		   				html += '<td class="seat" id="disabled"><img src="<%= ctxPath%>/resources/images/selectedseat.png" style="width:23px; height:23px;"><input  type="hidden"  value="item.dsno"/></td>';
		   			} else {
		   				html += '<td class="seat" id="selected"><img src="<%= ctxPath%>/resources/images/seat.png" style="width:23px; height:23px; cursor:pointer;"><input id="seat" type="hidden"  value='+item.dsno+'></td>'
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
	} // end of function goTimeSelectView(rname, tno) {} -------------------
	
	
	function checkSeat() {
		// 결제하기 버튼을 눌렀을 때 결제 전 선택한 좌석의 정보를 먼저 alert 띄워준 후 결제창으로 넘어간다.
		// 그 전에 체크박스를 모두 선택했는지 확인.
		
		 if (${sessionScope.loginuser == null}){
				alert("로그인 후 이용해주세요.");
				return;
		}
		 
		if(dsno == 0) {
			alert("좌석을 선택해주세요.");
			return;
		}
		
		if($("input#chk1").prop("checked") && $("input#chk2").prop("checked")) {
			searchSeatInfo(dsno);
		} else {
			alert("모두 동의 후 결제 버튼을 눌러주세요");
			return;
		}	
		
	}
	
	function searchSeatInfo(dsno) {
		// 선택한 좌석의 정보를 alert 해준다. 열람실명, 시간, 좌석명
		
		
		var html = '<div class="modal fade" id="myModal" role="dialog">' +
						    '<div class="modal-dialog modal-sm">'+
					      '<div class="modal-content">' +
					        '<div class="modal-header">' +
					          '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
					          '<h4 class="modal-title">열람실 예약하기</h4>' +
					        '</div>' +
					        '<div class="modal-body"  align="center">' +
					        '</div>' +
					        '<div class="modal-footer">' +
					          '<button type="button" class="btn btn-default" onclick="goCoinPurchaseEnd();">예약</button>' +
					          '<button type="button" class="btn btn-default" onclick="javascript:history.go(0);">취소</button>' +
					        '</div>'+
					        '</div>'+
					        '</div>'+
					  '</div>';
		
		$("div#md").html(html);
		
		$.ajax({
			url:"<%=ctxPath%>/reading/searchSeatInfo.sam",
			type:"get",
			data:{"dsno":dsno},
			dataType:"json",
		   	success:function(json) {
		   		html += "<span>";
		   		html += "1. 열람실 : " + json.rname + "<br>2. 시간 : " + json.tname + "<br>3. 좌석번호 : " + json.dsname + "<br><br>";
		   		html += "위 좌석으로 예약하시겠습니까?";
		   		html += "</span>"; 
		   		$("div.modal-body").html(html);
		   		
		   		var jsontname = json.tname;
		   		var tname = jsontname.replace(/\s/gi, "");
		   		html = "<input type='hidden' id='rname' value="+json.rname+">";
		   		html += "<input type='text' id='tname' value="+tname+">";
		   		html += "<input type='hidden' id='dsname' value="+json.dsname+">";
		   		$("form#seatinfo").html(html);
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	
	function goCoinPurchaseEnd() {
		//	alert("확인용 부모창의 함수 호출함. 사용자ID : " + userid + " , 결제금액 : " + coinmoney + "원");
		
		var perno = "${sessionScope.loginuser.perno}";

		//  아임포트 결제 팝업창 띄우기  
			var url = "<%= request.getContextPath()%>/reading/coinPurchaseEnd.sam?perno="+perno+"&dsno="+dsno; 
			
			window.open(url, "coinPurchaseEnd",
					    "left=350px, top=100px, width=820px, height=600px");
	 		
		}
	
	function goCoinUpdate() {
		$("#myModal").modal('hide');
		$.ajax({
			url:"<%=ctxPath%>/reading/updateSeatInfo.sam",
			type:"post",
			data:{"fk_dsno":dsno, "fk_perno":"${sessionScope.loginuser.perno}", "fk_tno":tno},
			dataType:"json",
		   	success:function(json) {
		   		if(json.m == 1) {
		   			alert("예약이 완료되었습니다.");
		   			sendEmail(dsno);
		   		} else {
		   			alert("예약이 실패되었습니다.");
		   		}
		   		location.reload();
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	
	function sendEmail(dsno) {
		
		$.ajax({
			url:"<%= ctxPath%>/reading/sendEmail.sam",
			type:"POST",
			data:{"rname":$("input#rname").val(), "tname":$("input#tname").val(), "dsname":$("input#dsname").val(), "email":"${sessionScope.loginuser.email}"},
			dataType:"json",
			success:function(json) {
				if(json.sendMailSuccess == 1) {
					alert("메일 전송 실패. 자세한 사항은 관리자에게 문의하세요.");
				}
			}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}

</script>

<div class="container container2">
	<h1 align="center"  style="font-weight:bold;">열람실 예약</h1><br><br>
	
	
	
	<ul class="nav nav-tabs" style="padding-left:240px;">
	  <c:forEach var="list" items="${requestScope.rRoomList}" varStatus="status">
         <li class="nav-item">
         	<c:if test="${status.count eq 1}">
         		<a class="nav-link active" data-toggle="tab" href="#${list.rcode}">${list.rname}</a>
         	</c:if>
         	<c:if test="${status.count ne 1}">
         		<a class="nav-link" data-toggle="tab" href="#${list.rcode}">${list.rname}</a>
         	</c:if>
		</li>
      </c:forEach>
  </ul>
	<br>
	<div class="tab-content" align="center">
		<c:forEach var="list" items="${requestScope.rRoomList}" varStatus="status">
			<c:if test="${status.count eq 1}">
				<div class="tab-pane active" id="${list.rcode}">
					<div class="form-group" style="width:150px;">
					  <label for="sel1">시간별 좌석보기</label>
					  <select class="form-control timeselect" id="sel1">
					  	<c:forEach var="tvo" items="${requestScope.timeList}">
					  		<option value="${tvo.tno}" >${tvo.tname}</option>
				        </c:forEach>
					  </select>
					</div>
			<c:if test="${status.count ne 1}">
				<div class="tab-pane" id="${list.rcode}">
					<div class="form-group" style="width:150px;">
					  <label for="sel1">시간별 좌석보기</label>
					  <select class="form-control timeselect" id="sel1">
					  	<c:forEach var="tvo" items="${requestScope.timeList}">
					  		<option value="${tvo.tno}" >${tvo.tname}</option>
				        </c:forEach>
					  </select>
					</div>
				</div>
			</c:if>
					<table id = "tableseat"> <%-- 좌석이미지가 들어 갈 부분 --%>
					</table>
				</div>
			</c:if>
         
      </c:forEach>
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
	<input type="checkbox" id="chk1"><label for="chk1">이용안내에 동의합니다.</label><br>
	<input type="checkbox" id="chk2"><label for="chk2">쾌적한 도서관 이용에 동참할 것을 동의합니다.</label><br><br>
	
	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"onclick="checkSeat();">결제하기</button>
	</div>

	<div id="md">
	</div>
      
    <div>
    	<form  id="seatinfo" name="seatinfo">
    	</form>
    </div>
</div>
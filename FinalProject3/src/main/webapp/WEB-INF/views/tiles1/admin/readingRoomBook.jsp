<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	String ctxPath = request.getContextPath();
    //     /MyMVC 
%>
<style type="text/css">
    
div#adminhome {
	min-height: 700px;
	padding-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
}

div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	min-height: 600px;
}
    
div#admincontent {
	float: left;
	padding: 0 50px 0 50px;
	width: 80%;
}    

.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 
}

h3 {
 font-weight: bold;
}

/* ======== Calendar ======== */
.my-calendar {
  width: 500px;
  margin: 20px;
  padding: 15px 15px 5px;
  text-align: center;
  font-weight: 800;
  cursor: default;
}
.my-calendar .clicked-date {
  border-radius: 25px;
  margin-top: 36px;
  float: left;
  width: 35%;
  padding: 15px 0 26px;
}
.my-calendar .calendar-box {
  float: right;
  width: 58%;
  padding-left: 30px;
  
  box-sizing: inherit;
}

.clicked-date .cal-day {
  font-size: 24px;
}
.clicked-date .cal-date {
  font-size: 85px;
}

.ctr-box {
  padding: 0 16px;
  margin-bottom: 20px;
  font-size: 20px;
}
.ctr-box .btn-cal {
  position: relative;
  float: left;
  width: 25px;
  height: 25px;
  margin-top: 5px;
  font-size: 16px;
  cursor: pointer;
  border: none;
  background: none;
  
}
.ctr-box .btn-cal:after {
  content: '<';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  line-height: 25px;
  font-weight: bold;
  font-size: 20px;
}
.ctr-box .btn-cal.next {
  float: right;
}
.ctr-box .btn-cal.next:after {
  content: '>';
}

.cal-table {
  width: 100%;
}
.cal-table th {
  width: 14.2857%;
  padding-bottom: 5px;
  font-size: 16px;
  font-weight: 900;
}
.cal-table td {
  padding: 3px 0;
  height: 35px;
  font-size: 15px;
  vertical-align: middle;
}
.cal-table td.day {
  position: relative;
  cursor: pointer;
}
.cal-table td.today {
  background: #ffd255;
  border-radius: 50%;
  color: #fff;
}
.cal-table td.day-active {
  background: #ff8585;
  border-radius: 50%;
  color: #fff;
}
.cal-table td.has-event:after {
  content: '';
  display: block;
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 4px;
  background: #FFC107;
}

</style>
<script type="text/javascript">

var rno = 1;
var tno = 1;

$(function() {
	
	
	
	var bdate = $("td.today").attr('data-fdate');
	//console.log(bdate); 오늘 날짜
	//selectDateBookList(bdate);
	bookListView(rno, tno);
	
	$("td#disabled").each(function() {
		console.log("gmdl");
	});

	$(document).on('click','td.day',function(){
		//console.log($(this).attr('data-fdate'));
		//yy.mm.dd 형태로 출력
		bdate = $(this).attr('data-fdate');
		//selectDateBookList(bdate);
	}); //document ------------
	
	
	/* $("select.timeselect").bind("change", function(){
		tno = $(this).val();
		cnt=0;
		goTimeSelectView(rname, tno);
	});
	
	$("select.timeselect").bind("change", function(){
		tno = $(this).val();
		cnt=0;
		goTimeSelectView(rname, tno);
	}); */
	
	$("select#sel3").bind("change", function(){
		rno = $(this).val();
		bookListView(rno, tno);
	});
	
	$("select#sel4").bind("change", function(){
		tno = $(this).val();
		bookListView(rno, tno);
	});
	
	
	
	
}); // $(function() {}--------------------------
		
		


function bookListView(rno, tno, bdate) {
	// ajax 들어갈 자리
	// 열람실 예약현황의 select 값들이 변할 때마다 ajax를 호출. 선택한 값에 따른 좌석의 정보를 읽어와야한다.
	html = "";
	var count = 0;
	$.ajax({
		url:"<%=ctxPath%>/admin/selectDateBookList.sam",
		type:"get",
		data:{"rno":rno, "tno":tno, "bdate":bdate},
		dataType:"json",
	   	success:function(json) {
	   		$.each(json, function(index, item){
	   			count++; // 한 줄에 5석씩 출력하기 위한 변수
	   			if(count == 1) {
	   				html += "<tr>";
	   			}
	   			
	   			if(item.dscheck == '1') {
	   				html += '<td class="seat" style="word-break:break-all;">'+item.dsname+item.perno+iten.name+'<input  type="hidden"  value="item.dsno"/></td>';
	   			} else {
	   				html += '<td class="seat">'+item.dsname+'<input id="seat" type="hidden"  value='+item.dsno+'></td>'
	   			}
	   			
	   			if(count == 5) {
	   				html += "</tr>";
	   				count = 0;
	   			}
	   			
	   		});
	   		$("tbody#seatinfo").html(html);
	   	}, error: function(request, status, error){
		      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	});
}

function goSearchDeposit() {
	//보증금 조회 메소드
	alert($("select#sel1").val() + ", " + $("select#sel2").val());
}

function goSearchBookList() {
	//보증금 조회 메소드
	alert($("select#sel3").val() + ", " + $("select#sel4").val());
}
</script>

<div id="adminhome">
	
	<div id="adminside" >
		<span style="font-family: 'Noto Sans KR', sans-serif;">관리자 페이지 홈</span>
		<br>
		<br>
		<span>회원/교수진 관리</span>
		<ul>
			<li>학생 관리</li>
			<li>교수진 관리</li>
			<li>활동중지 회원</li>
		</ul>
		<br>
		<span>게시글 관리</span>
		<ul>
			<li>게시글 관리</li>
			<li>댓글 관리</li>
			<li>스팸글 관리</li>
		</ul>
		<br>
		<a href="<%=ctxPath%>/admin/readingRoomBook.sam"><span>열람실 관리</span></a>	
		<br><br>
		<span>수업 관리</span>	
		<ul>
			<li>수업 개설</li>
		</ul> 	
	</div>
	
	<div id="admincontent">
	
		<div class="admsubtitle">
			<span >열람실 관리</span>
		</div>
		
	<div style="float:left;">
	
	  <div class="my-calendar clearfix" align="center">
	  <h3 style="padding-bottom:10px;">달력</h3>
	  
	    <div class="clicked-date">
	      <div class="cal-day"></div>
	      <div class="cal-date"></div>
	    </div>
	    <div class="calendar-box">
	      <div class="ctr-box clearfix">
	        <button type="button" title="prev" class="btn-cal prev">
	        </button>
	        <span class="cal-month"></span>
	        <span class="cal-year"></span>
	        <button type="button" title="next" class="btn-cal next">
	        </button>
	      </div>
	      <table class="cal-table">
	        <thead>
	          <tr>
	            <th>&nbsp;&nbsp;S</th>
	            <th>&nbsp;M</th>
	            <th>&nbsp;T</th>
	            <th>&nbsp;W</th>
	            <th>&nbsp;T</th>
	            <th>&nbsp;F</th>
	            <th>&nbsp;S</th>
	          </tr>
	        </thead>
	        <tbody class="cal-body"></tbody>
	      </table>
	    </div>
	  </div>
	  <!-- // .my-calendar -->
	</div>
	
	<div style="padding-top:32px;" align="center">
		<h3>보증금 현황</h3>
		<form>
			<select form=""class="form-control timeselect" id="sel1" style="width:140px; display:inline-block;">
				<c:forEach var="list" items="${requestScope.rRoomList}">
					<option value="${list.rno}" >${list.rname}</option>
			     </c:forEach>
			</select>
			<select class="form-control timeselect" id="sel2" style="width:140px; display:inline-block;">
				<c:forEach var="tvo" items="${requestScope.timeList}">
					<option value="${tvo.tno}" >${tvo.tname}</option>
			     </c:forEach>
			</select>
			<button type="submit" onclick="goSearchDeposit()">조회</button>
		</form>
		<div style="padding-top:25px;">
			<table class="table table-bordered" style="width:410px;">
				<thead>
					<tr>
						<th>열람실</th>
						<th>이용인원</th>
						<th>좌석반납</th>
						<th>미회수 보증금</th>
					<tr>
				</thead>
				<tbody>
					<tr>
						<td>제1열람실</td>
						<td>13/20</td>
						<td>10/20</td>
						<td>1,500원</td>
					</tr>
					<tr>
						<td>제2열람실</td>
						<td>13/20</td>
						<td>10/20</td>
						<td>1,500원</td>
					</tr>
					<tr>
						<td>제2열람실</td>
						<td>13/20</td>
						<td>13/20</td>
						<td>0원</td>
					</tr>
					<tr>
						<td>누적보증금액</td>
						<td>180,500원</td>
						<td>보증금액합계</td>
						<td>1,500원</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	</div>
	
	<div class="clearfix" align="center">
	<h3>열람실 예약 현황</h3><br><br>
	<form>
		<select form=""class="form-control timeselect" id="sel3" style="width:140px; display:inline-block;">
			<c:forEach var="list" items="${requestScope.rRoomList}">
				<option value="${list.rno}" >${list.rname}</option>
		     </c:forEach>
		</select>
		<select class="form-control timeselect" id="sel4" style="width:140px; display:inline-block;">
			<c:forEach var="tvo" items="${requestScope.timeList}">
				<option value="${tvo.tno}" >${tvo.tname}</option>
		     </c:forEach>
		</select>
	</form>
	<br>
		<table class="table table-bordered" style="width:800px; text-align: center">
				<thead>
					<tr>
						<th>A</th>
						<th>B</th>
						<th>C</th>
						<th>D</th>
						<th>E</th>
					<tr>
				</thead>
				<tbody id="seatinfo">
				</tbody>
			</table>
	</div>
</div>


<script type="text/javascript" src="<%= ctxPath%>/resources/js/calendar.js"></script>
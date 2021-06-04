<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	String ctxPath = request.getContextPath();
    //     /MyMVC 
%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
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

div.chart::after {
	content: ''; 
	dispaly: table; 
	clear: both;
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
<style>
@import 'https://code.highcharts.com/css/highcharts.css';

.highcharts-figure, .highcharts-data-table table {
    min-width: 310px; 
	max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}
.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
    padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
    padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}
.highcharts-data-table tr:hover {
    background: #f1f7ff;
}


/* Link the series colors to axis colors */
.highcharts-color-0 {
	fill: #7cb5ec;
	stroke: #7cb5ec;
}
.highcharts-axis.highcharts-color-0 .highcharts-axis-line {
	stroke: #7cb5ec;
}
.highcharts-axis.highcharts-color-0 text {
	fill: #7cb5ec;
}
.highcharts-color-1 {
	fill: #90ed7d;
	stroke: #90ed7d;
}
.highcharts-axis.highcharts-color-1 .highcharts-axis-line {
	stroke: #90ed7d;
}
.highcharts-axis.highcharts-color-1 text {
	fill: #90ed7d;
}


.highcharts-yaxis .highcharts-axis-line {
	stroke-width: 2px;
}

</style>
<script type="text/javascript">

var rno = 1;
var tno = 1;

$(function() {

	
	
	var bdate = $("td.today").attr('data-fdate');
	//console.log(bdate); 오늘 날짜
	//selectDateBookList(bdate);
	bookListView(bdate);
	viewChart(bdate);
		

	$(document).on('click','td.day',function(){
		//console.log($(this).attr('data-fdate'));
		//yy.mm.dd 형태로 출력
		bdate = $(this).attr('data-fdate');
		console.log("gn");
		bookListView(bdate);
		viewChart(bdate);
		//selectDateBookList(bdate);
	}); //document ------------
	
	
	$("select#sel3").bind("change", function(){
		rno = $(this).val();
		console.log(rno);
		bookListView(bdate);
	});
	
	$("select#sel4").bind("change", function(){
		tno = $(this).val();
		console.log("g"+tno);
		bookListView(bdate);
	});
	
	
	
	
}); // $(function() {}--------------------------
		
		


function bookListView(bdate) {
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
	   			
	   			if(item.bookcheck == '1') {
	   				html += '<td class="seat" style="word-break:break-all;">'+item.dsname+'<br>'+item.perno+'<br>'+item.name+'<input  type="hidden"  value='+item.dsno+'/></td>';
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

function viewChart(bdate) {
	
	var arr1 = new Array();
	var arr2 = new Array();
	var arr3 = new Array();
	
	$.ajax({
		url:"<%=ctxPath%>/admin/viewChart.sam",
		type:"get",
		data:{"bdate":bdate},
		dataType:"json",
	   	success:function(json) {
	   		$.each(json, function(index, item){
	   			arr1.push(Number(item.cnt1));  
	   			arr2.push(Number(item.cnt2));
	   			arr3.push(item.rname);
	   		});
	   		
	   		Highcharts.chart('container', {

	   		    chart: {
	   		        type: 'column',
	   		        styledMode: true
	   		    },

	   		    title: {
	   		        text: '이용 현황'
	   		    },
	   		    
	   		 	xAxis: [{
   		        	categories:[arr3[0], arr3[1], arr3[2]]
	   		    }],

	   		    yAxis: [{
	   		        className: 'highcharts-color-0',
	   		        title: {
	   		            text: '총 예약 수'
	   		        }
	   		    }, {
	   		        className: 'highcharts-color-1',
	   		        opposite: true,
	   		        title: {
	   		            text: '실제 이용 수'
	   		        }
	   		    }],

	   		    plotOptions: {
	   		        column: {
	   		            borderRadius: 5
	   		        }
	   		    },

	   		    series: [{
	   		    	data: [ arr1[0], arr1[1], arr1[2] ], 
	   		    	 name:"총 예약 수"
	   		    }, {
	   		    	data: [ arr2[0], arr2[1], arr2[2] ],
	   		        yAxis: 1, 
	   		        name:"실제 이용 수"
	   		    } 
	   		    ]

	   		});
	   	}, error: function(request, status, error){
		      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}

	});
	
	
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
	
	<div class="chart" style="float:right; padding-top:70px;">
		<figure class="highcharts-figure">
		    <div id="container" style="width:450px; height:300px;"></div>
		</figure>
	</div>
	
</div>

<div class="seattable clearfix" align="center">
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
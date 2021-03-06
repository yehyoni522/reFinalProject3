<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();

	// === #172. (웹채팅관련3) === 
	// === 서버 IP 주소 알아오기(사용중인 IP주소가 유동IP 이라면 IP주소를 알아와야 한다.) ===//
	InetAddress inet = InetAddress.getLocalHost(); 
	String serverIP = inet.getHostAddress();
	
 //System.out.println("serverIP : " + serverIP);
 // serverIP :192.168.35.133
	
	// String serverIP = "211.238.142.72"; 만약에 사용중인 IP주소가 고정IP 이라면 IP주소를 직접입력해주면 된다.
	
	// === 서버 포트번호 알아오기   ===
	int portnumber = request.getServerPort();
 // System.out.println("portnumber : " + portnumber);
 // portnumber : 9090
	
	String serverName = "http://"+serverIP+":"+portnumber; 
 // System.out.println("serverName : " + serverName);
 // serverName : http://211.238.142.72:9090 
%>

<script src="<%= ctxPath%>/resources/js/jquery.min.js"></script>
<script src="<%= ctxPath%>/resources/js/jquery.js-calendar.js"></script>
<link href='<%= ctxPath%>/resources/fullcalendar/main.css' rel='stylesheet' />
<script src='<%= ctxPath%>/resources/fullcalendar/main.js'></script>
<script src='<%= ctxPath%>/resources/fullcalendar/ko.js'></script>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/wordcloud.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<style type="text/css">
	div#calendar{
		float: left; 
		width: 42%;
		height:600px;
		padding:10px;
		margin-left: 100px;
	}
	div#logincheck{
		border-left: 1px solid black; 
		float: left; 
		height:600px;
		width: 42%; 
		padding: 20px 0px 0px 50px;
		
	}
	div#mainBoard{ 
		float: left; 
		margin-left: 120px;
        width: 80%; 
        }
        
     table#MainBoard{
      width: 100%;
     border-bottom: 1px solid #F5F5F5;
      }

      thead{
      	text-align:center;
        border-bottom: 1px solid #bcbcbc;
		font-size: 15pt;
		padding: 80px 0px 5px 0px;
      }
          
     th{
      	text-align:center;
      }
      
	.sidenav{
	  height: 100%;
	  width: 0;
	  position: fixed;
	  z-index: 1;
	  top: 0;
	  left: 0;
	  background-color: #111;
	  overflow-x: hidden;
	  transition: 0.5s;
	  padding-top: 60px;
	  z-index:2;
	}
	
	.sidenav a {
	  padding: 8px 20px 8px 32px;
	  text-decoration: none;
	  font-size: 23px;
	  color: #818181;
	  display: block;
	  transition: 0.3s;
	  text-align: right;
	}
	
	.sidenav a:hover {
	  color: #f1f1f1;
	}
	
	.sidenav .closebtn  {
	  position: absolute;
	  top: 0;
	  right: 25px;
	  font-size: 36px;
	}
	div#loginInfo{
		width:100%;
		margin:50px 0px 100px 0px;
		color: #f1f1f1;
	}
	@media screen and (max-height: 450px) {
	  .sidenav {padding-top: 15px;}
	  .sidenav a {font-size: 18px;}
	}
	
	table#MainBoard > thead > tr > td,
   table#MainBoard > tbody > tr > td {
   		height: 60px;
   		text-align: center;
   		border-bottom:  solid 1px #F5F5F5;
   }
   th#subject{
   		width: 600px;
   }
   th#category{
   		width: 195px;
   }
    tr#tr_MainBoard:hover{
     	background-color:#F5F5F5; 
     	cursor: pointer;
     }
 
	.fc-toolbar-chunk {
	  display: flex; 
	  padding-right: 50px;
	}
	
	.add-button { position: absolute;
	 top: 12px; 
	right: 10px;
	  background: #76828E; 
	  border: 0; 
	  color: white; 
	  height: 35px; 
	  border-radius: 3px; 
	  width: 50px; 
	  }
	.fc-sun {color:#e31b23}
	.fc-col-header-cell fc-day fc-day-sat {color:#007dc3}
	
	img#popup{
	position: fixed;
  	top: 550px;
  	right: 60px;
  	cursor:pointer;
  	 width: 80px; 
  	 height: 80px;
	
	}
	a#name{
	 padding: 8px 20px 8px 32px;
	  text-decoration: none;
	  font-size: 23px;
	  color: #f1f1f1;
	  display: block;
	  transition: 0.3s;
	  text-align: right;
	
	}
	label{
		height: 200px;
	}
	.highcharts-figure{
		margin-top: 50px;
	}
	.switch {
	  position: relative;
	  display: inline-block;
	  width: 50px;
	  height: 20px;
	  margin-left: 1100px;
	}
	a#subject{
	
		color:navy;
	
	}
	.switch input {display:none;}
	
	.slider {
	  position: absolute;
	  cursor: pointer;
	  top: 0;
	  left: 0;
	  right: 0;
	  bottom: 0;
	  background-color: #ccc;
	  -webkit-transition: .4s;
	  transition: .4s;
	}
	
	.slider:before {
	  position: absolute;
	  content: "";
	  height: 17px;
	  width: 16px;
	  left: 4px;
	  bottom: 2px;
	  background-color: white;
	  -webkit-transition: .4s;
	  transition: .4s;
	}
	
	input:checked + .slider {
	  background-color: #29CC97;
	}
	
	input:focus + .slider {
	  box-shadow: 0 0 1px #29CC97;
	}
	
	input:checked + .slider:before {
	  -webkit-transform: translateX(26px);
	  -ms-transform: translateX(26px);
	  transform: translateX(26px);
	}
	
	/* Rounded sliders */
	.slider.round {
	  border-radius: 34px;
	}
	
	.slider.round:before {
	  border-radius: 50%;
	}
	
</style>


<script>
	$(document).ready(function(){
		$("#container").hide();
		goViewBoard(1);
		$(document).on('click', 'tr#tr_MainBoard', function(){
		var seq = $(this).children(".seq").text();
		var categoryno = $(this).children(".categoryno").val();
		
		location.href ="<%= request.getContextPath()%>/board/view.sam?seq="+seq+"&categoryno="+categoryno+"&goBackURL=${requestScope.goBackURL}";
		});// end of click


	 if($("#toggle").is(":checked")==true){
	        	go_boardDisplay()
	        }else{
	        	$("#mainhead").show();
	        	$("#pageBar").show();
	        	$("#MainBoard").show();
	        }  
		 
		 $("#toggle").change(function(){
		        if($("#toggle").is(":checked")){
		        	go_boardDisplay()
		        }else{
		        	$("#mainhead").show();
		        	$("#pageBar").show();
		        	$("#MainBoard").show();
		        	$("#container").hide();

		        }
		    });
		 

	
	});// end of $(document).ready(function(){})--------------
	
	// === #127. Ajax로 불러온 댓글내용을 페이징처리하기 === //
	function goViewBoard(currentShowPageNo) {
		
		$.ajax({
			url:"<%= ctxPath%>/MainBoardList.sam",
			data:{"currentShowPageNo":currentShowPageNo},
			dataType:"json",
			success:function(json){ 
				// []  또는 
				// [{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열네번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열세번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열두번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열한번째 댓글입니다."},{"name":"이순신","regDate":"2021-05-28 10:41:52","content":"열번째 댓글입니다."}] 
				
				var html = "";
				
				if(json.length > 0) {
					$.each(json, function(index, item){
						html += "<tr id='tr_MainBoard'>";
						html += "<td class='seq'>"+item.seq+"</td>";
						html += "<input type='hidden' class='categoryno' value='"+item.categoryno+"'/>"
						
						var categoryno = item.categoryno;
						switch (categoryno) {
						case "1":
							html += "<td>자유게시판</td>";
							break;
						case "2":
							html += "<td>중고게시판</td>";
							break;
						case "3":
							html += "<td>동아리&공모전 모집</td>";
							break;
						}
						
						html += "<td class='comment'>"+ item.subject +"</td>";
						
						if(item.commentCount>0){
							html+="<span>"+ item.commentCount+"</span>"
						}
						else if(item.commentCount == 0){
							html+="<span>"+ item.subject+"</span>"
						}
						if(item.namecheck == 0){
						html += "<td class='comment'>"+ item.name +"</td>";
						}
						if(item.namecheck == 1){
							html += "<td class='comment'>익명</td>";
						}
						
						html += "<td class='comment'>"+ item.good +"</td>";
						html += "</tr>";
					});
				}
				else {
					html += "<tr>";
					html += "<td colspan='5' class='comment'>인기 게시글이 없습니다.</td>";
					html += "</tr>";
				}
				
				$("tbody#boardDisplay").html(html);
				
				// 페이지바 함수 호출
				makeboardPageBar(currentShowPageNo);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function goViewComment(currentShowPageNo) {}----------------------	
	
	
	
	// ==== 댓글내용 페이지바  Ajax로 만들기 ==== // 
	function makeboardPageBar(currentShowPageNo) {
	
		<%-- 원글에 대한 댓글의 totalPage 수를 알아오려고 한다. --%> 
		$.ajax({
			url:"<%= ctxPath%>/getboardTotalPage.sam",
			data:{"sizePerPage":"1"},
			type:"get",
			dataType:"json",
			success:function(json) {
	
				if(json.totalPage > 0) {
					// 댓글이 있는 경우 
					
					var totalPage = json.totalPage;
					
					var pageBarHTML = "<ul style='list-style: none;'>";
					
					var blockSize = 5;
		
					var loop = 1;
				    
				    if(typeof currentShowPageNo == "string") {
				    	currentShowPageNo = Number(currentShowPageNo);
				    }
				    
				    // *** !! 다음은 currentShowPageNo 를 얻어와서 pageNo 를 구하는 공식이다. !! *** //
					var pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;
					// === [맨처음][이전] 만들기 === 
					if(pageNo != 1) {
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewBoard(\"1\")'>[맨처음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewBoard(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					}
				
					while( !(loop > blockSize || pageNo > totalPage) ) {
					
						if(pageNo == currentShowPageNo) {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
						}
						else {
							pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewBoard(\""+pageNo+"\")'>"+pageNo+"</a></li>";
						}
						
						loop++;
						pageNo++;
					}// end of while------------------------
				
				
				// === [다음][마지막] 만들기 === 
					if(pageNo <= totalPage) {
						pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewBoard(\""+pageNo+"\")'>[다음]</a></li>";
						pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewBoard(\""+totalPage+"\")'>[마지막]</a></li>";
					}
					
					pageBarHTML += "</ul>";
				    
					$("div#pageBar").html(pageBarHTML);
				}
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});
		
	}// end of function makeCommentPageBar(currentShowPageNo) {}-----------------
	  function getEvent(){

	}//end of events---
	
	   document.addEventListener('DOMContentLoaded', function() {
		 
			 
		    var calendarEl = document.getElementById('calendar');
		    var calendar = new FullCalendar.Calendar(calendarEl, {
		    	headerToolbar: {
		    		  start: "today",
		    		  center: "prev title next",
		    		  end: "",
		    		},
		    	locale : "ko",
		    	initialView: 'dayGridMonth',
				dayMaxEvents: true,
				events: 
					function(info, successCallback, failureCallback) {
					$.ajax({
						url:"<%= ctxPath%>/scheduleView.sam",
						data:{"perno":"${sessionScope.loginuser.perno}"},
						dataType:"json",
						success:function(json){
						if(json.length > 0) {
							var events=[];
						      $.each(json, function(index, item){
								  events.push({
									  id:item.schno,//스케줄 번호
									  title:item.calsubject,//제목
									  start:item.startDate,//시작일
									  end:item.endDate,//종료일
									  color:item.color,//색상
									  description:item.memo//메모 
									});
			                 });// end of $.each(json1, function(index, item){}) ------------
						      successCallback(events); 
						}	
					      //console.log(events);
					      //$("#calendar").fullCalendar(events);
						  
						},
						error: function(request, status, error){
			                  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		           	}
					})//end of ajax--------
				   

				},//end of event----
		    	eventDidMount: function(info) {
		            tippy(info.el, {
		            	
		                  content: 
		                     info.event.extendedProps.description
		                 
		            });
		        },
		        eventClick: function(info) {
					$.ajax({
						url:"<%= ctxPath%>/scheduleEdit.sam",
						data:{"perno":"${sessionScope.loginuser.perno}",
							"title":info.event.title,
							"schno":info.event.id
							},
						dataType:"json",
						success:  function(data){
							if(data.loginYesNo == "Yes") {							
								window.open("<%= ctxPath%>/scheduleEditPopup.sam?schno="+data.schno+"&perno="+data.perno, "스케줄입력", " left=430px , top=80px,width=400, height=580, toolbar=no, menubar=no, scrollbars=no, resizable=yes" ) 
							}
							
						}

					})//end of ajax--------
		    		
		    	}


			
		    });

		  
		    
		    calendar.render();
	      });
  

function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}

function click_add(){
	
    window.open("<%= ctxPath%>/schedulePopup.sam", "스케줄입력", " left=430px , top=80px,width=430, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );  


}
function go_pop(){
	
	 window.open("<%= serverName%><%=ctxPath%>/chatting/multichat.sam","chat"," left=930px , top=80px,width=400, height=650, toolbar=no, menubar=no, scrollbars=yes, resizable=yes"); 
	
		 <%-- location.href="<%= serverName%><%=ctxPath%>/chatting/multichat.sam";  --%>
	}

function go_boardDisplay(){
	
	$("#mainhead").hide();
	$("#pageBar").hide();
	$("#MainBoard").hide();
	$("#container").show();
	$.ajax({
		url:"<%= ctxPath%>/chart/bestBoard.sam",
		dataType:"json",
		success:function(json){
			
			var resultArr=[];
			for(var i=0;i<json.length; i++){
				var obj;
				
			
				 obj ={	
						 name:json[i].subject,
						 weight:Number(json[i].good),
						 seq:json[i].seq,
						 categoryno:json[i].categoryno
					  };
		
				
				resultArr.push(obj)//배열속에 객체를 넣기
			}//end of for----------------------
			
			Highcharts.chart('container', {
			    chart: {
			        renderTo: 'chart',
			        margin:0,
			        height: 600,
			        width:1150,
			        marginBottom:50,
			        defaultSeriesType: 'areaspline'
			    },    
			  accessibility: {
			    screenReaderSection: {
			      beforeChartFormat: '<h5>{chartTitle}</h5>' +
			        '<div>{chartSubtitle}</div>' +
			        '<div>{chartLongdesc}</div>' +
			        '<div>{viewTableButton}</div>'
			    }
			  },
			  series: [{
			    type: 'wordcloud',
			    data: resultArr,
			    name: '좋아요'
			  }],
			  title: {
				  text: '<span style=" font-family: Noto Sans KR, sans-serif; font-size: 30px; font-weight:bold; margin-bottom: 10px;">커뮤니티</span><br><br>'+
				  		'<span style="font-size: 26px">실시간 인기글 </span>'
			     
			  },       
			  plotOptions: {
				    series: {
				        cursor: 'pointer',
				        point: {
				             events: {
				                 click: function(e) {          
				                    location.href ="<%= request.getContextPath()%>/board/view.sam?seq="+e.point.seq+"&categoryno="+e.point.categoryno;
				                }
				            }
				        }
				    }
				}
			});//end of Highcharts.chart-------
			
		},//end of success:function(json)-----
		
		error: function(request, status, error){
              alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
         }
	})//end of ajax------
}

</script>
   
<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="transform: scaleX(-1);" src="<%= ctxPath%>/resources/images/close.png" style="width:100%;"></a>
  <div id="loginInfo">
  	<c:if test="${empty sessionScope.loginuser}"><a href="<%=ctxPath%>/login.sam">로그인을 해주세요</a></c:if>
  		<c:if test="${sessionScope.loginuser.identity == 0}">
			<a id="name">${sessionScope.loginuser.name}님</a>
	</c:if>
		<c:if test="${sessionScope.loginuser.identity == 1}">
			<a id="name">${sessionScope.loginuser.name}님</a>
	</c:if>
  </div>
  <a href="<%=ctxPath%>/mypage/mypage.sam">마이페이지</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=4">공지사항</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=5">Q&A</a>
  <a href="<%=ctxPath%>/board/list.sam?categoryno=1">커뮤니티</a>
  <a href="<%=ctxPath %>/reading/index.sam">열람실좌석예약</a>
  
</div>

<div style="width: 40px; font-size: 20pt; font-weight: bold; cursor:pointer;">
	<span style="text-align: right; cursor:pointer; margin:0px 0px 50px 30px;" onclick="openNav()"><img src="<%= ctxPath%>/resources/images/addmenu.png" style="width:100%;"></span>
</div>
<div>
	<div id='calendar' style="position : relative;">
	<c:if test="${not empty sessionScope.loginuser}">
		<button class = "add-button" type = "button" onclick="click_add();">추가</button> 
		</c:if>
	</div>
</div>



<div id="logincheck"  >
	<c:if test="${empty sessionScope.loginuser}">
	<span style="display:inline-block; padding:250px 0px 0px 150px;  font-size: 20pt; font-weight: bold;">${sessionScope.loginuser.userid}로그인을 해주세요.</span>
	</c:if>
	
	
	<c:if test="${sessionScope.loginuser.identity == 0}">
		<div style="font-size: 25pt; font-weight: bold; margin-top:30px; margin-bottom: 50px;">
		내 강의
		</div>
		<c:forEach var="subjectvo" items="${requestScope.MainsubjectList}" varStatus="status">
			<div style="margin-top: 20px;">
			<span style="font-size: 20pt; font-weight: bold; margin-left: 50px; margin-bottom: 30px;"><a id="subject" href="<%=ctxPath%>/class/index.sam?subno=${subjectvo.subno}">${subjectvo.subname}</a></span>
			<br>
			<span style="font-size: 15pt; font-weight: bold; margin-left: 200px;">${subjectvo.day}&nbsp;${subjectvo.time}&nbsp; ${subjectvo.name}&nbsp;교수님</span>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${sessionScope.loginuser.identity == 1}">
			<div style="font-size: 25pt; font-weight: bold; margin-top:50px; margin-bottom: 50px;">
				${sessionScope.loginuser.name} 교수님 강의 목록
				</div>
				<c:forEach var="Prosubjectvo" items="${requestScope.MainProsubjectList}" varStatus="status"> 
			<div style="margin-top: 20px;">
			<span style="font-size: 20pt; font-weight: bold; margin-left: 50px; margin-bottom: 30px;"><a id="subject"  href="<%=ctxPath%>/class/index.sam?subno=${Prosubjectvo.subno}">${Prosubjectvo.subname}</a></span>
			<br>
			<span style="font-size: 15pt; font-weight: bold; margin-left: 200px;">${Prosubjectvo.day}&nbsp;${Prosubjectvo.time}&nbsp;</span>
			</div>
		</c:forEach>
	</c:if>
	
</div>

<div id="mainBoard" align="center">
		<label class="switch" >
  <input type="checkbox" id="toggle">
  <span class="slider round"></span>
</label>
		<div id= "mainhead">
		
				<h2 style="font-weight:bold;">커뮤니티</h2>
				<h3>실시간 인기글</h3>
				<br><br>
				</div>
			    <table id = "MainBoard">
			    
			        <thead>
			           <tr id="menu" align="center">	 
			              <th>No.</th>
			              <th id = "category">분류</th>
			              <th id="subject">제목</th>
			              <th>작성자</th>
			              <th>추천수</th>
			           </tr>
			        </thead>
			        <tbody id="boardDisplay" >
			     
			        </tbody>
			    </table>
			    <div id="pageBar" style="margin-top: 30px; padding-right: 30px;"></div>
			
	    <figure class="highcharts-figure">
  <div id="container"></div>
</figure>
</div>

<c:if test="${ not empty sessionScope.loginuser}">
<img src="<%= ctxPath%>/resources/images/chat.png" onclick="go_pop()" id="popup" >
</c:if>

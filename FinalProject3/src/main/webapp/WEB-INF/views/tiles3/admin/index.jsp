<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<%
    String ctxPath = request.getContextPath();
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
div#adimnCalendar{
	margin-left:20px;
	width: 90%;
	min-height: 40%;
}
div#calsub{
	margin:30px 0px 10px 0px;
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 	}

h3 {
 font-weight: bold;
}

.fc-toolbar-chunk {
  display: flex; 
  padding-right: 50px;
}

.add-button { position: absolute;
right: 10px;
  background: #76828E; 
  border: 0; 
  color: white; 
  height: 35px; 
  border-radius: 3px; 
  width: 50px; 
  }
</style>

<script type="text/javascript">
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
		editable : true,
	
		events: 
			function(info, successCallback, failureCallback) {
			$.ajax({
				url:"<%= ctxPath%>/scheduleView.sam",
				data:{"perno":"${sessionScope.loginuser.perno}"},
				dataType:"json",
				success:function(json){
					var events=[];
				
			      $.each(json, function(index, item){
					  events.push({
						  id:item.schno,
						  title:item.calsubject,
						  start:item.startDate,
						  end:item.endDate,
						  color:item.color,
						  description:item.memo
						});
					 
                 });// end of $.each(json1, function(index, item){}) ------------
    					
			      //console.log(events);
			      //$("#calendar").fullCalendar(events);
			      successCallback(events); 
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
        eventDragStop: function(event,jsEvent) {
            alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            if( (300 <= jsEvent.pageX) & (jsEvent.pageX <= 500) & (130 <= jsEvent.pageY) & (jsEvent.pageY <= 170)){
              alert('delete: '+ event.id);
              $('#MyCalendar').fullCalendar('removeEvents', event.id);
            }
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
		
					window.open("<%= ctxPath%>/scheduleEditPopup.sam?schno="+data.schno+"&perno="+data.perno, "스케줄입력", " left=430px , top=80px,width=400, height=580, toolbar=no, menubar=no, scrollbars=no, resizable=yes" ) 
				
					
				}

			})//end of ajax--------
    		
    	}


	
    });

  
    
    calendar.render();
    calendar.setOption('height', 600);
  });

function click_add(){
	 
    window.open("<%= ctxPath%>/schedulePopup.sam", "스케줄입력", " left=430px , top=80px,width=400, height=580, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );  


}
</script>
<div id="adminhome">
	
	<div id="admincontent">
	
		<div class="admsubtitle">
			<span >관리자 페이지</span>
		</div>
		<div id="calsub">
			<span >학사일정 관리</span>
		</div>
		<div id="adimnCalendar">
			<div id='calendar' style="position : relative; height: 600px;">
				<c:if test="${not empty sessionScope.loginuser}">
				<button class = "add-button" type = "button" onclick="click_add();">추가</button> 
				</c:if>
		</div>
	<div>
	
	</div>
</div>
	
	
		

</div>
</div>
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

<%-- === #174. (웹채팅관련5) === --%>
<title>채팅</title>

<style type="text/css">
	#other{
		font-size: 12px;
		
	}
	div#onlineList{
		font-size: 12px;
	}
	span#loginuserName{
		font-size: 12px;
	}
	#message{
		width:300px;
		height:60px;
		overflow: auto;
		border: solid 1px #ccc;
		margin: 0;
	}

	.chat-container{
		vertical-align: bottom;
		border: 1px solid #ccc; 
		margin:5px;
		min-height: 470px;
		overflow: auto;
		overflow-x:hidden;
	}
	
	.chat{
		font-size: 13px;
		color:black;
		margin: 5px;
		min-height: 20px;
		padding: 10px;
		min-width: 50px;
		text-align: left;
        height:auto;
        word-break : break-all;
        background: #F3F3F3;
        width:auto;
        display:inline-block;
        border-radius: 10px 10px 10px 10px;
	}
	
	.chat_1{
		font-size: 13px;
		color:red;
		margin: 5px;
		min-height: 20px;
		padding: 10px;
		min-width: 50px;
		text-align: left;
        height:auto;
        word-break : break-all;
        background: #F3F3F3;
        width:auto;
        display:inline-block;
        border-radius: 10px 10px 10px 10px;
	}
	
	.notice{
		color:#556677;
		font-weight: bold;
		border : none;
		text-align: center;
		display: block;
		font-size: 12px;
	}

	.my-chat{
		text-align: right;
		background: #ECF6FF;
		border-radius: 10px 10px 10px 10px;
		font-size: 13px;
		color:black;
		margin: 5px;
		min-height: 20px;
		padding: 10px;
		min-width: 50px;
		height:auto;
		word-break : break-all;
	}
	
	#bottom-container{
		margin:10px;
	}
	

	
	.chat-box{
		text-align:left;
	}
	.my-chat-box{
		text-align: right;
	}
	#btnSendMessage{
		padding: 12px 10px 12px 10px;
		cursor: pointer;
	}
	#date{
		font-size: 10px;
	}
	
	
</style>
 <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> 
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>  
<script src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	
   $(document).ready(function(){
	   
	   $("div#onlineList").hide();
	   
	   
	  
	   
	   var url = window.location.host; // 웹브라우저의 주소창의 포트까지 가져옴 

	   var pathname = window.location.pathname; // '/'부터 오른쪽에 있는 모든 경로

       var appCtx = pathname.substring(0, pathname.lastIndexOf("/"));  // "전체 문자열".lastIndexOf("검사할 문자");   

       var root = url+appCtx;
  
       var wsUrl = "ws://"+root+"/multichatstart.sam";  
   
       // alert(wsUrl);
       // ws://192.168.219.176:9090/finalproject3/chatting/multichatstart.sam
  
       var websocket = new WebSocket(wsUrl);
    

   	/*   -------------------------------------
   	               이벤트 종류             설명
   	     -------------------------------------
            onopen        WebSocket 연결
   	        onmessage     메시지 수신
   	        onerror       전송 에러 발생
   	        onclose       WebSocket 연결 해제
     */	
       
     let today = new Date();   

     let hours = today.getHours(); // 시
     let minutes = today.getMinutes();  // 분
     let year = today.getFullYear(); // 년도
     let month = today.getMonth() + 1;  // 월
     let date = today.getDate();  // 날짜

     var ampm = '오전';
     if(hours >11){
    	 ampm = '오후';
     }
     

     var dateall = year+'년'+month+'월'+date+'일';
     var time = ampm + hours + ':' + minutes ;
     
     
       var messageObj = {}; // 자바스크립트 객체 생성함.
       
       // === 웹소켓에 최초로 연결이 되었을 경우에 실행되어지는 콜백함수 정의하기 ===  
       websocket.onopen = function(){
    	  //  alert("웹소켓 연결됨!!");
    	  $("div#chatStatus").text("--------------"+dateall+"--------------");
    	  
     	messageObj = {  message : ""
		     	        , type : "first"
		     	        , to : "all" }; // 자바스크립트에서 객체의 데이터값 초기화
		     	        
    	  websocket.send(JSON.stringify(messageObj));   	    
    	  			
       };
       
       
	    // === 메시지 수신시 콜백함수 정의하기 === 
	    websocket.onmessage = function(event) {
			                       // 자음 ㄴ 임
			if(event.data.substr(0,1)=="「" && event.data.substr(event.data.length-1)=="」") { 
				$("div#connectingUserList").html(event.data);
			}
			else {
	          $("div#chatMessage").append(event.data);
	          $("div#chatMessage").append("<br/>");
	          $("div#chatMessage").scrollTop(99999999);
			}
	    };
    
	 // === 웹소캣 연결 해제시 콜백함수 정의하기 === 
        websocket.onclose = function() {
           websocket.send("채팅을 종료합니다.");
        }
	 
     // 메시지 입력후 엔터하기 
        $("input#message").keyup(function (key) {
             if (key.keyCode == 13) {
                $("input#btnSendMessage").click();
             }
          });
     
        
        
     // 메시지 보내기
        $("input#btnSendMessage").click(function() {
            if( $("input#message").val() != "") {
                
               // ==== 자바스크립트에서 replace를 replaceAll 처럼 사용하기 ====
                // 자바스크립트에서 replaceAll 은 없다.
                // 정규식을 이용하여 대상 문자열에서 모든 부분을 수정해 줄 수 있다.
                // 수정할 부분의 앞뒤에 슬래시를 하고 뒤에 gi 를 붙이면 replaceAll 과 같은 결과를 볼 수 있다.
                var messageVal = $("input#message").val();
                messageVal = messageVal.replace(/<script/gi, "&lt;script"); 
                // 스크립트 공격을 막으려고 한 것임.
               
                messageObj = {};
                messageObj.message = messageVal;
                messageObj.type = "all";
                messageObj.to = "all";
                 
                var to = $("input#to").val();
                if ( to != "" ) {
                    messageObj.type = "one";
                    messageObj.to = to;
                }
                 
                websocket.send(JSON.stringify(messageObj));
                // JSON.stringify() 는 값을 그 값을 나타내는 JSON 표기법의 문자열로 변환한다
                
                $("div#chatMessage").append("<div class='my-chat-box'><span id='date'>"+time+"</span><span class='my-chat'>" + messageVal + "</span><div><br/>");
                $("div#chatMessage").scrollTop(99999999);
                 
                $("input#message").val("");
            }
        });
        
        /////////////////////////////////////////////////////////////
        
     // 귀속말대화끊기 버튼은 처음에는 보이지 않도록 한다.
        $("button#btnAllDialog").hide();
        
        // 아래는 귓속말을 위해서 대화를 나누는 상대방의 이름을 클릭하면 상대방IP주소를 귓속말대상IP주소에 입력하도록 하는 것.
        $(document).on("click",".loginuserName",function(){
           /* class loginuserName 은 
              com.spring.chatting.websockethandler.WebsocketEchoHandler 의 
              protected void handleTextMessage(WebSocketSession wsession, TextMessage message) 메소드내에
              133번 라인에 기재해두었음.
           */
           
           var ip = $(this).next().val();
        //   alert(ip);
            $("input#to").val(ip); 
            
            $("span#privateWho").text($(this).text());
            $("button#btnAllDialog").show();
        });
        
        
        // 귀속말대화끊기 버튼을 클릭한 경우는 전체대상으로 채팅하겠다는 말이다. 
        $("button#btnAllDialog").click(function(){
              $("input#to").val("");
              $("span#privateWho").text("");
              $(this).hide();
        });
        
        $("button#onlineListBtn").click(function(){
        	$("div#onlineList").show();
            $(this).hide();
     	 });
        $("button#cBtn").click(function(){
        	$("div#onlineList").hide();
            $("button#onlineListBtn").show();
     	 });
   });
   
</script>


<div id="onlineList">
	현재접속자명단<div id="connectingUserList" style="overFlow: auto; height: 50px; border: 1px solid #ccc;"></div>
	<button id="cBtn">닫기</button>
</div>
<button id="onlineListBtn">접속자명단</button> 

<div id="chatMessage" class="chat-container" style="overFlow: auto; max-height: 500px;">
<div id="chatStatus" class="notice"></div><br/> 
<div class="notice" >
	- 1:1 채팅(귓속말)을 하시려면 상대의 이름을 클릭하세요.
</div><br>	 
</div> 
<input type="hidden" id="to" placeholder="귓속말대상IP주소"/>
<div id="obo" style="font-size: 14px;">
	귓속말대상 : <span id="privateWho" style="font-weight: bold; color: red;"></span>
	&nbsp;&nbsp;<button type="button" id="btnAllDialog">귀속말대화끊기</button>
</div>
<input type="text" id="message" size="50" placeholder="메시지 내용"/>
<input type="button" id="btnSendMessage" value="보내기" />
<br><br>
<div align="center">
	<input type="button" onClick="window.close()" value="채팅방나가기" />
</div>





    

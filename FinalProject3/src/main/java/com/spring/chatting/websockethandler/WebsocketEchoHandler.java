package com.spring.chatting.websockethandler;

import java.util.*;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.finalproject3.joseungjin.model.PersonVO;


// === # 177. (웹채팅관련8) === //
public class WebsocketEchoHandler extends TextWebSocketHandler{
   
   // === 웹소켓서버에 연결한 클라이언트 사용자들을 저장하는 리스트 ===
    private List<WebSocketSession> connectedUsers = new ArrayList<>(); 
   
   // init-method(@PostConstruct)
    public void init() throws Exception {
       
    }
 // === 클라이언트가 웹소켓서버에 연결했을때의 작업 처리하기 ===
    /*
       afterConnectionEstablished(WebSocketSession wsession) 메소드는 
              클라이언트가 웹소켓서버에 연결이 되어지면 자동으로 실행되는 메소드로서
       WebSocket 연결이 열리고 사용이 준비될 때 호출되어지는(실행되어지는) 메소드이다.
    */
    
    @Override
    public void afterConnectionEstablished(WebSocketSession wsession) 
       throws Exception {
       // >>> 파라미터 WebSocketSession wsession 은  웹소켓서버에 접속한 클라이언트 사용자임. <<<
       
       connectedUsers.add(wsession);
       
       // 웹소켓서버에 접속한 클라이언트의 IP Address 얻어오기
        /*
          STS 메뉴의 
          Run --> Run Configuration 
              --> Arguments 탭
              --> VM arguments 속에 맨 뒤에
              --> 한칸 띄우고 -Djava.net.preferIPv4Stack=true 
                          을 추가한다.  
        */
       
       System.out.println("====> 웹채팅확인용 : " + wsession.getId() + "님이 접속했습니다.");
        // ====> 웹채팅확인용 : 0님이 접속했습니다. 
        // ====> 웹채팅확인용 : 1님이 접속했습니다.
        // wsession.getId() 는 자동증가되는 고유한 숫자로 나옴.
        
        System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getHostName());
        // ====> 웹채팅확인용 : 연결 컴퓨터명 : DESKTOP-QHPLVB1
        
        System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getAddress().getHostName());
        // ====> 웹채팅확인용 : 연결 컴퓨터명 : DESKTOP-QHPLVB1
        
        System.out.println("====> 웹채팅확인용 : " + "연결 IP : " + wsession.getRemoteAddress().getAddress().getHostAddress()); 
        // ====> 웹채팅확인용 : 연결 IP : 211.238.142.77
        
        
    ///// ===== 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것 시작 ===== /////
        // Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
       /*
              먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
          websocket:handlers 태그안에 websocket:handshake-interceptors에
            HttpSessionHandshakeInterceptor를 추가하면 WebSocketHandler 클래스를 사용하기 전에 
                        먼저 HttpSession에 저장되어진 값들을 읽어 들여 WebSocketHandler 클래스에서 사용할 수 있도록 처리해준다.  네!! 아항,,,,,머지해서 테스트해볼게요!! ㅠㅠㅠ 넵 ㅠㅠㅠ아니에요 ㅠㅠ!!감사합니다!!
      */
        
        String connectingUserName = "「";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            Map<String, Object> map = webSocketSession.getAttributes();
            /*
               webSocketSession.getAttributes(); 은 
               HttpSession에 setAttribute("키",오브젝트); 되어 저장되어진 값들을 읽어오는 것으로써,
                   리턴값은  "키",오브젝트로 이루어진 Map<String, Object> 으로 받아온다.
            */ 
           PersonVO loginuser = (PersonVO)map.get("loginuser");  
           // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
            connectingUserName += loginuser.getName()+" "; 
        }
        connectingUserName += "」";
        
        System.out.println("확인용 connectingUserName : "+connectingUserName);
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            webSocketSession.sendMessage(new TextMessage(connectingUserName));
        }
        ///// ===== 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것 끝 ===== /////
    }
    
    // === 클라이언트가 웹소켓 서버로 메시지를 보냈을때의 Send 이벤트를 처리하기 ===
    /*
       handleTextMessage(WebSocketSession wsession, TextMessage message) 메소드는 
                 클라이언트가 웹소켓서버로 메시지를 전송했을 때 자동으로 호출되는(실행되는) 메소드이다.
                 첫번째 파라미터  WebSocketSession 은  메시지를 보낸 클라이언트임.
               두번째 파라미터  TextMessage 은  메시지의 내용임. 선생ㅇ님 저 찾았어요 왜 안되는지 잘못된 클래스를 임포트해왔어요 감사합니다,,, 
               네 이름이 똑같아서 헷갈렸나봐요 ㅠㅠㅠ 넹!
     */
    
    @Override
    protected void handleTextMessage(WebSocketSession wsession, TextMessage message) 
       throws Exception {
       
       // >>> 파라미터 WebSocketSession wsession은  웹소켓서버에 접속한 클라이언트임. <<<
       // >>> 파라미터 TextMessage message 은  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임. <<<
       
       // Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
       /*
              먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
          websocket:handlers 태그안에 websocket:handshake-interceptors에
            HttpSessionHandshakeInterceptor를 추가하면 WebSocketHandler 클래스를 사용하기 전에 
                        먼저 HttpSession에 저장되어진 값들을 읽어 들여 WebSocketHandler 클래스에서 사용할 수 있도록 처리해준다. 
      */ 
       Map<String, Object> map = wsession.getAttributes();
       PersonVO loginuser = (PersonVO)map.get("loginuser");  
       // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
       
       System.out.println("====> 웹채팅확인용 : 로그인ID : " + loginuser.getPerno());
       // ====> 웹채팅확인용 : 로그인ID : seoyh
       
        MessageVO messageVO = MessageVO.convertMessage(message.getPayload());
        /* 
                    파라미터 message 는  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임
          message.getPayload() 은  클라이언트 사용자가 보낸 웹소켓 메시지를 String 타입으로 바꾸어주는 것이다.
          /Board/src/main/webapp/WEB-INF/views/tiles1/chatting/multichat.jsp 파일에서 
                   클라이언트가 보내준 메시지는 JSON 형태를 뛴 문자열(String) 이므로 이 문자열을 Gson을 사용하여 MessageVO 형태의 객체로 변환시켜서 가져온다.
        */
        String hostAddress = "";
 
        for (WebSocketSession webSocketSession : connectedUsers) {
            if (messageVO.getType().equals("all")) { 
               // 채팅할 대상이 "전체" 일 경우
               // 메시지를 자기자신을 뺀 나머지 모든 사용자들에게 메시지를 보냄.
                if (!wsession.getId().equals(webSocketSession.getId())) {  
                   // wsession 은 메시지를 보낸 클라이언트임.
                   // webSocketSession 은 웹소켓서버에 연결된 모든 클라이언트중 하나임.
                   // wsession.getId() 와  webSocketSession.getId() 는 자동증가되는 고유한 숫자로 나옴 
                	//wsession.getRemoteAddress().getAddress().getHostAddress()
                    webSocketSession.sendMessage(
                            new TextMessage("<div id='other'><span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +loginuser.getName()+"("+loginuser.getPerno()+")"
                            				+ "</span></div>" + "<div class='chat-box'> <span class='chat'>"+ messageVO.getMessage()+"</span></div>"));  
                }
            } 
            else { // 채팅할 대상이 "전체"가 아닌 특정대상(지금은 귓속말대상 IP address 임) 일 경우 
               hostAddress = webSocketSession.getRemoteAddress().getAddress().getHostAddress(); 
                          // webSocketSession 은 웹소켓서버에 연결한 모든 클라이언트중 하나이며, 그 클라이언트의 IP address를 알아오는 것임.  
                
               if (messageVO.getTo().equals(hostAddress)) { 
                   // messageVO.getTo() 는 클라이언트가 보내온 귓속말대상 IP address 임.
                    webSocketSession.sendMessage(
                            new TextMessage(
                                    "<div class='chat-box'><span class='chat_1' style='color:red; font-weight: bold;'>"
                                    +"[<span style='font-weight:bold; cursor:pointer; ' class='loginuserName'>" +loginuser.getName()+"("+loginuser.getPerno()+")"+ "</span>]" + "▶ " + messageVO.getMessage()
                                    +"</span></div>") 
                    );
                    break; // 지금의 특정대상(지금은 귓속말대상 IP address 임)은 1개이므로 
                           // 특정대상(지금은 귓속말대상 IP address 임)에게만 메시지를 보내고  break;를 한다.
                }
            }
        }
 
        
        System.out.println("====> 웹채팅확인용 : 웹세션ID " + wsession.getId() + "의 메시지 : " + message.getPayload() );
        // ====> 웹채팅확인용 : 웹세션ID 23의 메시지 : {"message":"채팅방에 <span style='color: red;'>입장</span>했습니다","type":"all","to":"all"}
    }
    
    
    
    // === 클라이언트가 웹소켓서버와의 연결을 끊을때 작업 처리하기 ===
    /*
       afterConnectionClosed(WebSocketSession session, CloseStatus status) 메소드는 
                클라이언트가 연결을 끊었을 때 
                즉, WebSocket 연결이 닫혔을 때(채팅페이지가 닫히거나 채팅페이지에서 다른 페이지로 이동되는 경우) 자동으로 호출되어지는(실행되어지는) 메소드이다.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession wsession, CloseStatus status) 
       throws Exception {
        // 파라미터 WebSocketSession wsession 은 연결을 끊은 웹소켓 클라이언트임.
        // 파라미터 CloseStatus 은 웹소켓 클라이언트의 연결 상태.
       
       Map<String, Object> map = wsession.getAttributes();
       PersonVO loginuser = (PersonVO)map.get("loginuser");
       
       connectedUsers.remove(wsession);
       // 웹소켓 서버에 연결되어진 클라이언트 목록에서 연결은 끊은 클라이언트는 삭제시킨다.
       
        for (WebSocketSession webSocketSession : connectedUsers) {
           
           // 퇴장했다라는 메시지를 자기자신을 뺀 나머지 모든 사용자들에게 메시지를 보내도록 한다.
           if (!wsession.getId().equals(webSocketSession.getId())) { 
                webSocketSession.sendMessage(
                   new TextMessage("<div class='notice' >[<span style='font-weight:bold;'>" +loginuser.getName()+"("+loginuser.getPerno()+")"+ "</span>]" + "님이 <span style='color: red;'>퇴장</span>했습니다.</div>")
                ); 
            }
        }
       
        System.out.println("====> 웹채팅확인용 : 웹세션ID " + wsession.getId() + "이 퇴장했습니다.");
        
        
        ///// ===== 접속을 끊을시 접속자명단을 알려주기 위한 것 시작 ===== /////
        String connectingUserName = "「";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            Map<String, Object> map2 = webSocketSession.getAttributes();
            PersonVO loginuser2 = (PersonVO)map2.get("loginuser");  
           // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
   
            connectingUserName += loginuser2.getName()+" "; 
        }
        
        connectingUserName += "」";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            webSocketSession.sendMessage(new TextMessage(connectingUserName));
        }
        ///// ===== 접속해제시 접속자명단을 알려주기 위한 것 끝 ===== /////
    }
    
}
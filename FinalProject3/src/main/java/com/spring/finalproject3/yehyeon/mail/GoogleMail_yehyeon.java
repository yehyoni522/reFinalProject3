package com.spring.finalproject3.yehyeon.mail;

import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Component;
@Component
public class GoogleMail_yehyeon {

	public void sendmail(String recipient, String rname, String tname, String dsname) throws Exception {
		
		// 1. 정보를 담기 위한 객체
    	Properties prop = new Properties();
    	
    	// 2. SMTP(Simple Mail Transfer Protocoal) 서버의 계정 설정
   	    //    Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정 
    	prop.put("mail.smtp.user", "ssangyonguniv@gmail.com");
    	
    	// 3. SMTP 서버 정보 설정
    	//    Google Gmail 인 경우  smtp.gmail.com
    	prop.put("mail.smtp.host", "smtp.gmail.com");
         	
    	
    	prop.put("mail.smtp.port", "465");
    	prop.put("mail.smtp.starttls.enable", "true");
    	prop.put("mail.smtp.auth", "true");
    	prop.put("mail.smtp.debug", "true");
    	prop.put("mail.smtp.socketFactory.port", "465");
    	prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	prop.put("mail.smtp.socketFactory.fallback", "false");
    	
    	prop.put("mail.smtp.ssl.enable", "true");
    	prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    	
    	Authenticator smtpAuth = new MySMTPAuthenticator_yehyeon();
    	Session ses = Session.getInstance(prop, smtpAuth);
    	
    	// 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
    	ses.setDebug(true);
    	
    	// 메일의 내용을 담기 위한 객체생성
    	MimeMessage msg = new MimeMessage(ses);
    	
    	// 제목 설정
    	String subject = "[쌍용대학교LMS] 쌍용대학교 LMS 에서 예약한 좌석의 정보를 알려드립니다.";
    	msg.setSubject(subject);
    	
    	// 보내는 사람의 메일주소
    	String sender = "ssangyonguniv@gmail.com";
    	Address fromAddr = new InternetAddress(sender);
    	msg.setFrom(fromAddr);
    			
    	// 받는 사람의 메일주소
    	Address toAddr = new InternetAddress(recipient);
    	msg.addRecipient(Message.RecipientType.TO, toAddr);
    	    	
    	// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
    	msg.setContent("예약된 좌석 : <span style='font-size:10pt; color:gray;'>열람실 : "+rname+", 시간 : "+tname+ ", 좌석 : "+ dsname +" 입니다.</span><br>"
    						   + "<span style='font-size:8pt; color:#F15F5F;'>좌석 착석 후 마이페이지에서 이용현황을 변경해주시기 바랍니다.</span>", "text/html;charset=UTF-8");
    	
    	// 메일 발송하기
    	Transport.send(msg);
    	
	}// end of public void sendmail(String recipient, String certificationCode) throws Exception---------------------------- 
	
}

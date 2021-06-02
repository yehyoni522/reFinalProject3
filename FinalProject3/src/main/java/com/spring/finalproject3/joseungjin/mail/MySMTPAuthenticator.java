package com.spring.finalproject3.joseungjin.mail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
public class MySMTPAuthenticator extends Authenticator {

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		
		// Gmail 의 경우 @gmail.com 을 제외한 아이디만 입력한다.
		return new PasswordAuthentication("jodevelop7","wh40255947!");
	}
	
}

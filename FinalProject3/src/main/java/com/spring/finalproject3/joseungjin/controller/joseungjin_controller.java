package com.spring.finalproject3.joseungjin.controller;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.Sha256;
import com.spring.finalproject3.joseungjin.mail.GoogleMail;
import com.spring.finalproject3.joseungjin.model.InterPersonDAO;
import com.spring.finalproject3.joseungjin.model.PersonDAO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.service.InterMemberService;

@Controller
public class joseungjin_controller {
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterMemberService service;
	
	//메인페이지 요청
	@RequestMapping(value="/index.sam")
	public ModelAndView main(ModelAndView mav) {
		
		mav.setViewName("main/index.tiles1");
		// /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
		
		return mav;
	}
	
	//로그인 페이지 요청
	@RequestMapping(value="/login.sam",method= {RequestMethod.GET})
	public ModelAndView login(ModelAndView mav) {
		
		mav.setViewName("login/loginform.tiles2");
		// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
		
		return mav;
	}
	
// === #41. 로그인 처리하기 === // 
	@RequestMapping(value="/loginEnd.sam", method= {RequestMethod.POST})
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pwd", Sha256.encrypt(pwd));
		
		PersonVO loginuser = service.getLoginStudent(paraMap);
		
		if(loginuser == null) { // 로그인 실패시
			
			String message = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
			//  /WEB-INF/views/msg.jsp 파일을 생성한다.
		}
		
		else { // 아이디와 암호가 존재하는 경우
			
			HttpSession session = request.getSession();
			// 메모리에 생성되어져 있는 session을 불러오는 것이다.
			
			session.setAttribute("loginuser", loginuser);
			// session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
			mav.setViewName("redirect:/index.sam");
					
		}
	
		return mav;
	}
	
	// === #50. 로그아웃 처리하기 === // 
	@RequestMapping(value="/logout.sam")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		String message = "로그아웃 되었습니다.";
		String loc = request.getContextPath()+"/index.sam";
		
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	//=== 아이디 찾기 페이지 요청===//
	@RequestMapping(value="/idFind.sam")
	public ModelAndView idFind(ModelAndView mav, HttpServletRequest request) {
		
		mav.setViewName("member/idFind");
		// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
		
		return mav;
	}
	//=== 아이디 찾기 실행===//
	@RequestMapping(value="/idFindEnd.sam",method=RequestMethod.POST)
	public ModelAndView idFindEnd(ModelAndView mav, HttpServletRequest request) {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("name", name);
		paraMap.put("email", email);
		
		PersonVO idFind = service.idFind(paraMap);
		
		if(idFind == null) { //존재하지 않는 경우
			mav.addObject("check", 1);
			mav.setViewName("member/idFind");
	
		}
		
		else { // 존재하는 경우
			
			mav.addObject("check", 0);
			mav.addObject("perno",idFind.getPerno());
			// session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
			mav.setViewName("member/idFind");
					
		}
	
		return mav;
	}
	//=== 비밀번호 찾기 페이지 요청===//
		@RequestMapping(value="/pwdFind.sam")
		public ModelAndView pwdFind(ModelAndView mav, HttpServletRequest request) {
			
			mav.setViewName("member/pwdFind");
			// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
			
			return mav;
		}
		//=== 비밀번호 찾기 실행===//
		@RequestMapping(value="/pwdFindgo.sam",method=RequestMethod.POST)
		public ModelAndView pwdFindgo(ModelAndView mav, HttpServletRequest request,PersonVO pvo) {
			
			
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("email", email);
			PersonVO pwdFind = service.pwdFind(paraMap);
			
			boolean isUserExist =  service.isUserExist(paraMap);
			
			boolean sendMailSuccess = true; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
				if(isUserExist) {

					// 회원으로 존재하는 경우
					Random rnd = new Random();
					String certificationCode = "";
					char randchar = ' ';
					for(int i=0; i<5; i++) {
						randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
						certificationCode += randchar;
					}// end of for--------------------------------
					
					int randnum = 0;
					for(int i=0; i<7; i++) {
						randnum = rnd.nextInt(9 - 0 + 1) + 0;
						certificationCode += randnum;
					}// end of for--------------------------------
					
				
					GoogleMail mail = new GoogleMail();
					
					try {
						mail.sendmail(email, certificationCode);
						//세션불러오기
						HttpSession session =request.getSession();
						session.setAttribute("certificationCode", certificationCode);
						// 발급한 인증코드를 세션에 저장시킴.
					
					} catch (Exception e) {
						// 메일 전송이 실패한 경우
						e.printStackTrace();
						sendMailSuccess = false; // 메일 전송 실패했음을 기록함.
					}
					
					
					
					mav.addObject("check", 0);
				}
		
			mav.addObject("userid",pwdFind.getPerno());
			mav.addObject("isUserExist", isUserExist);
			mav.addObject("sendMailSuccess",sendMailSuccess);
			
			mav.setViewName("member/pwdFind");
			return mav;
		}
		//=== 비밀번호 찾기 인증===//
		@RequestMapping(value="/verifyCertification.sam",method=RequestMethod.POST)
		public ModelAndView verifyCertification(ModelAndView mav, HttpServletRequest request) {
			
			String userid = request.getParameter("userid");
			String userCertificationCode = request.getParameter("userCertificationCode");
			
			HttpSession session = request.getSession(); // 세션불러오기 
			String certificationCode = (String) session.getAttribute("certificationCode");  // 세션에 저장된 인증코드 가져오기 
			
			String message = "";
			String loc = "";
			
			if( certificationCode.equals(userCertificationCode) ) {
				message = "인증성공 되었습니다.";
				mav.addObject("in",1);
				mav.addObject("userid",userid);
				mav.setViewName("msg");
				mav.setViewName("member/pwdFind");
				
			}
			else {
				message = "발급된 인증코드가 아닙니다. 인증코드를 다시 발급받으세요!!";
				loc = request.getContextPath()+"/pwdFind.sam";
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				mav.setViewName("msg");
			}
			
			
			
			// !!!! 중요 !!!! //
			// !!!! 세션에 저장된 인증코드 삭제하기 !!!! // 
			session.removeAttribute("certificationCode");
			return mav;
			
		}
		@RequestMapping(value="/pwdUpdateEnd.sam",method=RequestMethod.POST)
		public ModelAndView pwdUpdateEnd(ModelAndView mav, HttpServletRequest request) {
		
			String userid = request.getParameter("userid");
				String pwd = request.getParameter("pwd");
				
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("userid",userid);
				paraMap.put("pwd", Sha256.encrypt(pwd));
				//System.out.println(userid);
				int n = service.pwdUpdate(paraMap);
		
				if(n == 0) {
					mav.addObject("message", "암호 변경 오류 발생!");
					mav.setViewName("msg");
					
				}
				else {
					mav.addObject("message", "암호변경 성공!!");
					mav.addObject("n", 1);
					
				}
			
			mav.addObject("userid",userid);
			mav.setViewName("msg");
			
			return mav;
		}	
		//=== 회원등록 페이지 요청===//
		@RequestMapping(value="/personRegister.sam")
		public ModelAndView personRegister(ModelAndView mav, HttpServletRequest request) {
			
			mav.setViewName("member/personRegister");
			
			return mav;
		}
		
		//=== 회원등록 실행===//
			@RequestMapping(value="/personRegisterGo.sam",method=RequestMethod.POST)
			public ModelAndView personRegisterGo(ModelAndView mav, HttpServletRequest request,PersonVO pvo) {
				
				
				String userid = request.getParameter("userid");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				
				// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("userid", userid);
				paraMap.put("name", name);
				paraMap.put("email", email);
				
				PersonVO pwdFind = service.pwdFind(paraMap);
				
				boolean isUserExist2 =  service.isUserExist2(paraMap);
				boolean sendMailSuccess = true; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
				
				if(isUserExist2) {
					
				
					// 회원으로 존재하는 경우
					// 인증키를 랜덤하게 생성하도록 한다.
					Random rnd = new Random();
					
					String certificationCode = "";
					// 인증키는 영문소문자 5글자 + 숫자 7글자 로 만들겠습니다.
					// 예: certificationCode ==> dngrn4745003
					
					char randchar = ' ';
					for(int i=0; i<5; i++) {
						/*
						    min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
						    int rndnum = rnd.nextInt(max - min + 1) + min;
						       영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.  	
						*/
						
						randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
						certificationCode += randchar;
					}// end of for--------------------------------
					
					int randnum = 0;
					for(int i=0; i<7; i++) {
						randnum = rnd.nextInt(9 - 0 + 1) + 0;
						certificationCode += randnum;
					}// end of for--------------------------------
					
				//System.out.println("~~~~ 확인용 certificationCode => " + certificationCode);
					// ~~~~ 확인용 certificationCode => pnfkj4609646
					
					// 랜덤하게 생성한 인증코드(certificationCode)를 비밀번호 찾기를 하고자 하는 사용자의 email로 전송시킨다. 
					GoogleMail mail = new GoogleMail();
					
					try {
						mail.sendmail(email, certificationCode);
						//세션불러오기
						HttpSession session =request.getSession();
						session.setAttribute("certificationCode", certificationCode);
						// 발급한 인증코드를 세션에 저장시킴.
					
					} catch (Exception e) {
						// 메일 전송이 실패한 경우
						e.printStackTrace();
						sendMailSuccess = false; // 메일 전송 실패했음을 기록함.
					}
					
					
						
						mav.addObject("check", 0);
					}
					mav.addObject("userid",pwdFind.getPerno());
					mav.addObject("isUserExist2", isUserExist2);
					mav.addObject("sendMailSuccess",sendMailSuccess);
					
					mav.setViewName("member/personRegister");
					return mav;
				}
		
		
	
	
}

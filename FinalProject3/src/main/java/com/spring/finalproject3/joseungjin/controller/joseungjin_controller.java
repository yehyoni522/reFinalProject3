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
		paraMap.put("pwd", pwd);
		
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
	
	
	
}

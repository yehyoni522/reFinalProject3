package com.spring.finalproject3.hyeminJang.comtroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.hyeminJang.service.InterMessageService;

@Controller
public class MessageController {
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterMessageService service;
	
	// 받은 쪽지함 (리스트) 보기 
	@RequestMapping(value="/inbox.sam")
	public ModelAndView inbox(ModelAndView mav, HttpServletRequest request) {
		/*
		List<InboxVO> boardList = null;
		
		HttpSession session = request.getSession();
		personVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		// == 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기 == //
		inboxList = service.inboxListNoSearch(perno);
		
				
		mav.addObject("inboxList", inboxList);	
		*/	
		mav.setViewName("message/inbox.tiles2");
		// /WEB-INF/views/tiles2/message/inbox.jsp 파일을 생성한다.
		
		return mav;
	}
	
	// 받은 쪽지 보기
	@RequestMapping(value="/inView.sam")
	public ModelAndView inView(ModelAndView mav) {
		
		mav.setViewName("message/inView.tiles2");
		// /WEB-INF/views/tiles2/message/write.jsp 파일을 생성한다.
		
		return mav;
	}
	
	// 쪽지 쓰기
	@RequestMapping(value="/write.sam")
	public ModelAndView write(ModelAndView mav) {
		
		mav.setViewName("message/write.tiles2");
		// /WEB-INF/views/tiles2/message/write.jsp 파일을 생성한다.
		
		return mav;
	}
	

	  // 아이디 찾아보기
	  @RequestMapping(value="/userFind.sam") 
	  public String userFind() {
	 
		  return "tiles2/message/userFind";
		  
	 }
	  
	  // inbox 쪽지 한개 삭제하기 
	  @RequestMapping(value="/inDel.sam") 
	  public String inDel() {
	 
		  return "tiles2/message/userFind";
		  
	 }
	  

	 
}

package com.spring.finalproject3.joseungjin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class joseungjin_controller {
	
	
		//메인페이지 요청
		@RequestMapping(value="/index.sam")
		public ModelAndView main(ModelAndView mav) {
			
			mav.setViewName("main/index.tiles1");
			// /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
			
			return mav;
		}
}

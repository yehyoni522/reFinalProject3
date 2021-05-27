package com.spring.finalproject3.seongkyung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminMemberController {

	@RequestMapping(value="/adminstudent.sam")
	public ModelAndView admin_student(ModelAndView mav) {
		
		mav.setViewName("adminmember/adminstudent.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/adminstudentinfo.sam")
	public ModelAndView admin_studentinfo(ModelAndView mav) {
		
		mav.setViewName("adminmember/adminstudentinfo.tiles2");
		
		return mav;
	}
	
}

package com.spring.finalproject3.seongkyung.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.service.InterAdminMemberService;

@Controller
public class AdminMemberController {
	
	@Autowired
	private InterAdminMemberService service;

	@RequestMapping(value="/admin/student.sam")
	public ModelAndView admin_student(ModelAndView mav, HttpServletRequest request) {
		
		List<PersonVO> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		/*
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		int totalCount = 0; 		// 총 학생 수
		int sizePerPage = 1; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    */
		
		// personList = service.adminsudentList();
		
		mav.setViewName("adminmember/adminstudent.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/studentinfo.sam")
	public ModelAndView admin_studentinfo(ModelAndView mav) {
		
		mav.setViewName("adminmember/adminstudentinfo.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/professor.sam")
	public ModelAndView admin_professor(ModelAndView mav) {
		
		mav.setViewName("adminmember/adminprofessor.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/blacklist.sam")
	public ModelAndView admin_blacklist(ModelAndView mav) {
		
		mav.setViewName("adminmember/adminblacklist.tiles2");
		
		return mav;
	}
	
}

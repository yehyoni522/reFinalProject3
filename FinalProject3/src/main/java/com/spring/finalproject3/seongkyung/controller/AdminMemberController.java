package com.spring.finalproject3.seongkyung.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.MyUtil;
import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.service.InteradminMemberService;

@Component
@Controller
public class AdminMemberController {
	
	@Autowired
	private InteradminMemberService service;
	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@RequestMapping(value="/admin/student.sam")
	public ModelAndView admin_student(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		//System.out.println(searchType);
		//System.out.println(searchWord);
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 2; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
	    if(str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}
		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}

		startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
		
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    personList = service.getAdminStudent(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
		
		mav.setViewName("adminmember/adminstudent.tiles2");
		
		return mav;
	}
	
	// 관리자 학생 상세정보페이지
	@RequestMapping(value="/admin/studentinfo.sam")
	public ModelAndView admin_studentinfo(ModelAndView mav, HttpServletRequest request) {
		
		String perno = request.getParameter("perno");
		
		System.out.println("1 :" +perno);
		
		String gobackURL = request.getParameter("gobackURL");
		
		mav.addObject("gobackURL", gobackURL);	
		
		try {
			
			Integer.parseInt(perno);
			
			List<PersonVO> personvo = null;
			
			personvo = service.getStudentView(perno);
			
			System.out.println("2 :" +personvo);
			
			mav.addObject("personList", personvo);
			
		} catch(NumberFormatException e) {
			
		}	
		
		mav.setViewName("adminmember/adminstudentinfo.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/professor.sam")
	public ModelAndView admin_professor(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 2; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getProfessorTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
	    if(str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}
		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}

		startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
		
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    personList = service.getAdminProfessor(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
			
		mav.setViewName("adminmember/adminprofessor.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/blacklist.sam")
	public ModelAndView admin_blacklist(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 2; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
	    if(str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}
		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}

		startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
		
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    personList = service.getAdminStudent(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
		
		mav.setViewName("adminmember/adminblacklist.tiles2");
		
		return mav;
		
	}
	
	
	@RequestMapping(value="/lesson/attendance.sam")
	public ModelAndView lesson_attendance(ModelAndView mav, HttpServletRequest request) {
		
		
		mav.setViewName("lessonadmin/lessonattendance.tiles2");
		
		return mav;
	}
	
	
	@RequestMapping(value="/lesson/quiz.sam")
	public ModelAndView lesson_quiz(ModelAndView mav, HttpServletRequest request) {
		
		
		mav.setViewName("lessonadmin/lessonquiz.tiles2");
		
		return mav;
	}
	
	
	@RequestMapping(value="/lesson/quizadd.sam")
	public ModelAndView lesson_quizadd(ModelAndView mav, HttpServletRequest request) {
		
		request.getParameter("");
		
		
		mav.setViewName("lessonadmin/lessonquizadd.tiles2");
		
		return mav;
	}
	
	@RequestMapping(value="/lesson/quizaddEnd.sam", method= {RequestMethod.POST})
	public ModelAndView lesson_quizaddEnd(ModelAndView mav, HttpServletRequest request) {
		
		QuizVO quizvo = null;
		QuestionVO questionvo = null;
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		int cnt = Integer.parseInt(request.getParameter("cnt"));	// 문제 추가를 클린한 횟수	
		cnt = cnt + 1;
		
		String quizname = request.getParameter("quizname");
		
		// System.out.println("cnt 확인중 : " + cnt);
		// System.out.println("~~~~~~ quizname 확인중 : " + quizname);
		
		// 시험과목 만들기
		quizvo = service.addquiz(quizname); // 쪽지시험 테이블에 insert 하고 select 해서 일련번호 가져오기
		
		if(quizvo != null) {
		
			////////////////////////////////////////////////////////////////
			
			// System.out.println("~~~~~ 확인용 : quizvo.getQuizname()" + quizvo.getQuizname());
			
			// System.out.println("n : " + n);
			// System.out.println("~~~~~ 확인용 :cnt : " + cnt);
			
			// System.out.println("~~~~~ 확인용 :quizno : " + quizvo.getQuizno());
			String quizno = String.valueOf(quizvo.getQuizno());
			
			////////////////////////////////////////////////////////////////
		
			// 생성된 시험과목에 딸린 문제 만들기 
			for(int i=0; i< cnt; i++) {
			  
			 String qzno = request.getParameter("qzno"+i); 
			 String qzcontent =request.getParameter("qzcontent"+i);
			 String answerfirst = request.getParameter("answerfirst"+i); 
			 String answersecond = request.getParameter("answersecond"+i); 
			 String answerthird = request.getParameter("answerthird"+i); 
			 String answerfourth = request.getParameter("answerfourth"+i); 
			 
			 String quizanswer = request.getParameter("quizanswer"+i); 
			 
			 //System.out.println("answerfirst : " + answerfirst + " answersecond : " + answersecond);
			 //System.out.println("answerthird : " + answerthird + " answerfourth : " + answerfourth);
			 //System.out.println("qzno : " + qzno + " qzcontent : " + qzcontent);
			 //System.out.println("quizanswer : " + quizanswer);		 
			 		 
			 paraMap.put("fk_quizno", quizno);
			 paraMap.put("qzno", qzno);
			 paraMap.put("qzcontent", qzcontent);
			 paraMap.put("answerfirst", answerfirst);
			 paraMap.put("answersecond", answersecond);
			 paraMap.put("answerthird", answerthird);
			 paraMap.put("answerfourth", answerfourth);
			 
			 paraMap.put("quizanswer", quizanswer);
			 	 
			 questionvo = service.addquestion(paraMap); // 쪽지시험_문제  insert
			 
			 paraMap.put("fk_questionno", String.valueOf(questionvo.getQuestionno()));
			 
			 if(questionvo != null) {
					 
				 int m = service.addquizans(paraMap); // 쪽지시험_정답 필드 생성
				 
			 }	 
			 
			 } // end of for
			 
			 mav.setViewName("redirect:/lesson/quiz.sam");

		}	 
			 

		
		return mav;
	}
	
	
	
	
}

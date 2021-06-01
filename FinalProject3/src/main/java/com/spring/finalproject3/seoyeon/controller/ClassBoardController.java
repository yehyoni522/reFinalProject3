package com.spring.finalproject3.seoyeon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.seoyeon.model.assignmentBoardVO;
import com.spring.finalproject3.seoyeon.service.InterClassBoardService;

@Component
@Controller
public class ClassBoardController {

	@Autowired 
	private InterClassBoardService service;
	
	@RequestMapping(value="/class/assignmentBoard.sam")
	public ModelAndView class_assignmentBoardList(ModelAndView mav, HttpServletRequest request) {
	
		List<assignmentBoardVO> assignmentList = null;
		
		// *** 근데 과목번호 어케 알아옴?? <==페이지 폼 내에서 전달...?
		String subno = request.getParameter("subno");
		
		// 어떤 과목인지 과목이름 알아오기 => 과목이름 맨위에 띄워주기 위해 / 리스트 where절 사용
		String subject = service.getSubjectname(subno);
		request.setAttribute("subject", subject);
		
		// 해당 수업을 듣는 학생의 총 인원수 알아오기(교수 총 수강인원 나타낼때 필요)
		String totalPerson = service.getTotalPerson(subno);
		
		// **필요없음** 로그인한 사람 알아오기 ==> 학생이면 (제출,점수) 컬럼 더 보여주기 위해, 교수면 글쓰기 버튼+(제출개수/총수강인원) 컬럼보여주기
	//	HttpSession session = request.getSession();
	//	PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
	//	String identity = service.getIdentity(loginuser);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		//페이징처리
		int totalCount = 0;         // 총 게시물 건수
		int sizePerPage = 5;        // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	    int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
	    String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	       
	    int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
		
	    // 총 게시물 건수(totalCount)
	    totalCount = service.getTotalAssign(paraMap); 
	    totalPage = (int) Math.ceil( (double)totalCount/sizePerPage );
	    
	       
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
		
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		assignmentList = service.assignListSearchWithPaging(paraMap);
	    
		
		// === 페이지바 만들기 === //
		int blockSize = 3;
		int loop = 1;   
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
		String pageBar = "<ul style='list-style: none;'>";
		String url = "";
		
		// === [맨처음][이전] 만들기 === 
		if(pageNo != 1) {
		pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?currentShowPageNo=1'>[맨처음]</a></li>";
				
		pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
		
			if(pageNo == currentShowPageNo) {
			pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
			pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}
			
			loop++;
			pageNo++;
		}// end of while------------------------
		
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);	    	
    	mav.addObject("totalCount",totalCount);
    	mav.addObject("assignmentList", assignmentList);
		
		
		mav.setViewName("class/assignmentBoard.tiles2");	
		
		return mav;
	}
	
	
	// 과제 게시판 글쓰기 폼
		@RequestMapping(value="/class/assignmentAdd.sam")
		public ModelAndView assignmentAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
					
			mav.setViewName("class/assignmentAdd.tiles2");		
			return mav;
		}
	
	// === 과제 게시판 글쓰기 완료 요청 === // 
		@RequestMapping(value="/class/assignmentAddEnd.sam", method= {RequestMethod.POST})
		/*
 			form 태그의 name 명과  BoardVO 의 필드명이 같다라면 
 			request.getParameter("form 태그의 name명"); 을 사용하지 않더라도
			자동적으로 BoardVO boardvo 에 set 되어진다.
		 */
	   
	   public ModelAndView assignmentAddEnd(Map<String,String> paraMap, ModelAndView mav, assignmentBoardVO assgnVO) {            
	      int n = service.assignmentAdd(assgnVO); // <== 파일첨부가 없는 글쓰기 
	  
		  if(n==1) {
		     mav.setViewName("redirect:/class/assignmentBoard.sam");
		  //   list.action 페이지로 redirect(페이지이동)해라는 말이다.
		  }
		 
		      
		      return mav;
		}
		
	//	과제 상세 페이지 보기
		@RequestMapping(value="/class/assignmentView.sam", method= {RequestMethod.POST})
		public ModelAndView assignmentView(HttpServletRequest request, ModelAndView mav) {
			
		// 	조회하고자 하는 과제번호 받아오기
			String assgnno = request.getParameter("assgnno");
			String gobackURL = request.getParameter("gobackURL");
			mav.addObject("gobackURL", gobackURL);
			
			try {
		         Integer.parseInt(assgnno);
		         
		         int login_userid;
		         
		         HttpSession session = request.getSession();
		         PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
		         
		         
		         if(loginuser != null) {
		            login_userid = loginuser.getPerno();
		            // login_userid 는 로그인 되어진 사용자의 userid 이다.
		         }
		         
		         // === #68. !!! 중요 !!! 
		           //     글1개를 보여주는 페이지 요청은 select 와 함께 
		         //     DML문(지금은 글조회수 증가인 update문)이 포함되어져 있다.
		         //     이럴경우 웹브라우저에서 페이지 새로고침(F5)을 했을때 DML문이 실행되어
		         //     매번 글조회수 증가가 발생한다.
		         //     그래서 우리는 웹브라우저에서 페이지 새로고침(F5)을 했을때는
		         //     단순히 select만 해주고 DML문(지금은 글조회수 증가인 update문)은 
		         //     실행하지 않도록 해주어야 한다. !!! === //
		         
		         assignmentBoardVO assignmentVO = null;
		         
	     //   	 assignmentVO = service.assignmentView(assgnno);
	             // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
		       
		         
		         mav.addObject("assignmentVO", assignmentVO);
		         
		      } catch(NumberFormatException e) {
		         
		      }
		      
		      mav.setViewName("board/view.tiles1");
			
			return mav;
		}
		
		
		
		
		
		
		
		
}	

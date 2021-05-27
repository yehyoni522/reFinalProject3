package com.spring.finalproject3.hyeminJang.comtroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.service.InterMessageService;

@Controller
public class MessageController {
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterMessageService service;
	
	// 받은 쪽지함 (리스트) 보기 
	@RequestMapping(value="/message/inbox.sam")
	public ModelAndView inbox(ModelAndView mav, HttpServletRequest request) {
		
		List<InboxVO> inboxList = null;
		
		/*
		HttpSession session = request.getSession();
		personVO loginuser = (MemberVO) session.getAttribute("loginuser");*/
		
		// == 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 == //

		String searchType = request.getParameter("searchType"); 
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType)) ) {
		searchType = "";
		}
		
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {
		searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("userid", String.valueOf(1004));
		
		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
		int totalCount = 0;         // 총 게시물 건수
		//   int sizePerPage = 10;       // 한 페이지당 보여줄 게시물 건수
		int sizePerPage = 10;        // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)  
		
		int startRno = 0;           // 시작 행번호
		int endRno = 0;             // 끝 행번호 
		

		// 총 게시물 건수(totalCount)
		totalCount = service.getTotalCount(paraMap);
		
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

		inboxList = service.inboxListSearchWithPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)

		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}


		// === #121. 페이지바 만들기 === //
		//   int blockSize = 10;
		int blockSize = 10;
		int loop = 1;
		

		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
		String pageBar = "<ul style='list-style: none;'>";
		String url = "list.action";

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
			
		}// end of while------------------------


		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}

		pageBar += "</ul>";

		mav.addObject("pageBar",pageBar);
		
		mav.addObject("inboxList", inboxList);	
		mav.setViewName("message/inbox.tiles2");
		// /WEB-INF/views/tiles2/message/inbox.jsp 파일을 생성한다.
		
		return mav;
	}
	
	// 받은 쪽지 보기
	@RequestMapping(value="/message/inView.sam")
	public ModelAndView inView(HttpServletRequest request, ModelAndView mav) {
		
		// 조회하고자 하는 글번호 받아오기
		String str_inboxSeq = request.getParameter("inboxSeq");
		
		try {
			int inboxSeq = Integer.parseInt(str_inboxSeq);
			
			InboxVO inboxvo = null;
			
			inboxvo = service.getInView(inboxSeq);
			
			mav.addObject("inboxvo", inboxvo);
		
		} catch(NumberFormatException e) {
			
		}
		
		mav.setViewName("message/inView.tiles2");
		// /WEB-INF/views/tiles2/message/write.jsp 파일을 생성한다.
		
		return mav;
	}
	
	// 쪽지 쓰기
	@RequestMapping(value="/message/write.sam")
	public ModelAndView write(ModelAndView mav) {
		
		mav.setViewName("message/write.tiles2");
		// /WEB-INF/views/tiles2/message/write.jsp 파일을 생성한다.
		
		return mav;
	}
	

	  // 아이디 찾아보기
	  @RequestMapping(value="/message/userFind.sam") 
	  public String userFind() {
	 
		  return "tiles2/message/userFind";
		  
	 }
	  
	  // inbox 쪽지 한개 삭제하기 
	  @RequestMapping(value="/message/inDel.sam") 
	  public String inDel() {
	 
		  return "tiles2/message/userFind";
		  
	 }
	  

	 
}

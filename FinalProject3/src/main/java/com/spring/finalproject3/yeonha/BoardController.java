package com.spring.finalproject3.yeonha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.MyUtil;




@Controller
public class BoardController {
	

	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardService service;	
	

	// 게시판 글쓰기 폼
	@RequestMapping(value="/board/add.sam")
	public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
				
		mav.setViewName("board/add.tiles1");		
		return mav;
	}
	
	// === #54. 게시판 글쓰기 완료 요청 === // 
	@RequestMapping(value="/board/addEnd.sam", method= {RequestMethod.POST})
	public ModelAndView addEnd(ModelAndView mav, BoardVO boardvo) { 

		// System.out.println("set하기전 : "+boardvo.getNamecheck());
		
		String namecheck = boardvo.getNamecheck();
		
		if(namecheck == null) {
			boardvo.setNamecheck("0");
		}
		// System.out.println("set한 후  : "+boardvo.getNamecheck());
		
		int n = service.add(boardvo); // <== 파일첨부가 없는 글쓰기 
		
		if(n==1) {
			mav.setViewName("redirect:/board/list.sam");
		    //   list.sam 페이지로 redirect(페이지이동)해라는 말이다.
		}
		
		else {
			mav.setViewName("board/error/add_error.tiles1");
			//   /WEB-INF/views/tiles1/board/error/add_error.jsp 파일을 생성한다.
		}
				
		return mav;
	}
	
	// 글 목록보기
	@RequestMapping(value="/board/list.sam")
	public ModelAndView list(ModelAndView mav, HttpServletRequest request) {		
					
		List<BoardVO> boardList = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");		
		
		// 카테고리번호(1:자유,2:중고,3:모집,4:전체공지,5:Q&A)	
		String categoryno = request.getParameter("categoryno");		
		mav.addObject("categoryno", categoryno); // jsp에서 카테고리 번호 호출하기 위함
		
		String searchType = request.getParameter("searchType"); 
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType)) ) {
			searchType = "";
		}
		
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("categoryno", categoryno);
		
		int totalCount = 0;         // 총 게시물 건수
		int sizePerPage = 2;        // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)  
		
		int startRno = 0;           // 시작 행번호
		int endRno = 0;             // 끝 행번호 
		
		totalCount = service.getTotalCount(paraMap);

		totalPage = (int) Math.ceil( (double)totalCount/sizePerPage ); 
		
		if(str_currentShowPageNo == null) {
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
		
		boardList = service.boardListSearchWithPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		
		// === #121. 페이지바 만들기 === //
		int blockSize = 3;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		

		String pageBar = "<ul style='list-style: none;'>";
		String url = "list.sam";
		
		// === [맨처음][이전] 만들기 === 
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?categoryno="+categoryno+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>&laquo;</a></li>";
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
		
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a class='boarda' href='"+url+"?categoryno="+categoryno+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}
			
			loop++;
			pageNo++;
		}// end of while------------------------
		
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?categoryno="+categoryno+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>&raquo;</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		// === #123. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
	    //           사용자가 목록보기 버튼을 클릭했을때 돌아갈 페이지를 알려주기 위해
	    //           현재 페이지 주소를 뷰단으로 넘겨준다.
		String gobackURL = MyUtil.getCurrentURL(request);
		// System.out.println("~~~goback확인용"+gobackURL);
		
		mav.addObject("gobackURL", gobackURL);
		
		// ==== 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 끝 ==== //
		/////////////////////////////////////////////////////////
		
		mav.addObject("boardList", boardList);
		mav.setViewName("board/list.tiles1");
		
		return mav;
	}
	
	// 검색어 입력시 자동글 완성하기
	@ResponseBody
	@RequestMapping(value="/board/wordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String wordSearchShow(HttpServletRequest request) {
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String categoryno = request.getParameter("categoryno");	
		
		// System.out.println(categoryno);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("categoryno", categoryno);
		
		List<String> wordList = service.wordSearchShow(paraMap);
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(wordList != null) {
			for(String word : wordList) {
				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("word", word); 
				
				jsonArr.put(jsonObj);
			}
		}
		
		return jsonArr.toString(); 
	}
	
	
	
	
	
	
}

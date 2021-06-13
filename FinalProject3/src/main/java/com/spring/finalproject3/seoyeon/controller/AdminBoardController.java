package com.spring.finalproject3.seoyeon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.seoyeon.model.AdminBoardVO;
import com.spring.finalproject3.seoyeon.model.AdminCommentVO;
import com.spring.finalproject3.seoyeon.service.InterAdminBoardService;


@Component
@Controller
public class AdminBoardController {
	
	@Autowired 
	private InterAdminBoardService service;
	
	// ============= ***** 관리자 게시판 페이지 ***** ============= //
		@RequestMapping(value="/admin/boardlist.sam")
		public ModelAndView admin_boardList(ModelAndView mav, HttpServletRequest request) {
			
			List<AdminBoardVO> boardList = null;
			
			// 검색어 입력
	       String searchType = request.getParameter("searchType");
	       String searchWord = request.getParameter("searchWord");
	       String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	       
	       if(searchType ==null||(!"total".equals(searchType)&&!"subject".equals(searchType)&&!"name".equals(searchType))&&!"content".equals(searchType)) {
	          searchType = "";
	       }
	       
	       if(searchWord ==null||"".equals(searchWord)||searchWord.trim().isEmpty()) {
	          searchWord="";
	       }
	       
	       String categoryno = request.getParameter("categoryno");
	       if(categoryno==null) {
	    	   categoryno="1";
	       }
	       mav.addObject("categoryno",categoryno);
	          
	       Map<String, String> paraMap = new HashMap<String, String>();
	       
	       paraMap.put("searchType",searchType);
	       paraMap.put("searchWord",searchWord);	   
	   
	       // 게시판 선택
	       String viewBoard = request.getParameter("viewBoard");

	       
	       if(viewBoard == null||!("1".equals(viewBoard) || "2".equals(viewBoard) || "3".equals(viewBoard)) ) { 
	    	   viewBoard = "";
			}
	       
	       paraMap.put("viewBoard",viewBoard);
	       
	       
	       // 보고자 하는 페이지 선택
	       String page = request.getParameter("page");

	       if(page == null ||!("5".equals(page) || "15".equals(page) || "30".equals(page)) ) { 
	    	   page = "5";
			}
	       
	       int sizePerPage=Integer.parseInt(page);
	       request.setAttribute("page", page);
	       
	       int totalCount = 0;         // 총 게시물 건수
	    // int sizePerPage = 5;        // 한 페이지당 보여줄 게시물 건수
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
			String url = "";
			
			// === [맨처음][이전] 만들기 === 
			if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
					
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
			}
			
			while( !(loop > blockSize || pageNo > totalPage) ) {
			
				if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
				}
				else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
				}
				
				loop++;
				pageNo++;
			}// end of while------------------------
			
			
			// === [다음][마지막] 만들기 === 
			if(pageNo <= totalPage) {
				pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
				pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
			}
			
			pageBar += "</ul>";
			
			mav.addObject("pageBar",pageBar);	    	
	    	mav.addObject("totalCount",totalCount);
	    	mav.addObject("boardList", boardList);
	    	mav.setViewName("admin/adminBoard.tiles3");	    		    	
	        
	    	return mav;
		}
		
	//	=== 검색어 입력 시 자동글 완성하기 ===
		@ResponseBody
	 	@RequestMapping(value="/admin/wordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	 	public String wordSearchShow(HttpServletRequest request) {
	 		String searchType = request.getParameter("searchType");
	 		String searchWord = request.getParameter("searchWord");
	 		String viewBoard = request.getParameter("viewBoard");

		       
		       if(viewBoard == null||!("1".equals(viewBoard) || "2".equals(viewBoard) || "3".equals(viewBoard)) ) { 
		    	   viewBoard = "";
				}
		       
	 		
	 		Map<String,String> paraMap = new HashMap<String, String>();
	 		paraMap.put("searchType", searchType);
	 		paraMap.put("searchWord", searchWord);
	 		paraMap.put("viewBoard",viewBoard);
	 		
	 		List<String> wordList = service.wordSearchShow(paraMap);
	 		
	 		JSONArray jsonArr = new JSONArray(); // []
	 		
	 		if(wordList != null) {
	 			for(String word : wordList) {
	 				JSONObject jsonObj = new JSONObject();
	 				jsonObj.put("word", word);
	 				
	 				jsonArr.put(jsonObj);
	 			}
	 		}
	 		
	 		return jsonArr.toString();
	 		// "[]" 또는
	 		// "[{"word":"글쓰기 첫번쨰 연습입니다"},{"word":"글쓰기 두번째 연습입니다"}]"
	 	}
		
		
//		=== 게시판 이동하기 ===
		@ResponseBody
	 	@RequestMapping(value="/admin/boardMove.sam", method = {RequestMethod.POST})
		public String admin_boardMove(HttpServletRequest request) {
			
			String[] seqArr = request.getParameterValues("seqArr");
			String categoryno = request.getParameter("categoryno");

			int n=0;
			
			for(String seq : seqArr) {
				Map<String,String> paraMap = new HashMap<String, String>();
		 		paraMap.put("categoryno", categoryno);
		 		paraMap.put("seq", seq);
				n = service.boardMove(paraMap);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n);
			
			return jsonObj.toString();			
		}
		 
//		=== 게시글 삭제하기 ===
		@ResponseBody
	 	@RequestMapping(value="/admin/boardDelete.sam", method = {RequestMethod.POST})
		public String admin_boardDelete(HttpServletRequest request) throws ServletException, IOException{
			
			String[] seqArr = request.getParameterValues("seqArr");
			
			int n=0;
			
			for(String seq : seqArr) {
				n = service.boardDelete(seq);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n);
			
			return jsonObj.toString();			
		}
		
		
	// ============= ***** 관리자 댓글 페이지 ***** ============= //
		@RequestMapping(value="/admin/commentList.sam")
		public ModelAndView admin_commentList(ModelAndView mav, HttpServletRequest request) {
	
			List<AdminCommentVO> commentList = null;
			
			// 검색어 입력
	       String searchType = request.getParameter("searchType");
	       String searchWord = request.getParameter("searchWord");
	       String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	       
	       if(searchType ==null||(!"total".equals(searchType)&&!"subject".equals(searchType)&&!"name".equals(searchType))&&!"content".equals(searchType)) {
	          searchType = "";
	       }
	       
	       if(searchWord ==null||"".equals(searchWord)||searchWord.trim().isEmpty()) {
	          searchWord="";
	       }
	          
	       Map<String, String> paraMap = new HashMap<String, String>();
	       
	       paraMap.put("searchType",searchType);
	       paraMap.put("searchWord",searchWord);
	   
	       // 게시판 선택
	       String viewBoard = request.getParameter("viewBoard");

	       if(viewBoard == null||!("1".equals(viewBoard) || "2".equals(viewBoard) || "3".equals(viewBoard)) ) { 
	    	   viewBoard = "";
			}
	       
	       paraMap.put("viewBoard",viewBoard);
	       
	       
	       // 보고자 하는 페이지 당 댓글 수 선택
	       String page = request.getParameter("page");

	       if(page == null ||!("5".equals(page) || "15".equals(page) || "30".equals(page)) ) { 
	    	   page = "5";
			}
	       
	       int sizePerPage=Integer.parseInt(page);
	       request.setAttribute("page", page);
	       
	       int totalCount = 0;         // 총 게시물 건수
	    // int sizePerPage = 5;        // 한 페이지당 보여줄 게시물 건수
	       int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	       int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
	       
	       int startRno = 0;           // 시작 행번호
	       int endRno = 0;             // 끝 행번호 
	       
	       // 총 게시물 건수(totalCount)
	       totalCount = service.getTotalComment(paraMap); 
	       
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
			
			commentList = service.commentListSearchWithPaging(paraMap);
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
			String url = "";
			
			// === [맨처음][이전] 만들기 === 
			if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
					
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
			}
			
			while( !(loop > blockSize || pageNo > totalPage) ) {
			
				if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
				}
				else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
				}
				
				loop++;
				pageNo++;
			}// end of while------------------------
			
			
			// === [다음][마지막] 만들기 === 
			if(pageNo <= totalPage) {
				pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
				pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?viewBoard="+viewBoard+"&page="+page+"&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
			}
			
			pageBar += "</ul>";
			
			mav.addObject("pageBar",pageBar);	    	
	    	mav.addObject("totalCount",totalCount);
	    	mav.addObject("commentList", commentList);
	    	mav.setViewName("admin/adminComment.tiles3");	    		    	
	        
	    	return mav;
		}
		
		
//		=== 검색어 입력 시 자동글 완성하기 ===
			@ResponseBody
		 	@RequestMapping(value="/admin/commentWordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
		 	public String commentWordSearchShow(HttpServletRequest request) {
		 		String searchType = request.getParameter("searchType");
		 		String searchWord = request.getParameter("searchWord");
		 		String viewBoard = request.getParameter("viewBoard");

			       
			       if(viewBoard == null||!("1".equals(viewBoard) || "2".equals(viewBoard) || "3".equals(viewBoard)) ) { 
			    	   viewBoard = "";
					}
			       
		 		
		 		Map<String,String> paraMap = new HashMap<String, String>();
		 		paraMap.put("searchType", searchType);
		 		paraMap.put("searchWord", searchWord);
		 		paraMap.put("viewBoard",viewBoard);
		 		
		 		List<String> wordList = service.commentWordSearchShow(paraMap);
		 		
		 		JSONArray jsonArr = new JSONArray(); // []
		 		
		 		if(wordList != null) {
		 			for(String word : wordList) {
		 				JSONObject jsonObj = new JSONObject();
		 				jsonObj.put("word", word);
		 				
		 				jsonArr.put(jsonObj);
		 			}
		 		}
		 		
		 		return jsonArr.toString();
		 		// "[]" 또는
		 		// "[{"word":"글쓰기 첫번쨰 연습입니다"},{"word":"글쓰기 두번째 연습입니다"}]"
		 	}
			
			
//			=== 댓글 삭제하기 ===
			@ResponseBody
		 	@RequestMapping(value="/admin/commentDelete.sam", method = {RequestMethod.POST})
			public String admin_commentDelete(HttpServletRequest request) {
				
				String[] comseqArr = request.getParameterValues("comseqArr");
				
				int n=0;
				
				for(String comseq : comseqArr) {
					n = service.commentDelete(comseq);
				}
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("n", n);
				
				return jsonObj.toString();			
			}
			 
			
			
			
			
}

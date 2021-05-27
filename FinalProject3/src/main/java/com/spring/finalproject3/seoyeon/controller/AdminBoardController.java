package com.spring.finalproject3.seoyeon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.spring.finalproject3.seoyeon.service.InterAdminBoardService;


@Component
@Controller
public class AdminBoardController {
	
	@Autowired 
	private InterAdminBoardService service;
	
	// ============= ***** 관리자 게시판 페이지 ***** ============= //
		@RequestMapping(value="/admin/boardlist.sam")
		public ModelAndView admin_boardList(ModelAndView mav, HttpServletRequest request) {
			
			
	       String searchType = request.getParameter("searchType");
	       String searchWord = request.getParameter("searchWord");
	       
	       if(searchType ==null||(!"subject".equals(searchType)&&!"name".equals(searchType))) {
	          searchType = "";
	       }
	       
	       if(searchWord ==null||"".equals(searchWord)||searchWord.trim().isEmpty()) {
	          searchWord="";
	       }
	          
	       Map<String, String> paraMap = new HashMap<String, String>();
	       
	       paraMap.put("searchType",searchType);
	       paraMap.put("searchWord",searchWord);
	       
	       List<AdminBoardVO> boardList = service.boardListSearch(paraMap);
		       
	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute("readCountPermission", "yes");
	    	
	    	mav.addObject("boardList", boardList);
	    	mav.setViewName("admin/adminBoard.tiles1");
	    	
	    	// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
	        if(!"".equals(searchType) && !"".equals(searchWord)) {
	           mav.addObject("paraMap",paraMap);
	        }
	        
	    	return mav;
		}
		
	//	=== 검색어 입력 시 자동글 완성하기 ===
		@ResponseBody
	 	@RequestMapping(value="/wordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	 	public String wordSearchShow(HttpServletRequest request) {
	 		String searchType = request.getParameter("searchType");
	 		String searchWord = request.getParameter("searchWord");
	 		
	 		Map<String,String> paraMap = new HashMap<String, String>();
	 		paraMap.put("searchType", searchType);
	 		paraMap.put("searchWord", searchWord);
	 		
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
		
	// ============= ***** 관리자 댓글 페이지 ***** ============= //
		@RequestMapping(value="/admin/commentList.sam")
		public ModelAndView admin_commentList(ModelAndView mav, HttpServletRequest request) {
	
			List<AdminBoardVO> boardList = null;
			 boardList = service.boardListNoSearch();
			 mav.addObject("boardList", boardList);
			 
	    	mav.setViewName("admin/adminComment.tiles1");
	    	
	    	return mav;
		}
}

package com.spring.finalproject3.yeonha;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.FileManager;
import com.spring.finalproject3.common.MyUtil;
import com.spring.finalproject3.joseungjin.model.PersonVO;




@Controller
public class BoardController {
	

	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardService service;	
	
	// === #155. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI : Dependency Injection) ===  
	@Autowired     // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;

	// 게시판 글쓰기 폼
	@RequestMapping(value="/board/add.sam")
	public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String categoryno = request.getParameter("categoryno");
		// System.out.println("view답글클릭: "+categoryno);
						
		String fk_seq = request.getParameter("fk_seq");
		String groupno = request.getParameter("groupno");
		String depthno = request.getParameter("depthno");	 
		
		
		mav.addObject("categoryno", categoryno);
		mav.addObject("fk_seq", fk_seq);
		mav.addObject("groupno", groupno);
		mav.addObject("depthno", depthno);
		
		
		mav.setViewName("board/add.tiles2");		
		return mav;
	}
	
	// === #54. 게시판 글쓰기 완료 요청 === // 
	@RequestMapping(value="/board/addEnd.sam", method= {RequestMethod.POST})
	public ModelAndView addEnd(HttpServletRequest request, Map<String,String> paraMap, ModelAndView mav, BoardVO boardvo, MultipartHttpServletRequest mrequest) { 

		// System.out.println("set하기전 : "+boardvo.getNamecheck());		
		
		// 첨부파일 관련 작업
		MultipartFile attach = boardvo.getAttach();
		
		if(!attach.isEmpty()) {
			// 파일이 있는경우
			
			// WAS절대경로 알아와서 저장하기
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			
			String path = root+"resources"+File.separator+"files";
			
			// 변수 설정 및 값 초기화 후 파일올리기
			String newFileName = ""; // WAS에 저장될 파일명
			byte[] bytes = null; // 첨부파일 내용
			long fileSize = 0; // 첨부파일 크기
			
			try {
				bytes = attach.getBytes(); // 첨부파일 내용 읽어오기
				
				String originalFilename = attach.getOriginalFilename();
				
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				
				// BoardVO에 값 넣어주기
				boardvo.setFileName(newFileName);
				boardvo.setOrgFilename(originalFilename);
				
				fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				boardvo.setFileSize(String.valueOf(fileSize));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String namecheck = boardvo.getNamecheck();
		
		if(namecheck == null) {
			boardvo.setNamecheck("0");
		}
		// System.out.println("set한 후  : "+boardvo.getNamecheck());
		
		// 첨부파일 있는 글쓰기 없는 글쓰기 나눠서 service 호출
		
		int n = 0;
		
		if(attach.isEmpty()) {
			// 첨부파일이 없는 경우라면
			n = service.add(boardvo);
		}
		else {
			//첨부파일이 있는 경우
			n = service.add_withFile(boardvo);
		}
		
		if(n==1) {
			String categoryno = boardvo.getCategoryno();
			mav.addObject("categoryno", categoryno); // jsp에서 카테고리 번호 호출하기 위함
			mav.setViewName("redirect:/board/list.sam");
		}
		
		else {
			mav.setViewName("board/error/add_error.tiles2");
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
		int sizePerPage = 10;       // 한 페이지당 보여줄 게시물 건수
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
		
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
				
		int blockSize = 5;
		
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
		
		String gobackURL = MyUtil.getCurrentURL(request);
		// System.out.println("~~~goback확인용"+gobackURL);
		
		/*
		 * if(gobackURL != null) { gobackURL = gobackURL.replaceAll("", "&"); }
		 */
		
		mav.addObject("gobackURL", gobackURL);				
		mav.addObject("boardList", boardList);
		mav.setViewName("board/list.tiles2");
		
		return mav;
	}
	
	
	// 검색어 입력시 자동글 완성하기
	@ResponseBody
	@RequestMapping(value="/board/wordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String wordSearchShow(HttpServletRequest request) {
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String categoryno = request.getParameter("categoryno");	
		
		// System.out.println(searchType);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("categoryno", categoryno);
		
		List<String> wordList = service.wordSearchShow(paraMap);
		
		JSONArray jsonArr = new JSONArray(); 
		
		if(wordList != null) {
			for(String word : wordList) {
				JSONObject jsonObj = new JSONObject(); 
				jsonObj.put("word", word); 
				
				jsonArr.put(jsonObj);
			}
		}
		
		return jsonArr.toString(); 
	}
	
	// 글 한개를 보여주는 페이지 요청  (댓글 목록 포함)
	@RequestMapping(value="/board/view.sam")
	public ModelAndView view(HttpServletRequest request, ModelAndView mav) {
		
		String seq = request.getParameter("seq");		
	    String searchType = request.getParameter("searchType");
	    String searchWord = request.getParameter("searchWord");
	    
	    if(searchType == null) {
			searchType = "";	
		}
		if(searchWord == null) {
			searchWord = "";	
		}
		
	    Map<String,String> paraMap = new HashMap<>();
	    paraMap.put("seq", seq);
	    paraMap.put("searchType", searchType);
	    paraMap.put("searchWord", searchWord);
	      
	    mav.addObject("searchType", searchType);
	    mav.addObject("searchWord", searchWord);
	    
	    
		String gobackURL = request.getParameter("gobackURL");		
	
		if(gobackURL != null && gobackURL.contains(" ")) {
			gobackURL = gobackURL.replaceAll(" ", "&");	
		}
		
		mav.addObject("gobackURL", gobackURL);
		
		String categoryno = request.getParameter("categoryno");
		// System.out.println(categoryno);
		mav.addObject("categoryno", categoryno);
		
		try {
			Integer.parseInt(seq);
			
			String login_userid = null;
			
			HttpSession session = request.getSession();
			PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
			
			if(loginuser != null) {
				login_userid = String.valueOf(loginuser.getPerno());				
			}
						
			BoardVO boardvo = null;
			
			if("yes".equals(session.getAttribute("readCountPermission"))) {
				
				boardvo = service.getView(paraMap, login_userid); // 조회수 증가 후 글 조회
				
				session.removeAttribute("readCountPermission");
			}
			else {				
				boardvo = service.getViewWithNoAddCount(paraMap); // 조회수 증가 없이 글 조회				
			}			
			
			mav.addObject("boardvo", boardvo);			
	   
		}catch(NumberFormatException e) {
	    	
	    }			
		mav.setViewName("board/view.tiles2");
		
		return mav;
	}
	
	// 댓글쓰기(Ajax로 처리)  
	@ResponseBody
	@RequestMapping(value="/board/addComment.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String addComment(CommentVO commentvo) {
		
		int n = 0;
		
		try {
			n = service.addComment(commentvo);
			System.out.println("댓글쓰기 결과: "+n);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n); 
		jsonObj.put("name", commentvo.getName()); 
		
		return jsonObj.toString();
	}

		
	// 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리)
	@ResponseBody
	@RequestMapping(value="/board/commentList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String commentList(HttpServletRequest request) {
		
		String fk_seq = request.getParameter("fk_seq");
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		int sizePerPage = 5; 
		
		int startRno = (( Integer.parseInt(currentShowPageNo) - 1 ) * sizePerPage) + 1;
	    int endRno = startRno + sizePerPage - 1;
	    
	    Map<String,String> paraMap = new HashMap<>();
	    paraMap.put("fk_seq", fk_seq);
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));  
		
		List<CommentVO> commentList = service.getCommentListPaging(paraMap);
	
		JSONArray jsonArr = new JSONArray(); 
		
		if(commentList != null) { 
			for(CommentVO cmtvo : commentList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("content", cmtvo.getContent());
				jsonObj.put("name", cmtvo.getName());
				jsonObj.put("reregDate", cmtvo.getReregDate());
				jsonObj.put("identity", cmtvo.getIdentity());
				jsonObj.put("comseq", cmtvo.getComseq());
				jsonObj.put("fk_comseq", cmtvo.getFk_comseq());
				jsonObj.put("co_groupno", cmtvo.getCo_groupno());
				jsonObj.put("co_depthno", cmtvo.getCo_depthno());
				jsonObj.put("fk_perno", cmtvo.getFk_perno());
				
				jsonArr.put(jsonObj);
			}
		}		
		
		return jsonArr.toString();

	}
	
	
	// 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리)
	@ResponseBody
	@RequestMapping(value="/board/getCommentTotalPage.sam", method= {RequestMethod.GET})
	public String getCommentTotalPage(HttpServletRequest request) {
	
		String fk_seq = request.getParameter("fk_seq");
		String sizePerPage = request.getParameter("sizePerPage");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("fk_seq", fk_seq);
		paraMap.put("sizePerPage", sizePerPage);
		
		// 원글 글번호(parentSeq)에 해당하는 댓글의 총 페이지수를 알아오기
		int totalPage = service.getCommentTotalPage(paraMap);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("totalPage", totalPage); 
		
		
		return jsonObj.toString();
	}
	
	
	// 글수정 페이지 요청
	@RequestMapping(value="/board/edit.sam")
	public ModelAndView requiredLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
		String seq = request.getParameter("seq");
	  
		String searchType = request.getParameter("searchType");
		// System.out.println("써치타입: "+searchType);
		if(searchType == null) {
			searchType = "";
		}
		mav.addObject(searchType);
		
		String searchWord = request.getParameter("searchWord");
		// System.out.println("써치워드: "+searchWord);
		if(searchWord == null) {
			searchWord = "";
		}
		mav.addObject(searchWord);
		 
		// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
		BoardVO boardvo = service.getViewNo(seq);
	  
		HttpSession session = request.getSession();
		PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
	  
		String loginuserPerno = String.valueOf(loginuser.getPerno());
		
		if( !loginuserPerno.equals(boardvo.getFk_perno()) ) {
			String message = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
	 
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg");
		}
		else {
			mav.addObject("boardvo", boardvo);
			mav.setViewName("board/edit.tiles2");
		}
	      
		return mav;
	}
	
	
	// 글수정 페이지 완료하기 
	@RequestMapping(value="/board/editEnd.sam", method= {RequestMethod.POST})
	public ModelAndView editEnd(ModelAndView mav, BoardVO boardvo, HttpServletRequest request) {   
	   
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		int n = service.edit(boardvo);
	   
		if(n == 0) {
			mav.addObject("message", "수정이 실패했습니다.");
		}
		else {
			mav.addObject("message", "글수정 성공!!");      
		}
	  
		mav.addObject("loc", request.getContextPath()+"/board/view.sam?seq="+boardvo.getSeq()+
							"&categoryno="+boardvo.getCategoryno()+
							"&searchType="+searchType+
							"&searchWord="+searchWord);
		mav.setViewName("msg");
       
		return mav;
	}

	// 글 삭제하기
	@RequestMapping(value="/board/del.sam", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
   
		String seq = request.getParameter("seq");
		String categoryno = request.getParameter("categoryno");	
		 	System.out.println(categoryno);
		
		String gobackURL = request.getParameter("gobackURL");
		mav.addObject("gobackURL", gobackURL);
      
		BoardVO boardvo = service.getViewNo(seq);
		
		HttpSession session = request.getSession();
		PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
      
		String loginuserPerno = String.valueOf(loginuser.getPerno());
		
		String loc = "javascript:history.back()";
		
		
		if( !loginuserPerno.equals(boardvo.getFk_perno()) ) {
			String message = "다른 사용자의 글은 삭제가 불가합니다.";
			       
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg");
      	}
      	else {
    	    
    	    int n = service.del(Integer.parseInt(seq));    	
    		
    	    if(n == 0) {
    	         mav.addObject("message", "글 삭제가 불가합니다.");
    	         mav.addObject("loc", loc);
    	         mav.setViewName("msg");
    	    }     
    	    else {
    	         mav.addObject("message", "글삭제 성공!!");
    	         mav.addObject("loc", request.getContextPath()+"/board/list.sam?categoryno="+categoryno);
    	         mav.setViewName("msg");
    	    }      		
      	}     
		return mav;
	}
	
	// 댓글 삭제하기
	@ResponseBody
	@RequestMapping(value="/board/commentdel.sam", method= {RequestMethod.GET})
	public String commentdel(HttpServletRequest request) {
		
		int comseq = Integer.parseInt(request.getParameter("comseq"));
		
		String fk_seq = request.getParameter("fk_seq");
		// System.out.println("게시물 번호: " + fk_seq);
		
		int n = 0;
		
		n = service.delcomment(comseq); // tbl_comment에서 댓글 삭제
		
		int m = 0;
		
		if(n == 1) {
			//System.out.println("게시물 번호: " + fk_seq);
			
			m = service.minusCommentCount(fk_seq); // tbl_board에서 commentCount -1 하기
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("m", m); 
		
		return jsonObj.toString();
	}
	
	
	// 댓글 수정완료하기
	@ResponseBody
	@RequestMapping(value="/board/comEditEnd.sam", method= {RequestMethod.POST})
	public String comEditEnd(HttpServletRequest request) {
	
		String comseq = request.getParameter("comseq");
		String content = request.getParameter("comEditVal");
		
		// System.out.println("댓글번호: "+comseq);
		// System.out.println("댓글수정내용: "+content);
				
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("comseq", comseq);
		paraMap.put("content", content);
		
		int n = 0;
		
		n = service.comEditEnd(paraMap);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n); 
		
		return jsonObj.toString();
			
	}	
	
	// 첨부파일 다운로드 받기
	@RequestMapping(value="/board/download.sam")
	public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {
		
		String seq = request.getParameter("seq");
		// 첨부파일이 있는 글번호
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");
		
		/*
			첨부파일이 있는 글번호에서
			202106040930484198194255200.png 처럼
			이러한 fileName 값을 DB에서 가져와야한다
			또한 orgFilename 값도 DB에서 가져와야 한다
		*/
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			Integer.parseInt(seq);
			
			BoardVO boardvo = service.getViewWithNoAddCount(paraMap);
			
			if(boardvo == null || (boardvo != null && boardvo.getFileName() == null)) {
				out = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체생성
				
				out.println("<script type='text/javascript'>alert('존재하지 않는 글번호 또는 첨부파일이 없으므로 파일 다운로드가 불가합니다!!'); history.back();</script>");
				return; // 종료
			}
			else {
				String fileName = boardvo.getFileName();
				// 202106040930484198194255200.png : 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다. 
				
				String orgFilename = boardvo.getOrgFilename();
				// 강아지.png 다운로드 시 보여줄 파일명
				
				// 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
		        // 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
		        // WAS 의 webapp 의 절대경로를 알아와야 한다.
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/"); 
				
				// System.out.println("~~~~~~ webapp 의 절대경로 => " + root);
				// ~~~~~~ webapp 의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\ 
				
				String path = root+"resources"+File.separator+"files";
				/* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
			             운영체제가 Windows 이라면 File.separator 는  "\" 이고,
			             운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
			    */
				
				// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
				// System.out.println("~~~~~~ path => " + path);
				// ~~~~~~ path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files 
			
				 
				// **** file 다운로드 하기 **** //
				boolean flag = false; // file 다운로드의 성공,실패를 알려주는 용도 
				flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
		        // file 다운로드 성공시 flag 는 true, 
		        // file 다운로드 실패시 flag 는 false 를 가진다.
				
				if(!flag) {
					// 다운로드가 실패할 경우 메시지를 띄워준다.		          
	            	
					out = response.getWriter();
	            	// 웹브라우저상에 메시지를 쓰기 위한 객체생성.
		               
	            	out.println("<script type='text/javascript'>alert('파일 다운로드가 실패되었습니다!!'); history.back();</script>"); 		         
				}
			}
			
		}catch(NumberFormatException e) {
			
			try {
				out = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체생성
				
				out.println("<script type='text/javascript'>alert('파일 다운로드가 불가합니다!!'); history.back();</script>");
			} catch(IOException e1) {
				
			}
		} catch(IOException e2) {
			
		}		
	}
	

	// 게시물 좋아요(ajax)
	@ResponseBody
	@RequestMapping(value="/board/goGoodAdd.sam", method= {RequestMethod.GET})
	public void goodAdd(HttpServletRequest request) {
		
		String seq = request.getParameter("seq");		
		
		int n  = service.goodAdd(seq);
		
		String loc = "javascript:history.back()";
		
		System.out.println(n);		
		
	
	}
   
	
	// 스마트에디터  /image/photoUpload.sam
	// 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
	@RequestMapping(value="/image/multiplePhotoUpload.sam", method={RequestMethod.POST})
	public void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
	       
		/*
  			1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
	  		>>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
       		우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
		*/
	      
		// WAS 의 webapp 의 절대경로를 알아와야 한다. 
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/"); 
		String path = root + "resources"+File.separator+"photo_upload";
		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
	      
		// System.out.println(">>>> 확인용 path ==> " + path); 
		// >>>> 확인용 path ==> C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload    
	      
		File dir = new File(path);
		if(!dir.exists())
			dir.mkdirs();
	      
		String strURL = "";
	      
		try {
			if(!"OPTIONS".equals(req.getMethod().toUpperCase())) {
				String filename = req.getHeader("file-name"); //파일명을 받는다 - 일반 원본파일명
	     
				// System.out.println(">>>> 확인용 filename ==> " + filename); 
				// >>>> 확인용 filename ==> berkelekle%ED%8A%B8%EB%9E%9C%EB%94%9405.jpg
	     
				InputStream is = req.getInputStream();
				/*
	        		요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때,
	        		혹은 이름 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
	        		이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.
	
	          		서블릿에서 payload body는 Request.getParameter()가 아니라 
	       			Request.getInputStream() 혹은 Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다.    
			 	*/
				String newFilename = fileManager.doFileUpload(is, filename, path);
	  
				int width = fileManager.getImageWidth(path+File.separator+newFilename);
	 
				if(width > 600)
					width = 600;
	    
				// System.out.println(">>>> 확인용 width ==> " + width);
				// >>>> 확인용 width ==> 600
				// >>>> 확인용 width ==> 121
	  
				String CP = req.getContextPath(); // board
	
				strURL += "&bNewLine=true&sFileName="; 
				strURL += newFilename;
				strURL += "&sWidth="+width;
				strURL += "&sFileURL="+CP+"/resources/photo_upload/"+newFilename;
			}
	  
			/// 웹브라우저상에 사진 이미지를 쓰기 ///
         	PrintWriter out = res.getWriter();
         	out.print(strURL);
	         
		} catch(Exception e){
			e.printStackTrace();
		}
	   
  	}   

}

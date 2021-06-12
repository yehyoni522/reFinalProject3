package com.spring.finalproject3.yeonha2;

import java.io.File;
import java.io.IOException;
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
import org.springframework.stereotype.Component;
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
import com.spring.finalproject3.yeonha.BoardVO;

@Component
@Controller
public class LessonBoard2Controller {
	

	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterLessonService service;	
	
	// === #155. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI : Dependency Injection) ===  
	@Autowired     // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;

	
	// 강의실 공지사항 글쓰기 폼
	@RequestMapping(value="/lesson/noticeAdd.sam")
	public ModelAndView noticeAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {									
		
		String fk_subno = request.getParameter("fk_subno");
		
		mav.addObject("fk_subno", fk_subno);
		mav.setViewName("lesson/noticeAdd.tiles4");		
		return mav;
	}
	
	
	// 강의실 공지사항 글쓰기 완료 요청 
	@RequestMapping(value="/lesson/noticeAddEnd.sam", method= {RequestMethod.POST})
	public ModelAndView noticeAddEnd(Map<String,String> paraMap, ModelAndView mav, LessonNoticeVO lenotivo, MultipartHttpServletRequest mrequest) {
		
		MultipartFile attach = lenotivo.getAttach();
		
		if(!attach.isEmpty()) {
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			
			// System.out.println("~~~~~~ webapp 의 절대경로 => " + root);
			
			String path = root+"resources"+File.separator+"files";
			
			String newFileName = "";
			
			byte[] bytes = null;
			// 첨부파일의 내용을 담는 것
			
			long fileSize = 0;
			// 첨부파일의 크기 
			
			try {
				bytes = attach.getBytes();
				// 첨부파일의 내용물을 읽어오는 것 
				
				String originalFilename = attach.getOriginalFilename();
				// originalFilename ==> "강아지.png"
				
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				
	//			System.out.println(">>> 확인용 newFileName => " + newFileName);
				// >>> 확인용 newFileName => 20210603123820876795424460900.png 
				// >>> 확인용 newFileName => 20210603124015876910815673500.png
				
			/*
			    3. BoardVO boardvo 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기   
			*/
				lenotivo.setFileName(newFileName);
				// WAS(톰캣)에 저장될 파일명(20210603123820876795424460900.png)
				
				lenotivo.setOrgFilename(originalFilename);
				// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
				// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
				
				fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				lenotivo.setFileSize(String.valueOf(fileSize));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		int n = 0;
		
		if(attach.isEmpty()) { 
			// 첨부파일이 없는 경우라면
			n = service.add(lenotivo); 
		}
		else {
			// 첨부파일이 있는 경우라면
			n = service.add_withFile(lenotivo); 
		}
				
		if(n==1) {
			String fk_subno = lenotivo.getFk_subno();
			mav.addObject("fk_subno",fk_subno);
			mav.setViewName("redirect:/lesson/notice.sam");
		    //   list.action 페이지로 redirect(페이지이동)해라는 말이다.
		}
		
		else {
			mav.setViewName("board/error/add_error.tiles1");
			//   /WEB-INF/views/tiles1/board/error/add_error.jsp 파일을 생성한다.
		}
		
		return mav;
		
	}
	
	
	
	// 공지사항 글 목록보기
	@RequestMapping(value="/lesson/notice.sam")
	public ModelAndView notice(ModelAndView mav, HttpServletRequest request) {		
				
		List<LessonNoticeVO> lenotivo = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");		
		
		String fk_subno = request.getParameter("fk_subno");
		 System.out.println(fk_subno);
		mav.addObject("fk_subno", fk_subno);
		
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("fk_subno", fk_subno);
		
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
		
		lenotivo = service.noticeSearchWithPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	
				
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		

		String pageBar = "<ul style='list-style: none;'>";
		String url = "list.sam";
		
		// === [맨처음][이전] 만들기 === 
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?&currentShowPageNo="+(pageNo-1)+"'>&laquo;</a></li>";
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
		
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a class='boarda' href='"+url+"?&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}
			
			loop++;
			pageNo++;
		}// end of while------------------------
		
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?&currentShowPageNo="+pageNo+"'>&raquo;</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		// System.out.println("~~~goback확인용"+gobackURL);
		
		/*
		 * if(gobackURL != null) { gobackURL = gobackURL.replaceAll("", "&"); }
		 */
		
		mav.addObject("gobackURL", gobackURL);				
		mav.addObject("lenotivo", lenotivo);
		
		mav.setViewName("lesson/notice.tiles4");
		
		return mav;
	}
		
	// 글 한개를 보여주는 페이지 요청  (댓글 목록 포함)
	@RequestMapping(value="/lesson/noticeView.sam")
	public ModelAndView view(HttpServletRequest request, ModelAndView mav) {
		
		String seq = request.getParameter("seq");		
	    
		
	    Map<String,String> paraMap = new HashMap<>();
	    paraMap.put("seq", seq);
	    
	    
		String gobackURL = request.getParameter("gobackURL");		
	
		if(gobackURL != null && gobackURL.contains(" ")) {
			gobackURL = gobackURL.replaceAll(" ", "&");	
		}
		
		mav.addObject("gobackURL", gobackURL);		
	
		String fk_subno = request.getParameter("fk_subno");
		mav.addObject("fk_subno", fk_subno);
		
		try {
			Integer.parseInt(seq);
			
			String login_userid = null;
			
			HttpSession session = request.getSession();
			PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
			
			if(loginuser != null) {
				login_userid = String.valueOf(loginuser.getPerno());				
			}
						
			LessonNoticeVO lenotivo = null;
			
			if("yes".equals(session.getAttribute("readCountPermission"))) {
				
				lenotivo = service.getView(paraMap, login_userid); // 조회수 증가 후 글 조회
				
				session.removeAttribute("readCountPermission");
			}
			else {				
				lenotivo = service.getViewWithNoAddCount(paraMap); // 조회수 증가 없이 글 조회				
			}			
			
			mav.addObject("lenotivo", lenotivo);			
	   
		}catch(NumberFormatException e) {
	    	
	    }			
		mav.setViewName("lesson/noticeView.tiles4");
		
		return mav;
	}
		
	// 첨부파일 다운로드 받기
	@RequestMapping(value="/lesson/download.sam")
	public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {
		
		String seq = request.getParameter("seq");
		// 첨부파일이 있는 글번호
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		
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
			
			LessonNoticeVO lenotivo = service.getViewWithNoAddCount(paraMap);
			
			if(lenotivo == null || (lenotivo != null && lenotivo.getFileName() == null)) {
				out = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체생성
				
				out.println("<script type='text/javascript'>alert('존재하지 않는 글번호 또는 첨부파일이 없으므로 파일 다운로드가 불가합니다!!'); history.back();</script>");
				return; // 종료
			}
			else {
				String fileName = lenotivo.getFileName();
				// 202106040930484198194255200.png : 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다. 
				
				String orgFilename = lenotivo.getOrgFilename();
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

	// 공지사항 수정하기
	@RequestMapping(value="/lesson/noticeEdit.sam")
	public ModelAndView requiredLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
		String seq = request.getParameter("seq");
		
		// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
		LessonNoticeVO lenotivo = service.getViewNo(seq);
	  
		HttpSession session = request.getSession();
		PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
	  
		String loginuserPerno = String.valueOf(loginuser.getPerno());
		
		if( !loginuserPerno.equals(lenotivo.getFk_perno()) ) {
			String message = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
	 
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg");
		}
		else {
			mav.addObject("lenotivo", lenotivo);
			mav.setViewName("lesson/noticeEdit.tiles4");
		}
	      
		return mav;
	}
	
	
	// 글수정 페이지 완료하기 
	@RequestMapping(value="/lesson/noticeEditEnd.sam", method= {RequestMethod.POST})
	public ModelAndView editEnd(ModelAndView mav, LessonNoticeVO lenotivo, HttpServletRequest request, MultipartHttpServletRequest mrequest) {   
	   
		// System.out.println("삭제체크여부"+(request.getParameter("delfileName")));
		
		String delFileCheck = request.getParameter("delfileName");
		
		
		MultipartFile attach = lenotivo.getAttach();
		
		// 게시글에 첨부파일이 있는지 확인하기(수정)
		String filename = service.isFilename(lenotivo);
		lenotivo.setFileName(filename);
		// System.out.println("파일삭제 작업전 파일이름: "+boardvo.getFileName());
		
		if(delFileCheck != null && filename != null) {
			// 게시글에 파일이 있는데 파일삭제에 체크가 되어있다면
			service.delFile(lenotivo); // 첨부파일 삭제 체크시 첨부파일 삭제
			lenotivo.setFileName(null);
		}
		filename = service.isFilename(lenotivo);
		lenotivo.setFileName(filename);
		// System.out.println("파일삭제 작업후 파일이름: "+boardvo.getFileName());
		
		if(!attach.isEmpty() && filename == null) {
			// 게시글이 파일이 없고 파일첨부가 되었다면
			
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			
			String path = root+"resources"+File.separator+"files";
			
			String newFileName = "";
			// WAS(톰캣)의 디스크에 저장될 파일명 
			
			byte[] bytes = null;
			// 첨부파일의 내용을 담는 것
			
			long fileSize = 0;
			// 첨부파일의 크기 
			
			try {
				bytes = attach.getBytes();
				// 첨부파일의 내용물을 읽어오는 것 
				
				String originalFilename = attach.getOriginalFilename();
				// originalFilename ==> "강아지.png"
				
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				
	//			System.out.println(">>> 확인용 newFileName => " + newFileName);
				// >>> 확인용 newFileName => 20210603123820876795424460900.png 
				// >>> 확인용 newFileName => 20210603124015876910815673500.png
				
			/*
			    3. BoardVO boardvo 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기   
			*/
				lenotivo.setFileName(newFileName);
				// WAS(톰캣)에 저장될 파일명(20210603123820876795424460900.png)
				
				lenotivo.setOrgFilename(originalFilename);
				// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
				// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
				
				fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
				lenotivo.setFileSize(String.valueOf(fileSize));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(!attach.isEmpty() && filename != null){
			// 게시글에 파일이 있는데 파일첨부를 했다면
			mav.addObject("message", "파일은 하나만 첨부가능합니다");
			mav.setViewName("msg");
			
			return mav;
		}
		
		int n = 0;
		
		if(attach.isEmpty()) { 
			// 첨부파일이 없는 경우라면
			n = service.edit_withFile(lenotivo);			
		}
		else {
			// 첨부파일이 있는 경우
			n = service.edit(lenotivo);
		}
		
		if(n == 0) {
			mav.addObject("message", "수정이 실패했습니다.");
		}
		else {
			mav.addObject("message", "글수정 성공!!");      
		}
	  
		mav.addObject("loc", request.getContextPath()+"/lesson/noticeView.sam?seq="+lenotivo.getSeq());
		mav.setViewName("msg");
       
		return mav;
	}
	
	
	// 글 삭제하기
	@RequestMapping(value="/lesson/noticeDeleteEnd.sam")
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
   
		System.out.println("글삭제 컨트롤");
		
		String seq = request.getParameter("seq");
		String fk_subno = request.getParameter("fk_subno");	
      
  	    
	    int n = service.del(Integer.parseInt(seq));    	
		
	    if(n == 1) {    
	         mav.addObject("message", "글삭제 성공!!");
	         mav.addObject("loc", request.getContextPath()+"/lesson/notice.sam?fk_subno="+fk_subno);
	         mav.setViewName("msg");
	      		
	    }     
		return mav;
	}
	
	
	
}

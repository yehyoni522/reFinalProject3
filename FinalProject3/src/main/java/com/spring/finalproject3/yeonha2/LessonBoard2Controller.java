package com.spring.finalproject3.yeonha2;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.FileManager;
import com.spring.finalproject3.common.MyUtil;

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
			
			System.out.println("~~~~~~ webapp 의 절대경로 => " + root);
			
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
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>&laquo;</a></li>";
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
		
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a class='boarda' href='"+url+"?&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}
			
			loop++;
			pageNo++;
		}// end of while------------------------
		
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a class='boarda' href='"+url+"?&searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>&raquo;</a></li>";
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


}

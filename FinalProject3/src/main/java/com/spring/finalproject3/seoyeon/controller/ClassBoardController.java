package com.spring.finalproject3.seoyeon.controller;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.FileManager;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.seoyeon.model.QnAVO;
import com.spring.finalproject3.seoyeon.model.SubmitVO;
import com.spring.finalproject3.seoyeon.model.assignmentBoardVO;
import com.spring.finalproject3.seoyeon.service.InterClassBoardService;

@Component
@Controller
public class ClassBoardController {

	@Autowired 
	private InterClassBoardService service;
	
	// === #155. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI : Dependency Injection) ===  
	@Autowired     // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;
   
	@RequestMapping(value="/class/assignmentBoard.sam")
	public ModelAndView class_assignmentBoardList(ModelAndView mav, HttpServletRequest request) {
	
		List<assignmentBoardVO> assignmentList = null;
		Map<String, String> paraMap = new HashMap<String, String>();
		
		// *** 근데 과목번호 어케 알아옴?? <==페이지 폼 내에서 전달...?
		String subno = request.getParameter("subno");
		subno="1000";//////////////////////////////////////////////////////////////////*** 과목번호 임의로 넣음 ***/////////////////////////////////////////////////////
		paraMap.put("subno", subno);
		
		// 어떤 과목인지 과목이름 알아오기 => 과목이름 맨위에 띄워주기 위해 / 리스트 where절 사용
		String subject = service.getSubjectname(subno);
		request.setAttribute("subject", subject);
		
		// 해당 수업을 듣는 학생의 총 인원수 알아오기(교수 총 수강인원 나타낼때 필요)
		String totalPerson = service.getTotalPerson(subno);
		request.setAttribute("totalPerson", totalPerson);
		
		// **필요없음** 로그인한 사람 알아오기 ==> 학생이면 (제출,점수) 컬럼 더 보여주기 위해, 교수면 글쓰기 버튼+(제출개수/총수강인원) 컬럼보여주기
	//	HttpSession session = request.getSession();
	//	PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
	//	String identity = service.getIdentity(loginuser);
		
		
		
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
			pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; font-weight:bold; color:red; padding:2px 4px;'>"+pageNo+"</li>";
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
		public ModelAndView assignmentAddEnd(Map<String,String> paraMap, ModelAndView mav, assignmentBoardVO assgnVO, MultipartHttpServletRequest mrequest) {            
	      
			
			// === #153. !!! 첨부파일이 있는 경우 작업 시작 !!! ===
		      MultipartFile attach = assgnVO.getAttach();
		      
		      if(!attach.isEmpty()) {
		         // attach(첨부파일)가 비어있지 않으면(즉, 첨부파일이 있는 경우라면) 
		         /*
		               1. 사용자가 보낸 첨부파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. 
		               >>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
		                          우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다.
		                          조심할 것은  Package Explorer 에서  files 라는 폴더를 만드는 것이 아니다.       
		          */
		         // WAS의 webapp 의 절대경로를 알아와야 한다.
		         HttpSession session = mrequest.getSession();
		         String root = session.getServletContext().getRealPath("/");
		         
		         String path = root +"resources"+File.separator+"files";// path가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다. 
		         
		         /* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		                   운영체제가 Windows 이라면 File.separator 는  "\" 이고,
		                   운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
		          */
		         
		         
		         //2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일 올리기
		         
		         String newFileName = ""; // WAS(톰캣)의 디스크에 저장될 파일명 
		         byte[] bytes = null;
		         //첨부파일의 내용을 담는 것
		         
		         long fileSize = 0;
		         //첨부파일의 크기
		         
		         try {
					bytes = attach.getBytes();
					//첨부파일의 내용물을 읽어오는 것
					
					String originalFilename = attach.getOriginalFilename();
					// originalFilename ==> "강아지.png"
					
					newFileName = fileManager.doFileUpload(bytes, originalFilename, path);				

					/*
		            3. BoardVO assgnVO 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기   
					*/
					
					assgnVO.setFileName(newFileName);
					// WAS(톰캣)에 저장될 파일명(20210603123721233984201685500.png)
					
					assgnVO.setOrgFilename(originalFilename);
					// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
		            // 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
					
					fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
					assgnVO.setFileSize(String.valueOf(fileSize));
					
				} catch (Exception e) {		
					e.printStackTrace();
				}
		      }
		      // === !!! 첨부파일이 있는 경우 작업 끝 !!! ===
			
		//	int n = service.assignmentAdd(assgnVO); // <== 파일첨부가 없는 글쓰기 
	  
		      int n = 0;
		      
		      if(attach.isEmpty()) {
		    	  // 파일첨부가 없는 경우
		    	  n=service.assignmentAdd(assgnVO);
		      }
		      else {
		    	  // 파일첨부가 있는 경우
		    	  n=service.assignmentAdd_withFile(assgnVO);
		      }
		      
		      
		      if(n==1) {
		         mav.setViewName("redirect:/class/assignmentBoard.sam");
		          //   list.action 페이지로 redirect(페이지이동)해라는 말이다.
		      }
		      
		      else {
		         mav.setViewName("board/error/add_error.tiles1");
		         //   /WEB-INF/views/tiles1/board/error/add_error.jsp 파일을 생성한다.
		      }
		      
		      return mav;
		}
		
	//	과제 상세 페이지 보기
		@RequestMapping(value="/class/assignmentView.sam")
		public ModelAndView assignmentView(HttpServletRequest request, ModelAndView mav) {
			
		// 	조회하고자 하는 과제번호 받아오기
			String assgnno = request.getParameter("assgnno");
			String gobackURL = request.getParameter("gobackURL");
			mav.addObject("gobackURL", gobackURL);
			
			assignmentBoardVO assignmentVO = null;

			// 과제 게시글1개 조회
			assignmentVO = service.assignmentView(assgnno);
			
			mav.addObject("assignmentVO", assignmentVO);
				
			/*
			try {
		         
		         
		         int login_perno=0;
		         
		         HttpSession session = request.getSession();
		         PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
		         
		         
		         if(loginuser != null) {
		        	 login_perno = loginuser.getPerno();
		            // login_userid 는 로그인 되어진 사용자의 userid 이다.
		         }
		         
		         assignmentBoardVO assignmentVO = null;
		         
	             // 과제 게시글1개 조회
	        	 assignmentVO = service.assignmentView(assgnno);

		         mav.addObject("assignmentVO", assignmentVO);
		         
		      } catch(NumberFormatException e) {
		         
		      }
		      */
		      mav.setViewName("class/assignmentInfo.tiles2");
			
			return mav;
		}
		
		// == 과제 게시글 수정 페이지 요청 
		@RequestMapping(value="/class/assignmentEdit.sam")
		public ModelAndView assignmentEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
			// 글 수정해야 할 글번호 가져오기
			String assgnno = request.getParameter("assgnno");
			
			// 글 수정해야할 글1개 내용 가져오기
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("assgnno", assgnno);
			
			assignmentBoardVO assignmentVO = service.assignmentView(assgnno);
			// 글조회수(readCount) 증가 없이 단순히 글1개만 조회 해주는 것이다.
			
			mav.addObject("assignmentVO", assignmentVO);
		
			mav.setViewName("class/assignmentEdit.tiles2");
		
			return mav;
		}
		
		
		// === 글수정 페이지 완료하기 === //
		@RequestMapping(value="/class/assignmentEditEnd.sam", method= {RequestMethod.POST})
		public ModelAndView assignmentEditEnd(ModelAndView mav, assignmentBoardVO assignmentVO, HttpServletRequest request) {
		
			int n = service.assignmentEdit(assignmentVO);
			
			if(n==1) {
				mav.addObject("message", "글수정 성공!!");		
			}			
			
			mav.addObject("loc", request.getContextPath()+"/class/assignmentView.sam?assgnno="+assignmentVO.getAssgnno());
		
			mav.setViewName("msg");
			
			return mav;
		}
		
		// === 글삭제 페이지 완료하기 === // 
		@RequestMapping(value="/class/assignmentDeleteEnd.sam")
		public ModelAndView assignmentDeleteEnd(HttpServletRequest request, ModelAndView mav) {
		
			// 삭제해야 할 글번호 가져오기
			String assgnno = request.getParameter("assgnno");

			int n = service.assignmentDelete(assgnno); 
			
			if(n==1) {
				mav.addObject("message", "글삭제 성공!!");
				mav.addObject("loc", request.getContextPath()+"/class/assignmentBoard.sam");
			}
			
			mav.setViewName("msg");
			
			return mav;
		}
		
		
		// === 댓글쓰기(Ajax 로 처리) === // 
		@ResponseBody
		@RequestMapping(value="/class/addSubmit.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
		public String addSubmit(SubmitVO submitvo) {
					
			int n = 0;			

			try {
				n = service.addSubmit(submitvo);
			} catch (Throwable e) {
				
			}
			// 댓글쓰기(insert) 및 원게시물에 제출의 개수 증가(update 1씩 증가)하기 
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n); // 정상이라면 {"n":1}  오류가 발생하면 {"n":0}
		
			return jsonObj.toString();  // 정상이라면  "{"n":1, "name":"서영학"}"  오류가 발생하면  "{"n":0, "name":"서영학"}"
		}
		
		@ResponseBody
		@RequestMapping(value="/class/addSubmit_withAttach.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
		public String addSubmit(SubmitVO submitvo, MultipartHttpServletRequest mrequest) {
		    // 댓글쓰기에 첨부파일이 있는 경우 
			System.out.println(submitvo.getContent());
			// === !!! 첨부파일이 있는 경우 작업 시작 !!! ===
			MultipartFile attach = submitvo.getAttach();
					
			if( !attach.isEmpty() ) {
			//	System.out.println("~~~~~~ 댓글쓰기 첨부파일 들어옴.");
				// attach(첨부파일)가 비어있지 않으면(즉, 첨부파일이 있는 경우라면) 
						
				/*
				   1. 사용자가 보낸 첨부파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. 
				   >>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
				              우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다.
				              조심할 것은  Package Explorer 에서  files 라는 폴더를 만드는 것이 아니다.       
				 */
				// WAS의 webapp 의 절대경로를 알아와야 한다.
				HttpSession session = mrequest.getSession();
				String root = session.getServletContext().getRealPath("/");
						
		//		System.out.println("~~~~ webapp 의 절대경로 => " + root);
				// ~~~~ webapp 의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\
						
				String path = root+"resources"+ File.separator +"files";
				/* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
			              운영체제가 Windows 이라면 File.separator 는  "\" 이고,
			              운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
			    */
						
				// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
		//		System.out.println("~~~~ path => " + path);
				// ~~~~ path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files  
						
			/*
			    2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일올리기   
			*/
				String newFileName = "";
				// WAS(톰캣)의 디스크에 저장될 파일명 
				
				byte[] bytes = null;
				// 첨부파일의 내용물을 담는 것  
				
				long fileSize = 0;
				// 첨부파일의 크기 
						
				try {
					bytes = attach.getBytes();
					// 첨부파일의 내용물을 읽어오는 것 
					
					newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path); 
					// 첨부되어진 파일을 업로드 하도록 하는 것이다. 
					// attach.getOriginalFilename() 은 첨부파일의 파일명(예: 강아지.png)이다.
					
			 //		System.out.println(">>> 확인용  newFileName => " + newFileName);
					// >>> 확인용  newFileName => 2020120910381893648363550700.png 
					
					/*
					    3. CommentVO commentvo 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기   
					*/
					submitvo.setFileName(newFileName);
					// WAS(톰캣)에 저장될 파일명(2020120809271535243254235235234.png) 
					
					submitvo.setOrgFilename(attach.getOriginalFilename());
					// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
					// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
					
					fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
					submitvo.setFileSize(String.valueOf(fileSize));
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			// === !!! 첨부파일이 있는 경우 작업 끝  !!! ===				
			
			int n = 0;
			
			
			
			try {
				n = service.addSubmit(submitvo);
			} catch (Throwable e) {
				
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n); // 정상이라면 {"n":1}  오류가 발생하면 {"n":0}
		
			return jsonObj.toString();    // 정상이라면  "{"n":1, "name":"서영학"}"  오류가 발생하면  "{"n":0, "name":"서영학"}"
		}
		
		
		     
	   // === #132. 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
	   @ResponseBody
	   @RequestMapping(value="/class/getSubmitTotalPage.sam", method= {RequestMethod.GET})
	   public String getSubmitTotalPage(HttpServletRequest request) {
	      
	      String fk_assgnno = request.getParameter("fk_assgnno");
	      String sizePerPage = request.getParameter("sizePerPage");
	      
	      Map<String,String> paraMap = new HashMap<>();
	      paraMap.put("fk_assgnno", fk_assgnno);
	      paraMap.put("sizePerPage", sizePerPage);
	      
	      // 원글 글번호(parentSeq)에 해당하는 댓글의 총 페이지수를 알아오기
	      int totalPage = service.getSubmitTotalPage(paraMap);
	      
	      JSONObject jsonObj = new JSONObject();
	      jsonObj.put("totalPage", totalPage); // {"totalPage":5}
	      
	   //   System.out.println("~~~~ 확인용 : " + jsonObj.toString());
	      
	      return jsonObj.toString(); 
	      // "{"totalPage":5}"
	   }
	   
	   
	   // 학생) 과제 제출했는지 확인하기
	   @ResponseBody
		@RequestMapping(value="/class/studentSubmit.sam", method= {RequestMethod.GET})
		public String studentSubmit(HttpServletRequest request) {
		   
		    String perno = request.getParameter("perno"); 
		    String assgnno = request.getParameter("assgnno");

		    Map<String, String> paraMap = new HashMap<>();
		    paraMap.put("perno", perno);
		    paraMap.put("assgnno", assgnno);
		    
		    
		    int n = service.studentSubmit(paraMap);
		
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("n",n);
            
		    return jsonObj.toString();  
	   }
	   
	   // === 교수) 전체 댓글 조회하기 (Ajax 로 처리) === //
			@ResponseBody
			@RequestMapping(value="/class/submitList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
			public String submitList(HttpServletRequest request) {
				
				String currentShowPageNo = request.getParameter("currentShowPageNo");
				String fk_assgnno = request.getParameter("fk_assgnno"); 
		    
				if(currentShowPageNo == null) {
			         currentShowPageNo = "1";
			      }
			      
			      int sizePerPage = 5;  // 한 페이지당 5개의 댓글을 보여줄 것임.
			      
			      // **** 가져올 게시글의 범위를 구한다.(공식임!!!) **** 
			      /*
			           currentShowPageNo      startRno     endRno
			          --------------------------------------------
			               1 page        ===>    1           5
			               2 page        ===>    6           10
			               3 page        ===>    11          15
			               4 page        ===>    16          20
			               ......                ...         ...
			       */
			      
			      int startRno = (( Integer.parseInt(currentShowPageNo) - 1 ) * sizePerPage) + 1;
			      int endRno = startRno + sizePerPage - 1; 
			      
			      Map<String, String> paraMap = new HashMap<>();
			      paraMap.put("fk_assgnno", fk_assgnno);
			      paraMap.put("startRno", String.valueOf(startRno));
			      paraMap.put("endRno", String.valueOf(endRno));
			      
			    List<SubmitVO> submitList = service.getSubmitListPaging(paraMap);
			    JSONArray jsonArr = new JSONArray(); // []
			    if(submitList != null) {
			    	for(SubmitVO cmtvo : submitList) {
			    		JSONObject jsonObj = new JSONObject();
			    		jsonObj.put("submitName", cmtvo.getSubmitName());
			    		jsonObj.put("content", cmtvo.getContent());
			    		jsonObj.put("submitDate", cmtvo.getSubmitDate());
			    		jsonObj.put("fileName", cmtvo.getFileName());
			            jsonObj.put("orgFilename", cmtvo.getOrgFilename());
			            jsonObj.put("fileSize", cmtvo.getFileSize());
		            
			    		jsonArr.put(jsonObj);
			    	}
			    }
			      
			    return jsonArr.toString(); 
			}
		   
	   // === 학생) 댓글 조회하기 (Ajax 로 처리) === //
		@ResponseBody
		@RequestMapping(value="/class/mysubmitList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
		public String mysubmitList(HttpServletRequest request) {
			
			String perno = request.getParameter("perno");
			String fk_assgnno = request.getParameter("fk_assgnno"); 
	    
		    Map<String, String> paraMap = new HashMap<>();
		    paraMap.put("perno", perno);
		    paraMap.put("fk_assgnno", fk_assgnno);
		    
		    List<SubmitVO> submitList = service.mysubmitList(paraMap);
		    JSONArray jsonArr = new JSONArray(); // []
		    if(submitList != null) {
		    	for(SubmitVO cmtvo : submitList) {
		    		JSONObject jsonObj = new JSONObject();
		    		jsonObj.put("submitName", cmtvo.getSubmitName());
		    		jsonObj.put("content", cmtvo.getContent());
		    		jsonObj.put("fileName", cmtvo.getFileName());
		            jsonObj.put("orgFilename", cmtvo.getOrgFilename());
		            jsonObj.put("fileSize", cmtvo.getFileSize());
		            jsonObj.put("submitDate", cmtvo.getSubmitDate());
	            
		    		jsonArr.put(jsonObj);
		    	}
		    }
		      
		    return jsonArr.toString(); 
		}
		
		
		// === 과제게시물 첨부파일 다운로드받기 === //
		@RequestMapping(value="/class/assgndownload.sam")
		   public void assgndownload(HttpServletRequest request, HttpServletResponse response) {
		      
		      String assgnno = request.getParameter("assgnno"); // 첨부파일이 잇는 글번호
		      
		      response.setContentType("text/html; charset=UTF-8");
		      PrintWriter out = null;
		      
		      try {
		         Integer.parseInt(assgnno);
		         
		         assignmentBoardVO assignmentVO = service.assignmentView(assgnno);
		         
		         if(assignmentVO==null || (assignmentVO != null && assignmentVO.getFileName() == null)) {
		            out = response.getWriter();
		            out.println("<script type='text/javascript'>alert('존재하지않는 글번호이거나 파일이 존재하지 않습니다. <br>파일 다운로드가 불가합니다!!'); history.back();</script>");
		            return;
		         }
		         else {
		            String fileName = assignmentVO.getFileName();
		            //이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다. 
		            
		            String orgFilename = assignmentVO.getOrgFilename();
		            
		            // 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
		            // 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
		            // WAS 의 webapp 의 절대경로를 알아와야 한다.
		            
		            // WAS의 webapp 의 절대경로를 알아와야 한다.
		            HttpSession session = request.getSession();
		            String root = session.getServletContext().getRealPath("/");
		            
		            String path = root +"resources"+File.separator+"files";// path가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다. 
		            
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
		      } catch (NumberFormatException e) {

		         try {
		         out = response.getWriter();
		         out.println("<script type='text/javascript'>alert('파일 다운로드가 불가합니다!!'); history.back();</script>");
		         
		         }catch(IOException e1){
		            
		         }
		      } catch(IOException e2){
		         
		      }
		      
		   }
		
		// === 댓글 첨부파일 다운로드 받기 === //
		@RequestMapping(value="/class/submitdownload.sam")
		public void submitdownload(HttpServletRequest request, HttpServletResponse response) {
			
			String submitno = request.getParameter("submitno");
			// 첨부파일이 있는 댓글번호
			 
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("submitno", submitno);
			paraMap.put("searchType", "");
			paraMap.put("searchWord", "");
					
			/*
			    첨부파일이 있는 글번호에서
			  20210604092841951821676968700.png 처럼
			    이러한 fileName 값을 DB에서 가져와야 한다.
			    또한 orgFilename 값도 DB에서 가져와야 한다.      
			*/
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = null;
			
			try {
				Integer.parseInt(submitno);
				
				SubmitVO submitvo = service.getSubmitOne(submitno);
				
				if(submitvo == null || (submitvo != null && submitvo.getFileName() == null) ) {
					out = response.getWriter();
					// 웹브라우저상에 메시지를 쓰기 위한 객체생성
					
					out.println("<script type='text/javascript'>alert('존재하지 않는 글번호 이거나 첨부파일 없으므로 파일 다운로드가 불가합니다!!'); history.back();</script>");
					
					return; // 종료
				}
				else {
					String fileName = submitvo.getFileName();
					// 20210604092841951821676968700.png  이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.  
					
					String orgFilename = submitvo.getOrgFilename();
					// 강아지.png  다운로드시 보여줄 파일명
					
					
					// 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
					// 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
					// WAS 의 webapp 의 절대경로를 알아와야 한다.
					HttpSession session = request.getSession();
					String root = session.getServletContext().getRealPath("/"); 
					
				//	System.out.println("~~~~~~ webapp 의 절대경로 => " + root);
					// ~~~~~~ webapp 의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\ 
					
					String path = root+"resources"+File.separator+"files";
					/* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
				             운영체제가 Windows 이라면 File.separator 는  "\" 이고,
				             운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
				    */
					
					// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
				//	System.out.println("~~~~~~ path => " + path);
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
				
			} catch(NumberFormatException e) {
				try {
					out = response.getWriter();
					// 웹브라우저상에 메시지를 쓰기 위한 객체생성
					
					out.println("<script type='text/javascript'>alert('파일 다운로드가 불가합니다!!'); history.back();</script>");
				} catch(IOException e1) {
					
				}
				
			} catch(IOException e2) {
				
			}
			
		}	
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
													
											//			질문게시판
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// 질문게시판 목록
		@RequestMapping(value="/class/qnaBoard.sam")
		public ModelAndView class_qnaBoardList(ModelAndView mav, HttpServletRequest request) {
		
			List<QnAVO> qnaList = null;
			
			Map<String, String> paraMap = new HashMap<String, String>();
			
			// *** 근데 과목번호 어케 알아옴?? <==페이지 폼 내에서 전달...?
			String subno = request.getParameter("subno");
			subno="1000";//////////////////////////////////////////////////////////////////*** 과목번호 임의로 넣음 ***/////////////////////////////////////////////////////
			paraMap.put("subno", subno);
			
			// 어떤 과목인지 과목이름 알아오기 => 과목이름 맨위에 띄워주기 위해 / 리스트 where절 사용
			String subject = service.getSubjectname(subno);
			request.setAttribute("subject", subject);
			
			//페이징처리
			int totalCount = 0;         // 총 게시물 건수
			int sizePerPage = 5;        // 한 페이지당 보여줄 게시물 건수
			int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		    int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
		    String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		    
		    int startRno = 0;           // 시작 행번호
		    int endRno = 0;             // 끝 행번호 
			
		    // 총 게시물 건수(totalCount)
		    totalCount = service.getTotalQna(paraMap); 
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
			qnaList = service.qnaListSearchWithPaging(paraMap);
		    
			
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
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; font-weight:bold; color:red; padding:2px 4px;'>"+pageNo+"</li>";
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
	    	mav.addObject("qnaList", qnaList);
			
						
			mav.setViewName("class/qnaBoard.tiles2");	
			
			return mav;
		}
		
		
		// === 질문 게시판 글쓰기 폼 페이지 요청 하기 === // 
		   @RequestMapping(value="/class/qnaAdd.sam")
		   public ModelAndView qnaAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		      
		      // === #142. 답변글쓰기가 추가된 경우 === //
		      String fk_qnano = request.getParameter("fk_qnano");
		      String groupno = request.getParameter("groupno");
		      String depthno = request.getParameter("depthno");
		      
		      mav.addObject("fk_qnano", fk_qnano);
		      mav.addObject("groupno", groupno);
		      mav.addObject("depthno", depthno);
		      ///////////////////////////////////////////////////
		      
		      mav.setViewName("class/qnaAdd.tiles2");
		      
		      return mav;
		   }
		   
		// === 질문 게시판 글쓰기 완료 요청 === // 
			@RequestMapping(value="/class/qnaAddEnd.sam", method= {RequestMethod.POST})
			public ModelAndView qnaAddEnd(Map<String,String> paraMap, ModelAndView mav, QnAVO qnavo) {
		   
				int n =service.qnaAdd(qnavo);
				 
				if(n==1) {
					mav.setViewName("redirect:/class/qnaBoard.sam");
				}
		      
				else {
					mav.setViewName("board/error/add_error.tiles1");
				}
				return mav;
			}
			
		// == 질문 게시판 글 1개 상세보기 == //
		@RequestMapping(value="/class/qnaView.sam")
		public ModelAndView qnaView(HttpServletRequest request, ModelAndView mav) {
			
			// 조회하고자 하는 글번호 받아오기
		    String qnano = request.getParameter("qnano");	
		    
		    // 글쓴이 perno받아오기
		    String fk_perno = request.getParameter("fk_perno");
			
		    Map<String,String> paraMap = new HashMap<>();
		    paraMap.put("qnano", qnano);		   
		    
		    // 로그인 한 사람이 글쓴이인지, 교수인지 확인 후 글 열람가능
		    int login_perno = 0;
		    HttpSession session = request.getSession();
	        PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
	        
	        QnAVO qnavo = null;
	        qnavo = service.getQnaView(paraMap);
        	mav.addObject("qnavo",qnavo);
        	mav.setViewName("class/qnaView.tiles2");
/*
	        if(loginuser != null) {
	            login_perno = loginuser.getPerno(); // 로그인 한 사람의 perno
	            int login_identity = loginuser.getIdentity();	                        
	            
            	// 글쓴 사람의 perno == 로그인한 사람의 perno인경우 글열람 허용
	            // 로그인한 사람이 글쓴이는 아니지만 교수일 경우 글열람 허용
	            if(login_perno==Integer.parseInt(fk_perno)||login_identity==1) {
	            	qnavo = service.getQnaView(paraMap);
	            	mav.addObject("qnavo",qnavo);
	            	mav.setViewName("class/qnaView.tiles2");
	            }
	            
	            // 그 외 글 열람 비허용
	            else {
	            	String message = "다른 사용자의 질문글은 열람이 불가합니다.";
	                String loc = "javascript:history.back()";
	                
	                mav.addObject("message", message);
	                mav.addObject("loc", loc);
	                mav.setViewName("msg");
	            }
	            
	         }
			*/
			return mav;
		}
		
		// == 과제 게시글 수정 페이지 요청 
		@RequestMapping(value="/class/qnaEdit.sam")
		public ModelAndView qnaEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
			// 글 수정해야 할 글번호 가져오기
			String qnano = request.getParameter("qnano");
			
			// 글 수정해야할 글1개 내용 가져오기
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("qnano", qnano);
			
			QnAVO qnavo= service.getQnaView(paraMap);
			// 글조회수(readCount) 증가 없이 단순히 글1개만 조회 해주는 것이다.
			
			mav.addObject("qnavo", qnavo);
		
			mav.setViewName("class/qnaEdit.tiles2");
		
			return mav;
		}
		
		
		// === 글수정 페이지 완료하기 === //
		@RequestMapping(value="/class/qnaEditEnd.sam", method= {RequestMethod.POST})
		public ModelAndView qnaEditEnd(ModelAndView mav, QnAVO qnavo, HttpServletRequest request) {
		
			int n = service.qnaEditEnd(qnavo);
			
			if(n==1) {
				mav.addObject("message", "글수정 성공!!");		
			}			
			
			mav.addObject("loc", request.getContextPath()+"/class/qnaView.sam?qnano="+qnavo.getQnano());
		
			mav.setViewName("msg");
			
			return mav;
		}
		
		
		// === 글삭제 페이지 완료하기 === // 
		@RequestMapping(value="/class/qnaDeleteEnd.sam")
		public ModelAndView qnaDeleteEnd(HttpServletRequest request, ModelAndView mav) {
		
			// 삭제해야 할 글번호 가져오기
			String qnano = request.getParameter("qnano");
			String fk_qnano = request.getParameter("fk_qnano");

			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("qnano", qnano);
			paraMap.put("fk_qnano", fk_qnano);
			
			System.out.println(qnano+" "+fk_qnano);
			int n = service.qnaDelete(paraMap); 
			
			if(n==1) {
				mav.addObject("message", "글삭제 성공!!");
				mav.addObject("loc", request.getContextPath()+"/class/qnaBoard.sam");
			}
			
			mav.setViewName("msg");
			
			return mav;
		}
			    
		
		// ==== #168. 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
		   @RequestMapping(value="/class/image/multiplePhotoUpload.sam", method={RequestMethod.POST})
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

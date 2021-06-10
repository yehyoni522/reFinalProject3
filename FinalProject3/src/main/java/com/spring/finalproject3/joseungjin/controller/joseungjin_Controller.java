package com.spring.finalproject3.joseungjin.controller;

import java.util.HashMap;






import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.finalproject3.common.Sha256;
import com.spring.finalproject3.joseungjin.mail.GoogleMail;
import com.spring.finalproject3.joseungjin.model.MainSubjectVO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.model.ScheduleDAO;
import com.spring.finalproject3.joseungjin.model.ScheduleVO;
import com.spring.finalproject3.joseungjin.service.InterMemberService;
import com.spring.finalproject3.yehyeon.model.SubjectVO;


@Controller
public class joseungjin_Controller {
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterMemberService service;
	
	//메인페이지 요청
	@RequestMapping(value="/index.sam")
	public ModelAndView main(ModelAndView mav,HttpServletRequest request) {
		
		List<Main_index_BoardVO> MainboardList = null;
		List<MainSubjectVO> MainsubjectList = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		if(session.getAttribute("loginuser") != null) {
			PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
			int userid = loginuser.getPerno();
		
			MainsubjectList = service.Mainsubject(userid);
			
			int ident = loginuser.getIdentity();
			if(ident ==2) {
				mav.setViewName("admin/index.tiles3");
			}
			else {
				MainboardList =service.MainboardView();
				
				
				mav.addObject("MainsubjectList",MainsubjectList);
				mav.addObject("MainboardList",MainboardList);
				mav.setViewName("main/index.tiles1");
			}
		}
		else {
			
			MainboardList =service.MainboardView();
			
			
			mav.addObject("MainsubjectList",MainsubjectList);
			mav.addObject("MainboardList",MainboardList);
			mav.setViewName("main/index.tiles1");
			
			
		}
	
	
		
	
		// /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
		
		return mav;
	}

	
	//로그인 페이지 요청
	@RequestMapping(value="/login.sam")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login/loginform.tiles2");
		return mav;
	}
	//===== 등록 작업======
	
	@RequestMapping(value="/admin/memberRegister.sam")
	public ModelAndView memberRegister(ModelAndView mav) {
		mav.setViewName("admin/memberRegister.tiles3");
		return mav;
	}
	
	
// === #41. 로그인 처리하기 === // 
	@RequestMapping(value="/loginEnd.sam", method= {RequestMethod.POST})
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pwd", Sha256.encrypt(pwd));
		
		PersonVO loginuser = service.getLoginStudent(paraMap);
		
		if(loginuser == null) { // 로그인 실패시
			
			String message = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
			//  /WEB-INF/views/msg.jsp 파일을 생성한다.
		}
		
		else { // 아이디와 암호가 존재하는 경우
			
			HttpSession session = request.getSession();
			// 메모리에 생성되어져 있는 session을 불러오는 것이다.
			
			session.setAttribute("loginuser", loginuser);
			String goBackURL = (String) session.getAttribute("goBackURL");
			// 예를 들면 goBackURL 은  shop/prodView.up?pnum=66 이거나
			// 또는 null 이다.
			
			// 막바로 페이지 이동을 시킨다. 
			if(goBackURL != null) {
				mav.setViewName("redirect:/"+goBackURL);
				session.removeAttribute("goBackURL"); // 세션에서 반드시 제거해주어야 한다.
			}
			else {
				mav.setViewName("redirect:/index.sam");
			}
			// session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
			
					
		}
	
		return mav;
	}
	
	// === #50. 로그아웃 처리하기 === // 
	@RequestMapping(value="/logout.sam")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		String message = "로그아웃 되었습니다.";
		String loc = request.getContextPath()+"/index.sam";
		
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	//=== 아이디 찾기 페이지 요청===//
	@RequestMapping(value="/idFind.sam")
	public ModelAndView idFind(ModelAndView mav, HttpServletRequest request) {	
		mav.setViewName("member/idFind");
		return mav;
	}
	//=== 아이디 찾기 실행===//
	@RequestMapping(value="/idFindEnd.sam",method=RequestMethod.POST)
	public ModelAndView idFindEnd(ModelAndView mav, HttpServletRequest request) {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("name", name);
		paraMap.put("email", email);
		
		PersonVO idFind = service.idFind(paraMap);
		
		if(idFind == null) { //존재하지 않는 경우
			mav.addObject("check", 1);
			mav.setViewName("member/idFind");
	
		}
		
		else { // 존재하는 경우
			
			mav.addObject("check", 0);
			mav.addObject("perno",idFind.getPerno());
			// session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
			mav.setViewName("member/idFind");
					
		}
	
		return mav;
	}
	//=== 비밀번호 찾기 페이지 요청===//
		@RequestMapping(value="/pwdFind.sam")
		public ModelAndView pwdFind(ModelAndView mav, HttpServletRequest request) {
			
			mav.setViewName("member/pwdFind");
			// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
			
			return mav;
		}
		//=== 비밀번호 찾기 실행===//
		@RequestMapping(value="/pwdFindgo.sam",method=RequestMethod.POST)
		public ModelAndView pwdFindgo(ModelAndView mav, HttpServletRequest request,PersonVO pvo) {
			
			
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
			Map<String,String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("email", email);
			int n =  service.isUserExist(paraMap);
			
			boolean sendMailSuccess = true; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
				if(n==1) {

					// 회원으로 존재하는 경우
					Random rnd = new Random();
					String certificationCode = "";
					char randchar = ' ';
					for(int i=0; i<5; i++) {
						randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
						certificationCode += randchar;
					}// end of for--------------------------------
					
					int randnum = 0;
					for(int i=0; i<7; i++) {
						randnum = rnd.nextInt(9 - 0 + 1) + 0;
						certificationCode += randnum;
					}// end of for--------------------------------
					
				
					GoogleMail mail = new GoogleMail();
					
					try {
						mail.sendmail(email, certificationCode);
						//세션불러오기
						HttpSession session =request.getSession();
						session.setAttribute("certificationCode", certificationCode);
						// 발급한 인증코드를 세션에 저장시킴.
					
					} catch (Exception e) {
						// 메일 전송이 실패한 경우
						e.printStackTrace();
						sendMailSuccess = false; // 메일 전송 실패했음을 기록함.
					}
					
					
					mav.addObject("userid",userid);
					mav.addObject("check", 0);
					mav.addObject("n", n);
					mav.addObject("sendMailSuccess",sendMailSuccess);
					
					mav.setViewName("member/pwdFind");
				}
				else {
					mav.addObject("check", 1);
					mav.setViewName("member/pwdFind");
				}
	
		
			return mav;
		}
		//=== 비밀번호 찾기 인증===//
		@RequestMapping(value="/verifyCertification.sam",method=RequestMethod.POST)
		public ModelAndView verifyCertification(ModelAndView mav, HttpServletRequest request) {
			
			String userid = request.getParameter("userid");
			String userCertificationCode = request.getParameter("userCertificationCode");
			
			HttpSession session = request.getSession(); // 세션불러오기 
			String certificationCode = (String) session.getAttribute("certificationCode");  // 세션에 저장된 인증코드 가져오기 
			
			String message = "";
			String loc = "";
			
			if( certificationCode.equals(userCertificationCode) ) {
				message = "인증성공 되었습니다.";
				mav.addObject("in",1);
				mav.addObject("userid",userid);
				mav.setViewName("msg");
				mav.setViewName("member/pwdFind");
				
			}
			else {
				message = "발급된 인증코드가 아닙니다. 인증코드를 다시 발급받으세요!!";
				loc = request.getContextPath()+"/pwdFind.sam";
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				mav.setViewName("msg");
			}
			
			
			
			// !!!! 중요 !!!! //
			// !!!! 세션에 저장된 인증코드 삭제하기 !!!! // 
			session.removeAttribute("certificationCode");
			return mav;
			
		}
		@RequestMapping(value="/pwdUpdateEnd.sam",method=RequestMethod.POST)
		public ModelAndView pwdUpdateEnd(ModelAndView mav, HttpServletRequest request) {
		
			String userid = request.getParameter("userid");
				String pwd = request.getParameter("pwd");
				
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("userid",userid);
				paraMap.put("pwd", Sha256.encrypt(pwd));
				//System.out.println(userid);
				int n = service.pwdUpdate(paraMap);
		
				if(n == 0) {
					mav.addObject("message", "암호 변경 오류 발생!");
					mav.setViewName("msg");
					
				}
				else {
					mav.addObject("message", "암호변경 성공!!");
					mav.addObject("n", 1);
					
				}
			
			mav.addObject("userid",userid);
			mav.setViewName("msg");
			
			return mav;
		}	
		//=== 회원등록 페이지 요청===//
		@RequestMapping(value="/personRegister.sam")
		public ModelAndView personRegister(ModelAndView mav, HttpServletRequest request) {
			
			mav.setViewName("member/personRegister");
			
			return mav;
		}
		
		//=== 회원등록 실행===//
			@RequestMapping(value="/personRegisterGo.sam",method=RequestMethod.POST)
			public ModelAndView personRegisterGo(ModelAndView mav, HttpServletRequest request,PersonVO pvo) {
				
				
				String userid = request.getParameter("userid");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				// /WEB-INF/views/tiles2/login/loginform.jsp 파일을 생성한다.
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("userid", userid);
				paraMap.put("name", name);
				paraMap.put("email", email);
				int regn =  service.isUserExist2(paraMap);
				
				boolean sendMailSuccess = true; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
					if(regn==1) {

						// 회원으로 존재하는 경우
						Random rnd = new Random();
						String certificationCode = "";
						char randchar = ' ';
						for(int i=0; i<5; i++) {
							randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
							certificationCode += randchar;
						}// end of for--------------------------------
						
						int randnum = 0;
						for(int i=0; i<7; i++) {
							randnum = rnd.nextInt(9 - 0 + 1) + 0;
							certificationCode += randnum;
						}// end of for--------------------------------
						
					
						GoogleMail mail = new GoogleMail();
						
						try {
							mail.sendmail(email, certificationCode);
							//세션불러오기
							HttpSession session =request.getSession();
							session.setAttribute("certificationCode", certificationCode);
							// 발급한 인증코드를 세션에 저장시킴.
						
						} catch (Exception e) {
							// 메일 전송이 실패한 경우
							e.printStackTrace();
							sendMailSuccess = false; // 메일 전송 실패했음을 기록함.
						}
						
						
						mav.addObject("userid",userid);
						mav.addObject("check", 0);
						mav.addObject("n", regn);
						mav.addObject("sendMailSuccess",sendMailSuccess);
						
						mav.setViewName("member/personRegister");
					}
					else {
						mav.addObject("check", 1);
						mav.setViewName("member/personRegister");
					}
		
			
				return mav;
			}
			
			//============   인기게시물 작업  시작 ==============
			// === #128. 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //

			@ResponseBody
			@RequestMapping(value="/MainBoardList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
			public String commentList(HttpServletRequest request) {
				
	
				String currentShowPageNo =request.getParameter("currentShowPageNo");
				
				if(currentShowPageNo == null) {
					currentShowPageNo = "1";
				}
				int sizePerPage = 4;// 한 페이지당 5개의 댓글을 보여줄 것임.
				int startRno = (( Integer.parseInt(currentShowPageNo) - 1 ) * sizePerPage) + 1;
			      int endRno = startRno + sizePerPage - 1; 
				
			      Map<String,String>paraMap = new HashedMap<>();
			      paraMap.put("startRno", String.valueOf(startRno));
			      paraMap.put("endRno", String.valueOf(endRno));

				List<Main_index_BoardVO> MainboardList = service.getboardistPaging(paraMap);
				
				JSONArray jsonArr = new JSONArray(); // []
				
				if(MainboardList != null) {
					for(Main_index_BoardVO mbvo : MainboardList) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("seq", mbvo.getSeq());
						jsonObj.put("categoryno", mbvo.getCategoryno());
						jsonObj.put("subject", mbvo.getSubject());
						jsonObj.put("name", mbvo.getName());
						jsonObj.put("commentCount", mbvo.getCommentCount());
						jsonObj.put("namecheck", mbvo.getNamecheck());
						jsonObj.put("good", mbvo.getGood());
						
						jsonArr.put(jsonObj);
						//System.out.println(jsonArr);
					}
				}
				
				return jsonArr.toString(); 

			}
			
			
			// === #132. 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
			@ResponseBody
			@RequestMapping(value="/getboardTotalPage.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
			public String getCommentTotalPage(HttpServletRequest request) {
				String parentSeq = request.getParameter("parentSeq");
				String sizePerPage = request.getParameter("sizePerPage");
				
				Map<String,String>paraMap = new HashedMap<>();
			      paraMap.put("parentSeq", parentSeq);
			      paraMap.put("sizePerPage", sizePerPage);
			      
			      // 원글 글번호(parentSeq)에 해당하는 댓글의 총페이지수 를 알아오기
			      int totalPage = service.getboardTotalPage(paraMap);
			      
			      JSONObject jsonObj = new JSONObject();
			      jsonObj.put("totalPage", totalPage);
			      
			      //System.out.println("~~~~확인용:"+jsonObj.toString());
				return jsonObj.toString();
			}
			//============   인기게시물 작업  끝==============
			
			
			//============   캘린더 작업 ==============
			
			//일정 추가하기 페이지 요청
			@RequestMapping(value="/schedulePopup.sam")
			public ModelAndView requiredLogin_schedulePopup(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
				
			mav.setViewName("calendar/schedulePopup");
			
			return mav;
			}
			
			
			// === 일정 추가 하기 === // 
			@RequestMapping(value="/scheduleEnd.sam", method= {RequestMethod.POST})
			public ModelAndView scheduleEnd(ModelAndView mav, ScheduleVO svo) {
				int n = service.scheduleAdd(svo); 
				
				if(n==1) {
					mav.addObject("message", "일정이 등록되었습니다.");
				}
				mav.setViewName("msg");
				return mav;
			}
			
			// === 일정가져오기 === // 
			@ResponseBody
			@RequestMapping(value="/scheduleView.sam",produces="text/plain;charset=UTF-8")
			public String scheduleView(HttpServletRequest request) {
				
				String perno = request.getParameter("perno"); 
				List<Map<String,String>>scheduleList = service.scheduleView(perno);
				
				
				JsonArray jsonArr = new JsonArray();
				for(Map<String,String>map:scheduleList) {
					
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("schno",map.get("schno"));
					jsonObj.addProperty("perno",map.get("fk_perno"));
					jsonObj.addProperty("calsubject",map.get("calsubject"));
					jsonObj.addProperty("startDate",map.get("startDate"));
					jsonObj.addProperty("endDate",map.get("endDate"));
					jsonObj.addProperty("color",map.get("color"));
					jsonObj.addProperty("memo",map.get("memo"));
					jsonArr.add(jsonObj);
				}//end of for(Map<String,String>map:deptnamePercentageList){}-----
				
				//System.out.println(jsonArr);
				return jsonArr.toString();
			
			}
			
			// === 일정 수정가져오기 === // 
			@ResponseBody
			@RequestMapping(value="/scheduleEdit.sam",produces="text/plain;charset=UTF-8")
			public String scheduleEdit(HttpServletRequest request) {
				
				String perno = request.getParameter("perno"); 
				String schno = request.getParameter("schno");
				
				Map<String,String>paraMap = new HashedMap<>();
				 paraMap.put("perno", perno);
			      paraMap.put("schno", schno);
			  
				
				ScheduleVO scvo = service.scheduleEdit(paraMap);
				
				JSONObject jsonObj = new JSONObject();
						jsonObj.put("schno",scvo.getSchno());
						jsonObj.put("title",scvo.getCalsubject());
						jsonObj.put("perno", scvo.getFk_perno());
						jsonObj.put("startDate", scvo.getStartDate());
						jsonObj.put("endDate", scvo.getEndDate());
						jsonObj.put("color", scvo.getColor());
						jsonObj.put("memo", scvo.getMemo());
					
				
				return jsonObj.toString(); 

			
			}
			
			
			//일정수정 팝업페이지 요청
			@RequestMapping(value="/scheduleEditPopup.sam")
			public ModelAndView requiredLogin_scheduleEditPopup(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
				String perno = request.getParameter("perno"); 	
				String schno = request.getParameter("schno");
				
				Map<String,String>paraMap = new HashedMap<>();
				 paraMap.put("perno", perno);
				 paraMap.put("schno", schno);
				 
				ScheduleVO scvo = service.scheduleEdit(paraMap);
				
				mav.addObject("schno",scvo.getSchno());
				mav.addObject("title",scvo.getCalsubject());
				mav.addObject("perno", scvo.getFk_perno());
				mav.addObject("startDate",scvo.getStartDate());
				mav.addObject("endDate", scvo.getEndDate());
				mav.addObject("color", scvo.getColor());
				mav.addObject("memo", scvo.getMemo());
				
				mav.setViewName("calendar/scheduleEditPopup");
				
				return mav;
			}
	
			// === #71. 글수정 페이지 요청 === //
			@RequestMapping(value="/scheduleEditEnd.sam",method= {RequestMethod.POST})
			public ModelAndView requiredLogin_scheduleEditEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,ScheduleVO scvo) {
				
				int n = service.scheduleEditEnd(scvo);
				// n 이 1 이라면 정상적으로 변경됨.
				// n 이 0 이라면 글수정에 필요한 글암호가 틀린경우 
				
				if(n == 0) {
					mav.addObject("message", "암호가 일치하지 않아 글 수정이 불가합니다.");
				}
				else {
					mav.addObject("message", "글수정 성공!!");
				}
				
				mav.setViewName("msg");
				
				return mav;
			}
			// === #76. 글삭제 페이지 요청 === //
			@RequestMapping(value="/schDelEnd.sam",method= {RequestMethod.POST})
			public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
				String perno = request.getParameter("fk_perno"); 	
				String schno = request.getParameter("schno");
				System.out.println("확인용~~~~~"+perno);
				Map<String,String>paraMap = new HashedMap<>();
				 paraMap.put("perno", perno);
				 paraMap.put("schno", schno);
				 
				
				 int n = service.scheduledel(paraMap);
				 if(n == 0) {
						mav.addObject("message", "글 삭제 실패!");
					}
					else {
						mav.addObject("message", "글삭제성공!!");
					}
					
					mav.setViewName("msg");
				
				
				return mav;
			}
			//============   캘린더 작업  끝==============
			
			//===== 관리자 수업 목록======
			@RequestMapping(value="/admin/adminSubjectList.sam")
			public ModelAndView subjectList(HttpServletRequest request,ModelAndView mav) {
				List<MainSubjectVO> adminsubjectList =null;
				
				String searchType = request.getParameter("searchType"); 
				String searchWord = request.getParameter("searchWord");
				String str_currentShowPageNo = request.getParameter("currentShowPageNo");
				
				if(searchType == null || (!"content".equals(searchType) && !"name".equals(searchType) && !"subname".equals(searchType)) ) {
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
				
			
				totalCount = service.getSubjectTotal(paraMap);
				//System.out.println("~~~~ 확인용 totalCount : " + totalCount);
				
				
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
				
				adminsubjectList = service.getsubjectList(paraMap);
			
				// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
				if(!"".equals(searchType) && !"".equals(searchWord)) {
					mav.addObject("paraMap", paraMap);
				}
				
				
				// === #121. 페이지바 만들기 === //
				int blockSize = 10;
				int loop = 1;
				int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
				String pageBar = "<ul style='list-style: none;'>";
				String url = "";
				
				// === [맨처음][이전] 만들기 === 
				if(pageNo != 1) {
					pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
					pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
				}
				
				while( !(loop > blockSize || pageNo > totalPage) ) {
					
					if(pageNo == currentShowPageNo) {
						pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
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
				mav.addObject("adminsubjectList",adminsubjectList);
				mav.setViewName("admin/adminSubjectList.tiles3");
				return mav;
			}
			
			// ===검색어 입력시 자동글 완성하기 3 === //
			@ResponseBody
			@RequestMapping(value="/admin/subjectwordSearchShow.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
			public String wordSearchShow(HttpServletRequest request) {
				
				String searchType = request.getParameter("searchType");
				String searchWord = request.getParameter("searchWord");
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("searchType", searchType);
				paraMap.put("searchWord", searchWord);
				
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

package com.spring.finalproject3.joseungjin.controller;

import java.util.HashMap;







import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		List<MainSubjectVO> MainProsubjectList=null;
		HttpSession session = request.getSession();
		if(session.getAttribute("loginuser") != null) {
			PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
			int userid = loginuser.getPerno();
		
			MainsubjectList = service.Mainsubject(userid);
			MainProsubjectList =service.MainProsubject(userid);
			int ident = loginuser.getIdentity();
			if(ident ==2) {
				mav.setViewName("admin/index.tiles3");
			}
			else {
				MainboardList =service.MainboardView();
				
				mav.addObject("MainProsubjectList",MainProsubjectList);
				mav.addObject("MainsubjectList",MainsubjectList);
				mav.addObject("MainboardList",MainboardList);
				mav.setViewName("main/index.tiles1");
			}
		}
		else {
			
			MainboardList =service.MainboardView();
			
			mav.addObject("MainProsubjectList",MainProsubjectList);
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
	//===== 관리자 등록 작업 시작 ======
	
	@RequestMapping(value="/admin/memberRegister.sam")
	public ModelAndView memberRegister(ModelAndView mav) {
		mav.setViewName("admin/memberRegister.tiles3");
		return mav;
	}
	//====회원 아이디 실시간 조회====
	
	@ResponseBody
	@RequestMapping(value="/admin/memberidCheck.sam", produces="text/plain;charset=UTF-8")
	public String memberidCheck(ModelAndView mav,HttpServletRequest request) {
		
		int perno = Integer.parseInt(request.getParameter("perno"));
		 int n =  service.memberidCheck(perno);
						
		JSONObject jsonObj = new JSONObject();
		if(n==1) {
				jsonObj.put("n",1);
		}
		else {
			jsonObj.put("n",0);
		}
	
		return jsonObj.toString(); 
	
	}
	
	//===관리자 회원 insert===//
	@RequestMapping(value="/admin/memberRegisterend.sam")
	public ModelAndView memberRegisterend(ModelAndView mav, HttpServletRequest request) {
		String perno = request.getParameter("perno");
		String fk_majseq = request.getParameter("fk_majseq");
		String gender = request.getParameter("gender");
		String name = request.getParameter("name");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String identity = request.getParameter("identity");
		String hp1 = request.getParameter("hp1"); 
		String hp2 = request.getParameter("hp2"); 
		String hp3 = request.getParameter("hp3"); 
		String mobile = hp1 + hp2 + hp3;
	
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("perno", perno);
		paraMap.put("fk_majseq", fk_majseq);
		paraMap.put("gender", gender);
		paraMap.put("name", name);
		paraMap.put("birthday", birthday);
		paraMap.put("email", email);
		paraMap.put("mobile", mobile);
		paraMap.put("address", address);
		paraMap.put("identity", identity);
		
		
		int n = service.registerMember(paraMap);
		
		if(n == 0) {
			mav.addObject("message", "회원정보등록 실패 ");
			mav.setViewName("msg");
			
		}
		else {
			mav.addObject("message", "회원정보등록 성공!!");
			String loc = "javascript:history.back()";
			mav.addObject("loc", loc);
		}
		mav.setViewName("msg");
		return mav;
	}
	
	
	
	
	
	//===== 관리자 등록 작업 끝 ======
	
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
			
			 mav.addObject("message", "입력하신 정보가 존재하지 않습니다."); 
			 mav.setViewName("msg");
			
	
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
					 mav.addObject("message", "입력하신 정보가 존재하지 않습니다."); 
					 mav.setViewName("msg");
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
				
				
			
				PersonVO pwdFind = service.pwdFind(paraMap);
				if(pwdFind !=null) {
				if(!pwdFind.getPwd().equals("1234")) {
					mav.addObject("message", "이미 등록한 회원입니다.");
					mav.setViewName("msg");
				}
				
				else {
					System.out.println(pwdFind);
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
					}
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
				if(scvo != null) {
						jsonObj.put("schno",scvo.getSchno());
						jsonObj.put("title",scvo.getCalsubject());
						jsonObj.put("perno", scvo.getFk_perno());
						jsonObj.put("startDate", scvo.getStartDate());
						jsonObj.put("endDate", scvo.getEndDate());
						jsonObj.put("color", scvo.getColor());
						jsonObj.put("memo", scvo.getMemo());
						jsonObj.put("loginYesNo","Yes");
				}
				else {
					jsonObj.put("loginYesNo","No");
				}
				
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
				
				HttpSession session = request.getSession();
				PersonVO loginuser = (PersonVO) session.getAttribute("loginuser");
				
				if(!Integer.toString(loginuser.getPerno()).equals(scvo.getFk_perno()) )  {
					String message = "다른 사용자의 일정 수정이 불가합니다.";
					String loc = "javascript:history.back()";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					mav.setViewName("msg");
				}
				else {
				mav.addObject("schno",scvo.getSchno());
				mav.addObject("title",scvo.getCalsubject());
				mav.addObject("perno", scvo.getFk_perno());
				mav.addObject("startDate",scvo.getStartDate());
				mav.addObject("endDate", scvo.getEndDate());
				mav.addObject("color", scvo.getColor());
				mav.addObject("memo", scvo.getMemo());
				
				mav.setViewName("calendar/scheduleEditPopup");
				}
				return mav;
			}
	
			// === #71. 글수정 페이지 요청 === //
			@RequestMapping(value="/scheduleEditEnd.sam",method= {RequestMethod.POST})
			public ModelAndView requiredLogin_scheduleEditEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,ScheduleVO scvo) {
				
				int n = service.scheduleEditEnd(scvo);
				// n 이 1 이라면 정상적으로 변경됨.
				// n 이 0 이라면 글수정에 필요한 글암호가 틀린경우 
				
				if(n == 0) {
					mav.addObject("message", "일정 수정 오류!!");
				}
				else {
					mav.addObject("message", "일정 수정 성공!!");
				}
				
				mav.setViewName("msg");
				
				return mav;
			}
			// === #76. 글삭제 페이지 요청 === //
			@RequestMapping(value="/schDelEnd.sam",method= {RequestMethod.POST})
			public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
				String perno = request.getParameter("fk_perno"); 	
				String schno = request.getParameter("schno");
				//System.out.println("확인용~~~~~"+perno);
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
			// >>> #191. Excel 파일로 다운받기 예제 <<< // 
			@RequestMapping(value="/admin/downloadExcelFile.sam") 
			  public String downloadExcelFile(HttpServletRequest request, Model model) {
			
				
				List<Map<String,String>> adminExcelsubjectList = service.getExcelsubjectList();
			   // === 조회결과물인 empList 를 가지고 엑셀 시트 생성하기 ===
			      // 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀안에 내용을 넣어주면 된다.
			      SXSSFWorkbook workbook = new SXSSFWorkbook();
			      
			      // 시트생성
			      SXSSFSheet sheet = workbook.createSheet("쌍용대학 수업목록");
			      
			      // 시트 열 너비 설정
			      sheet.setColumnWidth(0, 2000);
			      sheet.setColumnWidth(1, 4000);
			      sheet.setColumnWidth(2, 5000);
			      sheet.setColumnWidth(3, 3000);
			      sheet.setColumnWidth(4, 5000);
			      sheet.setColumnWidth(5, 2000);
			      sheet.setColumnWidth(6, 5000);
			      sheet.setColumnWidth(7, 1000);
			      
			   // 행의 위치를 나타내는 변수 
			      int rowLocation = 0;
			      
				////////////////////////////////////////////////////////////////////////////////////////
				// CellStyle 정렬하기(Alignment)
				// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
				// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
				CellStyle mergeRowStyle = workbook.createCellStyle();
				mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
				mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				// import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
				
				CellStyle headerStyle = workbook.createCellStyle();
				headerStyle.setAlignment(HorizontalAlignment.CENTER);
				headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				
				
				// CellStyle 배경색(ForegroundColor)만들기
				// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
				// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
				mergeRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // IndexedColors.DARK_BLUE.getIndex() 는 색상(남색)의 인덱스값을 리턴시켜준다.  
				mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
				
				headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex() 는 연한노랑의 인덱스값을 리턴시켜준다.  
				headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
				
				// Cell 폰트(Font) 설정하기
				// 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
				// 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
				// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
				Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
				mergeRowFont.setFontName("나눔고딕");
				mergeRowFont.setFontHeight((short)500);
				mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
				mergeRowFont.setBold(true);
				
				mergeRowStyle.setFont(mergeRowFont); 
				
				
				// CellStyle 테두리 Border
				// 테두리는 각 셀마다 상하좌우 모두 설정해준다.
				// setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서 적용한다.
				headerStyle.setBorderTop(BorderStyle.THICK);
				headerStyle.setBorderBottom(BorderStyle.THICK);
				headerStyle.setBorderLeft(BorderStyle.THIN);
				headerStyle.setBorderRight(BorderStyle.THIN);    
			      
				// Cell Merge 셀 병합시키기
		        /* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
		           CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
		        */
		        // 병합할 행 만들기
		        Row mergeRow = sheet.createRow(rowLocation);  // 엑셀에서 행의 시작은 0 부터 시작한다.
		        
		      // 병합할 행에 "우리회사 사원정보" 로 셀을 만들어 셀에 스타일을 주기  
		        for(int i=0; i<8; i++) {
		           Cell cell = mergeRow.createCell(i);
		           cell.setCellStyle(mergeRowStyle);
		           cell.setCellValue("쌍용대학교 수업목록");
		        }
		        
		        // 셀 병합하기
		        sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 7)); // 시작 행, 끝 행, 시작 열, 끝 열 
		        
		        // CellStyle 천단위 쉼표, 금액
		        CellStyle moneyStyle = workbook.createCellStyle();
		        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		        ////////////////////////////////////////////////////////////////////////////////////////////////
		     // 헤더 행 생성
		        Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
		                                                       // ++rowLocation는 전위연산자임. 
		        
		        // 해당 행의 첫번째 열 셀 생성
		        Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0 부터 시작한다.
		        headerCell.setCellValue("학기");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 두번째 열 셀 생성
		        headerCell = headerRow.createCell(1);
		        headerCell.setCellValue("학과");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 세번째 열 셀 생성
		        headerCell = headerRow.createCell(2);
		        headerCell.setCellValue("교번");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 네번째 열 셀 생성
		        headerCell = headerRow.createCell(3);
		        headerCell.setCellValue("교수명");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 다섯번째 열 셀 생성
		        headerCell = headerRow.createCell(4);
		        headerCell.setCellValue("수업명");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 여섯번째 열 셀 생성
		        headerCell = headerRow.createCell(5);
		        headerCell.setCellValue("요일");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 일곱번째 열 셀 생성
		        headerCell = headerRow.createCell(6);
		        headerCell.setCellValue("시간");
		        headerCell.setCellStyle(headerStyle);
		        
		        // 해당 행의 여덟번째 열 셀 생성
		        headerCell = headerRow.createCell(7);
		        headerCell.setCellValue("학점");
		        headerCell.setCellStyle(headerStyle);
		        
		        // HR사원정보 내용에 해당하는 행 및 셀 생성하기
		        Row bodyRow = null;
		        Cell bodyCell = null;
		        
		        for(int i=0; i<adminExcelsubjectList.size(); i++) {
		           
		            Map<String,String> adminsubjectMap =  adminExcelsubjectList.get(i);
		           
		           // 행생성
		           bodyRow = sheet.createRow(i + (rowLocation+1) );
		           
		           // 데이터 부서번호 표시 
		           bodyCell = bodyRow.createCell(0);
		           bodyCell.setCellValue(Integer.parseInt(adminsubjectMap.get("semeter")));
		           
		           // 데이터 부서명 표시 
		           bodyCell = bodyRow.createCell(1);
		           bodyCell.setCellValue(adminsubjectMap.get("content"));
		           
		           // 데이터 사원번호 표시 
		           bodyCell = bodyRow.createCell(2);
		           bodyCell.setCellValue(adminsubjectMap.get("fk_perno"));
		           
		           // 데이터 사원명 표시 
		           bodyCell = bodyRow.createCell(3);
		           bodyCell.setCellValue(adminsubjectMap.get("name"));
		           
		           // 데이터 입사일자 표시 
		           bodyCell = bodyRow.createCell(4);
		           bodyCell.setCellValue(adminsubjectMap.get("subname"));
		           
		           // 데이터 월급 표시 
		           bodyCell = bodyRow.createCell(5);
		           bodyCell.setCellValue(adminsubjectMap.get("day"));
		           
		           // 데이터 성별 표시 
		           bodyCell = bodyRow.createCell(6);
		           bodyCell.setCellValue(adminsubjectMap.get("time"));
		           
		           // 데이터 나이 표시 
		           bodyCell = bodyRow.createCell(7);
		           bodyCell.setCellValue(Integer.parseInt(adminsubjectMap.get("credit")));
		           
		        }// end of for----------------------------------------
		        
		        model.addAttribute("locale", Locale.KOREA);
		        model.addAttribute("workbook", workbook);
		        model.addAttribute("workbookName", "쌍용대학 수업목록");
		        
		      return "excelDownloadView";
			}// end of public String downloadExcelFile(HttpServletRequest request, Model model)------------- 
			
			
			
			// >>> 차트그리기(Ajax) 부서명별  인원수 및 퍼센티지 가져오기 <<< //
			@ResponseBody
			@RequestMapping(value="/chart/bestBoard.sam", produces="text/plain;charset=UTF-8")
			public String bestBoard(HttpServletRequest requset) {
				
				List<Map<String,String>> bestBoardList= service.getbestBoard();
				
				
				JsonArray jsonArr = new JsonArray();
				for(Map<String,String> map: bestBoardList) {
					
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("categoryno",map.get("categoryno"));
					jsonObj.addProperty("seq",map.get("seq"));
					jsonObj.addProperty("subject",map.get("subject"));
					jsonObj.addProperty("good",map.get("good"));
					
					jsonArr.add(jsonObj);
				}//end of for(Map<String,String>map:deptnamePercentageList){}-----
				//System.out.println(jsonArr);
				return jsonArr.toString(); 
			}
			
			
}

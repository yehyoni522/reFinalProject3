package com.spring.finalproject3.joseungjin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
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


import com.spring.finalproject3.common.Sha256;
import com.spring.finalproject3.joseungjin.mail.GoogleMail;
import com.spring.finalproject3.joseungjin.model.InterPersonDAO;
import com.spring.finalproject3.joseungjin.model.MainSubjectVO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonDAO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.service.InterMemberService;


@Controller
public class joseungjin_controller {
	
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
		}
		
		MainboardList =service.MainboardView();
		
		
		mav.addObject("MainsubjectList",MainsubjectList);
		mav.addObject("MainboardList",MainboardList);
		mav.setViewName("main/index.tiles1");
		// /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
		
		return mav;
	}

	
	//로그인 페이지 요청
	@RequestMapping(value="/login.sam")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login/loginform.tiles2");
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
			// === #128. 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //

			@ResponseBody
			@RequestMapping(value="/MainBoardList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
			public String commentList(HttpServletRequest request) {
				
	
				String currentShowPageNo =request.getParameter("currentShowPageNo");
				
				if(currentShowPageNo == null) {
					currentShowPageNo = "1";
				}
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
			
		
		
	
	
}

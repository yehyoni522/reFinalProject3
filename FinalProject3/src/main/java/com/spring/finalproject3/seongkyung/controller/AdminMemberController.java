package com.spring.finalproject3.seongkyung.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.MyUtil;
import com.spring.finalproject3.seongkyung.model.AttendanceVO;
import com.spring.finalproject3.seongkyung.model.InputatdcVO;
import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.model.StdtansVO;
import com.spring.finalproject3.seongkyung.model.SubjectVO;
import com.spring.finalproject3.seongkyung.service.InteradminMemberService;


@Component
@Controller
public class AdminMemberController {
	
	@Autowired
	private InteradminMemberService service;
	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@RequestMapping(value="/admin/student.sam")
	public ModelAndView admin_student(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		//System.out.println(searchType);
		//System.out.println(searchWord);
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 10; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
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
	    
	    personList = service.getAdminStudent(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
		
		mav.setViewName("adminmember/adminstudent.tiles3");
		
		return mav;
	}
	
	// 관리자 학생 상세정보페이지
	@RequestMapping(value="/admin/studentinfo.sam")
	public ModelAndView admin_studentinfo(ModelAndView mav, HttpServletRequest request) {
		
		String perno = request.getParameter("perno");
		
		// System.out.println("1 :" +perno);
		
		String gobackURL = request.getParameter("gobackURL");
		
		mav.addObject("gobackURL", gobackURL);	
		
		try {
			
			Integer.parseInt(perno);
			
			List<PersonVO> personvo = null;
			
			personvo = service.getStudentView(perno);
			
			// System.out.println("2 :" +personvo);
			
			mav.addObject("personList", personvo);
			
		} catch(NumberFormatException e) {
			
		}	
		
		mav.setViewName("adminmember/adminstudentinfo.tiles3");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/professor.sam")
	public ModelAndView admin_professor(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 2; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getProfessorTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
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
	    
	    personList = service.getAdminProfessor(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
			
		mav.setViewName("adminmember/adminprofessor.tiles3");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/blacklist.sam")
	public ModelAndView admin_blacklist(ModelAndView mav, HttpServletRequest request) {
		
		List<Map<String, String>> personList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"email".equals(searchType) && !"name".equals(searchType) && !"content".equals(searchType))) {
			searchType = "";
		}
		if(searchWord == null || ("".equals(searchWord) || searchWord.trim().isEmpty())) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0; 		// 총 게시물 건수
		int sizePerPage = 2; 		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바) 
		
		int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
	    
	    totalCount = service.getTotalCount(paraMap);  // 총 학생 수(totalCount)
	    
	    // System.out.println("totalCount : " + totalCount);
		
	    totalPage = (int) Math.ceil((double)totalCount/sizePerPage);
	    
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
	    
	    personList = service.getAdminStudent(paraMap);
	    
		// 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === #121. 페이지바 만들기 === //
		
		int blockSize = 5;
		
		int loop = 1;
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;	
		
		String pageBar = "<ul style='list-style:none;'>";
		
		String url = "student.sam";
		
		// === [맨처음][이전] 만들기 ===
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
		}
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}		
			
			loop++;
			pageNo++;
			
		} // end of while() {}
		
		// === [다음][마지막] 만들기 === 
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
		}
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar",pageBar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("gobackURL", gobackURL);
		
		mav.addObject("personList", personList);
		
		mav.setViewName("adminmember/adminblacklist.tiles3");
		
		return mav;
		
	}
	
	
	@RequestMapping(value="/lesson/attendance.sam")
	public ModelAndView lesson_attendance(ModelAndView mav, HttpServletRequest request) {
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		String subno = "";		
		
		HttpSession session = request.getSession();
		String subject = (String) session.getAttribute("subject");
		
		SubjectVO subjectvo = service.getAttendancesubno(subject);
		   
		subno = String.valueOf(subjectvo.getSubno());
		
		// System.out.println("subject!!! : " + subject);
		// System.out.println("subno!!!! : " + subno);
		
		paraMap.put("subno", subno);
		paraMap.put("subject", subject);
		
		mav.addObject("subno", subno);
		mav.addObject("subject", subject);
		
		mav.setViewName("lessonadmin/lessonattendance.tiles4");
		
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/lesson/attendancecheckList.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String attendancecheckList(HttpServletRequest request) {
		
	   String fk_subno = "";
	   String fk_perno = request.getParameter("fk_perno");	
	   String subject = request.getParameter("subject");	
	   
	   // System.out.println("subject : " + subject);
	   
	   // 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	   SubjectVO subjectvo = service.getAttendancesubno(subject);
	   
	   fk_subno = String.valueOf(subjectvo.getSubno());
	   
	   
	   List<InputatdcVO> inputatdclist = null;
	   
	   Map<String, String> paraMap = new HashMap<String, String>();  
	   
	   paraMap.put("fk_subno", fk_subno);
	   paraMap.put("fk_perno", fk_perno);
	   
	   // System.out.println("subno : " + subno);
	   // System.out.println("fk_perno : " + fk_perno);
	   
	   // 위의 subject 와 fk_perno 찍히는 거 확인.. subject 로 subno 검색해옴
	   
	   // 접속한 학생의 지금 까지의 출석 상태 를 보여준다.
	   inputatdclist = service.getStudentCheckSign(paraMap);
	   
	   JSONArray jsonArr = new JSONArray();
	   
	   // System.out.println(" 11 ");
	   
	   if(inputatdclist != null) {
		   
		   // System.out.println(" 2 ");  
		   
		   for(InputatdcVO ivo:inputatdclist) {			  
			   
			   JSONObject jsonObj = new JSONObject();
			   // System.out.println("ivo.getInputweekno() : " + ivo.getInputweekno());
			  //  System.out.println("ivo.getInputatdcdate() :" + ivo.getInputatdcdate());
			   
				   jsonObj.put("inputweekno", ivo.getInputweekno());
				   jsonObj.put("inputatdcdate", ivo.getInputatdcdate());
				   jsonObj.put("inputatdcstatus", ivo.getInputatdcstatus());

			   
			   jsonArr.put(jsonObj);
			   		   
		   }
		   
	   }
   		
		return jsonArr.toString();
	}
	
	
    // 출석시작을 눌러서 modal창이 띄어짐
	@RequestMapping(value="/lesson/studentmodal.sam") 
    public String lesson_studentmodal(HttpServletRequest request) {
       
	   String fk_perno = request.getParameter("fk_perno");	

       request.setAttribute("fk_perno", fk_perno);
       
       return "tiles4/lessonadmin/studentmodal";
       
   }	
	
	@ResponseBody
	@RequestMapping(value="/lesson/studentsign.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String studentsign(HttpServletRequest request) {
		
		String randomsign = request.getParameter("randomsign");
		String fk_subno = "";		
		String subject = request.getParameter("subject");
		String fk_perno = request.getParameter("fk_perno");			
		
		// 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	    SubjectVO subjectvo = service.getAttendancesubno(subject);
	   
	    fk_subno = String.valueOf(subjectvo.getSubno());
		
		System.out.println("randomsign!! : " + randomsign);
		
		System.out.println("fk_subno!! : " + fk_subno);
		System.out.println("fk_perno!! : " + fk_perno);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		paraMap.put("randomsign", randomsign);
	//	paraMap.put("fk_subno", String.valueOf(fk_subno));
		paraMap.put("fk_subno", fk_subno);
		paraMap.put("fk_perno", fk_perno);
		
		// 신호의 랜덤번호를 입력한 후 그 번호를 가지고 존재하는지 select 한 다음 출력신호 seq 를 가져와서 그 번호로 insert 한다.
		
		System.out.println("");
		
		int n = service.addstudentsign(paraMap);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("n", n);
		
		return jsonObj.toString();
	}
	
	
	@RequestMapping(value="/lesson/attendanceadmin.sam")
	public ModelAndView lesson_attendanceadmin(ModelAndView mav, HttpServletRequest request) {	
		
		List<AttendanceVO> attendanceList = null;	
		
		Map<String, String> paraMap = new HashMap<String, String>();
	
		String subno = request.getParameter("fk_subno");
		subno="1000";	////////////////////*** 과목번호 임의로 넣음 ***////////////////////////////
		paraMap.put("subno", subno);
		
		// select 에 넣을 출석신호를 보낸 날짜 List
		attendanceList = service.getattendanceList(paraMap);
		
		mav.addObject("subno", subno);
		mav.addObject("attendanceList", attendanceList);
		
		mav.setViewName("lessonadmin/lessonatdcadmin.tiles4");
		
		
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/lesson/studentsignList.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String studentsignList(HttpServletRequest request) {
		
		List<Map<String, String>> attendanceList = null;	
		
		String subno = request.getParameter("sunbo");
		String attendancedate = request.getParameter("attendancedate");	
		
		subno = "1000"; 
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		// System.out.println("fk_subno !!!: " + fk_subno);
		// System.out.println("fk_perno !!!: " + fk_perno);
		
		// System.out.println("attendancedate : " + attendancedate);
		
		paraMap.put("subno", subno);
		
		if( attendancedate != null && !"".equals(attendancedate)) {
			paraMap.put("attendancedate", attendancedate);
			
		}		
		
		// select 태그의 변화에 따라 해당 날짜에 출석한 학생들의 리스트를 알려준다.	
		attendanceList = service.studentsignList(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		for(Map<String,String>map:attendanceList) {

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name",map.get("name"));
			jsonObj.put("perno",map.get("perno"));
			jsonObj.put("inputatdcdate",map.get("inputatdcdate"));
			
			jsonArr.put(jsonObj);
		}//end of for(Map<String,String>map:deptnamePercentageList){}-----
		
		//System.out.println(jsonArr);
		return jsonArr.toString();
	}
	
	
    // 출석시작을 눌러서 modal창이 띄어짐
	@RequestMapping(value="/lesson/attendancemodal.sam") 
    public String lesson_attendancemodal(HttpServletRequest request) {
       
	   String fk_subno = request.getParameter("fk_subno");
	   String fk_perno = request.getParameter("fk_perno");	
       
	   // System.out.println("fk_subno @@@: " + fk_subno);
	   // System.out.println("fk_perno @@@: " + fk_perno);
	   
       request.setAttribute("fk_subno", fk_subno);
       request.setAttribute("fk_perno", fk_perno);
       
       return "tiles4/lessonadmin/attendancemodal";
       
   }
	
		
	@ResponseBody
	@RequestMapping(value="/lesson/attendancesign.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String attendancesign(HttpServletRequest request) {
		
		AttendanceVO attendancevo = null;
		
		String fk_perno = request.getParameter("fk_perno");	
		
		String fk_subno = "";		
		
		HttpSession session = request.getSession();
		String subject = (String) session.getAttribute("subject");
		
		SubjectVO subjectvo = service.getAttendancesubno(subject);
		   
		fk_subno = String.valueOf(subjectvo.getSubno());
		
		// System.out.println("fk_subno !!!: " + fk_subno);
		// System.out.println("fk_perno !!!: " + fk_perno);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		paraMap.put("fk_subno", fk_subno);
		paraMap.put("fk_perno", fk_perno);
		
		// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다.
		attendancevo = service.addattendancesign(paraMap);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("randomsign", attendancevo.getRandomsign());
		
		return jsonObj.toString();
	}
	
	
	@RequestMapping(value="/lesson/quizlist.sam")
	public ModelAndView lesson_quizlist(ModelAndView mav, HttpServletRequest request) {	
		
		List<Map<String, String>> quizvoList = null;
		
		Map<String, String> paraMap = new HashMap<String, String>();		

		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
				
		String subno = "";		
		
		HttpSession session = request.getSession();
		String subject = (String) session.getAttribute("subject");
		
		SubjectVO subjectvo = service.getAttendancesubno(subject);
		   
		subno = String.valueOf(subjectvo.getSubno());
		
		// System.out.println("subject!!! : " + subject);
		// System.out.println("subno!!!! : " + subno);
		
		paraMap.put("subno", subno);
		paraMap.put("subject", subject);
		
		//페이징처리
		int totalCount = 0;         // 총 게시물 건수
		int sizePerPage = 1;        // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	    int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
	       
	    int startRno = 0;           // 시작 행번호
	    int endRno = 0;             // 끝 행번호 
		
	    // 총 쪽지시험 수(totalCount)
	    totalCount = service.getTotalQuiz(paraMap); 
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
		quizvoList = service.quizvoListSearchWithPaging(paraMap);
	    
		
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
		
		mav.addObject("subno",subno);
		mav.addObject("pageBar",pageBar);	    	
    	mav.addObject("totalCount",totalCount);
    	mav.addObject("quizvoList", quizvoList);
		
		
		mav.setViewName("lessonadmin/lessonquizlist.tiles4");
		
		return mav;
	}
	
	
	@RequestMapping(value="/lesson/quizView.sam")
	public ModelAndView lesson_quizView(ModelAndView mav, HttpServletRequest request) {	
		
		SubjectVO subjectvo = null;
		
		List<QuestionVO> questionList = null;
		
		String quizname = request.getParameter("quizname");
		
		// System.out.println("확인용 quizname !! : " + quizname);
		
		// 받아온 시험명으로 과목명 검색하기
		subjectvo = service.getSubname(quizname);
		
		// 받아온 시험명으로 문제리스트 검색
		questionList = service.getQuestionList(quizname);
		
		mav.addObject("questionList", questionList);
		mav.addObject("subjectvo", subjectvo);
		mav.addObject("quizname", quizname);
			
		mav.setViewName("lessonadmin/lessonquizView.tiles4");
		
		return mav;
	}
	
	
	@RequestMapping(value="/lesson/quizViewEnd.sam")
	public ModelAndView lesson_quizViewEnd(ModelAndView mav, HttpServletRequest request) {
		
		String quizname = request.getParameter("quizname");
		
		Map<String,String> paraMap = new HashMap<>();
		
		// System.out.println(quizname);
		
		String subno = "";
		
		// 가져온 시험명으로 문제의 총 갯수를 구한다.
		int cnt = service.getQuizTotalCount(quizname);
		
		// 로그인 되어져 있는 유저 아이디로 사람번호를 구한다. ( 현재 로그인 기능 합치지 못해서 임의로 넣음 )
		String perno = request.getParameter("perno");
		
		HttpSession session = request.getSession();
		String subject = (String) session.getAttribute("subject");
		
		SubjectVO subjectvo = service.getAttendancesubno(subject);
		   
		subno = String.valueOf(subjectvo.getSubno());
		
		paraMap.put("subno", subno);
		
		paraMap.put("perno", perno);
		
		paraMap.put("quizname", quizname);
		
		for(int i=0; i<cnt; i++) {
			
			// 문제 번호
			String qzno = request.getParameter("qzno"+i);
			
			// 학생이 고른 답
			String stdtanswer = request.getParameter("questionno"+i);
				
			// System.out.println(qzno);
			// System.out.println(stdtanswer);		
			
			paraMap.put("qzno", qzno);
			paraMap.put("stdtanswer", stdtanswer);
			
			// 이미 시험을 쳤는지 검사한다.
			// StdtansVO stdtansvo = service.getscorecheck(paraMap);
			
				// 시험명으로 일련번호 검색 => 시험명과 문제번호로 문제 일련번호 검색  => 학생 정답 테이블에 넣기 
				int n = service.addStudentAnswer(paraMap);
				
				if(n != 1) {
					
					String message = "시험 정답 전송 실패";
					String loc = "javascript:history.back()";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					mav.setViewName("msg");
					
			}			
		}		
		mav.setViewName("redirect:/lesson/quizlist.sam");
		
		return mav;
	}
	
	@RequestMapping(value="/lesson/addquiz.sam")
	public ModelAndView lesson_addquiz2(ModelAndView mav, HttpServletRequest request) {	
		
		String subno = "";
		
		HttpSession session = request.getSession();
		String subject = (String) session.getAttribute("subject");
		
		SubjectVO subjectvo = service.getAttendancesubno(subject);
		   
		subno = String.valueOf(subjectvo.getSubno());
		
		mav.addObject("subno", subno);
		mav.addObject("subject", subject);
		
		mav.setViewName("lessonadmin/lessonaddquiz.tiles4");
		
		return mav;
	}
	
	
	@RequestMapping(value="/lesson/quizaddEnd.sam", method= {RequestMethod.POST})
	public ModelAndView lesson_quizaddEnd(ModelAndView mav, HttpServletRequest request) {
		
		QuizVO quizvo = null;
		QuestionVO questionvo = null;
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		int cnt = Integer.parseInt(request.getParameter("cnt"));	// 문제 추가를 클린한 횟수	
		// String su 
		
		String quizname = request.getParameter("quizname");
		
		// System.out.println("cnt 확인중 : " + cnt);
		// System.out.println("~~~~~~ quizname 확인중 : " + quizname);
		
		// 시험과목 만들기
		quizvo = service.addquiz(quizname); // 쪽지시험 테이블에 insert 하고 select 해서 일련번호 가져오기
		
		if(quizvo != null) {
		
			////////////////////////////////////////////////////////////////
			
			// System.out.println("~~~~~ 확인용 : quizvo.getQuizname()" + quizvo.getQuizname());
			
			// System.out.println("n : " + n);
			// System.out.println("~~~~~ 확인용 :cnt : " + cnt);
			
			// System.out.println("~~~~~ 확인용 :quizno : " + quizvo.getQuizno());
			String quizno = String.valueOf(quizvo.getQuizno());
			
			////////////////////////////////////////////////////////////////
		
			// 생성된 시험과목에 딸린 문제 만들기 
			for(int i=0; i< cnt; i++) {
			  
			 String qzno = request.getParameter("qzno"+i); 
			 
			 String qzcontent =request.getParameter("qzcontent"+i);
			 String answerfirst = request.getParameter("answerfirst"+i); 
			 String answersecond = request.getParameter("answersecond"+i); 
			 String answerthird = request.getParameter("answerthird"+i); 
			 String answerfourth = request.getParameter("answerfourth"+i); 
			 
			 String quizanswer = request.getParameter("quizanswer"+i); 
			 
			 //System.out.println("answerfirst : " + answerfirst + " answersecond : " + answersecond);
			 //System.out.println("answerthird : " + answerthird + " answerfourth : " + answerfourth);
			 System.out.println("~~~~~~~ 확인용 qzno : " + qzno + " qzcontent : " + qzcontent);
			 //System.out.println("quizanswer : " + quizanswer);		 
			 		 
			 paraMap.put("fk_quizno", quizno);
			 paraMap.put("qzno", qzno);
			 paraMap.put("qzcontent", qzcontent);
			 paraMap.put("answerfirst", answerfirst);
			 paraMap.put("answersecond", answersecond);
			 paraMap.put("answerthird", answerthird);
			 paraMap.put("answerfourth", answerfourth);
			 
			 paraMap.put("quizanswer", quizanswer);
			 	 
			 questionvo = service.addquestion(paraMap); // 쪽지시험_문제  insert
			 
			 paraMap.put("fk_questionno", String.valueOf(questionvo.getQuestionno()));
			 
			 if(questionvo != null) {
					 
				 int m = service.addquizans(paraMap); // 쪽지시험_정답 필드 생성
				 
			 }
			 
			 } // end of for
			 
			 mav.setViewName("redirect:/lesson/quizlist.sam");

		}	 
		mav.setViewName("redirect:/lesson/quizlist.sam");
		
		return mav;
	}

	
	
}

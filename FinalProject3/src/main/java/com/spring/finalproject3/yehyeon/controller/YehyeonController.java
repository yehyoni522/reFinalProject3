package com.spring.finalproject3.yehyeon.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.finalproject3.yehyeon.mail.GoogleMail_yehyeon;
import com.spring.finalproject3.yehyeon.model.BookListVO;
import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.SubjectVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;
import com.spring.finalproject3.yehyeon.service.InterReadingService;


@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
      그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. 
      즉, 여기서 bean의 이름은 boardController 이 된다. 
      여기서는 @Controller 를 사용하므로 @Component 기능이 이미 있으므로 @Component를 명기하지 않아도 BoardController 는 bean 으로 등록되어 스프링컨테이너가 자동적으로 관리해준다. 
*/
@Controller
public class YehyeonController {
	
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterReadingService service;
	
	
	////////////////////////////열람실 예약 페이지 시작///////////////////////////////////
	
	@RequestMapping(value="/reading/index.sam")
	public ModelAndView readingRoomView(ModelAndView mav) {
		
		List<RroomNumVO> rRoomList = service.readingRoomView();
		
		mav.addObject("rRoomList", rRoomList);
		
		List<TimeVO> timeList = service.timeView();
		mav.addObject("timeList", timeList);
		
		mav.setViewName("reading/index.tiles2");
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/reading/selectViewSeat.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String selectViewSeat(HttpServletRequest request) {
		
		String tno = request.getParameter("tno");
		String rname = request.getParameter("rname");
		
		//System.out.println("확인 ~~ tno : " + tno);
		//System.out.println("확인 ~~ rname : " + rname);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("tno", tno);
		paraMap.put("rname", rname);

		
		List<DetailSeatInfoVO> detailList = service.selectViewSeat(paraMap);
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(detailList != null) {
			for(DetailSeatInfoVO dvo : detailList) {
				
				JSONObject jsonObj = new JSONObject(); // {}
				
				jsonObj.put("dsno", dvo.getDsno());
				jsonObj.put("dsname", dvo.getDsname());
				jsonObj.put("dscheck", dvo.getDscheck());
				jsonObj.put("fk_rno", dvo.getFk_rno());
				jsonObj.put("fk_tno", dvo.getFk_tno());
				
				jsonArr.put(jsonObj);
			}
		}
		
		return jsonArr.toString(); 
	}
	
	@ResponseBody
	@RequestMapping(value="/reading/searchSeatInfo.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String searchSeatInfo(HttpServletRequest request) {
		
		String dsno = request.getParameter("dsno");
		
		DetailSeatInfoVO detailvo = service.searchSeatInfo(dsno);
				
		JSONObject jsonObj = new JSONObject(); // {}
		
		jsonObj.put("dsno", detailvo.getDsno());
		jsonObj.put("dsname", detailvo.getDsname());
		jsonObj.put("rname", detailvo.getRoomvo().getRname());
		jsonObj.put("tname", detailvo.getTimevo().getTname());
		
		
		return jsonObj.toString(); 
	}
	
	@RequestMapping(value="/reading/coinPurchaseEnd.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public ModelAndView coinPurchaseEnd(HttpServletRequest request, ModelAndView mav) {
		
		String dsno = request.getParameter("dsno");
		System.out.println("dsno : " + dsno);
		mav.addObject(dsno);
		mav.setViewName("paymentGateway");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/reading/selectRcheck.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String selectRcheck(HttpServletRequest request) {
		
		String perno = request.getParameter("perno");
		
		int n = service.selectRcheck(perno);
			
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);
		
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/reading/updateSeatInfo.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String requiredLogin_updateSeatInfo(HttpServletRequest request, HttpServletResponse response, BookListVO bookvo) {
		
		String dsno = request.getParameter("fk_dsno");
		String perno = request.getParameter("fk_perno");
		
		int n = service.updateDscheck(dsno);
		int m = 0;
		int l = 0;
		if(n == 1) {
			// insert 해주기 위해선 fk_dsno, fk_perno, fk_tno 를 알아야한다.
			m = service.insertBooklist(bookvo);
			
			if(m == 1) {
				l = service.updateRcheck(perno);
			}
			
		}
			
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("l", l);
		
		return jsonObj.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/reading/sendEmail.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String sendEmail(HttpServletRequest request) {
		
		String rname = request.getParameter("rname");
		String tname = request.getParameter("tname");
		String dsname = request.getParameter("dsname");
		String email = request.getParameter("email");
		
		GoogleMail_yehyeon mail = new GoogleMail_yehyeon();
		
		int sendMailSuccess = 0; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
		try {
			mail.sendmail(email, rname, tname, dsname);
		} catch (Exception e) {
			// 메일 전송이 실패한 경우
			e.printStackTrace();
			sendMailSuccess = 1; // 메일 전송 실패했음을 기록함.
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sendMailSuccess", sendMailSuccess);
		
		return jsonObj.toString();
	}
	
	
	////////////////////////////열람실 예약 페이지 끝///////////////////////////////////
	
	
	////////////////////////////관리자 전용 열람실 예약 내역 시작///////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/admin/viewChart.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String viewChart(HttpServletRequest request) {
		
		String bdate = request.getParameter("bdate");
		System.out.println("bdate" + bdate);
		
		List<Map<String,String>> readingroomchart = service.viewChart(bdate); 
		
		JsonArray jsonArr = new JsonArray();
		
		for(Map<String,String> map : readingroomchart) {
			
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("usecheck", map.get("usecheck")); 
			jsonObj.addProperty("cnt1", map.get("cnt1"));
			jsonObj.addProperty("cnt2", map.get("cnt2"));
			jsonObj.addProperty("fk_rno", map.get("fk_rno"));
			jsonObj.addProperty("rname", map.get("rname"));
			
			jsonArr.add(jsonObj);
		}// end of for------------------------------------

	/*	
		Gson gson = new Gson();
		return gson.toJson(jsonArr);
		
		또는
	*/
		return new Gson().toJson(jsonArr);
	}
	
	@RequestMapping(value="/admin/readingRoomBook.sam")
	public ModelAndView requiredLogin_readingRoomBook(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		List<RroomNumVO> rRoomList = service.readingRoomView();
		
		mav.addObject("rRoomList", rRoomList);
		
		List<TimeVO> timeList = service.timeView();
		mav.addObject("timeList", timeList);
		
		mav.setViewName("/admin/readingRoomBook.tiles3");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/selectDateBookList.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String selectDateBookList(HttpServletRequest request) {
		
		
		 String tno = request.getParameter("tno"); 
		 String rno = request.getParameter("rno"); 
		 String bdate = request.getParameter("bdate");
		 
		 System.out.println("~~~ 확인용 tno" + tno);
		 System.out.println("~~~ 확인용 rno" + rno);
		 System.out.println("~~~ 확인용 bdate" + bdate);

		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("tno", tno);
		paraMap.put("rno", rno);
		paraMap.put("bdate", bdate);

		
		//List<BookListVO> bookList = service.selectDateBookList(paraMap);
		List<Map<String, String>> mapList = service.selectDateBookList(paraMap);
		
		///////////////////////////////////////////////////////////////////////////
		/*
			System.out.println("~~~~~ 확인용 ~~~~");
			for(int i=0; i<mapList.size(); i++) {
				System.out.println(mapList.get(i).get("perno"));
				 ~~~~~ 확인용 ~~~~
						1-1
						1-2
						1-3
						1-4
						1-5
						1-6
						1-7
						1-8
						1-9
						1-10
						1-11
						1-12
						1-13
						1-14
						1-15
						1-16
						1-17
						1-18
						1-19
						1-20
				
			}
		 */
		///////////////////////////////////////////////////////////////////////////
		
		JSONArray jsonArr = new JSONArray(); // []
		

		if(mapList != null) { 
			for(Map<String,String> map : mapList) { //dsno, dsname,
				JSONObject jsonObj = new JSONObject(); // {}
				 
				jsonObj.put("dsno", map.get("dsno"));
				jsonObj.put("dsname", map.get("dsname"));
				jsonObj.put("bookcheck", map.get("bookcheck"));
				jsonObj.put("perno", map.get("perno"));
				jsonObj.put("name", map.get("name"));
				 
				jsonArr.put(jsonObj); 
			} 
		}

		
		return jsonArr.toString(); 
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/goDeleteBook.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String goDeleteBook() {
		
		int n = service.goDeleteBook();
		
		JSONObject jsonObj = new JSONObject();  // {}
		jsonObj.put("n", n);  // {"n":1}
		
		String json = jsonObj.toString(); 
		
		return json;   // "{"n":1}"
	}
	
	////////////////////////////관리자 전용 열람실 예약 내역 끝///////////////////////////////////	
	
	////////////////////////////관리자 전용 메인 페이지 시작///////////////////////////////////	
	
	@RequestMapping(value="/admin/index.sam")
	public ModelAndView adminIndex(ModelAndView mav) {
		
		
		mav.setViewName("admin/index.tiles3");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/addSubject.sam")
	public ModelAndView addSubject(ModelAndView mav) {
		
		
		mav.setViewName("admin/addSubject.tiles3");
		
		return mav;
	}
	
	@RequestMapping(value="/admin/subjectList.sam")
	public ModelAndView subjectList(ModelAndView mav) {
		
		
		mav.setViewName("admin/subjectList.tiles3");
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/searchProfessor.sam", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String searchProfessor(HttpServletRequest request) {
		
		String majseq = request.getParameter("majseq");
		List<Map<String, String>> mapList = service.searchProfessor(majseq);
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(mapList != null) { 
			for(Map<String,String> map : mapList) { //dsno, dsname,
				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("perno", map.get("perno"));
				jsonObj.put("name", map.get("name"));
				 
				jsonArr.put(jsonObj); 
			} 
		}
		return jsonArr.toString();   // "{"n":1}"
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/addSubjectEnd.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String addSubjectEnd(HttpServletRequest request, SubjectVO subvo) {
		
		
		int n = service.insertSubject(subvo);
		
		JSONObject jsonObj = new JSONObject();  // {}
		jsonObj.put("n", n);  // {"n":1}
		
		String json = jsonObj.toString(); 
		
		return json;   // "{"n":1}"
	}
	
	////////////////////////////관리자 전용 메인 페이지 시작///////////////////////////////////	
	
}

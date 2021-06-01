package com.spring.finalproject3.yehyeon.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.yehyeon.mail.GoogleMail_yehyeon;
import com.spring.finalproject3.yehyeon.model.BookListVO;
import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
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
	@RequestMapping(value="/reading/updateSeatInfo.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String updateSeatInfo(HttpServletRequest request, BookListVO bookvo) {
		
		String dsno = request.getParameter("fk_dsno");
		
		int n = service.updateDscheck(dsno);
		int m = 0;
		if(n == 1) {
			// insert 해주기 위해선 fk_dsno, fk_perno, fk_tno 를 알아야한다.
			m = service.insertBooklist(bookvo);
		}
			
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("m", m);
		
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
	
	@RequestMapping(value="/admin/readingRoomBook.sam")
	public ModelAndView readingRoomBook(ModelAndView mav) {
		
		mav.setViewName("/admin/readingRoomBook.tiles1");
		
		return mav;
	}
	
}

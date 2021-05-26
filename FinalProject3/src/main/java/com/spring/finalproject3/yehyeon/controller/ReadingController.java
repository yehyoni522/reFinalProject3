package com.spring.finalproject3.yehyeon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
public class ReadingController {
	
	
	@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
	private InterReadingService service;
	
	
	@RequestMapping(value="/reading/index.sam")
	public ModelAndView readingRoomView(ModelAndView mav) {
		
		List<RroomNumVO> rRoomList = service.readingRoomView();
		
		mav.addObject("rRoomList", rRoomList);
		
		List<TimeVO> timeList = service.timeView();
		mav.addObject("timeList", timeList);
		
		mav.setViewName("reading/index.tiles2");
		
		return mav;
	}
	
}

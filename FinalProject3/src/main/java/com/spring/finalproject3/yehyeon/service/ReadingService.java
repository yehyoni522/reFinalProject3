package com.spring.finalproject3.yehyeon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.yehyeon.model.InterReadingDAO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;


@Component
@Service
public class ReadingService implements InterReadingService {

	@Autowired
	private InterReadingDAO dao;
	
	
	
	@Override
	public List<RroomNumVO> readingRoomView() {
		
		List<RroomNumVO> rRoomList = dao.readingRoomView();
		
		return rRoomList;
	}



	@Override
	public List<TimeVO> timeView() {
		
		List<TimeVO> timeList = dao.timeView();
		return timeList;
	}

}

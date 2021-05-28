package com.spring.finalproject3.yehyeon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
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



	@Override
	public List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap) {
		
		List<DetailSeatInfoVO> detailList = dao.selectViewSeat(paraMap);
		
		return detailList;
	}

}

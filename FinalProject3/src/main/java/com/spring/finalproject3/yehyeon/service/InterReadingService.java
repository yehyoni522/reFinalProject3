package com.spring.finalproject3.yehyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;

public interface InterReadingService {

	List<RroomNumVO> readingRoomView(); // 열람실의 고유번호와 이름을 불러온다.

	List<TimeVO> timeView(); // 각 열람실의 시간테이블을 불러온다.

	List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap); // 열람실, 시간 마다의 좌석의 정보를 불러온다.

}

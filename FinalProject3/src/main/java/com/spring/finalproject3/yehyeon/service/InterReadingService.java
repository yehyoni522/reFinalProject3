package com.spring.finalproject3.yehyeon.service;

import java.util.List;

import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;

public interface InterReadingService {

	List<RroomNumVO> readingRoomView(); // 열람실의 고유번호와 이름을 불러온다.

	List<TimeVO> timeView(); // 각 열람실의 시간테이블을 불러온다.

}

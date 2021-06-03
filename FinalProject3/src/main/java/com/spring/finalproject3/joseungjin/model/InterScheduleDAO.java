package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterScheduleDAO {
	//일정추가하기
	int scheduleAdd(ScheduleVO svo);
	//일정가져오기
	List<Map<String, String>> scheduleView(String perno);

}

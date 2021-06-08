package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterScheduleDAO {
	//일정추가하기
	int scheduleAdd(ScheduleVO svo);
	//일정가져오기
	List<Map<String, String>> scheduleView(String perno);
	//일정 수정할 정보가져오기
	ScheduleVO scheduleEdit(Map<String, String> paraMap);
	//일정수정하기
	int scheduleEditEnd(ScheduleVO scvo);
	//일정삭제하기
	int scheduledel(Map<String, String> paraMap);

}

package com.spring.finalproject3.hyeminJang.model;

import java.util.List;
import java.util.Map;


public interface InterMypageDAO {

	
	// 열람실 사용유무 가져오기
	int getRcheck(int perno);

	// 예약된 열람실 내용 가져오기
	List<Map<String, String>> getBooking(int perno);

	// 예약된 열람실 내용 가져오기 (오늘것)
	Map<String, String> getBookingToday(int perno);

	// 입실확인update하기
	int updateUsecheck(String bno);

	// 회원정보수정하기
	int updateInfo(Map<String, String> paraMap);
	

}

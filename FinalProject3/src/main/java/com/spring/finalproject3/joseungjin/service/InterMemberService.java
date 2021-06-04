package com.spring.finalproject3.joseungjin.service;

import java.util.List;

import java.util.Map;

import com.spring.finalproject3.joseungjin.model.MainSubjectVO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.model.ScheduleVO;

public interface InterMemberService {

	PersonVO getLoginStudent(Map<String, String> paraMap);
	
	//아이디 찾기
	PersonVO idFind(Map<String, String> paraMap);
	//비밀번호찾기 회원 확인
	int isUserExist(Map<String, String> paraMap);
	//비밀번호 변경
	int pwdUpdate(Map<String, String> paraMap);
	//비밀번호 찾기
	PersonVO pwdFind(Map<String, String> paraMap);
	
	//회원 등록 정보 확인
	int isUserExist2(Map<String, String> paraMap);

	
	
	//메인 인기 게시글 가져오기
	List<Main_index_BoardVO> MainboardView();
	//총 페이지 알아오깅
	int getboardTotalPage(Map<String, String> paraMap);
	//페이징 처리 
	List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap);
	//수강 중인 목록
	List<MainSubjectVO> Mainsubject(int userid);
	//일정추가하기
	int scheduleAdd(ScheduleVO svo);
	//일정 가져오기
	List<Map<String, String>> scheduleView(String perno);

}

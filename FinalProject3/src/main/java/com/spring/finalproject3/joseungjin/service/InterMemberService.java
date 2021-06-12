package com.spring.finalproject3.joseungjin.service;

import java.util.List;

import java.util.Map;

import com.spring.finalproject3.joseungjin.model.MainSubjectVO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.model.ScheduleVO;
import com.spring.finalproject3.yehyeon.model.SubjectVO;

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
	//관리자 회원 등록 처리
	int registerMember(Map<String, String> paraMap);
	
	
	//메인 인기 게시글 가져오기
	List<Main_index_BoardVO> MainboardView();
	//총 페이지 알아오깅
	int getboardTotalPage(Map<String, String> paraMap);
	//페이징 처리 
	List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap);
	//수강 중인 목록
	List<MainSubjectVO> Mainsubject(int userid);
	//교수 수강 과목 가져오기
	List<MainSubjectVO> MainProsubject(int userid);
	//일정추가하기
	int scheduleAdd(ScheduleVO svo);
	//일정 가져오기
	List<Map<String, String>> scheduleView(String perno);
	//일정 수정할 데이터가져오기
	ScheduleVO scheduleEdit(Map<String, String> paraMap);
	//일정 수정하기
	int scheduleEditEnd(ScheduleVO scvo);
	//일정 삭제하기
	
	int scheduledel(Map<String, String> paraMap);
	//관리자 수업 목록 불러오기
	List<MainSubjectVO> getsubjectList(Map<String, String> paraMap);
	//총 페이지수 알아오기
	int getSubjectTotal(Map<String, String> paraMap);
	//검색 자동글
	List<String> wordSearchShow(Map<String, String> paraMap);
	//엑셀 가져오기
	List<Map<String, String>> getExcelsubjectList();
	
	//하이차트 인기게시판
	List<Map<String, String>> getbestBoard();



}

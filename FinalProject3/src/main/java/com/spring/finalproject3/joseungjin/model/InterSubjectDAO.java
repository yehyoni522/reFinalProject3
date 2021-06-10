package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.yehyeon.model.SubjectVO;

public interface InterSubjectDAO {
	//수강 중인 목록
	List<MainSubjectVO> Mainsubject(int userid);
	//교수 수강 중인 목록가져오기
	List<MainSubjectVO> MainProsubject(int userid);
	//관리자 수업 목록 가져오기
	List<MainSubjectVO> getsubjectList(Map<String, String> paraMap);
	// 총페이지수 알아오기
	int getSubjectTotal(Map<String, String> paraMap);
	//검색 자동글
	List<String> wordSearchShow(Map<String, String> paraMap);
	//엑셀 가져오기
	List<Map<String, String>> getExcelsubjectList();


}

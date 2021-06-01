package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seongkyung.model.PersonVO;

public interface InteradminMemberService {
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	List<Map<String, String>> getAdminStudent(Map<String, String> paraMap);
	
	// 총 학생 수(totalCount)
	int getTotalCount(Map<String, String> paraMap);
	
	// 관리자 학생 상세정보페이지
	List<PersonVO> getStudentView(String PERNO);
	
	// 총 교수 수(totalCount)
	int getProfessorTotalCount(Map<String, String> paraMap);
	
	// 관리자 교수관리메뉴에서 교수리스트를 출력
	List<Map<String, String>> getAdminProfessor(Map<String, String> paraMap);
	
	

}

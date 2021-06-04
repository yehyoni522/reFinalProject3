package com.spring.finalproject3.seongkyung.model;

import java.util.List;
import java.util.Map;

public interface InteradminMemberDAO {
	
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
	
	// 쪽지시험  insert
	int addquiz(String quizname);
	
	// 쪽지시험 테이블에서 시험명으로 쪽지시험 일련번호를 검색
	QuizVO getquiz(String quizname);
	
	// 쪽지시험_문제  insert 와 select
	int addquestion(Map<String, String> paraMap);
	
	// 쪽지시험_문제_문제번호로 문제일련번호 검색
	QuestionVO getquestion(Map<String, String> paraMap);
	
	// 쪽지시험_정답 insert
	int addquizans(Map<String, String> paraMap);
	
	

}

package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;

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
	
	// 쪽지시험 테이블에 insert 하고 select 해서 일련번호 가져오기
	QuizVO addquiz(String quizname);
	
	// 쪽지시험 테이블에서 시험명으로 쪽지시험 일련번호를 검색
	// QuizVO getquiz(String quizname);
	
	// 쪽지시험_문제  필드 생성
	QuestionVO addquestion(Map<String, String> paraMap);
	
	// 쪽지시험_문제_문제번호로 문제일련번호 검색
	// QuestionVO getquestion(String qzno);
	
	// 쪽지시험_정답 필드 생성
	int addquizans(Map<String, String> paraMap);
	
	// 사람번호로 교수의 정보와 과목 정보 얻어오기 (조인)
	List<Map<String, String>> getQuizList(String seq);
	
	

}

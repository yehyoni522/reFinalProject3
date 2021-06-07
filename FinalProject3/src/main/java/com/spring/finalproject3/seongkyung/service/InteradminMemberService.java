package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.model.SubjectVO;

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
	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것) 
	List<Map<String, String>> quizvoListSearchWithPaging(Map<String, String> paraMap);
	
	// 총 쪽지시험 수(totalCount)
	int getTotalQuiz(Map<String, String> paraMap);
	
	// 받아온 시험명으로 과목명 검색하기
	SubjectVO getSubname(String quizname);
	
	// 받아온 시험명으로 문제리스트 검색
	List<QuestionVO> getQuestionList(String quizname);
	
	// 가져온 시험명으로 문제의 총 갯수를 구한다.
	int getQuizTotalCount(String quizname);
	
	// 시험명으로 일련번호 검색 => 시험명과 문제번호로 문제 일련번호 검색  => 학생 정답 테이블에 넣기 
	int addStudentAnswer(Map<String, String> paraMap);
	
	
	
	
	

}

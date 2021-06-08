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
	
	// 시험명으로 일련번호 검색
	QuizVO getQuizNo(Map<String, String> paraMap);
	
	// 시험명과 문제번호로 문제 일련번호 검색 
	QuestionVO getQuestionNo(Map<String, String> paraMap);
	
	// 일련번호, 문제일련번호, 사람번호를 구했으면 그 값을 가지고 insert 
	int addStudentAnswer(Map<String, String> paraMap);
	
	// 과목번호로 해당 과목을 수강하는 학생들의 정보만 얻어온다.
	List<PersonVO> getStudentList(Map<String, String> paraMap);
	
	

}

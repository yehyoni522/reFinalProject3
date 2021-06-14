package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seongkyung.model.AttendanceVO;
import com.spring.finalproject3.seongkyung.model.InputatdcVO;
import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.model.StdtansVO;
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
	
	// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다.
	AttendanceVO addattendancesign(Map<String, String> paraMap);
	
	// select 에 넣을 출석신호를 보낸 날짜 List
	List<AttendanceVO> getattendanceList(Map<String, String> paraMap);
	
	// select 태그의 변화에 따라 해당 날짜에 출석한 학생들의 리스트를 알려준다.
	List<Map<String, String>> studentsignList(Map<String, String> paraMap);
	
	// 신호의 랜덤번호를 입력한 후 그 번호를 가지고 존재하는지 select 한 다음 출력신호 seq 를 가져와서 그 번호로 insert 한다.
	int addstudentsign(Map<String, String> paraMap);
	
	// 접속한 학생의 출석 상태를 보여준다.
	List<InputatdcVO> getStudentCheckSign(Map<String, String> paraMap);
	
	// 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	SubjectVO getAttendancesubno(String subject);
	
	// 이미 시험을 쳤는지 검사한다.
	StdtansVO getscorecheck(Map<String, String> paraMap);
	
	
	
	
	

}

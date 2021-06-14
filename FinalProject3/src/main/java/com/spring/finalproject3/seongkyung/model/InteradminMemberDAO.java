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
	int addquiz(Map<String, String> paraMap);
	
	// 쪽지시험 테이블에서 시험명으로 쪽지시험 일련번호를 검색
	QuizVO getquiz(Map<String, String> paraMap);
	
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
	
	// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다. -2-
	int addattendancesign(Map<String, String> paraMap);
	AttendanceVO getaddattendancesign(Map<String, String> paraMap);
	
	// select 에 넣을 출석신호를 보낸 날짜 List
	List<AttendanceVO> getattendanceList(Map<String, String> paraMap);
	
	// select 태그의 변화에 따라 해당 날짜에 출석한 학생들의 리스트를 알려준다.
	List<Map<String, String>> studentsignList(Map<String, String> paraMap);
	
	// 신호의 랜덤번호와 비교하면서 해당 신호의 행을 읽어옴
	AttendanceVO getinputstudentsign(Map<String, String> paraMap);
	
	// 신호를 입력  => 결석으로 된 행을 입력받은 시간을 넣어주면서 출석으로 바꾸어준다.
	int addstudentsign(Map<String, String> paraMap);
	
	// 현재 몇 주차인지 알아오자.
	int getinputweekno(Map<String, String> paraMap);
	
	// 출력시간과 입력시간을 비교해서 5분을 초과했으면 지각으로 한다.
	String gettimevs(Map<String, String> paraMap);
	
	// 지각 처리
	int changesign(Map<String, String> paraMap);
	
	// 수강 테이블의 학생들의 리스트를 가지고 온다.
	List<ClassVO> getStudentList(Map<String, String> paraMap);
	
	// 반복문으로 담긴 학생번호로 출석입력테이블에 행을 넣어준다.
	int addStudentList(Map<String, String> paraMap);
	
	// 접속한 학생의 출석 상태를 보여준다.
	List<InputatdcVO> getStudentCheckSign(Map<String, String> paraMap);
	
	// 만약 이미 출석이 되어있다면을 처리하기 위해 존재하는지 검색한다.
	InputatdcVO getchecksign(Map<String, String> paraMap);
	
	// 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	SubjectVO getAttendancesubno(String subject);
	
	// 정답을 비교하기 위해 정답테이블과 학생 정답테이블을 조인해서 담아온다
	Map<String, String> getScoreset(Map<String, String> paraMap);
	
	// 정답을 비교해서 정답이면 해당 학생 정답테이블의 행에서 점수를 1 올려준다.
	int updscore(Map<String, String> verseinfo);
	
	// 이미 시험을 쳤는지 검사한다.
	StdtansVO getscorecheck(Map<String, String> paraMap);
	
	// 시험명 알아오기
	String getquizname(String quizno);
	
	// 학생이 퀴즈를 풀었는지 알아오기
	String getquizcheck(String quizno);

	
	
	
	
	
	
	

}

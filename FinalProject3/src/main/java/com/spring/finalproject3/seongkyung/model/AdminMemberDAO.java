package com.spring.finalproject3.seongkyung.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class AdminMemberDAO implements InteradminMemberDAO{
	
	@Resource
	private SqlSessionTemplate sqlsession; // 로컬DB에 연결	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@Override
	public List<Map<String, String>> getAdminStudent(Map<String, String> paraMap) {

		List<Map<String,String>> personList = sqlsession.selectList("adminmember.getAdminStudent", paraMap);
				
		return personList;
	}
	
	
	// 총 학생 수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("adminmember.getTotalCount", paraMap);
		
		return n;
	}
	
	
	// 관리자 학생 상세정보페이지
	@Override
	public List<PersonVO> getStudentView(String PERNO) {
		
		List<PersonVO> personvo = sqlsession.selectList("adminmember.getStudentView", PERNO);
		
		return personvo;
	}

	
	// 총 교수 수(totalCount)
	@Override
	public int getProfessorTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("adminmember.getProfessorTotalCount", paraMap);
		
		return n;
	}

	
	// 관리자 교수관리메뉴에서 교수리스트를 출력
	@Override
	public List<Map<String, String>> getAdminProfessor(Map<String, String> paraMap) {

		List<Map<String,String>> personList = sqlsession.selectList("adminmember.getAdminProfessor", paraMap);
		
		return personList;
	}

	
	// 쪽지시험 insert
	@Override
	public int addquiz(String quizname) {
		
		int n = sqlsession.insert("adminmember.addquiz", quizname);
		
		return n;
	}

	
	// 쪽지시험 테이블에서 시험명으로 쪽지시험 일련번호를 검색
	@Override
	public QuizVO getquiz(String quizname) {
		
		QuizVO quizvo = sqlsession.selectOne("adminmember.getquiz", quizname);
		
		return quizvo;
	}

	
	// 쪽지시험_문제  insert
	@Override
	public int addquestion(Map<String, String> paraMap) {
		
		int j = sqlsession.insert("adminmember.addquestion", paraMap);
		
		return j;
	}

	
	// 쪽지시험_문제_문제번호로 문제일련번호 검색
	@Override
	public QuestionVO getquestion(Map<String, String> paraMap) {
		
		QuestionVO questionvo = sqlsession.selectOne("adminmember.getquestion", paraMap);

		return questionvo;
	}

	
	// 쪽지시험_정답 insert
	@Override
	public int addquizans(Map<String, String> paraMap) {
		
		int m = sqlsession.insert("adminmember.addquizans",paraMap);
		
		return m;
	}

	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<Map<String, String>> quizvoListSearchWithPaging(Map<String, String> paraMap) {

		List<Map<String, String>> quizvoList = sqlsession.selectList("adminmember.quizvoListSearchWithPaging", paraMap);
		
		return quizvoList;
	}

	
	// 총 쪽지시험 수(totalCount)
	@Override
	public int getTotalQuiz(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("adminmember.getTotalQuiz", paraMap);
		
		return n;
	}

	
	// 받아온 시험명으로 과목명 검색하기
	@Override
	public SubjectVO getSubname(String quizname) {

		SubjectVO subjectvo = sqlsession.selectOne("adminmember.getSubname", quizname);
			
		return subjectvo;
	}

	
	// 받아온 시험명으로 문제리스트 검색
	@Override
	public List<QuestionVO> getQuestionList(String quizname) {
		
		List<QuestionVO> questionList = sqlsession.selectList("adminmember.getQuestionList", quizname);
		
		return questionList;
	}

	
	// 가져온 시험명으로 문제의 총 갯수를 구한다.
	@Override
	public int getQuizTotalCount(String quizname) {
		
		int cnt = sqlsession.selectOne("adminmember.getQuizTotalCount", quizname);
		
		return cnt;
	}

	
	// 시험명으로 일련번호 검색
	@Override
	public QuizVO getQuizNo(Map<String, String> paraMap) {
		
		QuizVO quizvo = sqlsession.selectOne("adminmember.getQuizNo", paraMap);
		
		return quizvo;
	}

	
	// 시험명과 문제번호로 문제 일련번호 검색 
	@Override
	public QuestionVO getQuestionNo(Map<String, String> paraMap) {
		
		QuestionVO questionvo = sqlsession.selectOne("adminmember.getQuestionNo", paraMap);
		
		return questionvo;
	}

	
	// 일련번호, 문제일련번호, 사람번호를 구했으면 그 값을 가지고 insert 
	@Override
	public int addStudentAnswer(Map<String, String> paraMap) {
		
		int n = sqlsession.insert("adminmember.addStudentAnswer", paraMap);
		
		return n;
	}
	
	
	// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다. 
	@Override
	public int addattendancesign(Map<String, String> paraMap) {
		
		int n = sqlsession.insert("adminmember.addattendancesign", paraMap);
		
		return n;
	}

	
	// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다.
	@Override
	public AttendanceVO getaddattendancesign(Map<String, String> paraMap) {
		
		AttendanceVO attendancevo = sqlsession.selectOne("adminmember.getaddattendancesign", paraMap);
		
		return attendancevo;
	}

	
	// select 에 넣을 출석신호를 보낸 날짜 List
	@Override
	public List<AttendanceVO> getattendanceList(Map<String, String> paraMap) {
		
		List<AttendanceVO> attendanceList = sqlsession.selectList("adminmember.getattendanceList", paraMap);
		
		return attendanceList;
	}

	
	// select 태그의 변화에 따라 해당 날짜에 출석한 학생들의 리스트를 알려준다.
	@Override
	public List<Map<String, String>> studentsignList(Map<String, String> paraMap) {

		List<Map<String, String>> attendanceList = sqlsession.selectList("adminmember.studentsignList", paraMap);
		
		return attendanceList;
	}

	
	// 신호의 랜덤번호와 비교하면서 해당 신호의 행을 읽어옴
	@Override
	public AttendanceVO getinputstudentsign(Map<String, String> paraMap) {
		
		AttendanceVO attendancevo = sqlsession.selectOne("adminmember.getinputstudentsign", paraMap);
		
		return attendancevo;
	}

	
	// 신호를 입력  => 결석으로 된 행을 입력받은 시간을 넣어주면서 출석으로 바꾸어준다.
	@Override
	public int addstudentsign(Map<String, String> paraMap) {

		int n = sqlsession.update("adminmember.addstudentsign",paraMap);
		
		return n;
	}

	
	// 현재 몇 주차인지 알아오자.
	@Override
	public int getinputweekno(Map<String, String> paraMap) {
		
		int inputweekno = sqlsession.selectOne("adminmember.getinputweekno", paraMap);
		
		return inputweekno;
	}

	
	// 입력한 시간의 값을 알아온다.
	@Override
	public String gettimevs(Map<String, String> paraMap) {

		String inputatdcdate = sqlsession.selectOne("adminmember.gettimevs",paraMap);
		
		return inputatdcdate;
	}

	
	// 지각 처리
	@Override
	public int changesign(Map<String, String> paraMap) {
		
		int n = sqlsession.update("adminmember.changesign",paraMap);
		
		return n;
	}	
	
	// 수강 테이블의 학생들의 리스트를 가지고 온다.
	@Override
	public List<ClassVO> getStudentList(Map<String, String> paraMap) {
		
		List<ClassVO> sudentList = sqlsession.selectList("adminmember.getStudentList", paraMap);
		
		return sudentList;
	}

	
	// 반복문으로 담긴 학생번호로 출석입력테이블에 행을 넣어준다.
	@Override
	public int addStudentList(Map<String, String> paraMap) {

		int s = sqlsession.insert("adminmember.addStudentList", paraMap);
		
		return s;
	}

	
	// 접속한 학생의 출석 상태를 보여준다.
	@Override
	public List<InputatdcVO> getStudentCheckSign(Map<String, String> paraMap) {
		
		List<InputatdcVO >inputatdclist = sqlsession.selectList("adminmember.getStudentCheckSign", paraMap);
		
		return inputatdclist;
	}

	
	// 만약 이미 출석이 되어있다면을 처리하기 위해 존재하는지 검색한다.
	@Override
	public InputatdcVO getchecksign(Map<String, String> paraMap) {
		
		InputatdcVO inputatdcvo = sqlsession.selectOne("adminmember.getchecksign", paraMap);
		
		return inputatdcvo;
	}
	
	
	// 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	@Override
	public SubjectVO getAttendancesubno(String subject) {
		
		SubjectVO subjectvo = sqlsession.selectOne("adminmember.getAttendancesubno", subject);
		
		return subjectvo;
	}

	
	// 정답을 비교하기 위해 정답테이블과 학생 정답테이블을 조인해서 담아온다
	@Override
	public Map<String, String> getScoreset(Map<String, String> paraMap) {
		
		Map<String,String> verseinfo = sqlsession.selectOne("adminmember.getScoreset", paraMap);
		
		return verseinfo;
	}

	
	// 정답을 비교해서 정답이면 해당 학생 정답테이블의 행에서 점수를 1 올려준다.
	@Override
	public int updscore(Map<String, String> verseinfo) {
		
		int m = sqlsession.update("adminmember.updscore", verseinfo);
		
		return m;
	}

	
	// 이미 시험을 쳤는지 검사한다.
	@Override
	public StdtansVO getscorecheck(Map<String, String> paraMap) {
		
		StdtansVO stdtansvo = sqlsession.selectOne("adminmember.getscorecheck", paraMap);
		
		return stdtansvo;
	}
	
	



	
	

}

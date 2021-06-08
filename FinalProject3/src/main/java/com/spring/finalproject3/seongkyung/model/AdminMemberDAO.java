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

	
	// 과목번호로 해당 과목을 수강하는 학생들의 정보만 얻어온다.
	@Override
	public List<PersonVO> getStudentList(Map<String, String> paraMap) {
		
		List<PersonVO> studentList = sqlsession.selectList("adminmember.getStudentList", paraMap);
		
		return studentList;
	}

	
	// 출석신호 테이블 insert
	@Override
	public int addattendancesign(Map<String, String> paraMap) {
		
		int n = sqlsession.insert("adminmember.addattendancesign", paraMap);
		
		return n;
	}
	
	

}

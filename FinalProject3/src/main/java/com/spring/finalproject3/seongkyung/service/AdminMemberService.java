package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.seongkyung.model.InteradminMemberDAO;
import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.model.SubjectVO;

@Component
@Service
public class AdminMemberService implements InteradminMemberService {
	
	@Autowired
	private InteradminMemberDAO dao;
	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@Override
	public List<Map<String, String>> getAdminStudent(Map<String, String> paraMap) {

		List<Map<String, String>> personList = dao.getAdminStudent(paraMap);
		
		return personList;
		
	}
	
	
	// 총 학생 수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = dao.getTotalCount(paraMap);
		
		return n;
	}

	
	// 관리자 학생 상세정보페이지
	@Override
	public List<PersonVO> getStudentView(String PERNO) {
		
		List<PersonVO> personvo = dao.getStudentView(PERNO);
		
		return personvo;
	}

	
	// 총 교수 수(totalCount)
	@Override
	public int getProfessorTotalCount(Map<String, String> paraMap) {
		
		int n = dao.getProfessorTotalCount(paraMap);
		
		return n;
	}

	
	// 관리자 교수관리메뉴에서 교수리스트를 출력
	@Override
	public List<Map<String, String>> getAdminProfessor(Map<String, String> paraMap) {

		List<Map<String, String>> personList = dao.getAdminProfessor(paraMap);
		
		return personList;
	}
	

	// 쪽지시험 필드 생성
	@Override
	public QuizVO addquiz(String quizname) {
		
		QuizVO quizvo = null;
		
		int n = dao.addquiz(quizname);
		
		if(n==1) {
		   quizvo = dao.getquiz(quizname);
		}   
		
		return quizvo;
	}

	
	/*
	  // 쪽지시험 테이블에서 시험명으로 쪽지시험 일련번호를 검색
	  
	  @Override public QuizVO getquiz(String quizname) {
	  
	  QuizVO quizvo = dao.getquiz(quizname);
	  
	  return quizvo; }
	 */

	
	// 쪽지시험_문제  필드 생성
	@Override
	public QuestionVO addquestion(Map<String, String> paraMap) {
		
		QuestionVO questionvo = null;
		
		int m = dao.addquestion(paraMap);
		
		if(m==1) {
			questionvo = dao.getquestion(paraMap);
		}
		
		return questionvo;
	}

	
	  /*
	 // 쪽지시험_문제_문제번호로 문제일련번호 검색
	 @Override public QuestionVO getquestion(String qzno) {
	  
	 QuestionVO questionvo = dao.getquestion(qzno);
	  
	 return questionvo; }
	 */

	
	// 쪽지시험_정답 필드 생성
	@Override
	public int addquizans(Map<String, String> paraMap) {
		
		int m = dao.addquizans(paraMap);
		
		return m;
	}

	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<Map<String, String>> quizvoListSearchWithPaging(Map<String, String> paraMap) {
		
		List<Map<String, String>> quizvoList = dao.quizvoListSearchWithPaging(paraMap);
		
		return quizvoList;
	}

	
	// 총 쪽지시험 수(totalCount)
	@Override
	public int getTotalQuiz(Map<String, String> paraMap) {
		
		int n = dao.getTotalQuiz(paraMap);
		
		return n;
	}


	// 받아온 시험명으로 과목명 검색하기
	@Override
	public SubjectVO getSubname(String quizname) {

		SubjectVO subjectvo = dao.getSubname(quizname);
		
		return subjectvo;
	}

	
	// 받아온 시험명으로 문제리스트 검색
	@Override
	public List<QuestionVO> getQuestionList(String quizname) {

		List<QuestionVO> questionList = dao.getQuestionList(quizname);
		
		return questionList;
	}

	
	// 가져온 시험명으로 문제의 총 갯수를 구한다.
	@Override
	public int getQuizTotalCount(String quizname) {
		
		int cnt = dao.getQuizTotalCount(quizname);
		
		return cnt;
	}

	
	// 시험명으로 일련번호 검색 => 시험명과 문제번호로 문제 일련번호 검색  => 학생 정답 테이블에 넣기 
	@Override
	public int addStudentAnswer(Map<String, String> paraMap) {
		
		QuizVO quizvo = null;
		QuestionVO questionvo = null;
		
		int n = 0;
		
		// 시험명으로 일련번호 검색
		quizvo = dao.getQuizNo(paraMap);
		
		if(quizvo != null) {
			
			paraMap.put("quizno", String.valueOf(quizvo.getQuizno()));
			
			// 시험명과 문제번호로 문제 일련번호 검색 
			questionvo = dao.getQuestionNo(paraMap);	
			
		}
		
		if(questionvo != null) {
			
			paraMap.put("questionno", String.valueOf(questionvo.getQuestionno()));
			
			// 일련번호, 문제일련번호, 사람번호를 구했으면 그 값을 가지고 insert 
			n = dao.addStudentAnswer(paraMap);
			
		}
		
		return n;
	}
	

}

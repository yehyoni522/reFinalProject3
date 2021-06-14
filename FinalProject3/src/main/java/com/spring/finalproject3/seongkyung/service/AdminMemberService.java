package com.spring.finalproject3.seongkyung.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.seongkyung.model.AttendanceVO;
import com.spring.finalproject3.seongkyung.model.ClassVO;
import com.spring.finalproject3.seongkyung.model.InputatdcVO;
import com.spring.finalproject3.seongkyung.model.InteradminMemberDAO;
import com.spring.finalproject3.seongkyung.model.PersonVO;
import com.spring.finalproject3.seongkyung.model.QuestionVO;
import com.spring.finalproject3.seongkyung.model.QuizVO;
import com.spring.finalproject3.seongkyung.model.StdtansVO;
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
	public QuizVO addquiz(Map<String, String> paraMap) {
		
		QuizVO quizvo = null;
		
		int n = dao.addquiz(paraMap);
		// System.out.println("서비스 확인중!");
		if(n==1) {
			// System.out.println("서비스 확인중2!");
		   quizvo = dao.getquiz(paraMap);
		}   
		
		return quizvo;
	}

	
	// 쪽지시험_문제  필드 생성
	@Override
	public QuestionVO addquestion(Map<String, String> paraMap) {
		
		QuestionVO questionvo = null;
		
		int m = 0;
				
		m =	dao.addquestion(paraMap);
		
		if(m==1) {
			questionvo = dao.getquestion(paraMap);
		}
		else {
			questionvo = dao.getquestion(paraMap);
		}
		
		return questionvo;
	}
	
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
		
		// 일련번호가 존재한는 경우
		if(quizvo != null) {
			
			paraMap.put("quizno", String.valueOf(quizvo.getQuizno()));
			
			// 시험명과 문제번호로 문제 일련번호 검색 
			questionvo = dao.getQuestionNo(paraMap);	
			// System.out.println("22");
			if(questionvo != null) {
				
				paraMap.put("questionno", String.valueOf(questionvo.getQuestionno()));
				
				// 일련번호, 문제일련번호, 사람번호를 구했으면 그 값을 가지고 insert 
				n = dao.addStudentAnswer(paraMap);
				// System.out.println("33");
				// 점수를 구하기 위해 새로운 맵에 값을 담아온다.
				if(n==1) {
					// 정답을 비교하기 위해 정답테이블과 학생 정답테이블을 조인해서 담아온다
					Map<String,String> verseinfo = dao.getScoreset(paraMap);
					// System.out.println("44");
					if(verseinfo != null) {
						// System.out.println("55");
						if(Integer.parseInt(verseinfo.get("quizanswer")) == Integer.parseInt(verseinfo.get("stdtanswer"))) {
							// System.out.println("66");
							// 정답을 비교해서 정답이면 해당 학생 정답테이블의 행에서 점수를 1 올려준다.
							int m = dao.updscore(verseinfo);
							
							if(m==1) {
								
								return n;
							}
						}
						else {
							return n;
						}
						
					}
					else {
						n=2;
						return n;
					}
					
				}
				else {
					n=2;
					return n;
				}
				
			}
			else {
				
				n = 2;
				return n;
				
			}
		// n == 0	
		}		
		
		return n;
	}

	
	// 출석신호 테이블 insert 후 집어넣은 랜덤값을 가져온다.
	@Override
	public AttendanceVO addattendancesign(Map<String, String> paraMap) {
		
		AttendanceVO attendancevo = null;
		// 교수번호와 과목번호를 가지고 현재날짜의 출석테이블이 생성되어있는지 확인한다.
		attendancevo = dao.getaddattendancesign(paraMap);
		
		// 있다면 생성된 테이블의 정보를 그대로 넘겨준다.
		if(attendancevo != null) {			
			
			return attendancevo;

		}
		// 출석테이블에서 현재날짜의 출석정보가 없는 경우
		else {
			// 없다면 테이블을 가지고온 paraMap 데이터로 출석테이블에 행을 하나 만든다.
			int n = dao.addattendancesign(paraMap);
			// System.out.println("되냐0");
			if(n==1) {
				// 만들어진 오늘 날짜의 행을 검색한 다음 그 정보를 넘겨준다.
				attendancevo = dao.getaddattendancesign(paraMap);
				
				paraMap.put("atdcno", String.valueOf(attendancevo.getAtdcno()));
				
				// 출석신호의 총 개수가 출석입력 테이블에서 몇 주차인지 알 수 있게 한다.
				int weekno = dao.getinputweekno(paraMap);
				
				paraMap.put("weekno", String.valueOf(weekno));
				
				paraMap.put("attendancedate", attendancevo.getAttendancedate());
				
				// System.out.println("atdcno : " + attendancevo.getAtdcno());
				// System.out.println("atdcno : " + weekno);
				
				// 수강 테이블의 학생들의 리스트를 가지고 온다.
				List<ClassVO> sudentList = dao.getStudentList(paraMap);
				// System.out.println("되냐1");
				// 검색한 학생 리스트가 존재하는 경우
				if(sudentList != null) {
					
					// System.out.println("되냐2");
					
					int sfk_perno = 0;
					
					// 검색해온 학생 리스트를 반복문으로 담는다
					for(ClassVO classvo:sudentList) {					
						// System.out.println("되냐3");
						sfk_perno = classvo.getFk_perno();
						// System.out.println("sfk_perno : "+ sfk_perno);
						paraMap.put("sfk_perno", String.valueOf(sfk_perno));
						
						// 반복문으로 담긴 학생번호로 출석입력테이블에 행을 넣어준다.
						int s = dao.addStudentList(paraMap);					
						
					}
					
				}
				
			} 		
		}	
		return attendancevo;		
	}
	
	
	// select 에 넣을 출석신호를 보낸 날짜 List
	@Override
	public List<AttendanceVO> getattendanceList(Map<String, String> paraMap) {
		
		List<AttendanceVO> attendanceList = dao.getattendanceList(paraMap);
		
		return attendanceList;
	}

	
	// select 태그의 변화에 따라 해당 날짜에 출석한 학생들의 리스트를 알려준다.
	@Override
	public List<Map<String, String>> studentsignList(Map<String, String> paraMap) {

		List<Map<String, String>> attendanceList = dao.studentsignList(paraMap);
		
		if(attendanceList != null) {
			return attendanceList;
		}

		
		return attendanceList;
	}

	
	// 신호를 입력  => 결석으로 된 행을 입력받은 시간을 넣어주면서 출석으로 바꾸어준다.
	@Override
	public int addstudentsign(Map<String, String> paraMap) {
		
		// 신호의 랜덤번호와 비교하면서 해당 신호의 행 즉 랜덤번호가 적혀있는 행의 seq 를 읽어옴
		AttendanceVO attendancevo = dao.getinputstudentsign(paraMap);
		
		int n = 0;
			
		// System.out.println("attendancevo.getAtdcno() : "+ attendancevo.getAtdcno());
		
		// 출석신호가 존재하는 경우 
		if(attendancevo != null) {
			
			// 출석신호가 생성되면서 입력신호테이블에 행이 생성되어 있는지 알아온다.
			InputatdcVO inputatdcvo = dao.getchecksign(paraMap);
			
			// 입력신호테이블에 행이 있는 경우
			if(inputatdcvo != null) {
				
				// 입력신호테이블에 행을 출석으로 하는 경우
				if(inputatdcvo.getInputatdcstatus() == 0) {
					
					n = dao.addstudentsign(paraMap);
					
					// 출력시간과 입력시간을 비교해서 5분을 초과했으면 지각으로 한다.	
					if(n==1) {
						String inputatdcdate = dao.gettimevs(paraMap);
						
						if( (Long.parseLong(inputatdcdate) - Long.parseLong(attendancevo.getAttendancedate())) > 10) {
							
							// 지각 처리
							int m = dao.changesign(paraMap);
							
							// 지각처리가 완료되면 바로 넘겨준다.
							if(m==1) {							
								return n;							
							}										
						}					
					}												
				}
				// 이미 출석이 되어 있는 상태이므로 종료시켜준다.
				else {
					n = 2;
					return n;
				}				
			}		
		} 	
		return n;	
	}

	
	// 접속한 학생의 출석 상태를 보여준다.
	@Override
	public List<InputatdcVO> getStudentCheckSign(Map<String, String> paraMap) {
		
		List<InputatdcVO> inputatdclist = dao.getStudentCheckSign(paraMap);
		
		
		return inputatdclist;
	}

	
	// 흠.. subno가 검색이 안되므로 검색이 되는 subject 로 subno를 불러와 줍시다.
	@Override
	public SubjectVO getAttendancesubno(String subject) {
		
		SubjectVO subjectvo = dao.getAttendancesubno(subject);
		
		return subjectvo;
	}

	
	// 이미 시험을 쳤는지 검사한다.
	@Override
	public StdtansVO getscorecheck(Map<String, String> paraMap) {
		
		StdtansVO stdtansvo = dao.getscorecheck(paraMap);
		
		return stdtansvo;
	}

	
	// 시험명 알아오기
	@Override
	public String getquizname(String quizno) {
		
		String quizname = dao.getquizname(quizno);
		
		return quizname;
	}

	
	// 학생이 퀴즈를 풀었는지 알아오기
	@Override
	public String getquizcheck(String quizno) {

		String fk_quizno = dao.getquizcheck(quizno);
		
		if(fk_quizno != null) {
			fk_quizno = "1";
		}
		else {
			fk_quizno = "2";
			return fk_quizno;
		}
		
		return fk_quizno;
	}
	

}

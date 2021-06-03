package com.spring.finalproject3.seoyeon.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class ClassBoardDAO implements InterClassBoardDAO {

	@Resource
	private SqlSessionTemplate sqlsession;

	@Override
	public int getTotalAssign(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("classBoard.getTotalAssign", paraMap);
		return n;
	}

	@Override
	public List<assignmentBoardVO> assignListSearchWithPaging(Map<String, String> paraMap) {
		List<assignmentBoardVO> assignmentList = sqlsession.selectList("classBoard.assignListSearchWithPaging", paraMap);
		return assignmentList;
	}

	// === 과제 게시판 글쓰기 완료 요청 === // 
	@Override
	public int assignmentAdd(assignmentBoardVO assgnVO) {
		int n = sqlsession.insert("classBoard.assignmentAdd",assgnVO);
		return n;
	}

	// 어떤 과목인지 과목번호 알아오기 
	@Override
	public String getSubjectname(String subno) {
		String subject = sqlsession.selectOne("classBoard.getSubjectname", subno);
		return subject;
	}

	// 해당 수업을 듣는 학생의 총 인원수 알아오기(교수 총 수강인원 나타낼때 필요)
	@Override
	public String getTotalPerson(String subno) {
		String totalPerson = sqlsession.selectOne("classBoard.getTotalPerson", subno);
		return totalPerson;
	}
	
	
	
}

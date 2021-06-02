package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.seoyeon.model.InterClassBoardDAO;
import com.spring.finalproject3.seoyeon.model.assignmentBoardVO;

@Component
@Service
public class ClassBoardService implements InterClassBoardService {

	@Autowired
	private InterClassBoardDAO dao;
	
	// 총 게시물 건수(totalCount)
	@Override
	public int getTotalAssign(Map<String, String> paraMap) {
		int n = dao.getTotalAssign(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<assignmentBoardVO> assignListSearchWithPaging(Map<String, String> paraMap) {
		List<assignmentBoardVO> assignmentList = dao.assignListSearchWithPaging(paraMap);
		return assignmentList;
	}

	// === 과제 게시판 글쓰기 완료 요청 === // 
	@Override
	public int assignmentAdd(assignmentBoardVO assgnVO) {
		int n = dao.assignmentAdd(assgnVO);
		return n;
	}

	// 어떤 과목인지 과목번호 알아오기 
	@Override
	public String getSubjectname(String subno) {
		String subject = dao.getSubjectname(subno);
		return subject;
	}

	// 해당 수업을 듣는 학생의 총 인원수 알아오기(교수 총 수강인원 나타낼때 필요)
	@Override
	public String getTotalPerson(String subno) {
		String totalPerson = dao.getTotalPerson(subno);
		return totalPerson;
	} 
	
}

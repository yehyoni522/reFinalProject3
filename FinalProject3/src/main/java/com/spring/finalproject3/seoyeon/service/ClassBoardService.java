package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.finalproject3.seoyeon.model.InterClassBoardDAO;
import com.spring.finalproject3.seoyeon.model.QnAVO;
import com.spring.finalproject3.seoyeon.model.SubmitVO;
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

    // 과제 게시글1개 조회
	@Override
	public assignmentBoardVO assignmentView(String assgnno) {
		assignmentBoardVO assignmentVO = dao.assignmentView(assgnno);
		return assignmentVO;
	}

	// ===  1개글 수정하기 === //
	@Override
	public int assignmentEdit(assignmentBoardVO assignmentVO) {
		int n = dao.assignmentEdit(assignmentVO);
		return n;
	}

	// === 1개글 삭제하기 === //
	@Override
	public int assignmentDelete(String assgnno) {
		int n = dao.assignmentDelete(assgnno);
		return n;
	}

	// === 과제 제출 댓글 작성하기 === //
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int addSubmit(SubmitVO submitvo) throws Throwable {

		int n=0, m=0, result=0;
		
		n = dao.addSubmit(submitvo); // 댓글쓰기(tbl_assgn 테이블에 insert)
		
		if(n==1) {
			m = dao.updateSubmitCount(submitvo.getFk_assgnno()); // tbl_assgn 테이블에 submitCount 컬럼의 값을 1증가(update) 
		}
		
		if(m==1) {
			result=1;
		}
		
		return result;
	}

	// === 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //
	@Override
	public List<SubmitVO> getSubmitListPaging(Map<String, String> paraMap) {
		List<SubmitVO> submitList = dao.getSubmitListPaging(paraMap);
		return submitList;
	}

	// === 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
	@Override
	public int getSubmitTotalPage(Map<String, String> paraMap) {
		int totalPage = dao.getSubmitTotalPage(paraMap);
	    return totalPage;
	}

	// 학생이 과제 제출했는지 확인하기
	@Override
	public int studentSubmit(Map<String, String> paraMap) {
		int n = dao.studentSubmit(paraMap);
		return n;
	}

	// === 학생)댓글 페이징 처리해서 조회하기 (Ajax 로 처리) === //
	@Override
	public List<SubmitVO> mysubmitList(Map<String, String> paraMap) {
		List<SubmitVO> submitList = dao.mysubmitList(paraMap);
		return submitList;
	}

	// 글쓰기(파일첨부가 있는 글쓰기)
	@Override
	public int assignmentAdd_withFile(assignmentBoardVO assgnVO) {
		int n = dao.assignmentAdd_withFile(assgnVO); // 첨부파일이 있는 경우
		return n;
	}


	// 전체 질문게시판 글 개수
	@Override
	public int getTotalQna(Map<String, String> paraMap) {
		int n = dao.getTotalQna(paraMap);
		return n;
	}

	// 질문게시판 페이징 글목록
	@Override
	public List<QnAVO> qnaListSearchWithPaging(Map<String, String> paraMap) {
		List<QnAVO> qnaList = dao.qnaListSearchWithPaging(paraMap);
		return qnaList;
	}

	// 질문게시판 글 쓰기완료 요청
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int qnaAdd(QnAVO qnavo) {
		
		if(qnavo.getFk_qnano() == null || qnavo.getFk_qnano().trim().isEmpty() ) {
	         // 원글쓰기 이라면 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해야 한다. 
	         int groupno = dao.getGroupnoMax() + 1;
	         qnavo.setGroupno(String.valueOf(groupno));
	      }				
		else {
			dao.updateAnswerCount(qnavo.getFk_qnano());
		}
		int n = dao.qnaAdd(qnavo);
		return n;
	}

	// == 질문 게시판 글 1개 상세보기 == //
	@Override
	public QnAVO getQnaView(Map<String, String> paraMap) {
		QnAVO qnavo = dao.getQnaView(paraMap);
		return qnavo;
	}

	// == 질문 게시판 글 수정하기 == //
	@Override
	public int qnaEditEnd(QnAVO qnavo) {
		int n = dao.qnaEdit(qnavo);
		return n;
	}

	// == 질문 게시판 글 1개 삭제하기 == //
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int qnaDelete(Map<String, String> paraMap) {
		String fk_qnano = paraMap.get("fk_qnano");
		String qnano = paraMap.get("qnano");
		
		if(!(fk_qnano == null || fk_qnano.trim().isEmpty()) ) {
			// 답변글쓰기일 경우 원글의 answer count -1해야함
			dao.updateAnswerMinus(fk_qnano);
	      }				
		
		int n = dao.qnaDelete(qnano);
		return n;
	}

	// 댓글 1개 조회만을 해주는 것이다.
	@Override
	public SubmitVO getSubmitOne(String submitno) {
		SubmitVO submitvo = dao.getSubmitOne(submitno);
		return submitvo;
	}

}

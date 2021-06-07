package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seoyeon.model.QnAVO;
import com.spring.finalproject3.seoyeon.model.SubmitVO;
import com.spring.finalproject3.seoyeon.model.assignmentBoardVO;

public interface InterClassBoardService {

	// 총 게시물 건수(totalCount)
	int getTotalAssign(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	List<assignmentBoardVO> assignListSearchWithPaging(Map<String, String> paraMap);

	// === 과제 게시판 글쓰기 완료 요청 === // 
	int assignmentAdd(assignmentBoardVO assgnVO);

	// 어떤 과목인지 과목번호 알아오기 
	String getSubjectname(String subno);

	// 해당 수업을 듣는 학생의 총 인원수 알아오기(교수 총 수강인원 나타낼때 필요)
	String getTotalPerson(String subno);

    // 과제 게시글1개 조회
	assignmentBoardVO assignmentView(String assgnno);

	// === 글수정 페이지 완료하기 === //
	int assignmentEdit(assignmentBoardVO assignmentVO);

	// === 1개글 삭제하기 === //
	int assignmentDelete(String assgnno);

	// === 과제 제출 댓글 작성하기 === //
	int addSubmit(SubmitVO submitvo) throws Throwable;

	// === 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //
	List<SubmitVO> getSubmitListPaging(Map<String, String> paraMap);

	// === 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
	int getSubmitTotalPage(Map<String, String> paraMap);

	// 학생이 과제 제출했는지 확인하기
	int studentSubmit(Map<String, String> paraMap);

	// === 학생)댓글 페이징 처리해서 조회하기 (Ajax 로 처리) === //
	List<SubmitVO> mysubmitList(Map<String, String> paraMap);
	
	// 글쓰기(파일첨부가 있는 글쓰기)
	int assignmentAdd_withFile(assignmentBoardVO assgnVO);

	// 전체 질문게시판 글 개수
	int getTotalQna(Map<String, String> paraMap);

	// 질문게시판 페이징
	List<QnAVO> qnaListSearchWithPaging(Map<String, String> paraMap);

	// 질문게시판 글 쓰기완료 요청
	int qnaAdd(QnAVO qnavo);

	// == 질문 게시판 글 1개 상세보기 == //
	QnAVO getQnaView(Map<String, String> paraMap);

	// == 질문 게시판 글 1개 수정하기 == //
	int qnaEditEnd(QnAVO qnavo);

	// == 질문 게시판 글 1개 삭제하기 == //
	int qnaDelete(Map<String, String> paraMap);

	// 댓글 1개 조회만을 해주는 것이다.
	SubmitVO getSubmitOne(String seq);



}

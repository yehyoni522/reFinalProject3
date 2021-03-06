package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seoyeon.model.QnAVO;
import com.spring.finalproject3.seoyeon.model.SubmitVO;
import com.spring.finalproject3.seoyeon.model.assignmentBoardVO;
import com.spring.finalproject3.seoyeon.model.materialVO;
import com.spring.finalproject3.seoyeon.model.planVO;

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
	
	// 과제) 기존 첨부파일 삭제 후 새로운 첨부파일 등록 수정 update
	int assignmentEdit_delfile(Map<String, String> paraMap, assignmentBoardVO assignmentVO);
	
	// 과제) 새로운 첨부파일 등록 & 수정 update
	int assignmentEdit_withfile(assignmentBoardVO assignmentVO);

	// === 글수정 페이지 완료하기 === //
	int assignmentEdit(assignmentBoardVO assignmentVO);

	// === 1개글 삭제하기 === //
	int assignmentDelete(Map<String, String> paraMap);

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

	// 점수 변경하기
	int changeScore(Map<String, String> paraMap);

	// 자료) 총 갯수 알아오기
	int getTotalMaterial(Map<String, String> paraMap);

	// 자료) 페이징처리한 목록 가져오기
	List<materialVO> materialListSearchWithPaging(Map<String, String> paraMap);

	// 자료) 첨부파일 없는 글쓰기
	int materialAdd(materialVO mtrvo);

	// 자료) 첨부파일 있는 글쓰기
	int materialAdd_withFile(materialVO mtrvo);

	// 자료) 조회수 증가 + 글 상세 보기
	materialVO materialView(Map<String, String> paraMap, int login_perno);

	// 자료) 조회수 증가 없이 글 상세 보기
	materialVO materialViewNoAddCount(Map<String, String> paraMap);

	// 기존 첨부파일 삭제 후 새로운 첨부파일 등록 수정 update
	int materialEdit_delfile(Map<String, String> paraMap, materialVO mtrvo);

	// 새로운 첨부파일 등록 & 수정 update
	int materialEdit_withfile(materialVO mtrvo);
	
	// 자료) 글 수정하기
	int materialEdit(materialVO mtrvo);

	// 자료) 글 삭제하기
	int materialDelete(Map<String, String> paraMap);

	// 자료) 검색어 자동글
	List<String> materialWordSearchShow(Map<String, String> paraMap);

	// 질문) 원글 글쓴이 perno 받아오기
	String getOrgPerno(String qnano);

	// 계획) 정보 추출해오기
	planVO getInfo(String subno);

	// 계획) 계획 추출해오기
	List<planVO> getPlan(String subno);

	// 계획) 강의 계획서 등록하기 완료
	int planAdd(Map<String, String> paraMap);

	// 계획) 강의 계획서 수정하기 완료
	int planEdit(Map<String, String> paraMap);




}

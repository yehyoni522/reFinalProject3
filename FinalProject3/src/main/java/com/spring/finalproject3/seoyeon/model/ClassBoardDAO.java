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
	
	// 현재 시퀀스 알아오기
	@Override
	public String getAssignno() {
		String assgnno = sqlsession.selectOne("classBoard.getAssignno");
		return assgnno;
	}
	
	// 해당 수업 듣는 학생들의 총 perno 리스트
	@Override
	public List<String> pernoList(String fk_subno) {
		List<String> pernoList = sqlsession.selectList("classBoard.pernoList", fk_subno);
		
		return pernoList;
	}

	// === 과제 게시판 글쓰면 학생들한테 과제 부여하기
	@Override
	public int assgnStudent(Map<String, String> paraMap) {
		int n = sqlsession.	insert("classBoard.assgnStudent",paraMap);
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

    // 과제 게시글1개 조회
	@Override
	public assignmentBoardVO assignmentView(String assgnno) {
		assignmentBoardVO assignmentVO = sqlsession.selectOne("classBoard.assignmentView",assgnno);
		return assignmentVO;
	}

	// 과제) 첨부파일 있는 수정하기
	@Override
	public int assignmentEdit_withfile(assignmentBoardVO assignmentVO) {
		int n = sqlsession.update("classBoard.assignmentEdit_withfile", assignmentVO);
	    return n;
	}
	
	// ===1개글 수정하기 === //
	@Override
	public int assignmentEdit(assignmentBoardVO assignmentVO) {
		int n = sqlsession.update("classBoard.assignmentEdit", assignmentVO);
		return n;
	}

	// === 1개글 삭제하기 === //
	@Override
	public int assignmentDelete(Map<String, String> paraMap) {
		int n = sqlsession.delete("classBoard.assignmentDelete", paraMap);
		return n;
	}

	// 과제 제출 댓글쓰기
	@Override
	public int addSubmit(SubmitVO submitvo) {
		int n = sqlsession.insert("classBoard.addSubmit", submitvo);
		return n;
	}

	// tbl_assgn 테이블의 submitCount 컬럼의 값을 1증가(update)  
	@Override
	public int updateSubmitCount(String fk_assgnno) {
		int n = sqlsession.update("classBoard.updateSubmitCount", fk_assgnno);
		return n;
	}

	// === 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //
	@Override
	public List<SubmitVO> getSubmitListPaging(Map<String, String> paraMap) {
		List<SubmitVO> submitList = sqlsession.selectList("classBoard.getSubmitListPaging", paraMap);
	     return submitList;
	}

	// === 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
	@Override
	public int getSubmitTotalPage(Map<String, String> paraMap) {
		int totalPage = sqlsession.selectOne("classBoard.getSubmitTotalPage", paraMap);
	    return totalPage;
	}

	// 학생이 과제 제출했는지 확인하기
	@Override
	public int studentSubmit(Map<String, String> paraMap) {
		int n =  sqlsession.selectOne("classBoard.studentSubmit", paraMap);
		return n;
	}

	// === 학생)댓글 페이징 처리해서 조회하기 (Ajax 로 처리) === //
	@Override
	public List<SubmitVO> mysubmitList(Map<String, String> paraMap) {
		List<SubmitVO> submitList = sqlsession.selectList("classBoard.mysubmitList", paraMap);
		return submitList;
	}

	// 글쓰기(파일첨부가 있는 글쓰기)
	@Override
	public int assignmentAdd_withFile(assignmentBoardVO assgnVO) {
		int n = sqlsession.insert("classBoard.assignmentAdd_withFile",assgnVO);
		return n;
	}



	// 전체 질문게시판 글 개수{
	@Override
	public int getTotalQna(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("classBoard.getTotalQna", paraMap);
		return n;
	}

	// 질문게시판 페이징 글목록
	@Override
	public List<QnAVO> qnaListSearchWithPaging(Map<String, String> paraMap) {
		List<QnAVO> qnaList = sqlsession.selectList("classBoard.qnaListSearchWithPaging", paraMap);
		return qnaList;
	}

	// 질문게시판 글 쓰기완료 요청
	@Override
	public int qnaAdd(QnAVO qnavo) {
		int n = sqlsession.insert("classBoard.qnaAdd",qnavo);
		return n;
	}

	// === #145. tbl_board 테이블에서 groupno 컬럼의 최대값 구하기 === //
	@Override
	public int getGroupnoMax() {
		int max = sqlsession.selectOne("classBoard.getGroupnoMax");
		return max;
	}

	// == 질문 게시판 글 1개 상세보기 == //
	@Override
	public QnAVO getQnaView(Map<String, String> paraMap) {
		QnAVO qnavo = sqlsession.selectOne("classBoard.getQnaView", paraMap);
		return qnavo;
	}

	// == 질문 게시판 답변 글쓰기하면 원글의 answer cnt +1하기
	@Override
	public void updateAnswerCount(String fk_qnano) {
		sqlsession.selectOne("classBoard.updateAnswerCount",fk_qnano);
	}

	// == 질문 게시판 글 수정하기 == //
	@Override
	public int qnaEdit(QnAVO qnavo) {
		int n = sqlsession.update("classBoard.qnaEdit", qnavo);
		return n;
	}

	// 답변글쓰기 삭제일 경우 원글의 answer count -1해야함
	@Override
	public void updateAnswerMinus(String fk_qnano) {
		sqlsession.update("classBoard.updateAnswerMinus", fk_qnano);
	}

	// 글삭제하기
	@Override
	public int qnaDelete(String qnano) {
		int n = sqlsession.delete("classBoard.qnaDelete", qnano);
		return n;
	}

	//댓글 1개 조회
	@Override
	public SubmitVO getSubmitOne(String submitno) {
		SubmitVO submitvo = sqlsession.selectOne("classBoard.getSubmitOne",submitno);
		return submitvo;
	}

	// 점수 변경하기
	@Override
	public int changeScore(Map<String, String> paraMap) {
		int n = sqlsession.update("classBoard.changeScore",paraMap);
		return n;
	}
	
	
	////////////////////////////////////수업자료////////////////////////////////////
	
	// 자료) 총 갯수 알아오기
	@Override
	public int getTotalMaterial(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("classBoard.getTotalMaterial", paraMap);
		return n;
	}
	
	// 자료) 페이징처리한 목록 가져오기
	@Override
	public List<materialVO> materialListSearchWithPaging(Map<String, String> paraMap) {
		List<materialVO> mtrList = sqlsession.selectList("classBoard.materialListSearchWithPaging", paraMap);
	      return mtrList;
	}
	
	// 자료) 첨부파일 없는 글쓰기
	@Override
	public int materialAdd(materialVO mtrvo) {
		int n = sqlsession.insert("classBoard.materialAdd", mtrvo);
	    return n;
	}
	
	// 자료) 첨부파일 있는 글쓰기
	@Override
	public int materialAdd_withFile(materialVO mtrvo) {
		int n = sqlsession.insert("classBoard.materialAdd_withFile",mtrvo);
		return n;
	}
	
	// 자료) 글 상세 보기
	@Override
	public materialVO materialView(Map<String, String> paraMap) {
		materialVO mtrvo = sqlsession.selectOne("classBoard.materialView", paraMap);
	    return mtrvo;
	}
	
	// 자료) 조회수 증가 
	@Override
	public void materialAddReadCount(String mtrno) {
	      sqlsession.update("classBoard.materialAddReadCount", mtrno);		
	}
	
	// 자료) 새로운 첨부파일 및 수정하기 완료
	@Override
	public int materialEdit_withfile(materialVO mtrvo) {
		int n = sqlsession.update("classBoard.materialEdit_withfile", mtrvo);
	     return n;
	}
	
	// 자료) 글 수정하기
	@Override
	public int materialEdit(materialVO mtrvo) {
		 int n = sqlsession.update("classBoard.materialEdit", mtrvo);
	     return n;
	}
	
	// 자료) 글 삭제하기
	@Override
	public int materialDelete(Map<String, String> paraMap) {
		 int n = sqlsession.delete("classBoard.materialDelete", paraMap);
	     return n;
	}

	// 자료) 글 검색어 입력시 자동글 완성하기
	@Override
	public List<String> materialWordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("classBoard.materialWordSearchShow", paraMap);
		return wordList;
	}

	// 질문) 원글 글쓴이 perno 받아오기
	@Override
	public String getOrgPerno(String qnano) {
		String org_perno = sqlsession.selectOne("classBoard.getOrgPerno",qnano);
		return org_perno;
	}

	// 계획) 정보 추출해오기
	@Override
	public planVO getInfo(String subno) {
		planVO InfoVO = sqlsession.selectOne("classBoard.getInfo",subno);
		return InfoVO;
	}

	// 계획) 계획 추출해오기
	@Override
	public List<planVO> getPlan(String subno) {
		List<planVO> planVO = sqlsession.selectList("classBoard.getPlan",subno);
		return planVO;
	}

	// 계획) 강의 계획서 등록하기 완료
	@Override
	public int planAdd(Map<String, String> paraMap) {
		int n=sqlsession.insert("classBoard.planAdd",paraMap);
		return n;
	}

	// 계획) 강의 계획서 수정하기 완료
	@Override
	public int planEdit(Map<String, String> paraMap) {
		int n=sqlsession.update("classBoard.planEdit",paraMap);
		return n;
	}







	
	
}

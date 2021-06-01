package com.spring.finalproject3.seoyeon.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
@Repository
public class AdminBoardDAO implements InterAdminBoardDAO {

	@Resource
	private SqlSessionTemplate sqlsession;
	
	// === 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
	@Override
	public List<AdminBoardVO> boardListSearch(Map<String, String> paraMap) {
		List<AdminBoardVO> boardList = sqlsession.selectList("adminBoard.boardListSearch",paraMap);
		return boardList;
	}

	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("adminBoard.wordSearchShow", paraMap);
		return wordList;
	}

	// === 총 게시물 건수(totalCount) 구하기 - 검색이 있을때와 검색이 없을때로 나뉜다. === //
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("adminBoard.getTotalCount", paraMap);
		return n;
	}

	// === 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것) === //
	@Override
	public List<AdminBoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<AdminBoardVO> boardList = sqlsession.selectList("adminBoard.boardListSearchWithPaging", paraMap);
		return boardList;
	}

	// 선택한 글 게시판 이동시키기
	@Override
	public int boardMove(Map<String, String> paraMap) {
		int n = sqlsession.update("adminBoard.boardMove", paraMap);
		return n;
	}
	
	// === 페이징 처리한 댓글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것) === //
	@Override
	public List<AdminCommentVO> commentListSearchWithPaging(Map<String, String> paraMap) {
		List<AdminCommentVO> commentList = sqlsession.selectList("adminBoard.commentListSearchWithPaging", paraMap);
		return commentList;
	}

	// 총 댓글 건수 구하기
	@Override
	public int getTotalComment(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("adminBoard.getTotalComment", paraMap);
		return n;
	}

	// 댓글 검색어 입력시 자동글 완성하기
	@Override
	public List<String> commentWordSearchShow(Map<String, String> paraMap) {
		List<String> commentWordList = sqlsession.selectList("adminBoard.commentWordSearchShow", paraMap);
		return commentWordList;
	}

	// 댓글 삭제하기
	@Override
	public int commentDelete(String comseq) {
		int n = sqlsession.update("adminBoard.commentDelete",comseq); 		
		return n;
	}

//	=== 게시글 삭제하기 ===
	@Override
	public int boardDelete(String seq) {
		int n = sqlsession.update("adminBoard.boardDelete",seq); 		
		return n;
	}

}

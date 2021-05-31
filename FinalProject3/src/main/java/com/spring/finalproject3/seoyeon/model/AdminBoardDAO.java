package com.spring.finalproject3.seoyeon.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class AdminBoardDAO implements InterAdminBoardDAO {

	@Resource
	private SqlSessionTemplate sqlsession;
	
	// === 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
	@Override
	public List<AdminBoardVO> boardListSearch(Map<String, String> paraMap) {
		List<AdminBoardVO> boardList = sqlsession.selectList("adminBoard.boardListNoSearch");
		return boardList;
	}

	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("adminBoard.wordSearchShow", paraMap);
		return wordList;
	}

<<<<<<< HEAD
=======
	@Override
	public List<AdminBoardVO> boardListNoSearch() {
		List<AdminBoardVO> boardList = sqlsession.selectList("adminBoard.boardListNoSearch");
		return boardList;

	}

	@Override
	public List<AdminBoardVO> boardListSearch() {
		List<AdminBoardVO> boardList = sqlsession.selectList("adminBoard.boardListSearch");
		return boardList;
	}

	// === #116. 총 게시물 건수(totalCount) 구하기 - 검색이 있을때와 검색이 없을때로 나뉜다. === //
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("adminBoard.getTotalCount", paraMap);
		return n;
	}

	// === #119. 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것) === //
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

>>>>>>> refs/heads/main
}

package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.finalproject3.seoyeon.model.AdminBoardVO;
import com.spring.finalproject3.seoyeon.model.InterAdminBoardDAO;


public class AdminBoardService implements InterAdminBoardService {

	@Autowired
	private InterAdminBoardDAO dao; 
	
	// 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
	@Override
	public List<AdminBoardVO> boardListSearch(Map<String, String> paraMap) {
		List<AdminBoardVO> boardList = dao.boardListSearch(paraMap);
		return boardList;
	}

	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}

<<<<<<< HEAD
=======
	@Override
	public List<AdminBoardVO> boardListNoSearch() {
		List<AdminBoardVO> boardList = dao.boardListNoSearch();
		return boardList;
	}

	@Override
	public List<AdminBoardVO> boardListSearch() {
		List<AdminBoardVO> boardList = dao.boardListSearch();
		return boardList;
	}

	// === #115. 총 게시물 건수(totalCount) 구하기 - 검색이 있을때와 검색이 없을때로 나뉜다. === //
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}

	// === #118. 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것) === //
	@Override
	public List<AdminBoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<AdminBoardVO> boardList = dao.boardListSearchWithPaging(paraMap);
		return boardList;
	}

	// 선택한 글 게시판 이동시키기
	@Override
	public int boardMove(Map<String, String> paraMap) {
		int n =dao.boardMove(paraMap);
		return n;
	}

>>>>>>> refs/heads/main
}

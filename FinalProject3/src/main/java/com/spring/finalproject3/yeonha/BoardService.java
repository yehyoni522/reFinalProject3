package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements InterBoardService {

	@Autowired
	private InterBoardDAO dao;
	
	// 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기
	@Override
	public List<BoardVO> boardListNoSearch(String categoryno) {
		
		List<BoardVO> boardListNoSearch = dao.boardListNoSearch(categoryno);
		return boardListNoSearch;
	}

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	@Override
	public int add(BoardVO boardvo) {
		int n = dao.add(boardvo);
		return n;
	}



}

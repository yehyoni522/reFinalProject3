package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;


public interface InterBoardService {

	// 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기
	List<BoardVO> boardListNoSearch(String categoryno);

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	int add(BoardVO boardvo);




}

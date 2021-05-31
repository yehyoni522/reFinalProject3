package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;

public interface InterBoardDAO {

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	int add(BoardVO boardvo);

	// 총 게시물 건수(totalCount) 구하기
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한것)
	List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);

	// 글1개 조회하기 
	BoardVO getView(String seq);

	// 글조회수 1증가 하기
	void setAddReadCount(String seq);


	
}

package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;


public interface InterBoardService {

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	int add(BoardVO boardvo);
	
	// 총 게시물 건수(totalCount) 구하기
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한것)
	List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);

	// 글1개를 보여주는 페이지 요청
	BoardVO getView(Map<String, String> paraMap, String login_userid);

	// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
	BoardVO getViewWithNoAddCount(Map<String, String> paraMap);

	// 댓글쓰기(Ajax로 처리) 
	int addComment(CommentVO commentvo) throws Throwable;

	// 원게시물에 딸린 댓글들을 조회해오기
	List<CommentVO> getCommentList(String fk_seq);

	// 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리)
	List<CommentVO> getCommentListPaging(Map<String, String> paraMap);

	// 원글 글번호(parentSeq)에 해당하는 댓글의 총 페이지수를 알아오기
	int getCommentTotalPage(Map<String, String> paraMap);




}

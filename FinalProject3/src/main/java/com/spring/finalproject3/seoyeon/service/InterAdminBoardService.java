package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seoyeon.model.AdminBoardVO;

public interface InterAdminBoardService {

	// == 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 == //
	List<AdminBoardVO> boardListSearch(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);

<<<<<<< HEAD
=======
	// 검색어 없는 전체 글 목록 보여주기
	List<AdminBoardVO> boardListNoSearch();

	List<AdminBoardVO> boardListSearch();

	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을때와 검색이 없을때로 나뉜다.
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	List<AdminBoardVO> boardListSearchWithPaging(Map<String, String> paraMap);

	// 선택한 글 게시판 이동시키기
	int boardMove(Map<String, String> paraMap); 
	
	
>>>>>>> refs/heads/main
}

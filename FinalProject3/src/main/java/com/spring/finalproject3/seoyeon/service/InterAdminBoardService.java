package com.spring.finalproject3.seoyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.seoyeon.model.AdminBoardVO;

public interface InterAdminBoardService {

	// == 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 == //
	List<AdminBoardVO> boardListSearch(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);

}

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

}

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

}

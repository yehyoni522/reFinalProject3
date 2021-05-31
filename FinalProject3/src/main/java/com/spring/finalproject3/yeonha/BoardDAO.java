package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class BoardDAO implements InterBoardDAO {

	@Resource 
	private SqlSessionTemplate sqlsession; 

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession.insert("board.add", boardvo);
		return n;
	}

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("board.getTotalCount", paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한것)
	@Override
	public List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("board.boardListSearchWithPaging", paraMap);
		return boardList;
	}

	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("board.wordSearchShow", paraMap);
		return wordList;
	}




}

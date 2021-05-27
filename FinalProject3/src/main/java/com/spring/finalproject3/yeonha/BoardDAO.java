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
	
	
	@Override
	public List<BoardVO> boardListNoSearch(String categoryno) {		
		List<BoardVO> boardList = sqlsession.selectList("board.boardListNoSerch", categoryno);
		return boardList;
	}


	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession.insert("board.add", boardvo);
		return n;
	}




}

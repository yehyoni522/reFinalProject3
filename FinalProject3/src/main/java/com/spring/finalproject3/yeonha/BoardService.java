package com.spring.finalproject3.yeonha;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BoardService implements InterBoardService {

	@Autowired
	private InterBoardDAO dao;

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	@Override
	public int add(BoardVO boardvo) {
		int n = dao.add(boardvo);
		return n;
	}

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함 한것)
	@Override
	public List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = dao.boardListSearchWithPaging(paraMap);
		return boardList;
	}

	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}

	// 글1개를 보여주는 페이지 요청
	@Override
	public BoardVO getView(String seq, String login_userid) {
		BoardVO boardvo = dao.getView(seq); 
		
		if(login_userid != null &&
		   boardvo != null &&
		   !login_userid.equals(boardvo.getFk_perno()) ) {
			
		   dao.setAddReadCount(seq); 
		   boardvo = dao.getView(seq);
		}
		
		return boardvo;
	}

	// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
	@Override
	public BoardVO getViewWithNoAddCount(String seq) {
		BoardVO boardvo = dao.getView(seq); 
		return boardvo;
	}



}

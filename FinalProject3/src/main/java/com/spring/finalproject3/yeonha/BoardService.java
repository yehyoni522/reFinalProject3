package com.spring.finalproject3.yeonha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;





@Service
public class BoardService implements InterBoardService {

	@Autowired
	private InterBoardDAO dao;

	// 게시판 글쓰기완료, 게시글 등록 ( 파일첨부X)
	@Override
	public int add(BoardVO boardvo) {
		
		if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty() ) {
			// 원글쓰기 이라면 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해야 한다. 
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		
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
	public BoardVO getView(Map<String, String> paraMap, String login_userid) {
		BoardVO boardvo = dao.getView(paraMap); 
		
		if(login_userid != null &&
		   boardvo != null &&
		   !login_userid.equals(boardvo.getFk_perno()) ) {
			
		   dao.setAddReadCount(boardvo.getSeq()); 
		   boardvo = dao.getView(paraMap);
		}
		
		return boardvo;
	}

	// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
	@Override
	public BoardVO getViewWithNoAddCount(Map<String, String> paraMap) {
		BoardVO boardvo = dao.getView(paraMap); 
		return boardvo;
	}

	// 댓글쓰기(Ajax로 처리) 
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int addComment(CommentVO commentvo) throws Throwable {
		
		int n=0, result=0;
		
		n = dao.addComment(commentvo); // 댓글쓰기(tbl_comment 테이블에 insert)
		
		if(n==1) {
			result = dao.updateCommentCount(commentvo.getFk_seq()); // tbl_board 테이블에 commentCount 컬럼의 값을 1증가(update)      
		}
		
		
		return result;
	}

	// 원게시물에 딸린 댓글들을 조회해오기
	@Override
	public List<CommentVO> getCommentList(String fk_seq) {
		List<CommentVO> commentList = dao.getCommentList(fk_seq);
		return commentList;
	}

	// 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리)
	@Override
	public List<CommentVO> getCommentListPaging(Map<String, String> paraMap) {
		List<CommentVO> commentList = dao.getCommentListPaging(paraMap);
		return commentList;
	}

	// 원글 글번호(parentSeq)에 해당하는 댓글의 총 페이지수를 알아오기
	@Override
	public int getCommentTotalPage(Map<String, String> paraMap) {
		int totalPage = dao.getCommentTotalPage(paraMap);
		return totalPage;
	}

	// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
	@Override
	public BoardVO getViewNo(String seq) {
		BoardVO boardvo = dao.getViewNo(seq);
		return boardvo;
	}
	
	// 글수정 페이지 완료하기 
	@Override
	public int edit(BoardVO boardvo) {
		int n = dao.edit(boardvo);
		return n;
	}

	// 게시글 삭제하기
	@Override
	public int del(int seqno) {
		int n = dao.del(seqno);
		return n;
	}

	// (삭제할) 댓글 불러오기
	@Override
	public CommentVO getComment(String comseq) {
		CommentVO cmtvo = dao.getComment(comseq);
		return cmtvo;
	}

	// 댓글 삭제하기
	@Override
	public int delcomment(int comseq) {
		int n = dao.delcomment(comseq);
		return n;
	}

	// 첨부파일이 있는 글쓰기
	@Override
	public int add_withFile(BoardVO boardvo) {
		
		// 원글쓰기인지 , 답변글쓰기인지 구분하기 
		if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty() ) {
			// 원글쓰기 이라면 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해야 한다. 
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
				
		int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우
		return n;
	}




}

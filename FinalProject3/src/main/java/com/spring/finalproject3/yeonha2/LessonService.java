package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.yeonha.BoardVO;

@Service
public class LessonService implements InterLessonService {

	@Autowired
	private InterLessonDAO dao;
	
	
	// 공지사항 글쓰기완료(파일첨부X)
	@Override
	public int add(LessonNoticeVO lenotivo) {
		int n = dao.add(lenotivo);
		return n;
	}

	// 공지사항 글쓰기완료(파일첨부O)
	@Override
	public int add_withFile(LessonNoticeVO lenotivo) {
		int n = dao.add_withFile(lenotivo);
		return n;
	}

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기
	@Override
	public List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap) {
		List<LessonNoticeVO> notice = dao.noticeSearchWithPaging(paraMap);
		return notice;
	}

	// 조회수 증가 후 글 조회
	@Override
	public LessonNoticeVO getView(Map<String, String> paraMap, String login_userid) {

		LessonNoticeVO lenotivo = dao.getView(paraMap); // 글1개 조회하기 
		
		if(login_userid != null &&
				lenotivo != null &&
		   !login_userid.equals(lenotivo.getFk_perno()) ) {
		   // 글조회수 증가는 로그인을 한 상태에서 다른 사람의 글을 읽을때만 증가하도록 해야 한다.
			
		   dao.setAddReadCount(lenotivo.getSeq()); // 글조회수 1증가 하기
		   lenotivo = dao.getView(paraMap);
		}
		
		return lenotivo;
	}

	// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
	@Override
	public LessonNoticeVO getViewWithNoAddCount(Map<String, String> paraMap) {
		LessonNoticeVO lenotivo = dao.getView(paraMap);
		return lenotivo;
	}

	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}

	// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
	@Override
	public LessonNoticeVO getViewNo(String seq) {
		LessonNoticeVO lenotivo = dao.getViewNo(seq);
		return lenotivo;
	}

	// 게시글에 첨부파일이 있는지 확인하기(수정)
	@Override
	public String isFilename(LessonNoticeVO lenotivo) {
		String filename = dao.isFilename(lenotivo);
		return filename;
	}

	// 첨부파일 삭제 체크시 첨부파일 삭제
	@Override
	public int delFile(LessonNoticeVO lenotivo) {
		int delFile = dao.delFile(lenotivo);
		return delFile;
		
	}

	// 첨부파일이 없는 글수정 페이지 완료하기 
	@Override
	public int edit_withFile(LessonNoticeVO lenotivo) {
		int n = dao.edit_withFile(lenotivo);
		return n;
	}

	// 첨부파일이 있는 글수정 페이지 완료하기 
	@Override
	public int edit(LessonNoticeVO lenotivo) {
		int n = dao.edit(lenotivo);
		return n;
	}

	// 공지사항 삭제하기
	@Override
	public int del(int seqno) {
		int n = dao.del(seqno);
		return n;
	}	

}

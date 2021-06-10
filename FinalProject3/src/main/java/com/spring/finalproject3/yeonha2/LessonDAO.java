package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.finalproject3.yeonha.BoardVO;

@Component
@Repository
public class LessonDAO implements InterLessonDAO {

	@Resource 
	private SqlSessionTemplate sqlsession; 
	
	// 공지사항 글쓰기완료(파일첨부X)
	@Override
	public int add(LessonNoticeVO lenotivo) {		
		int n = sqlsession.insert("lesson.add", lenotivo);
		return n;
	}

	// 공지사항 글쓰기완료(파일첨부O)
	@Override
	public int add_withFile(LessonNoticeVO lenotivo) {
		int n = sqlsession.insert("lesson.add_withFile", lenotivo);
		return n;
	}

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("lesson.getTotalCount", paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기
	@Override
	public List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap) {
		List<LessonNoticeVO> notice = sqlsession.selectList("lesson.noticeSearchWithPaging", paraMap);
		return notice;
	}

	// 조회수 증가 후 글 조회
	@Override
	public LessonNoticeVO getView(Map<String, String> paraMap) {
		LessonNoticeVO lenotivo = sqlsession.selectOne("lesson.getView", paraMap);
		return lenotivo;
	}

	// 글조회수 1증가 하기
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("lesson.setAddReadCount", seq);	
		
	}

	// 자동검색어완성
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("lesson.wordSearchShow", paraMap);
		return wordList;
	}

	// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
	@Override
	public LessonNoticeVO getViewNo(String seq) {
		LessonNoticeVO lenotivo = sqlsession.selectOne("lesson.getViewNo", seq);
		return lenotivo;
	}

	// 게시글에 첨부파일이 있는지 확인하기(수정)
	@Override
	public String isFilename(LessonNoticeVO lenotivo) {
		String filename = sqlsession.selectOne("lesson.isFilename", lenotivo);
		return filename;
	}

	// 첨부파일 삭제 체크시 첨부파일 삭제 (수정)
	@Override
	public int delFile(LessonNoticeVO lenotivo) {
		int delFile = sqlsession.update("lesson.delFile", lenotivo);
		return delFile;
	}

	// 첨부파일이 없는 글수정 페이지 완료하기 
	@Override
	public int edit_withFile(LessonNoticeVO lenotivo) {
		int n = sqlsession.update("lesson.edit_withFile", lenotivo);
		return n;
	}

	// 첨부파일이 있는 글수정 페이지 완료하기
	@Override
	public int edit(LessonNoticeVO lenotivo) {
		int n = sqlsession.update("lesson.edit", lenotivo);
		return n;
	}

	// 공지사항 삭제하기
	@Override
	public int del(int seqno) {
		int n = sqlsession.delete("lesson.del", seqno);
		return n;
	}




}

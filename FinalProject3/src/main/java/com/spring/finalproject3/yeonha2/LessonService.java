package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LessonService implements InterLessonService {

	@Autowired
	private InterLessonDAO dao;
	
	// 첨부파일이 없는 글쓰기
	@Override
	public int add(LessonNoticeVO lenotivo) {
		int n = dao.add(lenotivo);
		return n;
	}

	// 첨부파일이 있는 글쓰기
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

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap) {
		List<LessonNoticeVO> lenotivo = dao.noticeSearchWithPaging(paraMap);
		return lenotivo;
	}

	// 조회수 증가 후 글 조회
	@Override
	public LessonNoticeVO getView(Map<String, String> paraMap, String login_userid) {
		
		LessonNoticeVO lenotivo = dao.getView(paraMap);
		
		if(login_userid != null &&
				lenotivo != null &&
				   !login_userid.equals(lenotivo.getFk_perno()) ) {
					
				   dao.setAddReadCount(lenotivo.getSeq()); 
				   lenotivo = dao.getView(paraMap);
				}
				
				return lenotivo;
	}	
	
	// 조회수 증가 없이 글 조회
	@Override
	public LessonNoticeVO getViewWithNoAddCount(Map<String, String> paraMap) {
		LessonNoticeVO lenotivo = dao.getView(paraMap);
		return lenotivo;
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
		int n  = dao.delFile(lenotivo);
		return n;
	}

	// 첨부파일이 없는 경우라면(수정)
	@Override
	public int edit_withFile(LessonNoticeVO lenotivo) {
		int n = dao.edit_withFile(lenotivo);
		return n;
	}

	// 글수정 페이지 완료하기 
	@Override
	public int edit(LessonNoticeVO lenotivo) {
		int n = dao.edit(lenotivo);
		return n;
	}

	// 글 삭제하기
	@Override
	public int del(int seq) {
		int n = dao.del(seq);
		return n;
	}



}

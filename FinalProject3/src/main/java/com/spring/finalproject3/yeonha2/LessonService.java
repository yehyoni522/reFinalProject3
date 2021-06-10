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

}

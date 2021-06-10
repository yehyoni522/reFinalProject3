package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

public interface InterLessonService {

	// 공지사항 글쓰기완료(파일첨부X)
	int add(LessonNoticeVO lenotivo);

	// 공지사항 글쓰기완료(파일첨부O)
	int add_withFile(LessonNoticeVO lenotivo);

	// 총 게시물 건수(totalCount) 구하기
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기
	List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap);

}

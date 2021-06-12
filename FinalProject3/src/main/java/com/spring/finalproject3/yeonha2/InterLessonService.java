package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

public interface InterLessonService {

	// 첨부파일이 없는 글쓰기
	int add(LessonNoticeVO lenotivo);

	// 첨부파일이 있는 글쓰기
	int add_withFile(LessonNoticeVO lenotivo);

	// 총 게시물 건수(totalCount) 구하기
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap);

	// 조회수 증가 후 글 조회
	LessonNoticeVO getView(Map<String, String> paraMap, String login_userid);
	
	// 조회수 증가 없이 글 조회
	LessonNoticeVO getViewWithNoAddCount(Map<String, String> paraMap);

	// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
	LessonNoticeVO getViewNo(String seq);

	// 게시글에 첨부파일이 있는지 확인하기(수정)
	String isFilename(LessonNoticeVO lenotivo);

	// 첨부파일 삭제 체크시 첨부파일 삭제
	int delFile(LessonNoticeVO lenotivo);

	// 첨부파일이 없는 경우라면(수정)
	int edit_withFile(LessonNoticeVO lenotivo);

	// 글수정 페이지 완료하기 
	int edit(LessonNoticeVO lenotivo);

	// 글 삭제하기
	int del(int seq);



}

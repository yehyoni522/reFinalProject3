package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

public interface InterLessonDAO {

	// 공지사항 글쓰기완료(파일첨부X)
	int add(LessonNoticeVO lenotivo);

	// 공지사항 글쓰기완료(파일첨부O)
	int add_withFile(LessonNoticeVO lenotivo);

	// 총 게시물 건수(totalCount) 구하기
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기
	List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap);

	// 글1개 조회하기 
	LessonNoticeVO getView(Map<String, String> paraMap);

	// 글조회수 1증가 하기
	void setAddReadCount(String seq);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);

	// 이전글, 다음글 필요없이 조회수 증가없는 글 1개 받아오기
	LessonNoticeVO getViewNo(String seq);

	// 게시글에 첨부파일이 있는지 확인하기(수정)
	String isFilename(LessonNoticeVO lenotivo);

	// 첨부파일 삭제 체크시 첨부파일 삭제
	int delFile(LessonNoticeVO lenotivo);

	// 첨부파일이 없는 글수정 페이지 완료하기 
	int edit_withFile(LessonNoticeVO lenotivo);

	// 첨부파일이 있는 글수정 페이지 완료하기
	int edit(LessonNoticeVO lenotivo);

	// 공지사항 삭제하기
	int del(int seqno);


}

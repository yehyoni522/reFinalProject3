package com.spring.finalproject3.hyeminJang.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.hyeminJang.model.InboxVO;

public interface InterMessageService {

	// 총게시물건수
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	List<InboxVO> inboxListSearchWithPaging(Map<String, String> paraMap);

	// 쪽지 글 조회
	InboxVO getInView(int inboxSeq);

	// 안읽은 글의 갯수 세기
	int getNonReadCount(int userid);

	


}

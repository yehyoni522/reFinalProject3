package com.spring.finalproject3.hyeminJang.model;

import java.util.List;
import java.util.Map;

public interface InterMessageDAO {

	// 총 게시물 건수(totalCount)
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	List<InboxVO> inboxListSearchWithPaging(Map<String, String> paraMap);

	// 쪽지1개 조회만을 해주는 것이다.
	InboxVO getInView(int inboxSeq);

	// 읽음표시 업데이트하기
	int updateReadState(int inboxSeq);

	// 안읽은 메세지갯수세기
	int getNonReadCount(int userid);
	

}

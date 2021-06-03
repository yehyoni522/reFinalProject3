package com.spring.finalproject3.hyeminJang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.model.OutboxVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;

public interface InterMessageService {

	// 총게시물건수
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	List<InboxVO> inboxListSearchWithPaging(Map<String, String> paraMap);

	// 쪽지 글 조회
	InboxVO getInView(int inboxSeq);

	// 안읽은 글의 갯수 세기
	int getNonReadCount(int userid);

	// inbox에서 체크박스에서 선택된 쪽지  삭제하기 
	int inDel(ArrayList<Integer> deleteArray);

	// 세부읽기에서 한개만 쪽지 삭제하기
	int inDelOne(int parseInt);

	// 사람번호검색
	PersonVO searchPerson(int parseInt);

	// 학과이름 가져오기
	String getNameMaj(int majseq);

	// 쪽지작성 insert
	int writeEnd(Map<String, String> paraMap);

	// inbox에서 체크박스에서 선택된 쪽지  삭제하기 
	int outDel(ArrayList<Integer> deleteArray);

	// outbox 총게시물건수
	int getTotalCountout(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것) <<outbox>>
	List<OutboxVO> outboxListSearchWithPaging(Map<String, String> paraMap);

	// 쪽지 글 조회 <<outbox>>
	OutboxVO getOutView(int outboxSeq);

	// 세부읽기에서 한개만 쪽지 삭제하기<<outbox>>
	int outDelOne(int parseInt);



	


}

package com.spring.finalproject3.hyeminJang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.model.OutboxVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.yehyeon.model.BookListVO;

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

	// 열람실 사용유무 가져오기
	int getRcheck(int perno);

	// 예약된 열람실 내용 가져오기
	List<Map<String, String>> getBooking(int perno);
	
	// 예약된 열람실 내용 가져오기 (오늘것)
	Map<String, String> getBookingToday(int perno);

	 // 입실확인update하기
	int updateUsecheck(String bno);

	// 회원정보수정하기
	int updateInfo(Map<String, String> paraMap);

	 //단대이름 알아오기
	String getNameCol(int majseq);



	


}

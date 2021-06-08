package com.spring.finalproject3.hyeminJang.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.yehyeon.model.BookListVO;

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

	// inbox에서 체크박스에서 선택된 쪽지  삭제하기 
	int inDel(ArrayList<Integer> deleteArray);

	 // 세부읽기에서 한개만 쪽지 삭제하기
	int inDelOne(int parseInt);

	// 사람번호검색
	PersonVO searchPerson(int parseInt);

	// 학과 이름 가져오기
	String getNameMaj(int majseq);

	// inbox 에 insert (로그인한 사람이 발신자, 배열로 얻어온 사람들이 수신자)
	int insertInbox(Map<String, String> paraMap);

	// outbox 에 insert (로그인한 사람이 발신자)
	int insertOutbox(Map<String, String> paraMap);

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

	//단대이름 알아오기
	String getNameCol(int majseq);


	

}

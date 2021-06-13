package com.spring.finalproject3.hyeminJang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.model.InterMessageDAO;
import com.spring.finalproject3.hyeminJang.model.InterMypageDAO;
import com.spring.finalproject3.hyeminJang.model.OutboxVO;

import com.spring.finalproject3.joseungjin.model.PersonVO;

//=== #31. Service 선언 === 
//트랜잭션 처리를 담당하는곳 , 업무를 처리하는 곳, 비지니스(Business)단
@Component
@Service
public class MessageService implements InterMessageService {
	
	@Autowired
	private InterMessageDAO dao; 
	
	@Autowired
	private InterMypageDAO dao2; 

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<InboxVO> inboxListSearchWithPaging(Map<String, String> paraMap) {
		List<InboxVO> inboxlist = dao.inboxListSearchWithPaging(paraMap);
		return inboxlist;
	}

	// 쪽지1개 조회만을 해주는 것이다.
	@Override
	public InboxVO getInView(int inboxSeq) {
		InboxVO inboxvo = dao.getInView(inboxSeq);
		
		if(inboxvo != null) {
			int n = dao.updateReadState(inboxSeq);
				n = n+1;
			if(n ==2) {
				dao.updateisRead(inboxSeq);
			}
					
				
		}
		
		return inboxvo;
	}

	// 안읽은 글의 갯수 세기
	@Override
	public int getNonReadCount(int userid) {
		
		int n = dao.getNonReadCount(userid);
		
		return  n;
	}


	// inbox에서 체크박스에서 선택된 쪽지  삭제하기 
	@Override
	public int inDel(ArrayList<Integer> deleteArray) {
		int n = dao.inDel(deleteArray);
		
		return n;
	}

	 // 세부읽기에서 한개만 쪽지 삭제하기
	@Override
	public int inDelOne(int parseInt) {
		
		int n = dao.inDelOne(parseInt);
		
		return n;
	}

	// 사람번호검색
	@Override
	public PersonVO searchPerson(int parseInt) {
		PersonVO pervo = dao.searchPerson(parseInt);
		return pervo;
	}

	// 학과 이름 가져오기
	@Override
	public String getNameMaj(int majseq) {
		String str = dao.getNameMaj(majseq);
		return str;
	}

	// 쪽지작성 insert 
	@Override
	public int writeEnd(Map<String, String> paraMap) {
		
		int n = dao.insertInbox(paraMap); // inbox 에 insert (로그인한 사람이 발신자, 배열로 얻어온 사람들이 수신자)
		
		if(n ==1) {
			n = dao.insertOutbox(paraMap); // outbox 에 insert (로그인한 사람이 발신자)
			
			n = dao.insertMessageSender(); //쪽지 발신관련 테이블에 insert
		}
		return n;
	}

	// outbox에서 체크박스에서 선택된 쪽지  삭제하기 
	@Override
	public int outDel(ArrayList<Integer> deleteArray) {
		int n = dao.outDel(deleteArray);
		
		return n;
	}

	// outbox 총게시물건수
	@Override
	public int getTotalCountout(Map<String, String> paraMap) {
		int n = dao.getTotalCountout(paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것) <<outbox>>
	@Override
	public List<OutboxVO> outboxListSearchWithPaging(Map<String, String> paraMap) {
		List<OutboxVO> outboxList = dao.outboxListSearchWithPaging(paraMap);
		return outboxList;
	}

	// 쪽지 글 조회 <<outbox>>
	@Override
	public OutboxVO getOutView(int outboxSeq) {
		OutboxVO outboxvo = dao.getOutView(outboxSeq);
		
		return outboxvo;
	}

	 // 세부읽기에서 한개만 쪽지 삭제하기<<outbox>>
	@Override
	public int outDelOne(int parseInt) {
		int n = dao.outDelOne(parseInt);
		
		return n;
	}

	// 열람실 사용유무 가져오기
	@Override
	public int getRcheck(int perno) {
		int check = dao2.getRcheck(perno);
		
		return check;
	}

	// 예약된 열람실 내용 가져오기
	@Override
	public List<Map<String, String>> getBooking(int perno) {
		List<Map<String, String>> bookvolist = dao2.getBooking(perno);
		return bookvolist;
	}

	@Override
	public Map<String, String> getBookingToday(int perno) {
		Map<String, String> bookvolist = dao2.getBookingToday(perno);
		return bookvolist;
	}

	 // 입실확인update하기
	@Override
	public int updateUsecheck(String bno) {
		int n = dao2.updateUsecheck(bno);
		return n;
	}

	// 회원정보수정하기
	@Override
	public int updateInfo(Map<String, String> paraMap) {
		int n = dao2.updateInfo(paraMap);
		return n;
	}

	 //단대이름 알아오기
	@Override
	public String getNameCol(int majseq) {
		String str = dao.getNameCol(majseq);
		return str;
	}

	// 학생이 듣고있는 수업정보가지고오기
	@Override
	public List< Map<String, String>> getscoreList(int perno) {
		List< Map<String, String>> list = dao2.getscoreList(perno);
		return list;
	}



}

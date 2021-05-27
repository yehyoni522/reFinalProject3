package com.spring.finalproject3.hyeminJang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.model.InterMessageDAO;

//=== #31. Service 선언 === 
//트랜잭션 처리를 담당하는곳 , 업무를 처리하는 곳, 비지니스(Business)단
@Component
@Service
public class MessageService implements InterMessageService {
	
	@Autowired
	private InterMessageDAO dao; 

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
			// 읽은것은 1로 변환해준다.
		}
		
		return inboxvo;
	}


}

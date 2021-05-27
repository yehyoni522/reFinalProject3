package com.spring.finalproject3.hyeminJang.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//=== #32. DAO 선언 ===
@Component
@Repository
public class MessageDAO implements InterMessageDAO {

	
	@Resource
	private SqlSessionTemplate sqlsession;
	// 로컬DB에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
	// 그러므로 sqlsession 는 null 이 아니다.

	
	// 총 게시물 건수(totalCount)구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("Message.getTotalCount", paraMap);
		
		return n;
	}


	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
	@Override
	public List<InboxVO> inboxListSearchWithPaging(Map<String, String> paraMap) {
		
		List<InboxVO> inboxlist = sqlsession.selectList("Message.inboxListSearchWithPaging", paraMap);
		
		return inboxlist;
	}

	// 쪽지1개 조회만을 해주는 것이다.
	@Override
	public InboxVO getInView(int inboxSeq) {

		InboxVO invo = sqlsession.selectOne("Message.getInView", inboxSeq);
		
		return invo;
	}

	// 읽은것은 1로 변환해준다.
	@Override
	public int updateReadState(int inboxSeq) {
		
		int n = sqlsession.update("Message.updateReadState", inboxSeq);
				
		return n;
	}
	


	
	
	
	
	
	
	
}

package com.spring.finalproject3.hyeminJang.model;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

>>>>>>> refs/heads/main
import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.finalproject3.joseungjin.model.PersonVO;

//=== #32. DAO 선언 ===
@Component
@Repository
public class MessageDAO implements InterMessageDAO {
	
	   // === #33. 의존객체 주입하기(DI: Dependency Injection) ===
	   // >>> 의존 객체 자동 주입(Automatic Dependency Injection)은
	   //     스프링 컨테이너가 자동적으로 의존 대상 객체를 찾아서 해당 객체에 필요한 의존객체를 주입하는 것을 말한다. 
	   //     단, 의존객체는 스프링 컨테이너속에 bean 으로 등록되어 있어야 한다. 

	   //     의존 객체 자동 주입(Automatic Dependency Injection)방법 3가지 
	   //     1. @Autowired ==> Spring Framework에서 지원하는 어노테이션이다. 
	   //                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.
	   
	   //     2. @Resource  ==> Java 에서 지원하는 어노테이션이다.
	   //                       스프링 컨테이너에 담겨진 의존객체를 주입할때 필드명(이름)을 찾아서 연결(의존객체주입)한다.
	   
	   //     3. @Inject    ==> Java 에서 지원하는 어노테이션이다.
	   //                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.   
	
	
/*
 	@Autowired private SqlSessionTemplate abc;
*/
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
    // 그러므로 abc 는 null 이 아니다.
	
	@Resource
	private SqlSessionTemplate sqlsession;
	// 로컬DB에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
	// 그러므로 sqlsession 는 null 이 아니다.
<<<<<<< HEAD
=======

	
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


	// 안읽은 메세지갯수세기
	@Override
	public int getNonReadCount(int userid) {
		int n = sqlsession.selectOne("Message.getNonReadCount", userid);
		return n;
	}


	// inbox에서 체크박스에서 선택된 쪽지  삭제하기 
	@Override
	public int inDel(ArrayList<Integer> deleteArray) {
		int n = 0;
		 for(int i=0; i<deleteArray.size();i++){
		        int deleteSeq = deleteArray.get(i);
		        n = sqlsession.delete("Message.inDel", deleteSeq);
		    }
		
		return n;
	}


	 // 세부읽기에서 한개만 쪽지 삭제하기
	@Override
	public int inDelOne(int parseInt) {
		int n = sqlsession.delete("Message.inDel", parseInt);
		return n;
	}

	// 사람번호검색
	@Override
	public PersonVO searchPerson(int parseInt) {
		PersonVO perno = sqlsession.selectOne("Message.searchPerson", parseInt);
		return perno;
	}

	// 학과 이름 가져오기
	@Override
	public String getNameMaj(int majseq) {
		String str = sqlsession.selectOne("Message.getNameMaj", majseq);
		return str;
	}
>>>>>>> refs/heads/main
	


	
	
	
	
	
	
	
}

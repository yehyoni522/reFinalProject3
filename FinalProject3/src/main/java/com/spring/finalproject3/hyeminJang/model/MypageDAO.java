package com.spring.finalproject3.hyeminJang.model;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.finalproject3.yehyeon.model.BookListVO;

//=== #32. DAO 선언 ===
@Component
@Repository
public class MypageDAO implements InterMypageDAO {

	
	@Resource
	private SqlSessionTemplate sqlsession;
	// 로컬DB에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
	// 그러므로 sqlsession 는 null 이 아니다.

	// 열람실 사용유무 가져오기
	@Override
	public int getRcheck(int perno) {
		int check = sqlsession.selectOne("mypage.getRcheck", perno);
		return check;
	}

	// 예약된 열람실 내용 가져오기
	@Override
	public List<Map<String, String>> getBooking(int perno) {
		
		List<Map<String, String>> booklist = null;//sqlsession.selectList("mypage.getBooking", perno);
		
		return booklist;
	}

	// 예약된 열람실 내용 가져오기 (오늘것)
	@Override
	public Map<String, String> getBookingToday(int perno) {
		
		Map<String, String> book = sqlsession.selectOne("mypage.getBookingToday", perno);
		
		return book;
	}

	// 입실확인update하기
	@Override
	public int updateUsecheck(String bno) {
		int n = sqlsession.update("mypage.updateUsecheck", bno);
		return n;
	}

	// 회원정보수정하기
	@Override
	public int updateInfo(Map<String, String> paraMap) {
		int n = sqlsession.update("mypage.updateInfo", paraMap);
		return n;
	}

	@Override
	public List< Map<String, String>> getscoreList(int perno) {
		List< Map<String, String>> list = sqlsession.selectList("mypage.getscoreList",perno);
		return list;
	}
	


	
	
	
	
	
	
	
}

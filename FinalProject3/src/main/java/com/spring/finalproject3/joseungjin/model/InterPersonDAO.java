package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterPersonDAO {

	//로그인 처리
	PersonVO getLogin(Map<String, String> paraMap);
	//아이디 찾기
	PersonVO idFind(Map<String, String> paraMap);
	
}

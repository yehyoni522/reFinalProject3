package com.spring.finalproject3.joseungjin.service;

import java.util.List;

import java.util.Map;

import com.spring.finalproject3.joseungjin.model.PersonVO;

public interface InterMemberService {

	PersonVO getLoginStudent(Map<String, String> paraMap);
	
	//아이디 찾기
	PersonVO idFind(Map<String, String> paraMap);
	//비밀번호찾기 회원 확인
	boolean isUserExist(Map<String, String> paraMap);
	//비밀번호 변경
	int pwdUpdate(Map<String, String> paraMap);
	//비밀번호 찾기
	PersonVO pwdFind(Map<String, String> paraMap);
	
	//회원 등록 정보 확인
	boolean isUserExist2(Map<String, String> paraMap);

}

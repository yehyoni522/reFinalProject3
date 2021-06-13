package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterPersonDAO {

	//로그인 처리
	PersonVO getLogin(Map<String, String> paraMap);
	//아이디 찾기
	PersonVO idFind(Map<String, String> paraMap);
	//비밀번호찾기 정보확인
	int isUserExist(Map<String, String> paraMap);
	//비밀번호 변경
	int pwdUpdate(Map<String, String> paraMap);
	//비밀번호 찾기
	PersonVO pwdFind(Map<String, String> paraMap);
	//회원등록 정보확인
	int isUserExist2(Map<String, String> paraMap);
	//관리자 회원 정보 등록
	int registerMember(Map<String, String> paraMap);
	//관리자 회원등록 아이디 중복확인
	int memberidCheck(int perno);


	
}

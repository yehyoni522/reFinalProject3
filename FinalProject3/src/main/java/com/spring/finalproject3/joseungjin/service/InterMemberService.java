package com.spring.finalproject3.joseungjin.service;

import java.util.List;

import java.util.Map;

import com.spring.finalproject3.joseungjin.model.PersonVO;

public interface InterMemberService {

	PersonVO getLoginStudent(Map<String, String> paraMap);
	
	//아이디 찾기
	PersonVO idFind(Map<String, String> paraMap);

}

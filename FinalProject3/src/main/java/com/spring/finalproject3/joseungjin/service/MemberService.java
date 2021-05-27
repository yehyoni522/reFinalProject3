package com.spring.finalproject3.joseungjin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.joseungjin.model.InterPersonDAO;

import com.spring.finalproject3.joseungjin.model.PersonVO;


@Component
@Service
public class MemberService implements InterMemberService {

	@Autowired
	private InterPersonDAO dao;


	@Override
	public PersonVO getLoginStudent(Map<String, String> paraMap) {
		PersonVO loginuser = dao.getLogin(paraMap);
	
		return loginuser;
	}

	//아이디 찾기
	@Override
	public PersonVO idFind(Map<String, String> paraMap) {
		PersonVO idFind = dao.idFind(paraMap);
		return idFind;
	}



}

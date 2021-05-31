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
	//비밀번호 찾기 회원 확인
	@Override
	public boolean isUserExist(Map<String, String> paraMap) {
		boolean isUserExist= dao.isUserExist(paraMap);
		return isUserExist;
	}
	//비밀번호 변경
	@Override
	public int pwdUpdate(Map<String, String> paraMap) {
		int n = dao.pwdUpdate(paraMap);
		return n;
	}
	//비밀번호 찾기
	@Override
	public PersonVO pwdFind(Map<String, String> paraMap) {
		PersonVO pwdFind = dao.pwdFind(paraMap);
		return pwdFind;
	}
	//회원등록  정보 확인
	@Override
	public boolean isUserExist2(Map<String, String> paraMap) {
		boolean isUserExist2= dao.isUserExist2(paraMap);
		return isUserExist2;
	}



}

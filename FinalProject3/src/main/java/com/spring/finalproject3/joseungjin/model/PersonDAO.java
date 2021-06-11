package com.spring.finalproject3.joseungjin.model;


import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class PersonDAO implements InterPersonDAO {

	@Resource
	private SqlSessionTemplate sqlsession; 
	
	//로그인 처리
	@Override
	public PersonVO getLogin(Map<String, String> paraMap) {
		PersonVO  loginuser = sqlsession.selectOne("member.getLogin", paraMap);
		return loginuser;
	}
	//아이디 찾기
	@Override
	public PersonVO idFind(Map<String, String> paraMap) {
		PersonVO  idFind= sqlsession.selectOne("member.idFind", paraMap);
		return idFind;
	}
	//비밀번호찾기정보확인
	@Override
	public int isUserExist(Map<String, String> paraMap) {
		int  n= sqlsession.selectOne("member.isUserExist", paraMap);
		return n;
	}
	//비밀번호 변경
	@Override
	public int pwdUpdate(Map<String, String> paraMap) {
		int n = sqlsession.update("member.pwdUpdate", paraMap);
		return n;
	}
	//비밀번호 조회
	@Override
	public PersonVO pwdFind(Map<String, String> paraMap) {
		PersonVO  pwdFind= sqlsession.selectOne("member.pwdFind", paraMap);
		return pwdFind;
	}
	//회원등록 정보 확인
	@Override
	public int isUserExist2(Map<String, String> paraMap) {
		int  isUserExist2= sqlsession.selectOne("member.personRegister", paraMap);
		return isUserExist2;
	}
	//관리자 회원정보등록
	@Override
	public int registerMember(Map<String, String> paraMap) {
		int  registerMember= sqlsession.insert("member.registerMember", paraMap);
		return registerMember;
	}

	
}

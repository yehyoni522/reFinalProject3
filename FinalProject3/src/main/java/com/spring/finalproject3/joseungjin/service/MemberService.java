package com.spring.finalproject3.joseungjin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.joseungjin.model.InterMain_index_BoardDAO;
import com.spring.finalproject3.joseungjin.model.InterPersonDAO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;


@Component
@Service
public class MemberService implements InterMemberService {

	@Autowired
	private InterPersonDAO dao;

	@Autowired
	private InterMain_index_BoardDAO bdao;
	
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
	public int isUserExist(Map<String, String> paraMap) {
		int n= dao.isUserExist(paraMap);
		return n;
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
	public int isUserExist2(Map<String, String> paraMap) {
		int isUserExist2= dao.isUserExist2(paraMap);
		return isUserExist2;
	}

	@Override
	public List<Main_index_BoardVO> MainboardView() {
		List<Main_index_BoardVO> MainboardList = bdao.MainboardView();
		return MainboardList;
	}
	//총페이지 알아오기
	@Override
	public int getboardTotalPage(Map<String, String> paraMap) {
		int totalPage = bdao.getboardTotalPage(paraMap);
		return totalPage;
	}
	//페이징 처리
	@Override
	public List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap) {
		List<Main_index_BoardVO> MainboardList = bdao.getboardistPaging(paraMap);
		
		return MainboardList;
	}



}

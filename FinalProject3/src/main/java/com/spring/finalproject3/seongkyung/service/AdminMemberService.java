package com.spring.finalproject3.seongkyung.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.seongkyung.model.InteradminMemberDAO;
import com.spring.finalproject3.seongkyung.model.PersonVO;

@Component
@Service
public class AdminMemberService implements InteradminMemberService {
	
	@Autowired
	private InteradminMemberDAO dao;
	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@Override
	public List<Map<String, String>> getAdminStudent(Map<String, String> paraMap) {

		List<Map<String, String>> personList = dao.getAdminStudent(paraMap);
		
		return personList;
		
	}
	
	
	// 총 학생 수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = dao.getTotalCount(paraMap);
		
		return n;
	}

	
	// 관리자 학생 상세정보페이지
	@Override
	public List<PersonVO> getStudentView(String PERNO) {
		
		List<PersonVO> personvo = dao.getStudentView(PERNO);
		
		return personvo;
	}

	
	// 총 교수 수(totalCount)
	@Override
	public int getProfessorTotalCount(Map<String, String> paraMap) {
		
		int n = dao.getProfessorTotalCount(paraMap);
		
		return n;
	}

	
	// 관리자 교수관리메뉴에서 교수리스트를 출력
	@Override
	public List<Map<String, String>> getAdminProfessor(Map<String, String> paraMap) {

		List<Map<String, String>> personList = dao.getAdminProfessor(paraMap);
		
		return personList;
	}
	

}

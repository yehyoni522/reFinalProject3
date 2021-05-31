package com.spring.finalproject3.seongkyung.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class AdminMemberDAO implements InteradminMemberDAO{
	
	@Resource
	private SqlSessionTemplate sqlsession; // 로컬DB에 연결	
	
	// 관리자 학생관리메뉴에서 학생리스트를 출력
	@Override
	public List<Map<String, String>> getAdminStudent(Map<String, String> paraMap) {

		List<Map<String,String>> personList = sqlsession.selectList("adminmember.getAdminStudent", paraMap);
				
		return personList;
	}
	
	
	// 총 학생 수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("adminmember.getTotalCount", paraMap);
		
		return n;
	}
	
	
	// 관리자 학생 상세정보페이지
	@Override
	public List<PersonVO> getStudentView(String PERNO) {
		
		List<PersonVO> personvo = sqlsession.selectList("adminmember.getStudentView", PERNO);
		
		return personvo;
	}

	
	// 총 교수 수(totalCount)
	@Override
	public int getProfessorTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession.selectOne("adminmember.getProfessorTotalCount", paraMap);
		
		return n;
	}

	
	// 관리자 교수관리메뉴에서 교수리스트를 출력
	@Override
	public List<Map<String, String>> getAdminProfessor(Map<String, String> paraMap) {

		List<Map<String,String>> personList = sqlsession.selectList("adminmember.getAdminProfessor", paraMap);
		
		return personList;
	}

}

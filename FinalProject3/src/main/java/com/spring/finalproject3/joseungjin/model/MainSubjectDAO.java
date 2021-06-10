package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.finalproject3.yehyeon.model.SubjectVO;

@Component
@Repository
public class MainSubjectDAO implements InterSubjectDAO {
	@Resource
	private SqlSessionTemplate sqlsession;
	//수강 중인 목록
	@Override
	public List<MainSubjectVO> Mainsubject(int userid) {
		List<MainSubjectVO> MainsubjectList = sqlsession.selectList("member.Mainsubject",userid);
		return MainsubjectList;
	}
	//관리자 수업 목록 가져오기
	@Override
	public List<MainSubjectVO> getsubjectList(Map<String, String> paraMap) {
		List<MainSubjectVO> adminsubjectList = sqlsession.selectList("member.adminsubject",paraMap);
		return adminsubjectList;
	}
	@Override
	public int getSubjectTotal(Map<String, String> paraMap) {
		int totalCount =sqlsession.selectOne("member.getSubjectTotal",paraMap);
		return totalCount;
	}
	// 검색어 입력시 자동완성하기//
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("member.wordSearchShow",paraMap);
		return wordList;
	}
	//엑셀 가져오기
	@Override
	public List<Map<String, String>> getExcelsubjectList() {
		List<Map<String, String>> adminExcelsubjectList =sqlsession.selectList("member.getExcelsubjectList");
		return adminExcelsubjectList;
	}

	
	
	
}

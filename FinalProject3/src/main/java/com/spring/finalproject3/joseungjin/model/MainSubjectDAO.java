package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
	
	
	
}

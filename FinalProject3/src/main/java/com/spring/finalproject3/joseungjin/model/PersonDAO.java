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
	@Override
	public PersonVO getLogin(Map<String, String> paraMap) {
		PersonVO  loginuser = sqlsession.selectOne("member.getLogin", paraMap);
		return loginuser;
	}
	
}

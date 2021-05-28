package com.spring.finalproject3.seongkyung.model;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class adminMemberDAO implements InteradminMemberDAO{
	
	@Resource
	private SqlSessionTemplate sqlsession; // 로컬DB에 연결	

}

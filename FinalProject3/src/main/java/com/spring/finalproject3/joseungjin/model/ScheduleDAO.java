package com.spring.finalproject3.joseungjin.model;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class ScheduleDAO implements InterScheduleDAO {
	@Resource
	private SqlSessionTemplate sqlsession;

	@Override
	public int scheduleAdd(ScheduleVO svo) {
		int n = sqlsession.insert("member.scheduleAdd", svo);
		return n;
	} 
	
	
}

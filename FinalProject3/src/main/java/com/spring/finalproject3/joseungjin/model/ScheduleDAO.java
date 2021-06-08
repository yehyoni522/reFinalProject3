package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

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
	//일정가져오기
	@Override
	public List<Map<String, String>> scheduleView(String perno) {
		List<Map<String,String>>scheduleList = sqlsession.selectList("member.scheduleView",perno);
		return scheduleList;
	}
	@Override
	public ScheduleVO scheduleEdit(Map<String, String> paraMap) {
		ScheduleVO scvo = sqlsession.selectOne("member.scheduleEdit",paraMap);
		return scvo;
	}
	//일정 수정
	@Override
	public int scheduleEditEnd(ScheduleVO scvo) {
		int n = sqlsession.update("member.scheduleEditEnd", scvo);
		return n;
	}
	//일정 삭제
	@Override
	public int scheduledel(Map<String, String> paraMap) {
		int n = sqlsession.delete("member.scheduledel", paraMap);
		return n;
	} 
	
	
}

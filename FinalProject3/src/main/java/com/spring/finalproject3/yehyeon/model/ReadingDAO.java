package com.spring.finalproject3.yehyeon.model;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class ReadingDAO implements InterReadingDAO {

	@Resource
	private SqlSessionTemplate sqlsession; // 로컬DB에 연결	
	
	@Override
	public List<RroomNumVO> readingRoomView() {
		
		List<RroomNumVO> rRoomList = sqlsession.selectList("Reading.readingRoomView");
		return rRoomList;
	}

	@Override
	public List<TimeVO> timeView() {
		
		List<TimeVO> timeList = sqlsession.selectList("Reading.timeView");
		return timeList;
	}

}

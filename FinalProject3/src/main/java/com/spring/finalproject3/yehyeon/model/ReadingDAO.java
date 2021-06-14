package com.spring.finalproject3.yehyeon.model;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap) {
		
		List<DetailSeatInfoVO> detailList = sqlsession.selectList("Reading.selectViewSeat", paraMap);
		return detailList;
	}

	@Override
	public DetailSeatInfoVO searchSeatInfo(String dsno) {
		
		DetailSeatInfoVO detailvo = sqlsession.selectOne("Reading.searchSeatInfo", dsno);
		
		return detailvo;
	}

	@Override
	public int updateDscheck(String dsno) {
		
		int n = sqlsession.update("Reading.updateDscheck", dsno);
		
		return n;
	}

	@Override
	public int insertBooklist(BookListVO bookvo) {
		
		int m = sqlsession.insert("Reading.insertBooklist", bookvo);
		return m;
	}

	@Override
	public List<Map<String, String>> selectDateBookList(Map<String, String> paraMap) {
		List<Map<String, String>> mapList = sqlsession.selectList("Reading.selectDateBookList", paraMap);
		return mapList;
	}

	@Override
	public List<Map<String, String>> viewChart(String bdate) {
		List<Map<String, String>> mapList = sqlsession.selectList("Reading.viewChart", bdate);
		return mapList;
	}

	@Override
	public int goDeleteBook() {
		int n = sqlsession.update("Reading.goDeleteBook");
		return n;
	}

	@Override
	public int selectRcheck(String perno) {
		int n = sqlsession.selectOne("Reading.selectRcheck", perno);
		return n;
	}

	@Override
	public int updateRcheck(String perno) {
		int n = sqlsession.update("Reading.updateRcheck", perno);
		return n;
	}

	@Override
	public List<Map<String, String>> searchProfessor(String majseq) {
		List<Map<String, String>> mapList = sqlsession.selectList("Reading.searchProfessor", majseq);
		return mapList;
	}

	@Override
	public int insertSubject(SubjectVO subvo) {
		int n = sqlsession.update("Reading.insertSubject", subvo);
		return n;
	}

	@Override
	public String getSubjectname(String subno) {
		String subject = sqlsession.selectOne("Reading.getSubjectname",subno);
		return subject;
	}

	@Override
	public List<Map<String, String>> getNoticeList(String subno) {
		List<Map<String, String>> noticeList = sqlsession.selectList("Reading.getNoticeList", subno);
		return noticeList;
	}

	@Override
	public List<Map<String, String>> getQnAList(String subno) {
		List<Map<String, String>> qnaList = sqlsession.selectList("Reading.getQnAList", subno);
		return qnaList;
	}

	@Override
	public List<Map<String, String>> getMaterialList(String subno) {
		List<Map<String, String>> materialList = sqlsession.selectList("Reading.getMaterialList", subno);
		return materialList;
	}

	@Override
	public int goUpdateRcheck() {
		int l = sqlsession.update("Reading.goUpdateRcheck");
		return l;
	}


}

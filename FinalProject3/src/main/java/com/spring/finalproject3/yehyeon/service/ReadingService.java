package com.spring.finalproject3.yehyeon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.yehyeon.model.BookListVO;
import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
import com.spring.finalproject3.yehyeon.model.InterReadingDAO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.SubjectVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;


@Component
@Service
public class ReadingService implements InterReadingService {

	@Autowired
	private InterReadingDAO dao;
	
	
	
	@Override
	public List<RroomNumVO> readingRoomView() {
		
		List<RroomNumVO> rRoomList = dao.readingRoomView();
		
		return rRoomList;
	}



	@Override
	public List<TimeVO> timeView() {
		
		List<TimeVO> timeList = dao.timeView();
		return timeList;
	}



	@Override
	public List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap) {
		
		List<DetailSeatInfoVO> detailList = dao.selectViewSeat(paraMap);
		
		return detailList;
	}


	
	@Override
	public DetailSeatInfoVO searchSeatInfo(String dsno) {
		
		DetailSeatInfoVO detailvo = dao.searchSeatInfo(dsno);
		
		return detailvo;
	}



	@Override
	public int updateDscheck(String dsno) {
		
		int n = dao.updateDscheck(dsno);
		
		return n;
	}



	@Override
	public int insertBooklist(BookListVO bookvo) {
		
		int m = dao.insertBooklist(bookvo);
		return m;
	}


	@Override
	public List<Map<String, String>> selectDateBookList(Map<String, String> paraMap) {
		List<Map<String, String>> mapList = dao.selectDateBookList(paraMap);
		return mapList;
	}



	@Override
	public List<Map<String, String>> viewChart(String bdate) {
		List<Map<String, String>> mapList = dao.viewChart(bdate);
		return mapList;
	}



	@Override
	public int goDeleteBook() {
		int n = dao.goDeleteBook();
		return n;
	}



	@Override
	public int selectRcheck(String perno) {
		int n = dao.selectRcheck(perno);
		return n;
	}



	@Override
	public int updateRcheck(String perno) {
		int n = dao.updateRcheck(perno);
		return n;
	}



	@Override
	public List<Map<String, String>> searchProfessor(String majseq) {
		List<Map<String, String>> mapList = dao.searchProfessor(majseq);
		return mapList;
	}



	@Override
	public int insertSubject(SubjectVO subvo) {
		int n = dao.insertSubject(subvo);
		return n;
	}



	@Override
	public String getSubjectname(String subno) {
		String subject = dao.getSubjectname(subno);
		return subject;
	}



	@Override
	public List<Map<String, String>> getNoticeList(String subno) {
		List<Map<String, String>> noticeList = dao.getNoticeList(subno);
		return noticeList;
	}



	@Override
	public List<Map<String, String>> getQnAList(String subno) {
		List<Map<String, String>> qnaList = dao.getQnAList(subno);
		return qnaList;
	}



	@Override
	public List<Map<String, String>> getMaterialList(String subno) {
		List<Map<String, String>> materialList = dao.getMaterialList(subno);
		return materialList;
	}

}

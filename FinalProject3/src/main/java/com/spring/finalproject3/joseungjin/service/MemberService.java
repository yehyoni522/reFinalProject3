package com.spring.finalproject3.joseungjin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.finalproject3.joseungjin.model.InterMain_index_BoardDAO;
import com.spring.finalproject3.joseungjin.model.InterPersonDAO;
import com.spring.finalproject3.joseungjin.model.InterScheduleDAO;
import com.spring.finalproject3.joseungjin.model.InterSubjectDAO;
import com.spring.finalproject3.joseungjin.model.MainSubjectVO;
import com.spring.finalproject3.joseungjin.model.Main_index_BoardVO;
import com.spring.finalproject3.joseungjin.model.PersonVO;
import com.spring.finalproject3.joseungjin.model.ScheduleVO;
import com.spring.finalproject3.yehyeon.model.SubjectVO;


@Component
@Service
public class MemberService implements InterMemberService {

	@Autowired
	private InterPersonDAO dao;

	@Autowired
	private InterMain_index_BoardDAO bdao;
	
	@Autowired
	private InterSubjectDAO sudao;
	
	@Autowired
	private InterScheduleDAO scdao;
	
	@Override
	public PersonVO getLoginStudent(Map<String, String> paraMap) {
		PersonVO loginuser = dao.getLogin(paraMap);
	
		return loginuser;
	}

	//아이디 찾기
	@Override
	public PersonVO idFind(Map<String, String> paraMap) {
		PersonVO idFind = dao.idFind(paraMap);
		return idFind;
	}
	//비밀번호 찾기 회원 확인
	@Override
	public int isUserExist(Map<String, String> paraMap) {
		int n= dao.isUserExist(paraMap);
		return n;
	}
	//비밀번호 변경
	@Override
	public int pwdUpdate(Map<String, String> paraMap) {
		int n = dao.pwdUpdate(paraMap);
		return n;
	}
	//비밀번호 찾기
	@Override
	public PersonVO pwdFind(Map<String, String> paraMap) {
		PersonVO pwdFind = dao.pwdFind(paraMap);
		return pwdFind;
	}
	//회원등록  정보 확인
	@Override
	public int isUserExist2(Map<String, String> paraMap) {
		int isUserExist2= dao.isUserExist2(paraMap);
		return isUserExist2;
	}
	//관리자 회원 등록 처리
	@Override
	public int registerMember(Map<String, String> paraMap) {
		 int registerMember=dao.registerMember(paraMap);
		return registerMember;
	}
	
	//회원등록 아이디 중복확인
	@Override
	public int memberidCheck(int perno) {
		 int n=dao.memberidCheck(perno);
			return n;
	}

	@Override
	public List<Main_index_BoardVO> MainboardView() {
		List<Main_index_BoardVO> MainboardList = bdao.MainboardView();
		return MainboardList;
	}
	//총페이지 알아오기
	@Override
	public int getboardTotalPage(Map<String, String> paraMap) {
		int totalPage = bdao.getboardTotalPage(paraMap);
		return totalPage;
	}
	//페이징 처리
	@Override
	public List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap) {
		List<Main_index_BoardVO> MainboardList = bdao.getboardistPaging(paraMap);
		
		return MainboardList;
	}
	
	//수강 중인 과목 보이기
	@Override
	public List<MainSubjectVO> Mainsubject(int userid) {
		List<MainSubjectVO> MainsubjectList =sudao.Mainsubject(userid);
		return MainsubjectList;
	}
	
	//교수 수강목록 가져오기
	@Override
	public List<MainSubjectVO> MainProsubject(int userid) {
		List<MainSubjectVO> MainProsubject =sudao.MainProsubject(userid);
		return MainProsubject;
	}

	//일정추가하기
	@Override
	public int scheduleAdd(ScheduleVO svo) {
		int n = scdao.scheduleAdd(svo);
		return n;
	}
	//일정 가져오기
	@Override
	public List<Map<String, String>> scheduleView(String perno) {
		List<Map<String,String>>scheduleList = scdao.scheduleView(perno);
		return scheduleList;
	}
	//일정 수정데이터 가져오기
	@Override
	public ScheduleVO scheduleEdit(Map<String, String> paraMap) {
		ScheduleVO scvo = scdao.scheduleEdit(paraMap);
		return scvo;
	}

	//일정 수정하기
	@Override
	public int scheduleEditEnd(ScheduleVO scvo) {
		int n = scdao.scheduleEditEnd(scvo);
		return n;
	}
	//일정삭제하기
	@Override
	public int scheduledel(Map<String, String> paraMap) {
		int n = scdao.scheduledel(paraMap);
		return n;
	}
	//관리자 수업 목록 가져오기
	@Override
	public List<MainSubjectVO> getsubjectList(Map<String, String> paraMap) {
		List<MainSubjectVO>  adminsubjectList =sudao.getsubjectList(paraMap);
		return adminsubjectList;
	}
	//총페이지 수 알아오기
	@Override
	public int getSubjectTotal(Map<String, String> paraMap) {
		int totalCount = sudao.getSubjectTotal(paraMap);
		return totalCount;
	}
	//검색어 자동
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sudao.wordSearchShow(paraMap);
	
		return wordList;
	}
	//엑셀가져오기
	@Override
	public List<Map<String, String>> getExcelsubjectList() {
		List<Map<String, String>> adminExcelsubjectList =sudao.getExcelsubjectList();
		return adminExcelsubjectList;
	}
	
	//하이차트 사용
	@Override
	public List<Map<String, String>> getbestBoard() {
		List<Map<String, String>> bestBoardList=bdao.getbestBoard();
		return bestBoardList;
	}
	







}

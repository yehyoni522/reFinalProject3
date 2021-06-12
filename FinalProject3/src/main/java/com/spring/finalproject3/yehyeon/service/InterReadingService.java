package com.spring.finalproject3.yehyeon.service;

import java.util.List;
import java.util.Map;

import com.spring.finalproject3.yehyeon.model.BookListVO;
import com.spring.finalproject3.yehyeon.model.DetailSeatInfoVO;
import com.spring.finalproject3.yehyeon.model.RroomNumVO;
import com.spring.finalproject3.yehyeon.model.SubjectVO;
import com.spring.finalproject3.yehyeon.model.TimeVO;

public interface InterReadingService {

	List<RroomNumVO> readingRoomView(); // 열람실의 고유번호와 이름을 불러온다.

	List<TimeVO> timeView(); // 각 열람실의 시간테이블을 불러온다.

	List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap); // 열람실, 시간 마다의 좌석의 정보를 불러온다.

	DetailSeatInfoVO searchSeatInfo(String dsno); // 선택한 좌석의 정보를 검색해온다.

	int updateDscheck(String dsno); // 결제 완료 후 예약여부를 0에서 1로 update 한다.

	int insertBooklist(BookListVO bookvo); //결제 완료 후 예약내역 테이블에 정보를 insert 한다.

	List<Map<String, String>> selectDateBookList(Map<String, String> paraMap); // 예약 현황에서 선택한 select 값에 따른 좌석 정보를 불러온다.

	List<Map<String, String>> viewChart(String bdate); // 차트 그리기

	int goDeleteBook(); // 현재 예약 중인 좌석을 이용 가능하도록 컬럼 값 1 -> 0 으로 변경한다.

	int selectRcheck(String perno); // 예약을 시도한 사람이 예약한 좌석이 있는지 없는지 확인한다.

	int updateRcheck(String perno); //예약한 사람의 rcheck 컬럼값을 0->1로 변경한다.

	List<Map<String, String>> searchProfessor(String majseq); // 학과 번호로 교수 목록 읽어온다.

	int insertSubject(SubjectVO subvo); // 과목 정보 insert한다.

	String getSubjectname(String subno); // 과목 이름을 불러온다.

	List<Map<String, String>> getNoticeList(String subno); // 과목별 공지사항 제목과 작성일자를 불러온다.

	List<Map<String, String>> getQnAList(String subno); // 과목별 질문게시판 제목과 작성일자를 불러온다.

	List<Map<String, String>> getMaterialList(String subno); // 과목별 수업자료 제목과 작성일자를 불러온다.

}

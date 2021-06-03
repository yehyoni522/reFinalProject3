package com.spring.finalproject3.yehyeon.model;

import java.util.List;
import java.util.Map;

public interface InterReadingDAO {

	List<RroomNumVO> readingRoomView(); // 열람실의 고유번호와 이름을 불러온다.

	List<TimeVO> timeView(); // 각 열람실의 시간테이블을 불러온다.

	List<DetailSeatInfoVO> selectViewSeat(Map<String, String> paraMap); // 열람실, 시간 마다의 좌석의 정보를 불러온다.

	DetailSeatInfoVO searchSeatInfo(String dsno); // 선택한 좌석의 정보를 검색해온다.

	int updateDscheck(String dsno); // 결제 완료 후 예약여부를 0에서 1로 update 한다.

	int insertBooklist(BookListVO bookvo); //결제 완료 후 예약내역 테이블에 정보를 insert 한다.

	List<BookListVO> selectDateBookList(Map<String, String> paraMap); // 예약 현황에서 선택한 select 값에 따른 좌석 정보를 불러온다.

}

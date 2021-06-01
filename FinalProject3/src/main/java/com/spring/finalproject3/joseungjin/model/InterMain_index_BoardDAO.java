package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterMain_index_BoardDAO {
	//메인 인기 게시글 보이게 하기
	List<Main_index_BoardVO> MainboardView();
	//페이징 처리
	int getboardTotalPage(Map<String, String> paraMap);
	List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap);

}

package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

public interface InterSubjectDAO {
	//수강 중인 목록
	List<MainSubjectVO> Mainsubject(int userid);

}

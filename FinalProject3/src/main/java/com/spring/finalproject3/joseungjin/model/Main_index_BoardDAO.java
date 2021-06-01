package com.spring.finalproject3.joseungjin.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class Main_index_BoardDAO implements InterMain_index_BoardDAO {
	@Resource
	private SqlSessionTemplate sqlsession;

	@Override
	public List<Main_index_BoardVO> MainboardView() {
		List<Main_index_BoardVO> MainboardList = sqlsession.selectList("member.MainboardView");
		return MainboardList;
	}

	@Override
	public int getboardTotalPage(Map<String, String> paraMap) {
		int totalPage = sqlsession.selectOne("member.getboardTotalPage",paraMap);
		return totalPage;
	}

	@Override
	public List<Main_index_BoardVO> getboardistPaging(Map<String, String> paraMap) {
		List<Main_index_BoardVO> MainBoardList= sqlsession.selectList("member.getboardistPaging",paraMap);
		return MainBoardList;
	}
}

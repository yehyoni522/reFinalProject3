package com.spring.finalproject3.yeonha2;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.finalproject3.yeonha.BoardVO;

@Component
@Repository
public class LessonDAO implements InterLessonDAO {

	@Resource 
	private SqlSessionTemplate sqlsession; 
	
	// 공지사항 글쓰기완료(파일첨부X)
	@Override
	public int add(LessonNoticeVO lenotivo) {		
		int n = sqlsession.insert("lesson.add", lenotivo);
		return n;
	}

	// 공지사항 글쓰기완료(파일첨부O)
	@Override
	public int add_withFile(LessonNoticeVO lenotivo) {
		int n = sqlsession.insert("lesson.add_withFile", lenotivo);
		return n;
	}

	// 총 게시물 건수(totalCount) 구하기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("lesson.getTotalCount", paraMap);
		return n;
	}

	// 페이징 처리한 글목록 가져오기
	@Override
	public List<LessonNoticeVO> noticeSearchWithPaging(Map<String, String> paraMap) {
		List<LessonNoticeVO> notice = sqlsession.selectList("lesson.noticeSearchWithPaging", paraMap);
		return notice;
	}

}

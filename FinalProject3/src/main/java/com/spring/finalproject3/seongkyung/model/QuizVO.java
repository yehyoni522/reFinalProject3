package com.spring.finalproject3.seongkyung.model;

public class QuizVO {

	private int quizno; 	// 쪽지시험_일련번호
	private int quizname;	// 쪽지시험_시험명
	private int fk_subno;	// FK_과목코드
	
	private int cnt; // 문제 갯수 읽기용
	
	public QuizVO() {}
	
	public QuizVO(int quizno, int quizname, int fk_subno) {
		super();
		this.quizno = quizno;
		this.quizname = quizname;
		this.fk_subno = fk_subno;
	}

	public int getQuizno() {
		return quizno;
	}

	public void setQuizno(int quizno) {
		this.quizno = quizno;
	}

	public int getQuizname() {
		return quizname;
	}

	public void setQuizname(int quizname) {
		this.quizname = quizname;
	}

	public int getFk_subno() {
		return fk_subno;
	}

	public void setFk_subno(int fk_subno) {
		this.fk_subno = fk_subno;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}

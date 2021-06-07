package com.spring.finalproject3.seongkyung.model;

public class QuizansVO {

	private int quizansno;		// 쪽지시험_정답_일련번호
	private int fk_quizno;		// 쪽지시험_일련번호	
	private int fk_questionno;	// 쪽지시험_문제_일련번호
	private String quizanswer;	// 쪽지시험_정답
	
	public QuizansVO() {}
	
	public QuizansVO(int quizansno, int fk_quizno, int fk_questionno, String quizanswer) {
		super();
		this.quizansno = quizansno;
		this.fk_quizno = fk_quizno;
		this.fk_questionno = fk_questionno;
		this.quizanswer = quizanswer;
	}

	public int getQuizansno() {
		return quizansno;
	}

	public void setQuizansno(int quizansno) {
		this.quizansno = quizansno;
	}

	public int getFk_quizno() {
		return fk_quizno;
	}

	public void setFk_quizno(int fk_quizno) {
		this.fk_quizno = fk_quizno;
	}

	public int getFk_questionno() {
		return fk_questionno;
	}

	public void setFk_questionno(int fk_questionno) {
		this.fk_questionno = fk_questionno;
	}

	public String getQuizanswer() {
		return quizanswer;
	}

	public void setQuizanswer(String quizanswer) {
		this.quizanswer = quizanswer;
	}
	
	
	
}

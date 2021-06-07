package com.spring.finalproject3.seongkyung.model;

public class StdtansVO {

	private int stdtanswerno; 	// 쪽지시험_학생정답_일련번호
	private int fk_perno;		// 사람번호
	private int fk_quizno;		// 쪽지시험_정답_일련번호
	private int fk_questionno;	// 쪽지시험_문제_일련번호
	private String stdtanswer;	// 쪽지시험_학생정답
	
	public StdtansVO() {}
	
	public StdtansVO(int stdtanswerno, int fk_perno, int fk_quizno, int fk_questionno, String stdtanswer) {
		super();
		this.stdtanswerno = stdtanswerno;
		this.fk_perno = fk_perno;
		this.fk_quizno = fk_quizno;
		this.fk_questionno = fk_questionno;
		this.stdtanswer = stdtanswer;
	}

	public int getStdtanswerno() {
		return stdtanswerno;
	}

	public void setStdtanswerno(int stdtanswerno) {
		this.stdtanswerno = stdtanswerno;
	}

	public int getFk_perno() {
		return fk_perno;
	}

	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}

	public int getFk_quizno() {
		return fk_quizno;
	}

	public void setFk_quizno(int fk_quizno) {
		this.fk_quizno = fk_quizno;
	}

	public String getStdtanswer() {
		return stdtanswer;
	}

	public void setStdtanswer(String stdtanswer) {
		this.stdtanswer = stdtanswer;
	}

	public int getFk_questionno() {
		return fk_questionno;
	}

	public void setFk_questionno(int fk_questionno) {
		this.fk_questionno = fk_questionno;
	}
	
	
	
}

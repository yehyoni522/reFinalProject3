package com.spring.finalproject3.seongkyung.model;

public class QuestionVO {

	private int questionno;		// 쪽지시험_문제_일련번호
	private int fk_quizno;		// 쪽지시험_일련번호
	private String qzno;		// 쪽지시험_문제_번호
	private String qzcontent;	// 쪽지시험_문제_내용		
	private String answerfirst;	// 쪽지시험_문제_답1
	private String answersecond;// 쪽지시험_문제_답2	
	private String answerthird;	// 쪽지시험_문제_답3
	private String answerfourth;// 쪽지시험_문제_답4
	
	public QuestionVO() {}
	
	public QuestionVO(int questionno, int fk_quizno, String qzno, String qzcontent, String answerfirst,
			String answersecond, String answerthird, String answerfourth) {
		super();
		this.questionno = questionno;
		this.fk_quizno = fk_quizno;
		this.qzno = qzno;
		this.qzcontent = qzcontent;
		this.answerfirst = answerfirst;
		this.answersecond = answersecond;
		this.answerthird = answerthird;
		this.answerfourth = answerfourth;
	}

	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	public int getFk_quizno() {
		return fk_quizno;
	}

	public void setFk_quizno(int fk_quizno) {
		this.fk_quizno = fk_quizno;
	}

	public String getQzno() {
		return qzno;
	}

	public void setQzno(String qzno) {
		this.qzno = qzno;
	}

	public String getQzcontent() {
		return qzcontent;
	}

	public void setQzcontent(String qzcontent) {
		this.qzcontent = qzcontent;
	}

	public String getAnswerfirst() {
		return answerfirst;
	}

	public void setAnswerfirst(String answerfirst) {
		this.answerfirst = answerfirst;
	}

	public String getAnswersecond() {
		return answersecond;
	}

	public void setAnswersecond(String answersecond) {
		this.answersecond = answersecond;
	}

	public String getAnswerthird() {
		return answerthird;
	}

	public void setAnswerthird(String answerthird) {
		this.answerthird = answerthird;
	}

	public String getAnswerfourth() {
		return answerfourth;
	}

	public void setAnswerfourth(String answerfourth) {
		this.answerfourth = answerfourth;
	}
	
}

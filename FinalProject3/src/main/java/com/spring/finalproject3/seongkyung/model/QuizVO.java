package com.spring.finalproject3.seongkyung.model;

public class QuizVO {

	private int quizno; 	// 쪽지시험_일련번호
	private String quizname;	// 쪽지시험_시험명
	private int fk_subno;	// FK_과목코드
	private String quizday;  // 시험 날짜
	
	private String subject; // 읽기용
	
	private SubjectVO subjectvo;
	private PersonVO personvo;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public QuizVO() {}
	
	public QuizVO(int quizno, String quizname, int fk_subno, String quizday) {
		super();
		this.quizday = quizday;
		this.quizno = quizno;
		this.quizname = quizname;
		this.fk_subno = fk_subno;
	}

	public SubjectVO getSubjectvo() {
		return subjectvo;
	}

	public void setSubjectvo(SubjectVO subjectvo) {
		this.subjectvo = subjectvo;
	}

	public PersonVO getPersonvo() {
		return personvo;
	}

	public void setPersonvo(PersonVO personvo) {
		this.personvo = personvo;
	}

	public int getQuizno() {
		return quizno;
	}

	public void setQuizno(int quizno) {
		this.quizno = quizno;
	}

	public String getQuizname() {
		return quizname;
	}

	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}

	public int getFk_subno() {
		return fk_subno;
	}

	public void setFk_subno(int fk_subno) {
		this.fk_subno = fk_subno;
	}

	public String getQuizday() {
		return quizday;
	}

	public void setQuizday(String quizday) {
		this.quizday = quizday;
	}
	
	

	
}

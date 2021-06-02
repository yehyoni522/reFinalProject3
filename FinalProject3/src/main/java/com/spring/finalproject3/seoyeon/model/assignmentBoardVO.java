package com.spring.finalproject3.seoyeon.model;

public class assignmentBoardVO {
	
	private String assgnno;		//과제번호
	private String fk_perno;	//사람번호
	private String fk_subno;	//과목코드
	private String subject;		//과제주제
	private String content;		//과제내용
	private String files;		//파일
	private String regDate;		//작성시간
	private String deadline;	//마감일
	private String submitCount;	//제출한 글 갯수
	
	private String score;		//점수
	private String status;		//제출여부
	public String getAssgnno() {
		return assgnno;
	}
	public void setAssgnno(String assgnno) {
		this.assgnno = assgnno;
	}
	public String getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
	}
	public String getFk_subno() {
		return fk_subno;
	}
	public void setFk_subno(String fk_subno) {
		this.fk_subno = fk_subno;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getSubmitCount() {
		return submitCount;
	}
	public void setSubmitCount(String submitCount) {
		this.submitCount = submitCount;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
}

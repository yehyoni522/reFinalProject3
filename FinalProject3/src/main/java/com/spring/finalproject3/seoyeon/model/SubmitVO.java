package com.spring.finalproject3.seoyeon.model;

public class SubmitVO {
	private String submitno;
	private String fk_perno;
	private String fk_subno;
	private String fk_assgnno;
	private String content;
	private String files;
	private String submitDate;
	private String score;
	private String status;
	
	private String submitName;
	
	public SubmitVO() { }

	public String getSubmitno() {
		return submitno;
	}

	public void setSubmitno(String submitno) {
		this.submitno = submitno;
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

	public String getFk_assgnno() {
		return fk_assgnno;
	}

	public void setFk_assgnno(String fk_assgnno) {
		this.fk_assgnno = fk_assgnno;
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

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
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

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}
	
	
	
}

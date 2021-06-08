package com.spring.finalproject3.seoyeon.model;

public class QnAVO {

	private String qnano;		//ㅇ질문번호(pk) 
	private String fk_subno;	//ㅇ과목코드 
	private String fk_perno;	//ㅇ사람번호 	
	private String subject;		//ㅇ글제목 
	private String content;		//ㅇ글내용 
	private String answer;		//ㅇ답변여부 
	private String regDate;		//ㅇ글쓴시간 
	private String status;		//ㅇ글삭제여부 
	private String groupno;		//ㅇ그룹번호 
	private String fk_qnano;	//ㅇ원글번호 
	private String depthno;		//ㅇ들여쓰기 
	
	private String name; 		// 글쓴이
	
	
	public String getQnano() {
		return qnano;
	}
	public void setQnano(String qnano) {
		this.qnano = qnano;
	}
	public String getFk_subno() {
		return fk_subno;
	}
	public void setFk_subno(String fk_subno) {
		this.fk_subno = fk_subno;
	}
	public String getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroupno() {
		return groupno;
	}
	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}
	public String getFk_qnano() {
		return fk_qnano;
	}
	public void setFk_qnano(String fk_qnano) {
		this.fk_qnano = fk_qnano;
	}
	public String getDepthno() {
		return depthno;
	}
	public void setDepthno(String depthno) {
		this.depthno = depthno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

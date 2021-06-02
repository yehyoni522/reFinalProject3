package com.spring.finalproject3.hyeminJang.model;

public class OutboxVO {

	
	
	private int outboxSeq; 	// 보낸쪽지번호seq
	private int fk_perno;	// 수신자
	private int sender;	// 발신자
	private String subject; // 내용 1000자 한계
	private String senDate;  // 보낸날짜 
	private int readState;  // 읽음표시 읽으면 0 -> 나중에 업데이트로 1로바꾸어줌
	private String outboxName; // 발신자 이름 가져오기
	
	public OutboxVO(int outboxSeq, int fk_perno, int sender, String subject, String senDate, int readState,
			String outboxName) {
		
		this.outboxSeq = outboxSeq;
		this.fk_perno = fk_perno;
		this.sender = sender;
		this.subject = subject;
		this.senDate = senDate;
		this.readState = readState;
		this.outboxName = outboxName;
	}
	
	public int getOutboxSeq() {
		return outboxSeq;
	}
	public void setOutboxSeq(int outboxSeq) {
		this.outboxSeq = outboxSeq;
	}
	public int getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSenDate() {
		return senDate;
	}
	public void setSenDate(String senDate) {
		this.senDate = senDate;
	}
	public int getReadState() {
		return readState;
	}
	public void setReadState(int readState) {
		this.readState = readState;
	}

	public String getOutboxName() {
		return outboxName;
	}

	public void setOutboxName(String outboxName) {
		this.outboxName = outboxName;
	}
	
	
	
	
}

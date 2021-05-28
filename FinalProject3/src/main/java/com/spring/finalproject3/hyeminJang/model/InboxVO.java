package com.spring.finalproject3.hyeminJang.model;

public class InboxVO {

	
	
	private int inboxSeq; 	// 받은쪽지번호seq
	private int fk_perno;	// 발신자
	private int receiver;	// 수신자
	private String subject; // 내용 1000자 한계
	private String reDate;  // 받은날짜 
	private int readState;  // 읽음표시 읽으면 0 -> 나중에 업데이트로 1로바꾸어줌
	private String inboxName; // 발신자이름
	
	public InboxVO() {};
	

	
	public InboxVO(int inboxSeq, int fk_perno, int receiver, String subject, String reDate, int readState,
			String inboxName) {
		
		this.inboxSeq = inboxSeq;
		this.fk_perno = fk_perno;
		this.receiver = receiver;
		this.subject = subject;
		this.reDate = reDate;
		this.readState = readState;
		this.inboxName = inboxName;
	}



	public int getInboxSeq() {
		return inboxSeq;
	}
	public void setInboxSeq(int inboxSeq) {
		this.inboxSeq = inboxSeq;
	}
	public int getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getReDate() {
		return reDate;
	}
	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	public int getReadState() {
		return readState;
	}
	public void setReadState(int readState) {
		this.readState = readState;
	}



	public String getInboxName() {
		return inboxName;
	}



	public void setInboxName(String inboxName) {
		this.inboxName = inboxName;
	}


	
	
	
	
}

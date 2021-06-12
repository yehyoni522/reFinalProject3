package com.spring.finalproject3.hyeminJang.model;

public class OutboxVO {

	
	
	private int outboxSeq; 	// 보낸쪽지번호seq
	private int fk_perno;	// 수신자
	private int sender;	// 발신자
	private String subject; // 내용 1000자 한계
	private String senDate;  // 보낸날짜 
	
	private String outboxName; // 수신자 이름 가져오기
	
	
	private String readDate;  // 읽으면 읽은 날짜로 업데이트
	private int isRead; //수신확인 0이면 안읽음 1이면 읽음
	
	public OutboxVO() {
		
	}
	

	
	public OutboxVO(int outboxSeq, int fk_perno, int sender, String subject, String senDate, String outboxName,
			String readDate, int isRead) {
		
		this.outboxSeq = outboxSeq;
		this.fk_perno = fk_perno;
		this.sender = sender;
		this.subject = subject;
		this.senDate = senDate;
		this.outboxName = outboxName;
		this.readDate = readDate;
		this.isRead = isRead;
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
	public String getReadDate() {
		return readDate;
	}
	public void setReadSDate(String readDate) {
		this.readDate = readDate;
	}

	public String getOutboxName() {
		return outboxName;
	}

	public void setOutboxName(String outboxName) {
		this.outboxName = outboxName;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	
	
	
	
}

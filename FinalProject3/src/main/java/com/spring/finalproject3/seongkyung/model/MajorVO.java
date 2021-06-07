package com.spring.finalproject3.seongkyung.model;

public class MajorVO {

	private int majseq;
	private String content;
	
	public MajorVO() {}

	public MajorVO(int majseq, String content) {
		super();
		this.majseq = majseq;
		this.content = content;
	}

	public int getMajseq() {
		return majseq;
	}

	public void setMajseq(int majseq) {
		this.majseq = majseq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

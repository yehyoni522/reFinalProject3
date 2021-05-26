package com.spring.finalproject3.yehyeon.model;

public class RroomNumVO {
	private int rno;
	private String rname;
	private String rcode;
	private int rtotalseat;
	
	public RroomNumVO() {}
	
	public RroomNumVO(int rno, String rname, String rcode, int rtotalseat) {
		this.rno = rno;
		this.rname = rname;
		this.rcode = rcode;
		this.rtotalseat = rtotalseat;
	}

	public int getRno() {
		return rno;
	}
	
	public void setRno(int rno) {
		this.rno = rno;
	}
	
	public String getRname() {
		return rname;
	}
	
	public void setRname(String rname) {
		this.rname = rname;
	}
	
	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public int getRtotalseat() {
		return rtotalseat;
	}
	
	public void setRtotalseat(int rtotalseat) {
		this.rtotalseat = rtotalseat;
	}
}

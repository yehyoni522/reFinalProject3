package com.spring.finalproject3.yehyeon.model;

public class TimeVO {

	private int tno;
	private String tname;
	
	public TimeVO() {}	
	
	public TimeVO(int tno, String tname) {
		this.tno = tno;
		this.tname = tname;
	}
	public int getTno() {
		return tno;
	}
	public void setTno(int tno) {
		this.tno = tno;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
	
}

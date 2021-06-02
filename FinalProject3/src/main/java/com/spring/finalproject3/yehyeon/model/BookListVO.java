package com.spring.finalproject3.yehyeon.model;

public class BookListVO {
	private int bno;
	private String bDate;
	private int fk_perno;
	private int fk_dsno;
	private int fk_tno;
	
	public int getBno() {
		return bno;
	}
	
	public void setBno(int bno) {
		this.bno = bno;
	}
	
	public String getbDate() {
		return bDate;
	}
	
	public void setbDate(String bDate) {
		this.bDate = bDate;
	}
	
	public int getFk_perno() {
		return fk_perno;
	}
	
	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}
	
	public int getFk_dsno() {
		return fk_dsno;
	}
	
	public void setFk_dsno(int fk_dsno) {
		this.fk_dsno = fk_dsno;
	}
	
	public int getFk_tno() {
		return fk_tno;
	}
	
	public void setFk_tno(int fk_tno) {
		this.fk_tno = fk_tno;
	}
	
}

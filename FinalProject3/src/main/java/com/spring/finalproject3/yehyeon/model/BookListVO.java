package com.spring.finalproject3.yehyeon.model;

public class BookListVO {
	private int bno;
	private String bdate;
	private int fk_perno;
	private int fk_dsno;
	private int fk_tno;
	
	private PersonVO personvo;
	private DetailSeatInfoVO detailvo;
	
	
	public DetailSeatInfoVO getDetailvo() {
		return detailvo;
	}

	public void setDetailvo(DetailSeatInfoVO detailvo) {
		this.detailvo = detailvo;
	}

	public PersonVO getPersonvo() {
		return personvo;
	}

	public void setPersonvo(PersonVO personvo) {
		this.personvo = personvo;
	}

	public int getBno() {
		return bno;
	}
	
	public void setBno(int bno) {
		this.bno = bno;
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

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	
}

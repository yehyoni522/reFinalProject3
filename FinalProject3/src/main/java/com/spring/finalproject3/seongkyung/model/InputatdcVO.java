package com.spring.finalproject3.seongkyung.model;

public class InputatdcVO {
	
	private int inatdcno;			// 출석 입력신호 Seq
	private int fk_atdcno;			// FK_출석신호 Seq
	private int fk_perno;			// FK_사람번호 Seq
	private int fk_subno;			// FK_과목코드 Seq
	private String inputatdcdate;	// 출석 입력신호 시간
	private int inputatdcstatus;	// 출석 상태 0 결석, 1 지각, 2 출석
	private int inputweekno;		// 출석 주차
	
	public InputatdcVO() {}

	public InputatdcVO(int inatdcno, int fk_atdcno, int fk_perno, String inputatdcdate, int inputatdcstatus, int inputweekno, int fk_subno) {
		super();
		this.inatdcno = inatdcno;
		this.fk_atdcno = fk_atdcno;
		this.fk_perno = fk_perno;
		this.fk_subno = fk_subno;
		this.inputatdcdate = inputatdcdate;
		this.inputatdcstatus = inputatdcstatus;
		this.inputweekno = inputweekno;
	}

	public int getInatdcno() {
		return inatdcno;
	}

	public void setInatdcno(int inatdcno) {
		this.inatdcno = inatdcno;
	}

	public int getFk_atdcno() {
		return fk_atdcno;
	}

	public void setFk_atdcno(int fk_atdcno) {
		this.fk_atdcno = fk_atdcno;
	}

	public int getFk_perno() {
		return fk_perno;
	}

	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}

	public String getInputatdcdate() {
		return inputatdcdate;
	}

	public void setInputatdcdate(String inputatdcdate) {
		this.inputatdcdate = inputatdcdate;
	}

	public int getInputatdcstatus() {
		return inputatdcstatus;
	}

	public void setInputatdcstatus(int inputatdcstatus) {
		this.inputatdcstatus = inputatdcstatus;
	}

	public int getInputweekno() {
		return inputweekno;
	}

	public void setInputweekno(int inputweekno) {
		this.inputweekno = inputweekno;
	}

	public int getFk_subno() {
		return fk_subno;
	}

	public void setFk_subno(int fk_subno) {
		this.fk_subno = fk_subno;
	}
	
	
	
	
}

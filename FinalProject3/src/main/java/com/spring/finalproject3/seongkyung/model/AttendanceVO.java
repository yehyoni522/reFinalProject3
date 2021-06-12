package com.spring.finalproject3.seongkyung.model;

public class AttendanceVO {

	private int atdcno; 			// 출석 신호 Seq
	private String attendancedate;	// 출석 신호 시간
	private int randomsign;			// 랜덤 신호_받아와서 저장
	private int fk_subno;			// FK_과목코드
	private int fk_perno;			// FK_사람번호
	
	public AttendanceVO() {}

	public AttendanceVO(int atdcno, String attendancedate, int randomsign, int fk_subno, int fk_perno) {
		super();
		this.atdcno = atdcno;
		this.attendancedate = attendancedate;
		this.randomsign = randomsign;
		this.fk_subno = fk_subno;
		this.fk_perno = fk_perno;
	}

	public int getAtdcno() {
		return atdcno;
	}

	public void setAtdcno(int atdcno) {
		this.atdcno = atdcno;
	}

	public String getAttendancedate() {
		return attendancedate;
	}

	public void setAttendancedate(String attendancedate) {
		this.attendancedate = attendancedate;
	}

	public int getRandomsign() {
		return randomsign;
	}

	public void setRandomsign(int randomsign) {
		this.randomsign = randomsign;
	}

	public int getFk_subno() {
		return fk_subno;
	}

	public void setFk_subno(int fk_subno) {
		this.fk_subno = fk_subno;
	}

	public int getFk_perno() {
		return fk_perno;
	}

	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}
	
	
	
	
}

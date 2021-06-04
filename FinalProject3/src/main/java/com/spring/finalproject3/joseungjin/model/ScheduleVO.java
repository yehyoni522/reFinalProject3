package com.spring.finalproject3.joseungjin.model;

public class ScheduleVO {
	
	private String calsubject;
	private String fk_perno;
	private String startDate; 
	private String endDate; 
	private String memo;
	private String color;
	
	public ScheduleVO() {}
	
	public ScheduleVO(String calsubject, String fk_perno,String startDate,String endDate, String memo,String color) {
		this.calsubject=calsubject;
		this.fk_perno = fk_perno;
		this.startDate = startDate;
		this.endDate = endDate;
		this.memo=memo;
		this.color=color;
	}

	
	public String getCalsubject() {
		return calsubject;
	}

	public void setCalsubject(String calsubject) {
		this.calsubject = calsubject;
	}


	public String getFk_perno() {
		return fk_perno;
	}

	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	

}

package com.spring.finalproject3.joseungjin.model;

public class ScheduleVO {
	
	private String calsubject;
	private String fk_perno;
	private String startDate; 
	private String endDate; 
	private String memo;
	
	
	public ScheduleVO() {}
	
	public ScheduleVO(String calsubject, String fk_perno,String startDate,String endDate, String memo) {
		this.calsubject=calsubject;
		this.fk_perno = fk_perno;
		this.startDate = startDate;
		this.endDate = endDate;
		this.memo=memo;
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

	

}

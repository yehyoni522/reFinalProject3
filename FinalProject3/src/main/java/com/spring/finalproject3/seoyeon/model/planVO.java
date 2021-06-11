package com.spring.finalproject3.seoyeon.model;

public class planVO {
	
	private String subno; //교과목코드 
	private String subname;//과목명 
	private String credit;//학점	 
	private String semester;//개설학기 
	private String day;	//강의요일
	private String time;//강의시간
	private String subcol;//개설대학 
	private String submajor;//개설학과
	private String name;//교수명 	
	private String mobile;//전화번호 
	private String email;//이메일	
	private String col;//교수 소속 대학
	private String major;//교수 소속 학과
	
	
	private String planno;//계획주차
	private String content;//계획내용
	private String etc;//계획기타
	
	
	public String getPlanno() {
		return planno;
	}
	public void setPlanno(String planno) {
		this.planno = planno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubno() {
		return subno;
	}
	public void setSubno(String subno) {
		this.subno = subno;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSubcol() {
		return subcol;
	}
	public void setSubcol(String subcol) {
		this.subcol = subcol;
	}
	public String getSubmajor() {
		return submajor;
	}
	public void setSubmajor(String submajor) {
		this.submajor = submajor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	} 
	

	
	
}

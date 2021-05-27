package com.spring.finalproject3.joseungjin.model;

public class PersonVO {
	private int perno;
	private int fk_majseq;
	private String name;
	private String gender;
	private String birthday;
	private String address;
	private String email;
	private String mobile;
	private String regDate;
	private String pwd;      
	private int idle;              
	private int identity;// 0:학생, 1:교수, 2:관리자
	
	public PersonVO() {}
	
	public PersonVO(int perno,int fk_majseq,String name,String gender,String birthday,String email,
			String address,String mobile,String regDate,String pwd,int idle,int identity) {
		this.perno = perno;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.mobile = mobile;
		this.regDate = regDate;
		this.pwd=pwd;
		this.idle=idle;
		this.identity=identity;
	}

	public int getPerno() {
		return perno;
	}

	public void setPerno(int perno) {
		this.perno = perno;
	}

	public int getFk_majseq() {
		return fk_majseq;
	}

	public void setFk_majseq(int fk_majseq) {
		this.fk_majseq = fk_majseq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}
	

	
	
}

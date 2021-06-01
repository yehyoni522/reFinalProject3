package com.spring.finalproject3.yeonha;

public class PersonVO {
	private String perno;
	private String fk_majseq;
	private String name;
	private String gender;
	private String birthday;
	private String address;
	private String email;
	private String mobile;
	private String regDate;
	private String pwd;      
	private String idle;              
	private String identity;// 0:학생, 1:교수, 2:관리자
	
	public PersonVO() {}

	
	public PersonVO(String perno, String fk_majseq, String name, String gender, String birthday, String address,
			String email, String mobile, String regDate, String pwd, String idle, String identity) {
		super();
		this.perno = perno;
		this.fk_majseq = fk_majseq;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
		this.regDate = regDate;
		this.pwd = pwd;
		this.idle = idle;
		this.identity = identity;
	}


	public String getPerno() {
		return perno;
	}

	public void setPerno(String perno) {
		this.perno = perno;
	}

	public String getFk_majseq() {
		return fk_majseq;
	}

	public void setFk_majseq(String fk_majseq) {
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

	public String getIdle() {
		return idle;
	}

	public void setIdle(String idle) {
		this.idle = idle;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	
	
	
}

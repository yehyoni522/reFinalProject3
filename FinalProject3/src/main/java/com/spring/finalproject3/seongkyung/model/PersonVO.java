package com.spring.finalproject3.seongkyung.model;

public class PersonVO {

	private int perno; 		// 사람번호
	private int fk_majseq; 	// 학과ID
	private String name; 	// 이름
	private String gender;	// 성별(남자:1  / 여자:2)
	private String birthday;// 생년월일
	private String address;	// 주소
	private String email;	// 이메일
	private String mobile;	// 휴대폰번호
	private String regDate;	// 가입일자
	private String pwd;		// 비밀번호
	private int idle;		// 휴면유무(0 : 활동중  / 1 : 휴면중) 
	private int identity;	// 정체성(0:학생, 1:교수, 2:관리자)
	
	private MajorVO majorvo;
	
	public PersonVO() {}
	
	public PersonVO(int perno, int fk_majseq, String name, String gender, String birthday, String address, String email,
			String mobile, String regDate, String pwd, int idle, int identity) {
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

	public MajorVO getMajorvo() {
		return majorvo;
	}

	public void setMajorvo(MajorVO majorvo) {
		this.majorvo = majorvo;
	}
	
	
	
}

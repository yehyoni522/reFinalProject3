package com.spring.finalproject3.seongkyung.model;

public class ClassVO {

	private int fk_perno; 	// 사람번호
	private int fk_subno; 	// 과목번호
	
	public ClassVO() {}
	
	public ClassVO(int fk_perno, int fk_subno) {
		super();
		this.fk_perno = fk_perno;
		this.fk_subno = fk_subno;
	}

	public int getFk_perno() {
		return fk_perno;
	}

	public void setFk_perno(int fk_perno) {
		this.fk_perno = fk_perno;
	}

	public int getFk_subno() {
		return fk_subno;
	}

	public void setFk_subno(int fk_subno) {
		this.fk_subno = fk_subno;
	}
	
	
	
	
}

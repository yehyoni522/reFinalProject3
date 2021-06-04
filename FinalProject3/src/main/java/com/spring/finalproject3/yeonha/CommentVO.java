package com.spring.finalproject3.yeonha;

public class CommentVO {

	private String comseq;        //댓글번호(commentSeq 시퀀스사용)
	private String fk_seq;        //원게시물 글번호
	private String fk_perno;      //사람번호(학번,교수번호,관리자번호?)
	private String content;       // 댓글내용
	private String reregDate;     // 작성일자
	private String status;
	
	private String name; // sql 문에서 가져온 tbl_person의 이름
	private String identity; // 0:학생, 1:교수, 2:관리자
	
	private String co_groupno; // 게시글 답글에서 사용
    private String fk_comseq;
    private String co_depthno;
    
	public CommentVO() {}		
	
	public CommentVO(String comseq, String fk_seq, String fk_perno, String content, String reregDate, String status,
			String name, String identity, String co_groupno, String fk_comseq, String co_depthno) {
		super();
		this.comseq = comseq;
		this.fk_seq = fk_seq;
		this.fk_perno = fk_perno;
		this.content = content;
		this.reregDate = reregDate;
		this.status = status;
		this.name = name;
		this.identity = identity;
		this.co_groupno = co_groupno;
		this.fk_comseq = fk_comseq;
		this.co_depthno = co_depthno;
	}

	public String getCo_groupno() {
		return co_groupno;
	}

	public void setCo_groupno(String co_groupno) {
		this.co_groupno = co_groupno;
	}

	public String getFk_comseq() {
		return fk_comseq;
	}

	public void setFk_comseq(String fk_comseq) {
		this.fk_comseq = fk_comseq;
	}

	public String getCo_depthno() {
		return co_depthno;
	}

	public void setCo_depthno(String co_depthno) {
		this.co_depthno = co_depthno;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComseq() {
		return comseq;
	}
	public void setComseq(String comseq) {
		this.comseq = comseq;
	}
	public String getFk_seq() {
		return fk_seq;
	}
	public void setFk_seq(String fk_seq) {
		this.fk_seq = fk_seq;
	}
	public String getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReregDate() {
		return reregDate;
	}
	public void setReregDate(String reregDate) {
		this.reregDate = reregDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}

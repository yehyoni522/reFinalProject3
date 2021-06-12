package com.spring.finalproject3.seoyeon.model;

public class AdminCommentVO {

   private String comseq;          // 댓글번호 
   private String fk_seq;    // 글번호
   private String name;			// 사용자 이름
   private String subject;      // 글제목
   private String content;      // 댓글내용 
   private String fk_perno;
   
   private String categoryno;	// 카테고리 번호
 
private String reregDate;      // 댓글쓴날짜
   private String status;       // 글삭제여부   1:사용가능한 글,  0:삭제된글 
   
   private String previousseq;      // 이전글번호
   private String previoussubject;  // 이전글제목
   private String nextseq;          // 다음글번호
   private String nextsubject;      // 다음글제목  
   
   public AdminCommentVO() {}

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

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getCategoryno() {
	return categoryno;
}

public void setCategoryno(String categoryno) {
	this.categoryno = categoryno;
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

public String getPreviousseq() {
	return previousseq;
}

public void setPreviousseq(String previousseq) {
	this.previousseq = previousseq;
}

public String getPrevioussubject() {
	return previoussubject;
}

public void setPrevioussubject(String previoussubject) {
	this.previoussubject = previoussubject;
}

public String getNextseq() {
	return nextseq;
}

public void setNextseq(String nextseq) {
	this.nextseq = nextseq;
}

public String getNextsubject() {
	return nextsubject;
}

public void setNextsubject(String nextsubject) {
	this.nextsubject = nextsubject;
}

public String getFk_perno() {
	return fk_perno;
}

public void setFk_perno(String fk_perno) {
	this.fk_perno = fk_perno;
}
   

 
}

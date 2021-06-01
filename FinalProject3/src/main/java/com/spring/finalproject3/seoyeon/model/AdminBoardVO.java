package com.spring.finalproject3.seoyeon.model;

public class AdminBoardVO {

   private String seq;          // 글번호 
   private String fk_perno;    // 사용자ID
   private String name;			// 사용자 이름
   private String subject;      // 글제목
   private String content;      // 글내용 
   
   private String files;			// 파일
   private String categoryno;	// 카테고리 번호
   private String readCount;    // 글조회수
   private String good;		// 좋아요
 
private String regDate;      // 글쓴날짜
   private String status;       // 글삭제여부   1:사용가능한 글,  0:삭제된글 
   private String commentCount;	// 댓글의 개수
   
   private String previousseq;      // 이전글번호
   private String previoussubject;  // 이전글제목
   private String nextseq;          // 다음글번호
   private String nextsubject;      // 다음글제목  
   
   public AdminBoardVO() {}
   

   
   
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
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
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getCategoryno() {
		return categoryno;
	}
	public void setCategoryno(String categoryno) {
		this.categoryno = categoryno;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
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
   
  public String getName() {
		return name;
	}
  public void setName(String name) {
		this.name = name;
	}
   
}

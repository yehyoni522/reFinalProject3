package com.spring.finalproject3.seoyeon.model;

public class AdminBoardVO {

	private String seq;          // 글번호 
   private String fk_userid;    // 사용자ID

   private String subject;      // 글제목
   private String content;      // 글내용 
   
   private String file;			// 파일
   private String categorynum;	// 카테고리 번호
   private String readCount;    // 글조회수
   private String thumb;		// 좋아요
   private String regDate;      // 글쓴날짜
   private String status;       // 글삭제여부   1:사용가능한 글,  0:삭제된글 
   
   private String previousseq;      // 이전글번호
   private String previoussubject;  // 이전글제목
   private String nextseq;          // 다음글번호
   private String nextsubject;      // 다음글제목  
   
   public String getSeq() {
	return seq;
}

public void setSeq(String seq) {
	this.seq = seq;
}

public String getFk_userid() {
	return fk_userid;
}

public void setFk_userid(String fk_userid) {
	this.fk_userid = fk_userid;
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

public String getFile() {
	return file;
}

public void setFile(String file) {
	this.file = file;
}

public String getCategorynum() {
	return categorynum;
}

public void setCategorynum(String categorynum) {
	this.categorynum = categorynum;
}

public String getThumb() {
	return thumb;
}

public void setThumb(String thumb) {
	this.thumb = thumb;
}

public String getReadCount() {
	return readCount;
}

public void setReadCount(String readCount) {
	this.readCount = readCount;
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

public String getCommentCount() {
	return commentCount;
}

public void setCommentCount(String commentCount) {
	this.commentCount = commentCount;
}

// === #81. 댓글형 게시판을 위한 commentCount 필드 추가하기 
   //          먼저 tbl_board 테이블에 commentCount 라는 컬럼이 존재해야 한다. 
   private String commentCount;     // 댓글수 
}

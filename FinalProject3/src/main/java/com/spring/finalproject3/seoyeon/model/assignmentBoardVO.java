package com.spring.finalproject3.seoyeon.model;

import org.springframework.web.multipart.MultipartFile;

public class assignmentBoardVO {
	
	private String assgnno;		//과제번호
	private String fk_perno;	//사람번호
	private String fk_subno;	//과목코드
	private String subject;		//과제주제
	private String content;		//과제내용
	private String files;		//파일
	private String regDate;		//작성시간
	private String deadline;	//마감일
	private String submitCount;	//제출한 글 갯수
	
    private String previousseq;      // 이전글번호
    private String previoussubject;  // 이전글제목
    private String nextseq;          // 다음글번호
    private String nextsubject;      // 다음글제목   
	
	private String score;		//점수
	private String status;		//제출여부
	
	private MultipartFile attach;
	   /* form 태그에서 type="file" 인 파일을 받아서 저장되는 필드이다. 
	       진짜파일 ==> WAS(톰캣) 디스크에 저장됨.
	            조심할것은 MultipartFile attach 는 오라클 데이터베이스 tbl_board 테이블의 컬럼이 아니다.   
	     /Board/src/main/webapp/WEB-INF/views/tiles1/board/add.jsp 파일에서 input type="file" 인 name 의 이름(attach)과 
	       동일해야만 파일첨부가 가능해진다.!!!!
	    */
	private String fileName;    // WAS(톰캣)에 저장될 파일명(2020120809271535243254235235234.png) 
	private String orgFilename; // 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명
	private String fileSize;    // 파일크기 
	
	public String getAssgnno() {
		return assgnno;
	}
	public void setAssgnno(String assgnno) {
		this.assgnno = assgnno;
	}
	public String getFk_perno() {
		return fk_perno;
	}
	public void setFk_perno(String fk_perno) {
		this.fk_perno = fk_perno;
	}
	public String getFk_subno() {
		return fk_subno;
	}
	public void setFk_subno(String fk_subno) {
		this.fk_subno = fk_subno;
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
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getSubmitCount() {
		return submitCount;
	}
	public void setSubmitCount(String submitCount) {
		this.submitCount = submitCount;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
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
	public MultipartFile getAttach() {
		return attach;
	}
	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOrgFilename() {
		return orgFilename;
	}
	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	
	
	
	
}

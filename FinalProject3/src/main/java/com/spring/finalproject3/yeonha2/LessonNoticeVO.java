package com.spring.finalproject3.yeonha2;

import org.springframework.web.multipart.MultipartFile;

public class LessonNoticeVO {

	private String seq;          // 글번호 
	private String fk_perno;	 // 사람번호(학번,교수번호,관리자번호?)
    private String subject;      // 글제목
    private String content;      // 글내용 
    private String readCount;    // 글조회수
    private String regDate;      // 글쓴시간   
    private String fk_subno;      // 과목코드  
   
    private String name; // sql 문에서 가져온 tbl_person의 이름   
    
    private MultipartFile attach; // form 태그에서 type="file" 인 파일을 받아서 저장되는 필드이다. 
    
    private String fileName;    // WAS(톰캣)에 저장될 파일명(2020120809271535243254235235234.png) 
    private String orgFilename; // 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명
    private String fileSize;    // 파일크기

    
    public LessonNoticeVO() {}
    
    public LessonNoticeVO(String seq, String fk_perno, String subject, String content, String readCount, String regDate,
			String fk_subno, String name, MultipartFile attach, String fileName, String orgFilename, String fileSize) {
		super();
		this.seq = seq;
		this.fk_perno = fk_perno;
		this.subject = subject;
		this.content = content;
		this.readCount = readCount;
		this.regDate = regDate;
		this.fk_subno = fk_subno;
		this.name = name;
		this.attach = attach;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
	}
    
    
    
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
	public String getFk_subno() {
		return fk_subno;
	}
	public void setFk_subno(String fk_subno) {
		this.fk_subno = fk_subno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

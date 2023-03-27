package com.jerajinsolution.file.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDto {
	private Long fno;				//파일 번호
	private String originalName;	//업로드 할 때의 파일(원본) ex)ajax.txt
	private String targetName;		//변경된 파일 이름 ex)ajax1.txt
	private String folder;			//업로드 날짜 폴더 ex)20230216
	private String regdate;			//업로드 한 날짜
	private Long fileSize;			//파일 크기
	private String fileType;		//contentType ex)image/jpeg, audio/mp3
	private Long no;				//게시판 글 번호(foreign key)
	
	public Long getFno() {
		return fno;
	}
	public void setFno(Long fno) {
		this.fno = fno;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	
	@Override
	public String toString() {
		return "FileDto [fno=" + fno + ", originalName=" + originalName + ", targetName=" + targetName + ", folder="
				+ folder + ", regdate=" + regdate + ", fileSize=" + fileSize + ", fileType=" + fileType + ", no=" + no
				+ "]";
	}
	
	public static String getFolderDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return sdf.format(date);	//20230216
	}
	
	
}

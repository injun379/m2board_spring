package com.jerajinsolution.board.service;

import java.util.List;

import com.jerajinsolution.file.FileDto;
import com.jerajinsolution.member.service.MemberDto;

public class BoardDto {
	private Long no;
	private String title;
	private String content;
	private String regdate;
	private int readcount;
	private MemberDto memberDto;
	private List<FileDto> fileList; //게시물 1개에(BoardDto)에 속해 있는 파일들
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public MemberDto getMemberDto() {
		return memberDto;
	}
	public void setMemberDto(MemberDto memberDto) {
		this.memberDto = memberDto;
	}
	public List<FileDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileDto> fileList) {
		this.fileList = fileList;
	}
	
	@Override
	public String toString() {
		return "BoardDto [no=" + no + ", title=" + title + ", content=" + content + ", regdate=" + regdate
				+ ", readcount=" + readcount + ", memberDto=" + memberDto + ", fileList=" + fileList + "]";
	}
	
}

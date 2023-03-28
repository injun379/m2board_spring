package com.jerajinsolution.file.service;

import java.util.List;

public interface FileServieInterface {
	/* 게시물 파일 등록 */
	int addBoardFile(FileDto fileDto);
	
	/* 게시물에 포함된 파일 목록 조회 */
	List<FileDto> getBoardFileList(Long no);
	
	/* 게시물 파일 조회 */
	FileDto getFile(Long fno);
}

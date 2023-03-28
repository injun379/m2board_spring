package com.jerajinsolution.file.service;

import java.util.List;

public interface FileInterface {
	/* 게시물 파일 등록 */
	int insertBoardFile(FileDto fileDto);
	
	/* 게시물에 포함된 파일 목록 조회 */
	List<FileDto> selectBoardFile(Long no);

	/* 게시물 파일 조회 */
	FileDto selectFile(Long fno);
	
}

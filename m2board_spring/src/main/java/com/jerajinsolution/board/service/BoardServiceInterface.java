package com.jerajinsolution.board.service;

import java.util.List;

public interface BoardServiceInterface {
	
	/* 게시물 등록 */
	int addBoard(BoardDto boardDto);
	
	/* 게시물 목록 */
	List<BoardDto> getBoardList();
}

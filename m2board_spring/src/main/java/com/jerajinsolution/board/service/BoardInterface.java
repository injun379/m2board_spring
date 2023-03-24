package com.jerajinsolution.board.service;

import java.util.List;

public interface BoardInterface {

	/* 게시물 등록 */
	int insertBoard(BoardDto boardDto);
	
	/* 게시물 목록 */
	List<BoardDto> selectBoardList();
}

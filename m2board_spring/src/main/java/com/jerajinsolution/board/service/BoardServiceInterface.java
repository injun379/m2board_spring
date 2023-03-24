package com.jerajinsolution.board.service;

import java.util.List;

public interface BoardServiceInterface {
	
	/* 게시물 등록 */
	int addBoard(BoardDto boardDto);
	
	/* 게시물 목록 */
	List<BoardDto> getBoardList();
	
	/* 게시물 조회 수 증가 */
	int pulsReadcount(Long no);
	
	/* 게시물 상세 보기 */
	BoardDto getBoardView(Long no);
	
	/* 게시물 수정 */
	int modifyBoard(BoardDto boardDto);
	
	/* 게시물 삭제 */
	int removeBoard(BoardDto boardDto);
}

package com.jerajinsolution.board.service;

import java.util.List;

public interface BoardInterface {

	/* 게시물 등록 */
	int insertBoard(BoardDto boardDto);
	
	/* 게시물 목록 */
	List<BoardDto> selectBoardList();
	
	/* 게시물 조회수 증가 */
	int updateReadcount(Long no);
	
	/* 게시물 상세 보기 */
	BoardDto selectBoardDetail(Long no);
	
	/* 게시물 수정 */
	int updateBoard(BoardDto boardDto);
	
	/* 게시물 삭제 */
	int deleteBoard(BoardDto boardDto);
}

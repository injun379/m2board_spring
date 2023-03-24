package com.jerajinsolution.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardService implements BoardServiceInterface {

	@Autowired 
	private BoardInterface boardInterface;
	
	/* 게시물 등록 */
	@Override
	public int addBoard(BoardDto board) {
		return boardInterface.insertBoard(board);
	}

	/* 게시물 목록 */
	@Override
	public List<BoardDto> getBoardList() {
		return boardInterface.selectBoardList();
	}

}

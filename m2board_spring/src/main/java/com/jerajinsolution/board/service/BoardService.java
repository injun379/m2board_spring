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

	/* 게시물 조회수 증가 */
	@Override
	public int pulsReadcount(Long no) {
		return boardInterface.updateReadcount(no);
	}

	/* 게시물 상세 보기 */
	@Override
	public BoardDto getBoardView(Long no) {
		return boardInterface.selectBoardDetail(no);
	}

	/* 게시물 수정 */
	@Override
	public int modifyBoard(BoardDto boardDto) {
		return boardInterface.updateBoard(boardDto);
	}

	/* 게시물 삭제 */
	@Override
	public int removeBoard(BoardDto boardDto) {
		return boardInterface.deleteBoard(boardDto);
	}

}

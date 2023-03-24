package com.jerajinsolution.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao implements BoardInterface {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplete;

	/* 게시물 등록 */
	@Override
	public int insertBoard(BoardDto boardDto) {
		return sqlSessionTemplete.insert("com.jerajinsolution.board.service.BoardMapper.boardInsert", boardDto);
	}

	/* 게시물 목록 */
	@Override
	public List<BoardDto> selectBoardList() {
		return sqlSessionTemplete.selectList("com.jerajinsolution.board.service.BoardMapper.boardList");
	}

	/* 게시물 조회수 증가 */
	@Override
	public int updateReadcount(Long no) {
		return sqlSessionTemplete.update("com.jerajinsolution.board.service.BoardMapper.boardReadcount", no);
	}

	/* 게시물 상세 보기 */
	@Override
	public BoardDto selectBoardDetail(Long no) {
		return sqlSessionTemplete.selectOne("com.jerajinsolution.board.service.BoardMapper.boardView", no);
	}

	/* 게시물 수정 */
	@Override
	public int updateBoard(BoardDto boardDto) {
		return sqlSessionTemplete.update("com.jerajinsolution.board.service.BoardMapper.boardUpdate", boardDto);
	}

	/* 게시물 삭제 */
	@Override
	public int deleteBoard(BoardDto boardDto) {
		return sqlSessionTemplete.delete("com.jerajinsolution.board.service.BoardMapper.boardDelete", boardDto);
	}
	

}

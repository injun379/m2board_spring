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

}

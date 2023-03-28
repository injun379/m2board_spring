package com.jerajinsolution.file.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao implements FileInterface {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insertBoardFile(FileDto fileDto) {
		return sqlSessionTemplate.insert("com.jerajinsolution.file.service.FileMapper.boardFileInsert", fileDto);
	}

	@Override
	public List<FileDto> selectBoardFile(Long no) {
		return sqlSessionTemplate.selectList("com.jerajinsolution.file.service.FileMapper.boardFileSelect", no);
	}

	@Override
	public FileDto selectFile(Long fno) {
		return sqlSessionTemplate.selectOne("com.jerajinsolution.file.service.FileMapper.fileSelect", fno);
	}

	@Override
	public int deleteBoardFile(Long no) {
		return sqlSessionTemplate.delete("com.jerajinsolution.file.service.FileMapper.boardFileDelete", no);
	}
	
}

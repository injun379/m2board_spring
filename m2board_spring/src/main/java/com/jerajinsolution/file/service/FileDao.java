package com.jerajinsolution.file.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao implements FileInterface {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insertBoardFile(FileDto fileDto) {
		System.out.println("fileDto: " + fileDto + " in FileDao");
		return sqlSessionTemplate.insert("com.jerajinsolution.file.service.FileMapper.boardFileInsert", fileDto);
	}
	
}

package com.jerajinsolution.file.service;

import org.springframework.beans.factory.annotation.Autowired;

public class FileService implements FileServieInterface {

	@Autowired
	private FileInterface fileInterface;
	
	@Override
	public int addBoardFile(FileDto fileDto) {
		return fileInterface.insertBoardFile(fileDto);
	}

}

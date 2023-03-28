package com.jerajinsolution.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class FileService implements FileServieInterface {

	@Autowired
	private FileInterface fileInterface;
	
	@Override
	public int addBoardFile(FileDto fileDto) {
		return fileInterface.insertBoardFile(fileDto);
	}

	@Override
	public List<FileDto> getBoardFileList(Long no) {
		return fileInterface.selectBoardFile(no);
	}

	@Override
	public FileDto getFile(Long fno) {
		return fileInterface.selectFile(fno);
	}

	@Override
	public int removeBoardFile(Long no) {
		return fileInterface.deleteBoardFile(no);
	}

}

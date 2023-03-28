package com.jerajinsolution.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jerajinsolution.board.service.BoardDto;
import com.jerajinsolution.board.service.BoardInterface;
import com.jerajinsolution.file.service.FileDto;
import com.jerajinsolution.file.service.FileInterface;
import com.jerajinsolution.member.service.MemberDto;


@Controller
public class BoardController {
	
	@Autowired
	private BoardInterface boardDao;
	
	@Autowired
	private FileInterface fileDao;
	
	/* 게시물 등록 화면 요청 */
	@RequestMapping(value="/BoardInsert.do", method = RequestMethod.GET)
	public String boardInsert() {
		return "board/insert";
	}
	
	
	/* 게시물 등록 처리 */
	@RequestMapping(value="/BoardInsertAction.do", method = RequestMethod.POST)
	public String boardinsert(Model model,
			HttpSession session,
			HttpServletRequest request,
//			@RequestParam(value="title", required=false) String title,
//			@RequestParam(value="content", required=true) String content,
			MultipartHttpServletRequest multipart) {
		
		System.out.println("multipart: " + multipart);
		
		List<MultipartFile> list = multipart.getFiles("files");
		System.out.println("list: " + list);
		
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo == null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		String fileLocation = "C:/oraclejava/upload";
		String folder = FileDto.getFolderDate();	//문자열로 현재 연월일을 얻어온다. ex)20230216
		File uploadPath = new File(fileLocation, folder);	//new File("C:/oraclejava/upload", 20230216);
		if(!uploadPath.exists()) {	//폴더 존재 여부 확인
			uploadPath.mkdirs();	//만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성
		}
		
		List<FileDto> fileList = new ArrayList<FileDto>();
		
		for(int i = 0; i<list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			long size = list.get(i).getSize();
			
			System.out.println("파일명 :" + fileRealName);
			System.out.println("사이즈" + size);
			
			File saveFile = new File(uploadPath.toString() + "/" + fileRealName);
			
			 /*파일명이 중복으로 존재할 경우*/
            if (fileRealName != null && !fileRealName.equals("")) {
                if (saveFile.exists()) {
                	//파일명 앞에 업로드 시간 초단위로 붙여 파일명 중복을 방지
                	fileRealName = System.currentTimeMillis() +  "_" + fileRealName ;
                    
                    saveFile = new File(uploadPath.toString() + "/" + fileRealName);
                }
            }
            
			System.out.println(fileRealName);
			System.out.println(saveFile);
			try {
				list.get(i).transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			FileDto fileDto = new FileDto();
			fileDto.setTargetName(fileRealName);
			fileDto.setOriginalName(list.get(i).getOriginalFilename());
			fileDto.setFileSize(list.get(i).getSize());
//			fileDto.setFileType(list.get(i).get);
			fileDto.setFolder(folder);
			fileList.add(fileDto);
			
			System.out.println("fileDto: " + fileDto);
		}
		System.out.println("fileList: " + fileList);
			
		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(request.getParameter("title"));
		boardDto.setContent(request.getParameter("content"));
		boardDto.setMemberDto(userInfo);
		
		
		int result = 0;
		
		result = boardDao.insertBoard(boardDto);
		System.out.println("seq: " + boardDto.getNo());
		
		boardDto.setFileList(fileList); //게시물 하나에 딸린 파일리스트를 담아주기
		System.out.println(boardDto);
		
		for(FileDto file : fileList) {
			file.setNo(boardDto.getNo());
			System.out.println("file: " + file);
			fileDao.insertBoardFile(file);
		}
		
		
		if(result >= 1) {
			return "redirect:BoardList.do";
		}else {
			model.addAttribute("msg", "글 등록 실패");
			model.addAttribute("url", "javascript:history.back();");
			return "/board/result";
		}
	}
	
	/* 게시물 목록 조회 */
	@RequestMapping(value="/BoardList.do", method = RequestMethod.GET)
	public String boardList(Model model,
			HttpSession session) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo == null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		System.out.println("userInfo: " + userInfo);
		String logInfo = userInfo.getName() + "(" + userInfo.getId() + ")님이 로그인하였습니다.";
		
		List<BoardDto> list = boardDao.selectBoardList();
		
		/*
		for(BoardDto dto : list) {
			System.out.println(dto);
		}
		*/
		
		model.addAttribute("logInfo", logInfo);
		model.addAttribute("list", list);
		
		return "/board/list";
	}
	
	/* 게시물 상세 보기 */
	@RequestMapping(value="/BoardView.do", method=RequestMethod.GET)
	public String boardView(Model model,
			HttpSession session,
			@RequestParam(value="no", required=true) Long no) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		if(boardDao.updateReadcount(no) >= 1) {
			BoardDto boardDto = boardDao.selectBoardDetail(no);
			List<FileDto> fileList = fileDao.selectBoardFile(no); 
			boardDto.setFileList(fileList);
			model.addAttribute("boardDto", boardDto);
			return "/board/content";
		}else {
			model.addAttribute("msg", no + "번 게시물이 존재하지 않습니다..");
			model.addAttribute("url", "BoardList.do");
			return "/board/result";
		}
	}
	
	/* 게시물 수정 화면 요청 */
	@RequestMapping(value="/BoardUpdate.do", method=RequestMethod.GET)
	public String boardUpdate(Model model,
			HttpSession session,
			@RequestParam(value="no", required=true) Long no) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo == null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}

		BoardDto boardDto = boardDao.selectBoardDetail(no);
		model.addAttribute("/board/update");
		model.addAttribute("boardDto", boardDto);
		return "/board/update";
	}
	
	/* 게시물 수정 처리 */
	@RequestMapping(value="/BoardUpdateAction.do", method=RequestMethod.POST)
	public String boardUpdateAction(Model model,
			HttpSession session,
			@RequestParam(value="no", required=true) Long no,
			@RequestParam(value="title", required=true) String title,
			@RequestParam(value="content", required=true) String content) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo == null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		String id = userInfo.getId();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		
		BoardDto boardDto = new BoardDto();
		boardDto.setNo(no);
		boardDto.setTitle(title);
		boardDto.setContent(content);
		boardDto.setMemberDto(memberDto);
		
		System.out.println(boardDto);
		
		int result = boardDao.updateBoard(boardDto);
		
		if(result >= 1) {
			return "redirect:BoardView.do?no=" + boardDto.getNo();
		}else {
			model.addAttribute("msg", "글 수정 실패");
			model.addAttribute("url", "javascript:history.back();");
			return "/board/result";
		}
	}
	
	
	/* 게시물 삭제 처리 */
	@RequestMapping(value="/BoardDeleteAction.do", method=RequestMethod.GET)
	public String boardDeleteAction(Model model,
			HttpSession session,
			@RequestParam(value="no", required=true) Long no) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		String fileLocation = "C:/oraclejava/upload";
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		String id = userInfo.getId();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		
		BoardDto boardDto = new BoardDto();
		boardDto.setNo(no);
		boardDto.setMemberDto(memberDto);
		
		
		System.out.println(boardDto);
		
		List<FileDto> fileList = fileDao.selectBoardFile(no);
		for(FileDto fileDto : fileList) {
			File file = new File(fileLocation + "/" + fileDto.getFolder() + "/" + fileDto.getTargetName());
			
			if(file.exists() == true){		
				file.delete();				// 해당 경로의 파일이 존재하면 파일 삭제
			}
		}
		
		fileDao.deleteBoardFile(no);
		int result = boardDao.deleteBoard(boardDto);
		
		if(result >= 1) {
			model.addAttribute("msg", no + "번 게시물이 삭제되었습니다.");
			model.addAttribute("url", "BoardList.do");
		}else {
			model.addAttribute("msg", "글 삭제 실패");
			model.addAttribute("url", "javascript:history.back();");
		}
		return "/board/result";
	}
		
}

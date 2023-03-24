package com.jerajinsolution.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jerajinsolution.board.service.BoardDao;
import com.jerajinsolution.board.service.BoardDto;
import com.jerajinsolution.board.service.BoardInterface;
import com.jerajinsolution.member.service.MemberDto;


@Controller
public class BoardController {
	
	@Autowired
	private BoardInterface boardDao;
	
	/* 게시물 등록 화면 요청 */
	@RequestMapping(value="/BoardInsert.do", method = RequestMethod.GET)
	public ModelAndView boardInsert() {
		return new ModelAndView("board/insert");
	}
	
	/* 게시물 등록 처리 */
	@RequestMapping(value="/BoardInsertAction.do", method = RequestMethod.POST)
	public ModelAndView boardinsert(Model model,
			HttpSession session) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			ModelAndView mav = new ModelAndView("/board/result");
			mav.addObject("msg", "먼저 로그인하셔야 합니다.");
			mav.addObject("url", "Login.do");
			return mav;
		}
		
		
//		String folder = getFolder();	//문자열로 현재 연월일을 얻어온다. ex)20230216
//		uploadPath = new File(MultiPartType.getLocation(), folder);	//new File("C:/oraclejava/upload", 20230216);
//		if(!uploadPath.exists()) {	//폴더 존재 여부 확인
//			uploadPath.mkdirs();	//만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성
//		}
//		
//		MultipartRequest multi = null; // 파일 업로드를 지원하는 request 객체로서, 'multipart/for-data' 요청을 다루는 유틸리티 클래스
//		try {
//			multi = new MultipartRequest(
//							request,			 	//기존의 request 객체
//							uploadPath.toString(), 	//파일 업로드 디렉토리
//							1024 * 1024 * 20, 		//첨부파일 최대 용량 설정(byte) : 20MB
//							"UTF-8", 				//인코딩 방식 지정
//							new DefaultFileRenamePolicy());	//중복 파일 처리
//		} catch (IOException e) {	//용량 초과 시 예외 처리
//			e.printStackTrace();
//		}
//		
//		//MultipartRequest에서 값을 꺼내온다!!!
//		String title = multi.getParameter("title");
//		String content = multi.getParameter("content");
//		
//		String id = userInfo.getId();
//		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		
		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(title);
		boardDto.setContent(content);
		boardDto.setMemberDto(memberDto);
//		
//		List<FileDto> fileList = new ArrayList<FileDto>();
//		
//		Enumeration<?> fileEnum = multi.getFileNames(); //form태그의 name들(name="file1", name="file2")
//		while(fileEnum.hasMoreElements()) {	//파일 정보가 있으면 
//			String name = (String) fileEnum.nextElement(); //ex) name="file1"
//			String targetName = multi.getFilesystemName(name); //변경된 파일 이름(중복된 파일이 있을 경우 파일명 변경되는 것에 대응)
//			
//			if(targetName != null) {
//				FileDto fileDto = new FileDto();
//				fileDto.setTargetName(targetName);
//				fileDto.setOriginalName(multi.getOriginalFileName(name));
//				fileDto.setFileSize(multi.getFile(name).length());
//				fileDto.setFileType(multi.getContentType(name));
//				fileDto.setFolder(folder);
//				fileList.add(fileDto);
//			}
//		}
//		
//		boardDto.setFileList(fileList); //게시물 하나에 딸린 파일리스트를 담아주기
		
		
		System.out.println(boardDto);
		
		boolean result = boardDao.insertBoard(boardDto);
		
		if(result) {
			return new ModelAndView("redirect:BoardList.do");
		}else {
			ModelAndView mav = new ModelAndView("/board/result");
			mav.addObject("msg", "글 등록 실패");
			mav.addObject("url", "javascript:history.back();");
			return mav;
		}
	}
	
	/* 게시물 목록 조회 */
	@RequestMapping(value="/BoardList.do", method = RequestMethod.GET)
	public ModelAndView boardList(Model model,
			HttpSession session) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			ModelAndView mav = new ModelAndView("/board/result");
			mav.addObject("msg", "먼저 로그인하셔야 합니다.");
			mav.addObject("url", "login");
			return mav;
		}
		
		String logInfo = userInfo.getName() + "(" + userInfo.getId() + ")님이 로그인하였습니다.";
		
		List<BoardDto> list = boardDao.selectBoardList();
		
		/*
		for(BoardDto dto : list) {
			System.out.println(dto);
		}
		*/
		
		ModelAndView mav = new ModelAndView("/board/list");
		mav.addObject("logInfo", logInfo);
		mav.addObject("list", list);
		
		return mav;
	}
}

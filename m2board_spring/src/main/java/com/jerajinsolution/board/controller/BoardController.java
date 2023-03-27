package com.jerajinsolution.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jerajinsolution.board.service.BoardDto;
import com.jerajinsolution.board.service.BoardInterface;
import com.jerajinsolution.member.service.MemberDto;

@Controller
public class BoardController {
	
	@Autowired
	private BoardInterface boardDao;
	
	/* 게시물 등록 화면 요청 */
	@RequestMapping(value="/BoardInsert.do", method = RequestMethod.GET)
	public String boardInsert() {
		return "board/insert";
	}
	
	/* 게시물 등록 처리 */
	@RequestMapping(value="/BoardInsertAction.do", method = RequestMethod.POST)
	public String boardinsert(Model model,
			HttpSession session,
			@RequestParam(value="title", required=true) String title,
			@RequestParam("uploadFile") MultipartFile uploadFile,
			@RequestParam(value="content", required=true) String content) {
		
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
			
		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(title);
		boardDto.setContent(content);
		boardDto.setMemberDto(userInfo);
		
		System.out.println(boardDto);
		
		int result = boardDao.insertBoard(boardDto);
		
		if(result == 1) {
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

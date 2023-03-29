package com.jerajinsolution.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jerajinsolution.cookie.Cooker;
import com.jerajinsolution.member.service.MemberDto;
import com.jerajinsolution.member.service.MemberInterface;



@Controller
public class MemberController {
	
	@Autowired
	private MemberInterface memberDao;

	/* 로그인 화면 요청 */
	@RequestMapping(value = "/Login.do", method = RequestMethod.GET)
	public String loginView() {
		return "/member/login";
	}
	
	/* 로그인 */
	@RequestMapping(value = "/LoginAction.do", method = RequestMethod.POST)
	public String login(Model model,
			HttpSession session,
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="setid", required=false) String setid,
			HttpServletRequest request,
			HttpServletResponse response) {
		
//		System.out.println(request.getServletContext().getRealPath("/").concat("resources/upload").toString());
		System.out.println(session.getServletContext().getRealPath("/resources/upload"));
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setPassword(password);
		
		System.out.println(memberDto);
		
		int setidNum = 0;
		if(request.getParameter("setid") != null) {
			setidNum = Integer.parseInt(setid);
		}
		
		MemberDto userInfo = memberDao.selectLogin(memberDto); //아이디, 패스워드 일치하는지 확인
		
		System.out.println("userInfo: " + userInfo);
		
		Cookie cookie;
		try {
			cookie = Cooker.createCookie("id", id, setidNum == 1 ? 60*60*24*30 : 0);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(userInfo != null) { //로그인 성공
			System.out.println("로그인 성공!!");
			session.setAttribute("userInfo", userInfo);
			return "redirect:BoardList.do";
		}else { //로그인 실패
			System.out.println("로그인 실패~~");
			
			int idCnt = memberDao.selectId(id);
			
			if(idCnt == 0 ) {
				model.addAttribute("msg", "존재하지 않는 아이디입니다.");
				model.addAttribute("url", "Login.do");	//로그인 화면 재요청
				return "/board/result";
			}
			else {
				model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("url", "Login.do");	//로그인 화면 재요청
				return "/board/result";
			}
		}
	}
	
	/* 로그아웃 */
	@RequestMapping(value="/Logout.do", method=RequestMethod.GET)
	public String logout(Model model,
			HttpSession session) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		String logoutInfo = userInfo.getName() + "(" + userInfo.getId() + ")님이 로그아웃 하셨습니다.";
		session.invalidate(); //세션 종료(세션 초기화)
		
		model.addAttribute("msg", logoutInfo);
		model.addAttribute("url", "Login.do");
		return "/board/result";
	}
	
	/* 회원 가입 화면 요청 */
	@RequestMapping(value="/MemberRegister.do", method=RequestMethod.GET)
	public String memberRegisterView() {
		return "/member/member";
	}
	
	/* 회원 가입 처리 요청 */
	@RequestMapping(value="/MemberRegisterAction.do", method=RequestMethod.POST) 
	public String memberRegisterAction(Model model,
			@RequestParam(value="user_id", required=true) String id,
			@RequestParam(value="user_pw", required=true) String password,
			@RequestParam(value="user_name", required=true) String name,
			@RequestParam(value="user_birth", required=true) String birth,
			@RequestParam(value="user_phone", required=true) String phone,
			@RequestParam(value="zipcode", required=true) String zipcode,
			@RequestParam(value="address1", required=true) String address1,
			@RequestParam(value="address2", required=true) String address2) {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setPassword(password);
		memberDto.setName(name);
		memberDto.setBirth(birth);
		memberDto.setPhone(phone);
		memberDto.setZipcode(zipcode);
		memberDto.setAddress1(address1);
		memberDto.setAddress2(address2);
		
		int result = memberDao.insertMember(memberDto);
		
		if(result == 1) { //회원 등록 성공
			model.addAttribute("memberDto", memberDto);
			return "/member/success";
		}else { //회원 등록 실패
			model.addAttribute("msg", id + "회원 등록에 실패하였습니다.");
			model.addAttribute("url", "javacsript:history.back();");
			return "/board/result";
		}
	}
	
	/* 아이디 중복 체크 */
	@RequestMapping(value="/CheckID.do", method= RequestMethod.POST)
	public String checkID(Model model,
			@RequestParam(value="user_id", required=true) String id) {
		int idCount = memberDao.selectId(id);
		model.addAttribute("count", idCount);
		return "/member/checkid";
	}
}

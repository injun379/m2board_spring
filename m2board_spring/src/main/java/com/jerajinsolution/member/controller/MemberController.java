package com.jerajinsolution.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jerajinsolution.member.service.MemberDto;
import com.jerajinsolution.member.service.MemberInterface;



@Controller
public class MemberController {
	
	@Autowired
	private MemberInterface memberDao;

	/* 로그인 화면 요청 */
	@RequestMapping(value = "/Login.do", method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		return new ModelAndView("/member/login");
	}
	
	/* 로그인 */
	@RequestMapping(value = "/LoginAction.do", method = RequestMethod.POST)
	public ModelAndView login(Model model,
			HttpSession session,
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="setid", required=true) String setid) {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setPassword(password);
		
		System.out.println(memberDto);
		
		MemberDto userInfo = memberDao.selectLogin(memberDto); //아이디, 패스워드 일치하는지 확인
		
		System.out.println("userInfo: " + userInfo);
		
		if(userInfo != null) { //로그인 성공
			
			System.out.println("로그인 성공!!");
			session.setAttribute("userInfo", userInfo);
			return new ModelAndView("redirect:BoardList.do");
		}else { //로그인 실패
			System.out.println("로그인 실패~~");
			ModelAndView mav = new ModelAndView("/board/result");
			mav.addObject("msg", "아이디 혹은 패스워드가 틀립니다.");
			mav.addObject("url", "Login.do");	//로그인 화면 요청
			return mav;
		}
	}
	
	/* 로그아웃 */
	@RequestMapping(value="/Logout.do", method=RequestMethod.GET)
	public ModelAndView logout(Model model,
			HttpSession session) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			ModelAndView mav = new ModelAndView("/WEB-INF/board/result.jsp");
			mav.addObject("msg", "먼저 로그인하셔야 합니다.");
			mav.addObject("url", "Login.do");
			return mav;
		}
		
		String logoutInfo = userInfo.getName() + "(" + userInfo.getId() + ")님이 로그아웃 되었습니다.";
		session.invalidate(); //세션 종료(세션 초기화)
		
		ModelAndView mav = new ModelAndView("/board/result");
		mav.addObject("msg", logoutInfo);
		mav.addObject("url", "Login.do");
		return mav;
	}
	
	/* 회원 가입 화면 요청 */
	@RequestMapping(value="/MemberRegister.do", method=RequestMethod.GET)
	public ModelAndView memberRegisterView(Model model) {
		return new ModelAndView("/member/member");
	}
	
	/* 회원 가입 처리 요청 */
	@RequestMapping(value="/MemberRegisterAction.do", method=RequestMethod.POST) 
	public ModelAndView memberRegisterAction(Model model,
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
			return new ModelAndView("/member/success", "memberDto", memberDto);
		}else { //회원 등록 실패
			ModelAndView mav = new ModelAndView("/board/result");
			mav.addObject("msg", id + "회원 등록에 실패하였습니다.");
			mav.addObject("url", "javacsript:history.back();");
			return mav;
		}
	}
	
	/* 아이디 중복 체크 */
	@RequestMapping(value="/CheckID.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView checkID(Model model,
			@RequestParam(value="user_id", required=true) String id) {
		int idCount = memberDao.selectId(id);
		return new ModelAndView("/member/checkid", "count", idCount);
	}
}

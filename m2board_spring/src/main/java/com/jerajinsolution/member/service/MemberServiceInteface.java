package com.jerajinsolution.member.service;

public interface MemberServiceInteface {
	
	/* 로그인 */
	MemberDto getLogin(MemberDto memberDto);
	
	/* 회원 가입 */
	int registerMember(MemberDto memberDto);
	
	/* 아이디 중복 체크  */
	int existId(String id);
	
}

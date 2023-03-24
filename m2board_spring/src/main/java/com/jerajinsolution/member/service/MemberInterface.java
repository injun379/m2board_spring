package com.jerajinsolution.member.service;

public interface MemberInterface {
	
	/* 로그인 */
	MemberDto selectLogin(MemberDto memberDto);
	
	/* 회원 가입 */
	int insertMember(MemberDto memberDto);
	
	/* 아이디 중복 체크 */
	int selectId(String id);

}

package com.jerajinsolution.member.service;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberService implements MemberServiceInteface {
	
		@Autowired
		private MemberInterface memberInterface;

		/* 로그인 */
		@Override
		public MemberDto getLogin(MemberDto memberDto) {
			return memberInterface.selectLogin(memberDto);
		}

		/* 회원 가입 */
		@Override
		public int registerMember(MemberDto memberDto) {
			return memberInterface.insertMember(memberDto);
		}
		
		/* 아이디 중복 체크 */
		@Override
		public int existId(String id) {
			return memberInterface.selectId(id);
		}
		
}

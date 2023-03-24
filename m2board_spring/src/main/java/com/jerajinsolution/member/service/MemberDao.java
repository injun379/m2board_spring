package com.jerajinsolution.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao implements MemberInterface {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplete;
	
	@Override
	public MemberDto selectLogin(MemberDto memberDto) {
		return sqlSessionTemplete.selectOne("com.jerajinsolution.member.service.MemberMapper.login", memberDto) ;
	}

	@Override
	public int selectId(String id) {
		return sqlSessionTemplete.selectOne("com.jerajinsolution.member.service.MemberMapper.checkId", id);
	}

	@Override
	public int insertMember(MemberDto memberDto) {
		return sqlSessionTemplete.insert("com.jerajinsolution.member.service.MemberMapper.insertMember", memberDto);
	}

}

package com.example.booklog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.booklog.dto.MemberDTO;
import com.example.booklog.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

	public void join(MemberDTO memberDTO) {
		memberMapper.insertMember(memberDTO);
	}
	
//	[아이디 중복 확인] 반환값이 true라면 이미 사용중인 아이디
	public boolean isLoginIdDuplicated(String loginId) {
		return memberMapper.countByLoginId(loginId) > 0;
	}

//	[로그인] 일치하는 회원이 있으면 MemberDTO를 반환, 없으면 null
	public MemberDTO login(String loginId, String pw) {
		return memberMapper.selectByLoginIdAndPwd(loginId, pw);
	}
	
	
	

}











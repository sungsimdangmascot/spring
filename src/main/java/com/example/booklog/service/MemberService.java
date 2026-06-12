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

//	public boolean isLoginIdDuplicated(String loginId) {
//		return memberMapper.countByLoginId(loginId) > 0;
//	}
//
//	public MemberDTO login(String loginId, String pw) {
//		return memberMapper.selectByLoginIdAndPwd(loginId, pw);
//	}
	
	
	

}











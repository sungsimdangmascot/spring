package com.example.booklog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.booklog.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	void insertMember(MemberDTO memberDTO);

	int countByLoginId(@Param("loginId") String loginId);
//
	MemberDTO selectByLoginIdAndPwd(@Param("loginId") String loginId,
								   @Param("pw") String pw);
}










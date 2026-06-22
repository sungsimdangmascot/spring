package com.example.booklog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.booklog.dto.MemberDTO;
import com.example.booklog.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/join")
	public String joinForm() {
		return "member/join";
	}
	// [회원가입 처리] POST /member/join
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {
		memberDTO.setMemberLoginId(memberDTO.getMemberLoginId().trim());
		if(memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
			//중복된 아이디가 있는 경우
			model.addAttribute("error", "duplicate");
			return "member/join";
		}
		
		memberService.join(memberDTO);
		return "redirect:/member/login";
	}
	
//	[로그인 폼] GET /member/login
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}
	
	
	@PostMapping("/login")
	public String login(@RequestParam("loginId") String loginId,
					    @RequestParam("pw") String pw,
					    HttpSession session, Model model){
//		DB에서 아이디, 비밀번호 일치여부 확인
		MemberDTO loginMember = memberService.login(loginId.trim(), pw);
		
		if(loginMember == null) {
//			로그인 실패
			model.addAttribute("error", "fail");
			return "member/login";
		}
		
//		로그인 성공 - 세션에 회원 정보를 저장
		session.setAttribute("loginMember", loginMember);
//		System.out.println(session.getAttribute("loginMember"));
		return "redirect:/board/list";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main";
	}
}

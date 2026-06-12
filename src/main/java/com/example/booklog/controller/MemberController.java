package com.example.booklog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {
//		if(memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
//			model.addAttribute("error", "duplicate");
//			return "member/join";
//		}
		
		memberService.join(memberDTO);
		return "redirect:/member/login";
	}
	
	
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}
	
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/board/list";
//	}
}

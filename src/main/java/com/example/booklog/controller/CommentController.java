package com.example.booklog.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.booklog.dto.CommentDTO;
import com.example.booklog.dto.MemberDTO;
import com.example.booklog.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment")
public class CommentController {
   
   @Autowired
   private CommentService commentService;
//   [댓글 등록
   
   @PostMapping("/insert")
   @ResponseBody
   public Map<String, Object> insert(@RequestBody CommentDTO commentDTO,
                        HttpSession session){
      
      Map<String, Object> result = new HashMap<>();
      
//      1) 로그인 여부 확인
      MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
      if(loginMember == null) {
//         로그인 안된 상태 
         result.put("success", false);
         result.put("message", "로그인이 필요합니다");
         return result;
      }
   
//       댓글 작성자 번호
      commentDTO.setMemberId(loginMember.getMemberId());
      
//       DB에 댓글 추가
      commentService.insertComment(commentDTO);
      
//      작성자 아이디를 DTO에 추가
      commentDTO.setMemberLoginId(loginMember.getMemberLoginId());
      commentDTO.setMemberId(loginMember.getMemberId());
      
//      작성시간

      String now = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      commentDTO.setCommentRegDate(now);
      
//      성공 응답 
      result.put("success", true);
      result.put("comment", commentDTO);
      return result;
   }
   
//   댓글 삭제

   @PostMapping("/delete/{commentId}")
   @ResponseBody
   public Map<String, Object> delete(@PathVariable("commentId") int commentId,
               HttpSession session){
      Map<String, Object> result = new HashMap<>();
      
      // 로그인 여부 확인
      MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
      if(loginMember == null) {
         result.put("success", false);
         result.put("message", "로그인이 필요합니다.");
         return result;
      }
      
      boolean deleted = commentService.deleteComment(commentId, loginMember.getMemberId());
      if(!deleted) {
         result.put("success", false);
         result.put("message", "삭제 권한이 없습니다.");
         return result;
      }
      
//      성공 응답 반환

      result.put("success", true);
      return result;
   }

}









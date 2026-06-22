package com.example.booklog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.booklog.dto.CommentDTO;
import com.example.booklog.mapper.CommentMapper;

@Service
public class CommentService {
   
   @Autowired
   private CommentMapper commentMapper;
   
//   [댓글 목록]
   public List<CommentDTO> getCommentsByBoardId(int boardId){
      return commentMapper.selectByBoardId(boardId);
   }
   
//   [댓글 등록]
   public void insertComment(CommentDTO commentDTO) {
      commentMapper.insertComment(commentDTO);
   }
   
//   [댓글 삭제]
   public boolean deleteComment(int commentId, int memberId) {
      return commentMapper.deleteComment(commentId, memberId) > 0;
   }

}

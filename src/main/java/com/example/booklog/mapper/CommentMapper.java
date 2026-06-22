package com.example.booklog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.booklog.dto.CommentDTO;

@Mapper
public interface CommentMapper {

   List<CommentDTO> selectByBoardId(@Param("boardId") int boardId);

   void insertComment(CommentDTO commentDTO);

   int deleteComment(@Param("commentId") int commentId,
                     @Param("memberId") int memberId);

   void deleteByBoardId(@Param("boardId") int boardId);
   
   
}











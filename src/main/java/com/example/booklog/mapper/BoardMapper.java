package com.example.booklog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.booklog.dto.BoardDTO;

@Mapper
public interface BoardMapper {
		// [게시글 목록]
		List<BoardDTO> selectAll(@Param("offset") int offset, 
				@Param("size") int size,
				@Param("keyword") String keyword);
		
	//   [전체 게시글 수]
	   int selectCount(@Param("keyword") String keyword);
	   
	//   [게시글 상세 조회] 게시글 번호 1개 조회
	   BoardDTO selectById(@Param("boardId") int boardId);
	   
	//   [조회수 증가] 상세보기 화면을 열때 실행
	   void updateHit(@Param("boardId") int boardId);
	   
	   
	//   [게시글 작성]
	   void insertBoard(BoardDTO boardDTO);
	   
	//   [게시글 수정] 제목, 내용 수정
	   void updateBoard(BoardDTO boardDTO);
	   
	//   [게시글 삭제] 게시글 번호에 해당하는 행 삭제
	   void deleteBoard(@Param("boardId") int boardId);
	}


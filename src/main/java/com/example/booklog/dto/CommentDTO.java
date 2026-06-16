package com.example.booklog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
	private int commentId; 
	private String commentContent; 
	private String commentRegDate; 
	private int boardId;
	private int memberId; 
	private String memberLoginId;
	
}















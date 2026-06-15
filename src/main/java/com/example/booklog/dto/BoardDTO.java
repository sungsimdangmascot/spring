package com.example.booklog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

	private int boardId;
	private String boardTitle;
	private String boardAuthor;
	private String boardPublisher;
	private String boardContent;
	private String boardRegDate;
	private int boardViews;
	private int memberId;
	private String memberLoginId;
}

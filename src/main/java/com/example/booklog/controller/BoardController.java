package com.example.booklog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.booklog.dto.BoardDTO;
import com.example.booklog.dto.CommentDTO;
import com.example.booklog.dto.MemberDTO;
import com.example.booklog.service.BoardService;
import com.example.booklog.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;

	@GetMapping({ "", "/", "/main" })
	public String main() {
		return "board/main";
	}
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		keyword = keyword == null ? "" : keyword.trim();
		if (page < 1) {
			page = 1;
		}
		int size = 10; // 한페이지에 표시할 게시글 수

		int totalCount = boardService.getBoardCount(keyword); // 전체 게시글 수

//      페이지 수 계산
		int totalPages = (int) Math.ceil((double) totalCount / size);
		if (totalPages == 0) {
			totalPages = 1;
		}
		if (page > totalPages) {
			page = totalPages;
		}
		int offset = (page - 1) * size;
		List<BoardDTO> boardList = boardService.getBoardList(offset, size, keyword);
//   
		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword);
//      // 검색어를 화면 유지용으로 전달

		return "board/list";
	}

// [게시글 작성 폼 이동] GET /board/write (로그인 필요)

	@GetMapping("/write")
	public String writeForm(HttpSession session) {

//    로그인 여부 확인
		if (session.getAttribute("loginMember") == null) {
			return "redirect:/member/login";
		}
//    로그인이 된 상태라면 폼 화면으로 이동
		return "board/write";

	}

//  [게시글 작성 처리] POST /board/write
	@PostMapping("/write")
	public String write(BoardDTO boardDTO, HttpSession session) {
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login";
		}

		boardDTO.setMemberId(loginMember.getMemberId());
		boardService.insertBoard(boardDTO);

		return "redirect:/board/list";
	}

//[상세보기] GET  /board/detail/1

	@GetMapping("/detail/{boardId}")
	public String detail(@PathVariable("boardId") int boardId, Model model, HttpSession session) {

//   상세보기를 열때마다 조회수 + 1
		boardService.incrementHit(boardId);
		BoardDTO board = boardService.getBoardId(boardId);
		if (board == null) {
			return "redirect:/board/list";
		}
		model.addAttribute("board", board);

		List<CommentDTO> commentList = commentService.getCommentsByBoardId(boardId);
		model.addAttribute("commentList", commentList);
		
//		로그인 회원 정보
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if(loginMember != null) {
			model.addAttribute("loginMemberId", loginMember.getMemberId());
			model.addAttribute("loginMemberLoginId", loginMember.getMemberLoginId());
		}
		
		
		return "board/detail";
	}

//수정 GET /board/update/1
	@GetMapping("/update/{boardId}")
	public String updateForm(@PathVariable("boardId") int boardId, HttpSession session, Model model) {
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}

		BoardDTO board = boardService.getBoardId(boardId);
		if (board == null) {
			return "redirect:/board/list";
		}
		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail/" + boardId;
		}

// 수정 폼으로 이동
		model.addAttribute("board", board);
		return "board/update";
	}


//[수정 처리] POST /board/update/{boardId}
	@PostMapping("/update/{boardId}")
	public String update(@PathVariable("boardId") int boardId, BoardDTO boardDTO, HttpSession session) {
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}

		BoardDTO board = boardService.getBoardId(boardId);
		if (board == null) {
			return "redirect:/board/list";
		}
		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail/" + boardId;
		}
//  게시판 수정에 필요한 게시판 번호를 DTO에 담아 전달
		boardDTO.setBoardId(boardId);
		boardService.updateBoard(boardDTO);
		return "redirect:/board/detail/" + boardId;
	}

//----------------------------------
//[게시글 삭제] POST /board/delete/1 (로그인 + 작성자만)

	@PostMapping("/delete/{boardId}")
	public String delete(@PathVariable("boardId") int boardId, HttpSession session) {
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

// 1) 로그인 확인
		if (loginMember == null) {
			return "redirect:/member/login";
		}

// 2) 작성자 확인
		BoardDTO board = boardService.getBoardId(boardId);
		if (board == null) {
			return "redirect:/board/list";
		}
		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail/" + boardId;
		}

// 3) 삭제 실행
		boardService.deleteBoard(boardId);
		return "redirect:/board/list";

	}

}

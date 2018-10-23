package com.taehun.boardChat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taehun.boardChat.dto.Comment;
import com.taehun.boardChat.dto.Post;
import com.taehun.boardChat.service.CommentService;
import com.taehun.boardChat.service.PostService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private int postSize = 10;	// 한번에 보여질 게시글 개수
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	// 게시판 목록 조회 페이지
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "all", required = false) String category,
			@RequestParam(defaultValue = "all", required = false) String option,
			@RequestParam(defaultValue = "", required = false) String search, Model model
			) throws Exception {

		/*******/
		/* 검색 */		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("page", page);
		param.put("category", category);
		param.put("option", option);
		param.put("search", search);
		
		/* 페이징 처리 */
		int postCount = postService.count(param);
		int pageCount = postCount / postSize;		// 페이징 개수
		if(postCount % postSize != 0)
			pageCount++;
		
		int topPostNum = postSize * (page - 1);

		param.put("topPostNum", topPostNum);
		param.put("bottomPostNum", postSize);
		/* 페이징 처리 끝 */
		
		List<Post> postList = postService.displayList(param);
		List<Post> noticeList = postService.displayNoticeList();
		/* 검색 끝 */
		/********/
		
		/* 각 Post 별 Comment 개수 추가 */
		for(int i = 0; i < postList.size(); i++) {
			int commentCount = commentService.count(postList.get(i).getId());
			postList.get(i).setCommentCount(commentCount);
		}
		
		for(int i = 0; i < noticeList.size(); i++) {
			int commentCount = commentService.count(noticeList.get(i).getId());
			noticeList.get(i).setCommentCount(commentCount);
		}
		/* 각 Post 별 Comment 개수 추가 끝*/
		
		model.addAttribute("postList", postList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("postCount", postCount);
		model.addAttribute("pageCount", pageCount);
		
		/* 검색 옵션들 클라이언트에게 전송*/
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		model.addAttribute("option", option);
		model.addAttribute("search", search);
		/* 검색 옵션들 클라이언트에게 전송 끝*/
		
		return "list";
	}
	
	// 게시글 세부 조회 페이지
	@RequestMapping("/view")
	public String view(@RequestParam int id, Model model) throws Exception {
		
		// content 줄바꿈 작업
		Post post = postService.displayDetail(id);
		post.setContents(post.getContent().split("\\r?\\n"));
		
		model.addAttribute("post", post);
		
		return "view";
	}
	
	// 게시글 작성 페이지
	@RequestMapping("/write")
	public String write() throws Exception {
		
		return "write";
	}
	
	// 게시글 추가 작업
	@RequestMapping(value = "/post/create.process", method = RequestMethod.POST)
	public String postCreate(Post post) throws Exception {
		
		postService.create(post);
		
		return "redirect:/board/list";
	}
	
	// 게시글 수정 페이지
	@RequestMapping("/modify")
	public String modify(@RequestParam int id, Model model) throws Exception {
		
		model.addAttribute("post", postService.displayDetail(id));
		
		return "modify";
	}
	
	// 게시글 수정 작업
	@RequestMapping(value = "/post/update.process", method = RequestMethod.POST)
	public String postUpdate(Post post) throws Exception {
		
		postService.update(post);
			
		return "redirect:/board/list";
	}
	
	// 게시글 삭제 페이지
	@RequestMapping("/delete")
	public String delete(@RequestParam int id) throws Exception {
		
		return "delete";
	}

	
	// 게시글 삭제 작업
	@RequestMapping("/post/delete.process")
	public String postDelete(@RequestParam int id) throws Exception {
		
		postService.remove(id);
		
		return "redirect:/board/list";
	}
	
	/*
	 * 코멘트
	 */
	
	// 댓글 조회 작업
	@RequestMapping("/comment/select.process")
	@ResponseBody
	public List<Comment> commentList(@RequestParam int post_id, Model model) throws Exception {
		
		List<Comment> commentList = commentService.displayList(post_id);
		
		for (int i = 0; i < commentList.size(); i++) {
			commentList.get(i).setContents(commentList.get(i).getContent().split("\\r?\\n"));
		}
		
		return commentList;
	}
	
	// 댓글 추가 작업
	@RequestMapping(value = "/comment/create.process", method = RequestMethod.POST)
	@ResponseBody
	public void commentCreate(Comment comment) throws Exception {
		
		System.out.println("들어옴");
		commentService.create(comment);

	}
	
	// 댓글 수정 작업
	@RequestMapping(value = "/comment/update.process", method = RequestMethod.POST)
	@ResponseBody
	public void commentUpdate(Comment comment) throws Exception {
		
		commentService.update(comment);
	}
	
	// 댓글 삭제 작업
	@RequestMapping("/comment/delete.process")
	@ResponseBody
	public void commentDelete(@RequestParam int id) throws Exception {
		
		commentService.remove(id);
	}
	
}

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

	private int postSize = 10;	// �ѹ��� ������ �Խñ� ����
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	// �Խ��� ��� ��ȸ ������
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "all", required = false) String category,
			@RequestParam(defaultValue = "all", required = false) String option,
			@RequestParam(defaultValue = "", required = false) String search, Model model
			) throws Exception {

		/*******/
		/* �˻� */		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("page", page);
		param.put("category", category);
		param.put("option", option);
		param.put("search", search);
		
		/* ����¡ ó�� */
		int postCount = postService.count(param);
		int pageCount = postCount / postSize;		// ����¡ ����
		if(postCount % postSize != 0)
			pageCount++;
		
		int topPostNum = postSize * (page - 1);

		param.put("topPostNum", topPostNum);
		param.put("bottomPostNum", postSize);
		/* ����¡ ó�� �� */
		
		List<Post> postList = postService.displayList(param);
		List<Post> noticeList = postService.displayNoticeList();
		/* �˻� �� */
		/********/
		
		/* �� Post �� Comment ���� �߰� */
		for(int i = 0; i < postList.size(); i++) {
			int commentCount = commentService.count(postList.get(i).getId());
			postList.get(i).setCommentCount(commentCount);
		}
		
		for(int i = 0; i < noticeList.size(); i++) {
			int commentCount = commentService.count(noticeList.get(i).getId());
			noticeList.get(i).setCommentCount(commentCount);
		}
		/* �� Post �� Comment ���� �߰� ��*/
		
		model.addAttribute("postList", postList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("postCount", postCount);
		model.addAttribute("pageCount", pageCount);
		
		/* �˻� �ɼǵ� Ŭ���̾�Ʈ���� ����*/
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		model.addAttribute("option", option);
		model.addAttribute("search", search);
		/* �˻� �ɼǵ� Ŭ���̾�Ʈ���� ���� ��*/
		
		return "list";
	}
	
	// �Խñ� ���� ��ȸ ������
	@RequestMapping("/view")
	public String view(@RequestParam int id, Model model) throws Exception {
		
		// content �ٹٲ� �۾�
		Post post = postService.displayDetail(id);
		post.setContents(post.getContent().split("\\r?\\n"));
		
		model.addAttribute("post", post);
		
		return "view";
	}
	
	// �Խñ� �ۼ� ������
	@RequestMapping("/write")
	public String write() throws Exception {
		
		return "write";
	}
	
	// �Խñ� �߰� �۾�
	@RequestMapping(value = "/post/create.process", method = RequestMethod.POST)
	public String postCreate(Post post) throws Exception {
		
		postService.create(post);
		
		return "redirect:/board/list";
	}
	
	// �Խñ� ���� ������
	@RequestMapping("/modify")
	public String modify(@RequestParam int id, Model model) throws Exception {
		
		model.addAttribute("post", postService.displayDetail(id));
		
		return "modify";
	}
	
	// �Խñ� ���� �۾�
	@RequestMapping(value = "/post/update.process", method = RequestMethod.POST)
	public String postUpdate(Post post) throws Exception {
		
		postService.update(post);
			
		return "redirect:/board/list";
	}
	
	// �Խñ� ���� ������
	@RequestMapping("/delete")
	public String delete(@RequestParam int id) throws Exception {
		
		return "delete";
	}

	
	// �Խñ� ���� �۾�
	@RequestMapping("/post/delete.process")
	public String postDelete(@RequestParam int id) throws Exception {
		
		postService.remove(id);
		
		return "redirect:/board/list";
	}
	
	/*
	 * �ڸ�Ʈ
	 */
	
	// ��� ��ȸ �۾�
	@RequestMapping("/comment/select.process")
	@ResponseBody
	public List<Comment> commentList(@RequestParam int post_id, Model model) throws Exception {
		
		List<Comment> commentList = commentService.displayList(post_id);
		
		for (int i = 0; i < commentList.size(); i++) {
			commentList.get(i).setContents(commentList.get(i).getContent().split("\\r?\\n"));
		}
		
		return commentList;
	}
	
	// ��� �߰� �۾�
	@RequestMapping(value = "/comment/create.process", method = RequestMethod.POST)
	@ResponseBody
	public void commentCreate(Comment comment) throws Exception {
		
		System.out.println("����");
		commentService.create(comment);

	}
	
	// ��� ���� �۾�
	@RequestMapping(value = "/comment/update.process", method = RequestMethod.POST)
	@ResponseBody
	public void commentUpdate(Comment comment) throws Exception {
		
		commentService.update(comment);
	}
	
	// ��� ���� �۾�
	@RequestMapping("/comment/delete.process")
	@ResponseBody
	public void commentDelete(@RequestParam int id) throws Exception {
		
		commentService.remove(id);
	}
	
}

package com.taehun.boardChat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taehun.boardChat.dto.Comment;
import com.taehun.boardChat.mapper.CommentMapper;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	public void create(Comment comment) throws Exception {
		commentMapper.insert(comment);
	}
	
	public void remove(int id) throws Exception {
		commentMapper.delete(id);
	}
	
	public void update(Comment comment) throws Exception {
		commentMapper.update(comment);
	}
	
	public List<Comment> displayList(int post_id) throws Exception {
		return commentMapper.select(post_id);
	}
	
	public int count(int post_id) throws Exception {
		return commentMapper.count(post_id);
	}
	
}

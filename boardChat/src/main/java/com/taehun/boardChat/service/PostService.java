package com.taehun.boardChat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taehun.boardChat.dto.Post;
import com.taehun.boardChat.mapper.PostMapper;

@Service
public class PostService {

	@Autowired
	private PostMapper postMapper;
	
	public void create(Post post) throws Exception {
		postMapper.insert(post);
	}
	
	public void remove(int id) throws Exception {
		postMapper.delete(id);
	}
	
	public void update(Post post) throws Exception {
		postMapper.update(post);
	}
	
	public int count(Map<String, Object> param) throws Exception {
		return postMapper.count(param);
	}
	
	public List<Post> displayNoticeList() throws Exception {
		return postMapper.selectNotice();
	}
	
	public List<Post> displayList(Map<String, Object> param) throws Exception {
		return postMapper.select(param);
	}
	
	public Post displayDetail(int id) throws Exception {
		return postMapper.selectDetail(id);
	}
}

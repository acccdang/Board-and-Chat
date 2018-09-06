package com.taehun.boardChat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.taehun.boardChat.dto.Comment;

@Mapper
public interface CommentMapper {
	
	public void insert(Comment comment) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public void update(Comment comment) throws Exception;
	
	public List<Comment> select(int post_id) throws Exception;
	
	public int count(int post_id) throws Exception;
}

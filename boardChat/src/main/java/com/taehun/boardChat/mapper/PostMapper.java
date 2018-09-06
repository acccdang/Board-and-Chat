package com.taehun.boardChat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.taehun.boardChat.dto.Post;

@Mapper
public interface PostMapper {

	public void insert(Post post) throws Exception;

	public void delete(int id) throws Exception;

	public void update(Post post) throws Exception;

	public int count(Map<String, Object> param) throws Exception;
	
	public List<Post> selectNotice() throws Exception;
	
	public List<Post> select(Map<String, Object> param) throws Exception;

	public Post selectDetail(int id) throws Exception;
}

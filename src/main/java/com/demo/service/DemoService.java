package com.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.demo.model.Posts;
import com.demo.utils.HttpConnectionUtil;
import com.demo.utils.StringUtil;

@Component
public class DemoService {

	@SuppressWarnings("unchecked")
	public List<Posts> getPosts(String id) {
		List<Posts> postList = new ArrayList<Posts>();
		
		String url = "https://jsonplaceholder.typicode.com/posts";
		if (StringUtil.isNumeric(id))
		{
			url = url.concat("/").concat(id);
		}
		
		try {
			Object response = HttpConnectionUtil.sendJsonGetRequest(url, null, null, Posts.class);
		
			if (response instanceof ArrayList)
			{
				List<Map<?, ?>> responseList = (List<Map<?,?>>)response;
				responseList.forEach(thisPost ->{
					Posts post = new Posts((Integer)thisPost.get("userId"), (Integer)thisPost.get("id"), (String)thisPost.get("title"), (String)thisPost.get("body"));
					postList.add(post);
				});
			}
			else if (response instanceof Posts)
			{
				postList.add((Posts) response);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return postList;
	}
	
	public Posts insertPost(Posts newPost)
	{
		Posts createdPost = null;
		
		String url = "http://jsonplaceholder.typicode.com/posts";
		try {
			createdPost = HttpConnectionUtil.sendJsonPostRequest(url, null, newPost, Posts.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return createdPost;
	}
}

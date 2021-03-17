package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.controller.request.PostRequest;
import com.demo.controller.response.CommonResponse;
import com.demo.controller.response.ErrorResponse;
import com.demo.exception.BusinessException;
import com.demo.model.Posts;
import com.demo.service.DemoService;

@RestController
@RequestMapping("api/")
public class PostsController {

	@Autowired
	DemoService demoService;

	@RequestMapping(value="/posts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPosts(HttpServletRequest request) {
		CommonResponse response = new CommonResponse();

		try {
			List<Posts> list = demoService.getPosts(null);
			response.setDatas(list);
			response.setCount(list.size());
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse error = null;
			if (e instanceof BusinessException) {
				error = new ErrorResponse(((BusinessException) e).getCode(), e.getMessage());
				response.setError(error);
			}
			else
				error = new ErrorResponse("9999", e.getMessage());
			response.setStatus(1);
		}

		return response;
	}	
	
	@RequestMapping(value="/posts/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPosts(HttpServletRequest request, @PathVariable(value = "id")String id) {
		CommonResponse response = new CommonResponse();

		try {
			List<Posts> list = demoService.getPosts(id);
			response.setDatas(list);
			response.setCount(list.size());
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse error = null;
			if (e instanceof BusinessException) {
				error = new ErrorResponse(((BusinessException) e).getCode(), e.getMessage());
				response.setError(error);
			}
			else
				error = new ErrorResponse("9999", e.getMessage());
			response.setStatus(1);
		}

		return response;
	}	
	
	@RequestMapping(value="/posts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getPosts(HttpServletRequest request, @Valid @RequestBody PostRequest postRequest) {
		CommonResponse response = new CommonResponse();

		try {
			Posts newPost = new Posts(postRequest.getUserId(), postRequest.getId(), postRequest.getTitle(), postRequest.getBody());
			
			Posts createdPost = demoService.insertPost(newPost);
			response.setDatas(createdPost);

		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse error = null;
			if (e instanceof BusinessException) {
				error = new ErrorResponse(((BusinessException) e).getCode(), e.getMessage());
				response.setError(error);
			}
			else
				error = new ErrorResponse("9999", e.getMessage());
			response.setStatus(1);
		}

		return response;
	}	
}

package com.demo.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	
	@NotNull
	@Min(value = 0)
	int userId;
	
	@NotNull
	@Min(value = 0)
	int id;
	
	@NotBlank
	String title;

	@NotBlank
	String body;
}

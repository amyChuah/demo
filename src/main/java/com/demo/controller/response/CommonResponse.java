package com.demo.controller.response;

import lombok.Data;

@Data
public class CommonResponse {

    private int status = 0; 
    private ErrorResponse error;
    private Object datas;
    private int count = 0;
}

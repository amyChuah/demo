package com.demo.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2901913655709831242L;

    private String message;

    private String code;

    private String detail;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(String code, String message, String detail) {
        super(message);
        this.message = message;
        this.code = code;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}

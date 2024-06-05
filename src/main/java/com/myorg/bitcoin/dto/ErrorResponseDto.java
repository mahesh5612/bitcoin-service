package com.myorg.bitcoin.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDto {

	private HttpStatus errorCode;

	private String message;

	public ErrorResponseDto() {
	}

	public ErrorResponseDto(HttpStatus errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

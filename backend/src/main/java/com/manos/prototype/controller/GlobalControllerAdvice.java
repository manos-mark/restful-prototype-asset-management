package com.manos.prototype.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.manos.prototype.dto.ApplicationExceptionDto;
import com.manos.prototype.exception.ApplicationException;
import com.manos.prototype.exception.EntityAlreadyExistException;
import com.manos.prototype.exception.EntityNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApplicationExceptionDto handleException(ApplicationException e) {
		ApplicationExceptionDto dto = new ApplicationExceptionDto();

		dto.setError(e.getClass().getSimpleName());
		dto.setMessage(e.getMessage());
		dto.setTimeStamp(System.currentTimeMillis());

		return dto;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApplicationExceptionDto handleException(EntityNotFoundException e) {
		ApplicationExceptionDto dto = new ApplicationExceptionDto();
		
		dto.setError(e.getClass().getSimpleName());
		dto.setMessage(e.getMessage());
		dto.setTimeStamp(System.currentTimeMillis());
		
		return dto;
	}
	
	@ExceptionHandler(EntityAlreadyExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApplicationExceptionDto handleException(EntityAlreadyExistException e) {
		ApplicationExceptionDto dto = new ApplicationExceptionDto();

		dto.setError(e.getClass().getSimpleName());
		dto.setMessage(e.getMessage());
		dto.setTimeStamp(System.currentTimeMillis());

		return dto;
	}

//	@ExceptionHandler //badRequestException
//	public ResponseEntity<ApplicationExceptionDto> handleException(Exception e) {
//		ApplicationExceptionDto dto = new ApplicationExceptionDto();
//
//		dto.setError(e.getClass().getSimpleName());
//		dto.setMessage(e.getMessage());
//		dto.setTimeStamp(System.currentTimeMillis());
//
//		return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
//	}
}

package com.manos.prototype.controller;

import java.util.Set;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.manos.prototype.dto.ApplicationExceptionDto;
import com.manos.prototype.exception.ApplicationException;
import com.manos.prototype.exception.EntityAlreadyExistsException;
import com.manos.prototype.exception.EntityNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleResourceNotFoundException(ConstraintViolationException e) {
         Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
         StringBuilder strBuilder = new StringBuilder();
         for (ConstraintViolation<?> violation : violations ) {
              strBuilder.append(violation.getMessage() + "\n");
         }
         return strBuilder.toString();
    }

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
	
	@ExceptionHandler(NoResultException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApplicationExceptionDto handleException(NoResultException e) {
		ApplicationExceptionDto dto = new ApplicationExceptionDto();
		
		dto.setError(e.getClass().getSimpleName());
		dto.setMessage(e.getMessage());
		dto.setTimeStamp(System.currentTimeMillis());
		
		return dto;
	}
	
	@ExceptionHandler(EntityAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApplicationExceptionDto handleException(EntityAlreadyExistsException e) {
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

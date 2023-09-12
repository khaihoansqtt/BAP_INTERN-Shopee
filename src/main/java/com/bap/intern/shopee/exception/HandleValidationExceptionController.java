package com.bap.intern.shopee.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bap.intern.shopee.dto.exceptionRes.InvalidParamRes;

@ControllerAdvice
@Order(1)
public class HandleValidationExceptionController extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
											HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errorDetails = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errorDetails.put(fieldName, message);
		});
		return new ResponseEntity<>(new InvalidParamRes(errorDetails), HttpStatus.BAD_REQUEST);
	}
}

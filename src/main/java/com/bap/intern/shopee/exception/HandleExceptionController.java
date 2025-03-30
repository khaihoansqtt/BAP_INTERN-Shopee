package com.bap.intern.shopee.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.exception.customException.CategoryNotExistedException;
import com.bap.intern.shopee.exception.customException.ExistedEmailException;
import com.bap.intern.shopee.exception.customException.ImportProductFromFileException;
import com.bap.intern.shopee.exception.customException.ProductNotExistedException;
import com.bap.intern.shopee.exception.customException.UnauthenticatedException;
import com.bap.intern.shopee.exception.customException.UserException;

import io.jsonwebtoken.io.IOException;

@ControllerAdvice
@Order(2)
public class HandleExceptionController {

	// Xử lý lỗi khi chưa đăng nhập mà đỏi hỏi
	@ExceptionHandler
	public ResponseEntity<BaseRes> handleUnauthenticatedException(UnauthenticatedException e) {
		BaseRes res = new BaseRes();
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setMessage(e.getMessage());
		return new ResponseEntity<BaseRes>(res, HttpStatus.UNAUTHORIZED);
	}
	
	// Xử lý các ngoại lệ do bad request
	@ExceptionHandler({ExistedEmailException.class, UserException.class,
						CategoryNotExistedException.class, ProductNotExistedException.class})
	public ResponseEntity<BaseRes> handleExistedEmailException(RuntimeException e) {
		BaseRes res = new BaseRes();
		res.setStatus(HttpStatus.BAD_REQUEST.value());
		res.setMessage(e.getMessage());
		return new ResponseEntity<BaseRes>(res, HttpStatus.BAD_REQUEST);
	}

	// Xử lý lỗi ImportProductFromFile
	@ExceptionHandler({ImportProductFromFileException.class})
	public ResponseEntity<BaseRes> handleServerException(IOException e) {
		BaseRes res = new BaseRes();
		res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		res.setMessage(e.getMessage());
		return new ResponseEntity<BaseRes>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	 Xử lý lỗi chung
//	@ExceptionHandler
//	public ResponseEntity<BaseRes> handleGeneralException(Exception e) {
//		BaseRes res = new BaseRes();
//		res.setStatus(HttpStatus.BAD_REQUEST.value());
//		res.setMessage(e.getMessage());
//		return new ResponseEntity<BaseRes>(res, HttpStatus.BAD_REQUEST);
//	}
}

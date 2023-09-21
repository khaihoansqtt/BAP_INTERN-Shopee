package com.bap.intern.shopee.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.exception.customException.CategoryNotExistedException;
import com.bap.intern.shopee.exception.customException.ExistedEmailException;
import com.bap.intern.shopee.exception.customException.ProductNotExistedException;
import com.bap.intern.shopee.exception.customException.ServerException;
import com.bap.intern.shopee.exception.customException.UnauthenticatedException;
import com.bap.intern.shopee.exception.customException.UserException;

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

	// Xử lý lỗi do server
//	@ExceptionHandler
//	public ResponseEntity<CommonRes> handleServerException(ServerException e) {
//		CommonRes res = new CommonRes();
//		res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		res.setMessage(e.getMessage());
//		return new ResponseEntity<CommonRes>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	// Xử lý lỗi chung
//	@ExceptionHandler
//	public ResponseEntity<CommonRes> handleGeneralException(Exception e) {
//		CommonRes res = new CommonRes();
//		res.setStatus(HttpStatus.BAD_REQUEST.value());
//		res.setMessage(e.getMessage());
//		return new ResponseEntity<CommonRes>(res, HttpStatus.BAD_REQUEST);
//	}
}
